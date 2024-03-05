package com.thoreausawyer.boardback.dto.response.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.common.ResponseCode;
import com.thoreausawyer.boardback.common.ResponseMessage;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.repository.resultSet.GetRelationListResultSet;

import lombok.Getter;

@Getter
public class GetRelationListResponseDto extends ResponseDto{

    private List<String> relativeWordList;

    private GetRelationListResponseDto(List<GetRelationListResultSet> resultSets){
        super(ResponseCode.SUCCESS,  ResponseMessage.SUCCESS);

        List<String> relativeWordList = new ArrayList<>();
        for (GetRelationListResultSet resultSet: resultSets){
            String relativeWord =  resultSet.getSearchWord();
            relativeWordList.add(relativeWord);
        }
        this.relativeWordList = relativeWordList;
    }

    // 근데 static을 하는 이유는?
    public static ResponseEntity<GetRelationListResponseDto> success(List<GetRelationListResultSet> resultSets){
        GetRelationListResponseDto result = new GetRelationListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
}
