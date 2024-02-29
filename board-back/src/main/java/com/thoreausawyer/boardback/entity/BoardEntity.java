package com.thoreausawyer.boardback.entity;

import java.util.Date;
import java.time.Instant;
import java.text.SimpleDateFormat;

import com.thoreausawyer.boardback.dto.request.board.PostBoardRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="board")
@Table(name="board")
public class BoardEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //오토크리트먼트설정한 것은 이렇게 처리
    private int boardNumber;
    private String title;
    private String content;
    private String writeDatetime;
    private int favoriteCount;
    private int commentCount;
    private int viewCount;
    private String writerEmail;

    public BoardEntity(PostBoardRequestDto dto, String email){
        // Date를 작업을 해줘야한다.  
        Date now = Date.from(Instant.now()); // 1. 현재시간 Instant로 구해오기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 2. SimpleDateFormat으로 시간 형태를 만든다.
        String writeDateTime = simpleDateFormat.format(now); // 3. simpleDateFormat을 이용해서 writeDateTime을 만든다.
        
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDateTime;
        this.favoriteCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
        this.writerEmail = email;
    }

    public void increaseViewCount(){
        this.viewCount++;
    }

    public void increaseFavoriteCount(){
        this.favoriteCount++;
    }
    
    public void increaseCommentCount(){
        this.commentCount++;
    }
    
    public void decreaseFavoriteCount(){
        this.favoriteCount--;
    }


}
