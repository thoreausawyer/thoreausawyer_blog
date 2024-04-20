// package com.thoreausawyer.boardback.awsconfig;

// import com.amazonaws.services.s3.AmazonS3Client;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.DeleteObjectRequest;
// import com.amazonaws.services.s3.model.ObjectMetadata;
// import com.amazonaws.services.s3.model.PutObjectRequest;
// // import com.ittam.web.command.NoticeImgVO; //dto인듯
// import lombok.*;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.server.ResponseStatusException;

// import java.io.IOException;
// import java.io.InputStream;
// import java.net.URL;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// @RequiredArgsConstructor
// @Component
// public class S3Uploader {
    
//     private final AmazonS3Client amazonS3Client;

//     @Value("${cloud.aws.s3.bucket}")
//     private String bucket;

//     public List<NoticeImgVO> uploadImage(List<MultipartFile> multipartFiles) {
//         List<NoticeImgVO> noticeImgVOList = new ArrayList<>();

//         multipartFiles.forEach(file -> {
//             FileName fileNameResult = createFileName(file.getOriginalFilename());
//             String fileName = fileNameResult.getFileName();
//             String uuid = fileNameResult.getUuid();
//             ObjectMetadata objectMetadata = new ObjectMetadata();
//             objectMetadata.setContentLength(file.getSize());
//             objectMetadata.setContentType(file.getContentType());

//             try(InputStream inputStream = file.getInputStream()) {
//                 amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                         .withCannedAcl(CannedAccessControlList.PublicRead));
//             } catch (IOException e) {
//                 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 실패");
//             }

//             URL url = amazonS3Client.getUrl(
//                     bucket,
//                     fileName
//             );

//             noticeImgVOList.add(NoticeImgVO.builder()
//                     .noticeimg_name(file.getOriginalFilename())
//                     .noticeimg_path(url.toString())
//                     .noticeimg_uuid(uuid)
//                     .build());
//         });
//         return noticeImgVOList;
//     }

//     public String uploadImageOne(MultipartFile multipartFile) {

//         String fileName = createFileName(multipartFile.getOriginalFilename()).getFileName();
//         ObjectMetadata objectMetadata = new ObjectMetadata();
//         objectMetadata.setContentLength(multipartFile.getSize());
//         objectMetadata.setContentType(multipartFile.getContentType());

//         try(InputStream inputStream = multipartFile.getInputStream()) {
//             amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                     .withCannedAcl(CannedAccessControlList.PublicRead));
//         } catch (IOException e) {
//             throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 실패");
//         }

//         return fileName;
//     }

//     public void deleteImage(String fileName) {
//         amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
//     }

//     private FileName createFileName(String fileName) {
//         String uuid = UUID.randomUUID().toString();
//         String fileNameResult = uuid.concat(getFileExtension(fileName));
//         return FileName.builder().fileName(fileNameResult).uuid(uuid).build();
//     }

//     private String getFileExtension(String fileName) {
//         try {
//             return fileName.substring(fileName.lastIndexOf("."));
//         } catch (StringIndexOutOfBoundsException e) {
//             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//         }
//     }

//     public String getThumbnailPath(String path) {
//         return amazonS3Client.getUrl(bucket, path).toString();
//     }

//     @Data
//     @AllArgsConstructor
//     @NoArgsConstructor
//     @Builder
//     public static class FileName {
//         private String fileName;
//         private String uuid;
//     }
    
// }
