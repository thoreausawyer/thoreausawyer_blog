package com.thoreausawyer.boardback.service;

import org.springframework.core.io.Resource; //요것을 import
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    String upload(MultipartFile file);
    Resource getImage(String fileName);

}
