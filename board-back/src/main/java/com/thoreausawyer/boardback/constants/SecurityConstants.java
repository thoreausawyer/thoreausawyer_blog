package com.thoreausawyer.boardback.constants;

// 해당 클래스는 Spring Security 및 JWT 관련 상수를 정의한 상수 클래스입니다.
/** 가독성을 좋게하기 위해서
 * HTTP
 *     headers : {
 *			Authorization : Bearer ${jwt}
 * 	   }
 */

public class SecurityConstants {

  //JWT 토큰을 담을 HTTP 요청 헤더 이름
  public static final String TOKEN_HEADER = "Authorization";
  
  //헤더의 접두사
  public static final String TOKEN_PREFIX = "Bearer "; //한칸 띄어쓰기 중요!
  
  //토큰 타입
  public static final String TOKEN_TYPE = "JWT";

  
}


