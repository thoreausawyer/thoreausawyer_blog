package com.thoreausawyer.boardback.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thoreausawyer.boardback.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor //필수 생성자를 필수 멤버 변수에 생성자를 만들어줄 수 있다.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    //생성자를 통해서 IOC 할 수 있도록 DI하는 것이다.
    private final JwtProvider jwtProvider;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {

            String token = parseBearerToken(request);

            // token이 null이면 바로 다음 필터로 넘겨버림
            if(token == null){
                filterChain.doFilter(request, response);
                return;
            }
    
            //토큰에서 email을 꺼내옴
            String email = jwtProvider.validate(token);
    
            // null이면 바로 또 다음 필터로 넘겨버림(signkey가 안 맞거나, 토큰 기간 만료)
            if(email == null){
                filterChain.doFilter(request, response);
                return;
            }
    
            //여기까지 잘 왔다면,
            //유저 패스워드 어센티케이션 토큰이라고 하는 객체를 생성함
            //사용자의 이름과 비밀번호, 권한을 포함할 수 있는 객체를 만듦
            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);
            //그럼 이 객체에 정보가 담긴 것이다. 이 정보를 request에 세부정보를 설정한다(WebAuthenticationDetailsSource()라는 메서드로).
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
            //컨텍스트에 등록(빈 켄텍스트를 만들고 -> )
            SecurityContext securittyContext = SecurityContextHolder.createEmptyContext();
            // 비어있는 컨텍스트에 authenticationToken을 추가 )
            securittyContext.setAuthentication(authenticationToken);
    
            //채워 넣은 컨텍스트를 외부에서 직접 사용할 수 있도록 폴더에 담기
            SecurityContextHolder.setContext(securittyContext);

        } catch (Exception exception) {
            exception.printStackTrace();
        }   

        //다음 필터로 넘기기
        filterChain.doFilter(request, response);

    }

    //request에서 header가져와서 header의 authorization key 찾고,
    //거기서 다시 bearer 인증인지 확인하는 메서드
    private String parseBearerToken(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");

        //authorization 필드를 가지고 있는가 확인 작업.
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        // authorization value에다가 bearer가 맞는지 확인
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        // 7번 인덱스부터 꺼내오면, 그 부분이 토큰 부분이다
        String token = authorization.substring(7);
        return token;

    }

}
