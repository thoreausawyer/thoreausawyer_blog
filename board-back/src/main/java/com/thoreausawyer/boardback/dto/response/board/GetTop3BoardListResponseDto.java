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
public class GetTop3BoardListResponseDto extends ResponseDto{

    private List<BoardListItem> top3List;

    private GetTop3BoardListResponseDto(List<BoardListViewEntity> boardEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.top3List = BoardListItem.getList(boardEntities); // 그냥 GetList하고, 쿼리문만 만져주면 될 것 같긴 했다.
    }

    public static ResponseEntity<GetTop3BoardListResponseDto> success(List<BoardListViewEntity> boardEntities){
        GetTop3BoardListResponseDto result = new GetTop3BoardListResponseDto(boardEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
