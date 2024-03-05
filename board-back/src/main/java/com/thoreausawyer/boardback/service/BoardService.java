package com.thoreausawyer.boardback.service;

import org.springframework.http.ResponseEntity;

import com.thoreausawyer.boardback.dto.response.board.GetBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetCommentListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetLatestBoardListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.thoreausawyer.boardback.dto.request.board.PatchBoardRequestDto;
import com.thoreausawyer.boardback.dto.request.board.PostBoardRequestDto;
import com.thoreausawyer.boardback.dto.request.board.PostCommentRequestDto;
import com.thoreausawyer.boardback.dto.response.board.PostBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PutFavoriteResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PatchBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostCommentResponseDto;
import com.thoreausawyer.boardback.dto.response.board.DeleteBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetSearchBoardListResponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber); //pathValuable로 보든넘버를 받아서 내보내줌.
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber); //pathValuable로 보든넘버를 받아서 내보내줌.
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(); // 매개변수 없음
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList(); // 매개변수 없음
    ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord); 

    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavoite(Integer boardNumber, String email);
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);

    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);



}
