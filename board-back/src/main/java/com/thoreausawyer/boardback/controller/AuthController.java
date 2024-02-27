package com.thoreausawyer.boardback.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoreausawyer.boardback.dto.request.auth.SignInRequestDto;
import com.thoreausawyer.boardback.dto.request.auth.SignUpRequestDto;
import com.thoreausawyer.boardback.dto.response.auth.SignInResponseDto;
import com.thoreausawyer.boardback.dto.response.auth.SignUpResponseDto;
import com.thoreausawyer.boardback.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    //컨트롤러에는 비지니스로직이 적히면 안된다

    @PostMapping("/sign-up")                        //반환하는 것은 signUp이다
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody // SignUpRequestDto를 requestBody란 이름으로 가져옴
    ){  
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("----------------- 1 -------------------");
        System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
        System.out.println("---------------------AuthController /sign-up 시작----");
        System.out.println("requestBody : " + requestBody);
        System.out.println("-----------------------------------------------------------------------");
        
        //signIn에 requestBody를 전달해서, 그 결과를 받아와서 reponse에 담는다.
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("----------------- 4 -------------------");
        System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
        System.out.println("---------------------AuthController /sign-up 시작----");
        System.out.println("response : " + response);
        System.out.println("-----------------------------------------------------------------------");
        return response;
    }
    
    @PostMapping("/sign-in")                        //반환하는 것은 signIn이다
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody //SignInRequestDto를 requestBody란 이름으로 받을 것임.
        ) {
            
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("----------------- 1 -------------------");
            System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
            System.out.println("---------------------AuthController /sign-in requestBody 시작----");
            System.out.println("requestBody : " + requestBody);
            System.out.println("-----------------------------------------------------------------------");
            
            //signIn에 requestBody를 전달해서, 그 결과를 받아와서 reponse에 담는다.
            //(authService에 있는 signIn에 requestBody를 전달해서)
            ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
            
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("----------------- 4 -------------------");
            System.out.println("----------------- " + LocalDateTime.now() + " -------------------");
            System.out.println("---------------------AuthController /sign-in response 시작----");
            System.out.println("response : " + response);
            System.out.println("-----------------------------------------------------------------------");
            return response;
    }


    
}
