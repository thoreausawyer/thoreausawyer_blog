// package com.thoreausawyer.boardback.awsconfig;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.env.Environment;

// import com.amazonaws.auth.AWSStaticCredentialsProvider;
// import com.amazonaws.auth.BasicAWSCredentials;
// import com.amazonaws.regions.Regions;
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;

// @Configuration
// public class S3Config {

//     private final Environment environment;

//     public S3Config(Environment environment) {
//         this.environment = environment;
//     }
   
//     @Bean
//     public AmazonS3 amazonS3Client() {
//         String accessKey = environment.getProperty("AWS_ACCESS_KEY_ID");
//         String secretKey = environment.getProperty("AWS_SECRET_ACCESS_KEY");

//         BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

//         return AmazonS3ClientBuilder
//                 .standard()
//                 .withRegion(Regions.AP_NORTHEAST_2)
//                 .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                 .build();
//     }
// }
