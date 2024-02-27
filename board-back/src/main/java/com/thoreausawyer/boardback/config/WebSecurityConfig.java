package com.thoreausawyer.boardback.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.thoreausawyer.boardback.filter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration 
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  
  @Bean
  protected CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.addExposedHeader("Authorization"); //없어도 작동됨 // 어떤분은 이렇게 해도 된다고 해서 추가해놓았습니다.

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;

  }
  


  @Bean
  public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
    
    //내가 수정했던 방법 된다!! 스프링부트 3.2.3 최신버전
    http.cors(cors -> cors
                  .configurationSource(corsConfigurationSource())
                )
                .csrf((csrf) -> csrf.disable())
                .httpBasic((basic) -> basic.disable())
                .sessionManagement((management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(request -> request
                  .requestMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/api/v1/board/**", "/api/v1/user/*").permitAll()
                  .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();


    //서타몽
    //스프링부트 3.2.0 버전
    // System.out.println("----------------------------------");
    // System.out.println("----SecurityFilterChain 시작----");
    // http
    // .cors(cors -> cors
    // .configurationSource(corsConfigurationSource())
    // );
    // http
    // .csrf(CsrfConfigurer::disable)
    // .httpBasic(HttpBasicConfigurer::disable)
    // .sessionManagement(sessionManagement -> sessionManagement
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    // )
    // .authorizeHttpRequests(request -> request
    // .requestMatchers("/", "/api/v1/auth/**", "/api/v1/search/**", "/file/**").permitAll()
    // .requestMatchers(HttpMethod.GET, "/api/v1/board/**", "/api/v1/user/*").permitAll()
    // .anyRequest().authenticated()
    // )
    // .exceptionHandling(exceptionHandling -> exceptionHandling
    // .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
    // )
    // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    
    // System.out.println("---------SecurityFilterChain 리턴되나???---------");

    // return http.build();

  }

}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\":\"AF\",\"message\": \"Authorization Failed\"}");
      }
  
  

}
