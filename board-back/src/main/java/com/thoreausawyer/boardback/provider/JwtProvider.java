package com.thoreausawyer.boardback.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component //재화역전을 통한 의존성 주입이 가능함
public class JwtProvider {

  //서타몽 방법
  @Value("${secret-key}")
  private String secretKey;

  public String create(String email) {

    //서타몽&박동훈 공통
    Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
    
    //서타몽 3.2.0
    Key Key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    String jwt = Jwts.builder()
        .signWith(Key, SignatureAlgorithm.HS512)
        .setSubject(email)
        .setIssuedAt(new Date())
        .setExpiration(expiredDate)
        .compact();
        
    return jwt;


    // 내 버전 3.2.3 -> 안됨, 다시 해보기
    // String jwt = Jwts.builder()
    //     .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS512)
    //     .subject(email)
    //     .issuedAt(new Date())
    //     .expiration(expiredDate)
    //     .compact();
        
    // return jwt;


    
  }
  
  public String validate(String jwt) {

      //서타몽 버전(스프링부트 3.2.0버전)
      Claims claims = null;
      Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
      
      try {
        claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
        
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
      return claims.getSubject();

      // 내 버전 3.2.3 -> 안됨 다시 해보기
      // try {

      // Jwts.parserBuilder()
      //     .(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
      //     .build()
      //     .parseSignedClaims(jwt);

      // } catch (JwtException e) {
      //   e.printStackTrace();
      //   return null;
      // }

      // return jwt;

  }

}
