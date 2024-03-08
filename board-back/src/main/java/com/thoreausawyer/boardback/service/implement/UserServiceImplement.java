package com.thoreausawyer.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thoreausawyer.boardback.dto.request.user.PatchNicknameRequestDto;
import com.thoreausawyer.boardback.dto.request.user.PatchProfileImageRequestDto;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.dto.response.user.GetSignInUserResponseDto;
import com.thoreausawyer.boardback.dto.response.user.GetUserResponseDto;
import com.thoreausawyer.boardback.dto.response.user.PatchNicknameResponseDto;
import com.thoreausawyer.boardback.dto.response.user.PatchProfileImageResponseDto;
import com.thoreausawyer.boardback.entity.UserEntity;
import com.thoreausawyer.boardback.repository.UserRepository;
import com.thoreausawyer.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {

        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return GetUserResponseDto.notExistUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);
    }

    // 닉네임 수정
    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email) {
        
        try {
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return PatchNicknameResponseDto.notExistUser();

            String getNickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(email);
            if (existedNickname)  return PatchNicknameResponseDto.duplicatedNickname();

            userEntity.setNickname(getNickname);
            userRepository.save(userEntity);
            
        } catch (Exception Exception) {
            Exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    // 프로필 이미지 수정
    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String email) {
        try {

            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return PatchNicknameResponseDto.notExistUser();

            String getProfileImage = dto.getProfileImage();
            userEntity.setProfilImage(getProfileImage);
            userRepository.save(userEntity);
            
        } catch (Exception Exception) {
            Exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }
    
}
