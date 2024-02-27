package com.thoreausawyer.boardback.service.implement;
import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thoreausawyer.boardback.service.FileService;

@Service
public class FileServiceImplement implements FileService{

    @Value("${file.path}") //스프링프레임워크 어노테이션
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;

    @Override
    public String upload(MultipartFile file) {

        if (file.isEmpty()) return null;

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자명 가져오기.
        String uuid = UUID.randomUUID().toString(); //임시 랜덤한 uuid를 만들어줌 // java.util에 있는 UUID
        String saveFileName = uuid + extension; // uuid + 확장자
        String savePath = filePath + saveFileName;

        try {
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        
        String url = fileUrl + saveFileName;
        return url;

    }

    @Override
    public Resource getImage(String fileName) {

        Resource resource = null;

        try {
            resource = new UrlResource("file:" + filePath + fileName);            
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return resource;
    }
    
}
