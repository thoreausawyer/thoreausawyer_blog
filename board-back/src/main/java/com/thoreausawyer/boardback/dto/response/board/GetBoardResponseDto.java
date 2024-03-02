package com.thoreausawyer.boardback.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.common.ResponseCode;
import com.thoreausawyer.boardback.common.ResponseMessage;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.entity.ImageEntity;
import com.thoreausawyer.boardback.repository.resultSet.GetBoardResultSet;

import lombok.Getter;

@Getter
public class GetBoardResponseDto extends ResponseDto{
   
    private int boardNumber;
    private String title;
    private String content;
    private List<String> boardImageList;
    private String writeDatetime;
    private String writerEmail;
    private String writerNickname;
    private String writerProfileImage;

    private GetBoardResponseDto(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        List<String> boardImageList = new ArrayList<>();
        for (ImageEntity imageEntity: imageEntities){
            String boardImage = imageEntity.getImage();
            boardImageList.add(boardImage);
        }

        this.boardNumber = resultSet.getBoardNumber();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.boardImageList = boardImageList; // imageEntity의 imageEntities들을 List돌려서 boardImageList를 만든다
        this.writeDatetime = resultSet.getWriteDatetime();
        this.writerEmail = resultSet.getWriterEmail();
        this.writerNickname = resultSet.getWriterNickname();
        this.writerProfileImage = resultSet.getWriterProfileImage();

    }

    public static ResponseEntity<GetBoardResponseDto> success(GetBoardResultSet resultSet, List<ImageEntity> imageEntities){
        GetBoardResponseDto result = new GetBoardResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistBoard(){
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}

