package com.thoreausawyer.boardback.service;

import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.dto.request.auth.SignInRequestDto;
import com.thoreausawyer.boardback.dto.request.auth.SignUpRequestDto;
import com.thoreausawyer.boardback.dto.response.auth.SignInResponseDto;
import com.thoreausawyer.boardback.dto.response.auth.SignUpResponseDto;

//비지니스 로직처리
public interface AuthService {
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

}
