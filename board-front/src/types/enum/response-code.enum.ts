enum ResponseCode {
  // HTTP Status 200 = 성공
  SUCCESS = "SU",

  // HTTP Status 400 = 요청 실패
  VALIDATION_FAILED = "VF",
  DUPLICATE_EMAIL = "DE",
  DUPLICATE_NICKNAME = "DN",
  DUPLICATE_TEL_NUMBER = "DT",
  NOT_EXISTED_USER = "NU",
  NOT_EXISTED_BOARD = "NB",

  // HTTP Status 401 = 권한 없음
  SIGN_IN_FAIL = "SF",
  AUTHORIZATION_FAIL = "AF",

  // HTTP Status 403 = 접근 금지
  NO_PERMISSION = "NP",

  // HTTP Status 500 = 서버 내부 오류
  DATABASE_ERROR = "DBE",
}

export default ResponseCode;
