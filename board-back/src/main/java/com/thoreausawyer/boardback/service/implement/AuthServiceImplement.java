package com.thoreausawyer.boardback.service.implement;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thoreausawyer.boardback.dto.request.auth.SignInRequestDto;
import com.thoreausawyer.boardback.dto.request.auth.SignUpRequestDto;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.dto.response.auth.SignInResponseDto;
import com.thoreausawyer.boardback.dto.response.auth.SignUpResponseDto;
import com.thoreausawyer.boardback.entity.UserEntity;
import com.thoreausawyer.boardback.provider.JwtProvider;
import com.thoreausawyer.boardback.repository.UserRepository;
import com.thoreausawyer.boardback.service.AuthService;

import lombok.RequiredArgsConstructor;

//어플리케이션의 구현체
@Service //재화 역전위한
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService  {

    //데이터베이스에 접근하는 방법이 3가지 정도 있다. 필드,생성자(스프링측 추천),세터메서드 방법 그중 1개 택.

    // 1. 필드 방법:비어있는 상태가 만들어질 수 있다. 
    // @RequiredArgsConstructor와 함께 final로 만들면, 알아서 생성자를 만들어 준다.
    @Autowired //알아서 의존성 주입됨
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //의존성을 내부에서 집어넣을 거라서 final로 지정 X

    // 2. 생성자 방법:비어있는 상태가 만들어 질 수가 없다(인스턴스를 만드려면 반드시 생성자가 필요하니까).
    // 스프링 권장 방법
    // @Autowired //Autowired도 안 붙여도 된다. 알아서 해줌.
    // public AuthServiceImplement(UserRepository userRepository){
    //     this.userRepository = userRepository;
    // }

    // 3. 세터 메서드 방법:비어있는 상태가 만들어질 수 있다.
    // @Autowired
    // public void setUserRespository(UserRepository userRepository){
    //     this.userRepository = userRepository;
    // }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {
            
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if(existedEmail) return SignUpResponseDto.duplicateEmail();
            
            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return SignUpResponseDto.duplicateNickname();
            
            String telNumber =  dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if(existedTelNumber) return SignUpResponseDto.duplicateTelNumber();
            
            //패스워드 암호화 
            //패스워드르 dto에서 꺼내옴
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            //빌더패턴을 많이들 쓴다.  하지만 이것이 더 깔끔한 코드라고 생각한다.
            UserEntity userEntity = new UserEntity(dto); //UserEntity의 매개변수에 dto를 집어넣고 그에대한 생성자(UserEntity.java에 생성자를 this.~~로 만든다)를 만들어야한다.
            userRepository.save(userEntity);//데이터베이스에 저장까지 가능하다.

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();

    }

    @Override                                       // 1. 사용자가 입력한 Dto에서
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        
        // 토큰을 필요로 한다.
        String token = null;

        try {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("----------------- 2 -------------------");
            System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
            System.out.println("---------------------AuthServiceImplement signIn 시작----");
            // 2. 사용자가 입력한 패스워드를 가져온다
            String email = dto.getEmail();
            System.out.println("email : " + email);
            // 3. UserEntity를 userRepository에서 찾을 것이다.
            UserEntity userEntity = userRepository.findByEmail(email);
            // 4. 못 찾으면 실패 메서드 반환
            if(userEntity == null) return SignInResponseDto.signInFailed();
            
            //찾으면 비밀번호 차례
            // 5. 사용자가 입력한 패스워드를 가져온다
            String password = dto.getPassword();
            // 6. UserEntity(저장되어 있는) 패스워드를 가져온다.
            String encodedPassword = userEntity.getPassword();
            System.out.println("password : " + password);
            System.out.println("encodedPassword : " + encodedPassword);
            System.out.println("passwordEncoder : " + passwordEncoder.encode(encodedPassword));
            // 7. 사용자가 입력한 패스워드를 암호화해서, 저장되어 있는 암호화 되어 있는 것과 비교해본다.
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            // 8. 일치 하지 않으면 실패 메서드 반환
            if(!isMatched) return SignInResponseDto.signInFailed();

            // 9. 성공하면 입장권인, 토큰을 만들어야 한다.
            token = jwtProvider.create(email);
            System.out.println("-----------------------------------------------------------------------");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("----------------- 3 -------------------");
        System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
        System.out.println("---------------------SignInResponseDto token 시작 ----");
        System.out.println(token.toString());
        System.out.println("--------------------- SignInResponseDto.success(token) token 발급된 것----");
        System.out.println(SignInResponseDto.success(token));
        System.out.println("-----------------------------------------------------------------------");
            // 10. 만들어진 토큰을 반환한다.
        return SignInResponseDto.success(token);
    }   

   
}
