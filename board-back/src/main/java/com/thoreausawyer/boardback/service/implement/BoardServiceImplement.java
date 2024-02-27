package com.thoreausawyer.boardback.service.implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thoreausawyer.boardback.dto.request.board.PostBoardRequestDto;
import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.dto.response.board.PostBoardResponseDto;
import com.thoreausawyer.boardback.entity.BoardEntity;
import com.thoreausawyer.boardback.entity.ImageEntity;
import com.thoreausawyer.boardback.repository.BoardRespository;
import com.thoreausawyer.boardback.repository.ImageRepository;
import com.thoreausawyer.boardback.repository.UserRepository;
import com.thoreausawyer.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {
    
    private  final UserRepository userRepository;
    private final BoardRespository boardRespository;
    private final ImageRepository imageRepository;


    @Override
	public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {

        try {

            //존재하는 이메일인지 체크
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email); // 이곳에서 builder를 통해 생성해도 되지만, 개인의 취향으로 dto, email을 모두 넘겨버리고,
            boardRespository.save(boardEntity);                     //  BoardEntity에서 dto, email을 받는 생성자를 만들어서 처리.
            
            // 게시물을 만들면 나오는 boardNuber가지고, 다시 boardImage리스트를 만들어 저장한다.
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
    
}
