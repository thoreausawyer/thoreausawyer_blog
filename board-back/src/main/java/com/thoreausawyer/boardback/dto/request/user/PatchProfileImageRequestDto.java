package com.thoreausawyer.boardback.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchProfileImageRequestDto {
    @NotBlank
    private String profileImage;
}
