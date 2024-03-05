package com.thoreausawyer.boardback.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.common.ResponseCode;
import com.thoreausawyer.boardback.common.ResponseMessage;
import com.thoreausawyer.boardback.dto.object.BoardListItem;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetSearchBoardListResponseDto extends ResponseDto{

    private List<BoardListItem> searchList;

    private GetSearchBoardListResponseDto(List<BoardListViewEntity> boardListViewEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BoardListItem.getList(boardListViewEntities);
    }
    
    public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities){
        GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
