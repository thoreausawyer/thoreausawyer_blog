package com.thoreausawyer.boardback.domain;

import lombok.Data;

@Data
public class AuthenticationRequest {
  
  private String username;
  private String password;

}
