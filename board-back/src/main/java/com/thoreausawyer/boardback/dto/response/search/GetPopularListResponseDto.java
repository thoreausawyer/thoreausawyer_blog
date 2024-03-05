package com.thoreausawyer.boardback.dto.response.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.repository.resultSet.GetPopularListResultSet;
import com.thoreausawyer.boardback.common.ResponseCode;
import com.thoreausawyer.boardback.common.ResponseMessage;

import lombok.Getter;

@Getter
public class GetPopularListResponseDto extends ResponseDto  {

    private List<String> popularWordList;

    // 생성자, 기본 순서
    // 어떤 것들을 받아올지 정해줘야함.
    private GetPopularListResponseDto(List<GetPopularListResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
       
        List<String> popularWordList = new ArrayList<>();
        for (GetPopularListResultSet resultSet: resultSets){
            String popularWord = resultSet.getSearchWord();
            popularWordList.add(popularWord);
        }

        this.popularWordList = popularWordList;
    }
     
    public static ResponseEntity<GetPopularListResponseDto> success(List<GetPopularListResultSet> resultSets){
        GetPopularListResponseDto result = new GetPopularListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
}
