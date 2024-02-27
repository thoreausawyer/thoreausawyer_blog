package com.thoreausawyer.boardback.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thoreausawyer.boardback.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    
    private final FileService fileService;

    // 이미지 업로드
    @PostMapping("/upload")
    public String upload(
        @RequestParam("file") MultipartFile file
    ) {
        String url = fileService.upload(file);
        return url;
    }

    // 이미지 불러오기
    @GetMapping(value="{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE} ) // (직접 어떤 녀석을 작업하겠다 ,리스폰스의 타입)
    public Resource getImage( //org.springframework.core.io.Resource;
        @PathVariable("fileName") String fileName
    ){
        Resource resource = fileService.getImage(fileName);
        return resource;
    } 

}

