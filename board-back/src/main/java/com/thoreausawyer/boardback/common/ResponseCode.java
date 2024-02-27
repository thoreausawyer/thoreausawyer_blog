package com.thoreausawyer.boardback.common;

public interface ResponseCode {

  //인터페이스는 무조건 static final로 지정을 해야한다. 인터페이스이기 때문에 정적인, 상수인 변수여야한다. --그래서 지워도됨, static final로 인식함. 그런데 왜? 

  // HTTP Status 200 = 성공
  String SUCCESS = "SU";

  // HTTP Status 400 = 요청 실패
  String VALIDATION_FAILED = "VF";
  String DUPLICATE_EMAIL = "DE";
  String DUPLICATE_NICKNAME = "DN";
  String DUPLICATE_TEL_NUMBER = "DT";
  String NOT_EXISTED_USER = "NU";
  String NOT_EXISTED_BOARD = "NB";

  // HTTP Status 401 = 권한 없음
  String SIGN_IN_FAIL = "SF";
  String AUTHORIZATION_FAIL = "AF";

  // HTTP Status 403 = 접근 금지
  String NO_PERMISSION = "NP";

  // HTTP Status 500 = 서버 내부 오류
  String DATABASE_ERROR = "DBE";
}
