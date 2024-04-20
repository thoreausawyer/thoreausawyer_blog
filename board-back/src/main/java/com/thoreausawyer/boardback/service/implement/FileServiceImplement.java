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

    
    // 안되면 복구!!
    
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
    
    
    // // S3 설정


    //     // AWS SDK

    // @Service
    // public class S3Service {

    //     @Autowired
    //     private AmazonS3 amazonS3;

    //     public List<String> listObjects(String bucketName) {
    //         ObjectListing objectListing = amazonS3.listObjects(bucketName);
    //         List<String> objectKeys = new ArrayList<>();
    //         for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
    //             objectKeys.add(objectSummary.getKey());
    //         }
    //         return objectKeys;
    //     }
    // }

   
    // private S3Config s3Config;

    // @Autowired
    // public FileServiceImplement(S3Config s3Config) {

    //     this.s3Config = s3Config;
    // }

    // @Value("${cloud.aws.s3.bucket}")
    // private String bucket;

    // @Override
    // public String upload(MultipartFile file) {

    //     if (file.isEmpty()) return null;

    //     String originalFileName = file.getOriginalFilename();
    //     String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자명 가져오기.
    //     String uuid = UUID.randomUUID().toString(); //임시 랜덤한 uuid를 만들어줌 // java.util에 있는 UUID
    //     String saveFileName = uuid + extension; // uuid + 확장자
    //     String savePath = filePath + saveFileName;

    //     try {
    //         file.transferTo(new File(savePath));

    //         // 파일을 S3로 업로드
    //         s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, saveFileName, new File(savePath)).withCannedAcl(CannedAccessControlList.PublicRead));
    //         String s3Url = s3Config.amazonS3Client().getUrl(bucket, saveFileName).toString();
            
    //         // 파일을 로컬에 저장한 후에 삭제
    //         new File(savePath).delete(); // 파일 삭제

    //         return s3Url;

    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return null;
    //     }
    // }
    
    // @Override
    // public Resource getImage(String fileName) {
    //     Resource resource = null;
        
    //     try {
            
    //         // S3에 있는 파일을 가져오기 위해 URL 형식으로 변경
    //         String s3Url = s3Config.amazonS3Client().getUrl(bucket, fileName).toString();
    //         resource = new UrlResource(s3Url);
            
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         return null;
    //     }
    //     return resource;
    // }
    
    // public class S3FileChecker {
    // public static void main(String[] args) {
    //     String bucketName = "your-bucket-name";
    //     String objectKey = "your-object-key"; // 업로드된 파일의 키

    //     // AmazonS3 클라이언트 생성
    //     AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

    //     // S3 객체 가져오기
    //     S3Object object = s3Client.getObject(bucketName, objectKey);

    //     // 객체가 존재하면 성공적으로 업로드된 것입니다.
    //     System.out.println("Object found: " + object);
    //     }
    // }


}
