package com.thoreausawyer.boardback.common;

public interface ResponseMessage {

  //인터페이스는 무조건 static final로 지정을 해야한다. 인터페이스이기 때문에 정적인, 상수인 변수여야한다. --그래서 지워도됨, static final로 인식함. 그런데 왜? 

  // HTTP Status 200 = 성공
  String SUCCESS = "Success.";

  // HTTP Status 400 = 요청 실패
  String VALIDATION_FAILED = "Validation failed.";
  String DUPLICATE_EMAIL = "Duplicate email.";
  String DUPLICATE_NICKNAME = "Duplicate nickname.";
  String DUPLICATE_TEL_NUMBER = "Duplicate tel number.";
  String NOT_EXISTED_USER = "This user does not exist.";
  String NOT_EXISTED_BOARD = "This board does not exist.";

  // HTTP Status 401 = 권한 없음
  String SIGN_IN_FAIL = "Login information mismatch.";
  String AUTHORIZATION_FAIL = "Authorization Failed.";

  // HTTP Status 403 = 접근 금지
  String NO_PERMISSION = "Do not have permission.";

  // HTTP Status 500 = 서버 내부 오류
  String DATABASE_ERROR = "Database error.";
} 
