package com.thoreausawyer.boardback.service.implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thoreausawyer.boardback.dto.request.board.PostBoardRequestDto;
import com.thoreausawyer.boardback.dto.request.board.PostCommentRequestDto;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetCommentListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostBoardResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostCommentResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PutFavoriteResponseDto;
import com.thoreausawyer.boardback.dto.response.board.IncreaseViewCountResponseDto;
import com.thoreausawyer.boardback.entity.BoardEntity;
import com.thoreausawyer.boardback.entity.CommentEntity;
import com.thoreausawyer.boardback.entity.FavoriteEntity;
import com.thoreausawyer.boardback.entity.ImageEntity;
import com.thoreausawyer.boardback.repository.BoardRespository;
import com.thoreausawyer.boardback.repository.CommentRepository;
import com.thoreausawyer.boardback.repository.FavoriteRepository;
import com.thoreausawyer.boardback.repository.ImageRepository;
import com.thoreausawyer.boardback.repository.UserRepository;
import com.thoreausawyer.boardback.repository.resultSet.GetBoardResultSet;
import com.thoreausawyer.boardback.repository.resultSet.GetCommentListResultSet;
import com.thoreausawyer.boardback.repository.resultSet.GetFavoriteListResultSet;
import com.thoreausawyer.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {
    
    private final BoardRespository boardRespository;
    private  final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
    
        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRespository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetFavoriteListResponseDto.success(resultSets);
    
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {

        List<GetCommentListResultSet> resultSets = new ArrayList<>();

        try {

            boolean existedBoard = boardRespository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRepository.getCommentList(boardNumber);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
    }


    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        
        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            
            resultSet = boardRespository.getBoard(boardNumber);
            if (resultSet == null) return GetBoardResponseDto.noExistBoard();
            
            imageEntities = imageRepository.findByBoardNumber(boardNumber);

            // 카운터가 5씩 증가하는 거 방지 
            // 조회수, JPA ORM기법을 그대로 쓸 수 있도록 작성한 문법
            // BoardEntity boardEntity = boardRespository.findByBoardNumber(boardNumber);
            // boardEntity.increaseViewCount();
            // boardRespository.save(boardEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(resultSet, imageEntities);
    }


    @Override
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

        try {
            //존재하는 이메일인지 체크
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email); // 이곳에서 builder를 통해 생성해도 되지만, 개인의 취향으로 dto, email을 모두 넘겨버리고,
            boardRespository.save(boardEntity);                     //  BoardEntity에서 dto, email을 받는 생성자를 만들어서 처리.
            
            // 게시물을 만들면 나오는 boardNumber가지고, 다시 boardImage리스트를 만들어 저장한다.
            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            // 리스트 반복 돌리기
            for (String image: boardImageList){
                ImageEntity imageEntity = new ImageEntity(boardNumber, image); // ImageEntity도 생성자를 만들어준다
                imageEntities.add(imageEntity);
            }
            
            imageRepository.saveAll(imageEntities); // 바로 이미지 entity를 바로 save해도 되는 그러면 한번에 데이터베이스 접근하는 작업이 많아지기에, saveAll로 한번에 하는게 좋다.

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostBoardResponseDto.success();
	}

    
    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {

        try {

            BoardEntity boardEntity = boardRespository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PostCommentResponseDto.noExistBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PostCommentResponseDto.noExistUser();

            // 넘겨줄 매개변수 Entity가서 생성자로 만들어주기, 서타몽 깔끔한 방법
            CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRespository.save(boardEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavoite(Integer boardNumber, String email) {
    
        try {

            // 이런 작업을 하는 이유 -> jwt.io  디버거에서 jwt를 막 만들어서 들어오거나, 들어왔을 적에 favorite테이블에는 제약조건이 걸려있다.
            //                         존재하지 않는 유저가 등록을 하려고 하면, 막히게 하려고 사용.
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PutFavoriteResponseDto.noExistUser();
            
            BoardEntity boardEntity = boardRespository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PutFavoriteResponseDto.noExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            }
            else{
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }
            
            boardRespository.save(boardEntity);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    
        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
        try {
            
            // getBoard에 있는 것을 옮겨옴. viewCount가 5개씩 증가하는 것 방지하기 위해. 독립적인 veiwcount매서드를 만들어줌.
            // 조회수, JPA ORM기법을 그대로 쓸 수 있도록 작성한 문법
            BoardEntity boardEntity = boardRespository.findByBoardNumber(boardNumber);
            if (boardNumber == null) return IncreaseViewCountResponseDto.noExistBoard();

            boardEntity.increaseViewCount();
            boardRespository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return IncreaseViewCountResponseDto.success();
    }






    
    
}
