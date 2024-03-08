package com.thoreausawyer.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoreausawyer.boardback.dto.request.board.PatchBoardRequestDto;
import com.thoreausawyer.boardback.dto.response.board.PatchBoardResponseDto;
import com.thoreausawyer.boardback.dto.request.board.PostBoardRequestDto;
import com.thoreausawyer.boardback.dto.request.board.PostCommentRequestDto;
import com.thoreausawyer.boardback.dto.response.board.GetBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetCommentListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetTop3BoardListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PutFavoriteResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostCommentResponseDto;
import com.thoreausawyer.boardback.dto.response.board.DeleteBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetUserBoardListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetSearchBoardListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetLatestBoardListResponseDto;

import com.thoreausawyer.boardback.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    // 특정 게시물
    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
        return response;
    }
    // 좋아요 리스트
    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetFavoriteListResponseDto> response = boardService.getFavoriteList(boardNumber);
        return response;
    }
    // 댓글 리스트
    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(boardNumber);
        return response;
    }
    // 조회수 1개씩 올라가기
    @GetMapping("/{boardNumber}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(
        @PathVariable("boardNumber") Integer boardNumber
    ){
        ResponseEntity<? super IncreaseViewCountResponseDto> response = boardService.increaseViewCount(boardNumber);
        return response;
    }
    // 최신 게시물 리스트
    @GetMapping("/latest-list")
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList(){
        ResponseEntity<? super GetLatestBoardListResponseDto> response = boardService.getLatestBoardList();
        return response;
    }
    // 주간 3위 게시물 리스트
    @GetMapping("/top-3")
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList(){
        ResponseEntity<? super GetTop3BoardListResponseDto> response = boardService.getTop3BoardList();
        return response;
    }
    // 검색어 리스트
    @GetMapping(value={"/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}"})// 엔드포인트가 2개일 경우 , 자바에서의 배열인 value={}를 사용.
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
        @PathVariable("searchWord") String searchWord,
        @PathVariable(value="preSearchWord", required=false) String preSearchWord // required = false을 하는 이유는 : 필수로 여기지 않게 위하여, 없을 수 있으니까.
    ){ 
        ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord, preSearchWord); // 넘어감
        return response;
    }
    //특정 유저 리스트
    @GetMapping("user-board-list/{email}")
    public ResponseEntity<? super GetUserBoardListResponseDto> getUserBoardList(
        @PathVariable("email") String email
    ){
        ResponseEntity<? super GetUserBoardListResponseDto> response = boardService.getUserBoardList(email);
        return response;
    }

    // 게시물 등록
    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(
        @RequestBody @Valid PostBoardRequestDto RequestBody,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(RequestBody, email);
        return response;
    }
    // 댓글 등록
    @PostMapping("/{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postCommnet(
        @RequestBody @Valid PostCommentRequestDto requestBody,
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(requestBody, boardNumber, email);
        return response;
    }
    // 좋아요 등록
    @PutMapping("/{boardNumber}/favorite") 
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PutFavoriteResponseDto> response = boardService.putFavoite(boardNumber, email);
        return response;
    }
    // 특정 게시물 수정
    @PatchMapping("/{boardNumber}")
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(
        @RequestBody @Valid PatchBoardRequestDto requsetBody,
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(requsetBody, boardNumber, email);
        return response;
    }
    // 특정 게시물 삭제
    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(
        @PathVariable("boardNumber") Integer boardNumber,
        @AuthenticationPrincipal String email
    ){
        ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(boardNumber, email);
        return response;
    }

}
