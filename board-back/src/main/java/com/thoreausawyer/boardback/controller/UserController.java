package com.thoreausawyer.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoreausawyer.boardback.dto.request.board.PatchBoardRequestDto;
import com.thoreausawyer.boardback.dto.request.user.PatchNicknameRequestDto;
import com.thoreausawyer.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.thoreausawyer.boardback.dto.response.user.GetSignInUserResponseDto;
import com.thoreausawyer.boardback.dto.response.user.GetUserResponseDto;
import com.thoreausawyer.boardback.dto.response.user.PatchNicknameResponseDto;
import com.thoreausawyer.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.thoreausawyer.boardback.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    // 유저 정보
    @GetMapping("{email}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
        @PathVariable("email") String email
    ){
        //로그인한 사용자 정보를 불러냄
        ResponseEntity<? super GetUserResponseDto> response = userService.getUser(email);
        return response;
    }

    // 유저 로그인
    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(
        //인증처리를 하고나서 인증처리한 사람이 누군지를 받기위한 어노테이션 (JwtAuthenticationFilter의 context에 넣어둔 email을 꺼내오는)
        @AuthenticationPrincipal String email
    ){
        //로그인한 사용자 정보를 불러냄
        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
        return response;
    }

    @PatchMapping("nickname")
    public ResponseEntity<? super PatchNicknameResponseDto> patchNicknam(
        @RequestBody @Valid PatchNicknameRequestDto requestBody,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PatchNicknameResponseDto> response = userService.patchNickname(requestBody, email);
        return response;
    }
    @PatchMapping("profile-image")
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(
        @RequestBody @Valid PatchProfileImageRequestDto requestBody,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PatchProfileImageResponseDto> response = userService.patchProfileImage(requestBody, email);
        return response;
    }

    
}
