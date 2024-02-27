package com.thoreausawyer.boardback.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thoreausawyer.boardback.dto.response.ResponseDto;

@RestControllerAdvice //이게 뭘까? 
public class BadRequestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class}) //이 2개의 경우에 보내준다.
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception){
        return ResponseDto.validationFailed(); //validationFaild를 내보내준다. 어떤 경우에??
    }
    
}
