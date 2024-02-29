package com.thoreausawyer.boardback.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.thoreausawyer.boardback.dto.request.board.PostCommentRequestDto;

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
@Entity(name="comment")
@Table(name="comment")
public class CommentEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber;
    private String content;
    private String writeDatetime;
    private String userEmail;
    private int boardNumber;

    public CommentEntity(PostCommentRequestDto dto, Integer boardNumber, String email){
        
        // 클래스를 만들어서 불러오기해서 사용해도 된다. 일일이 이렇게 만들어서 사용해도 되고.
        Date now = Date.from(Instant.now()); // 1. 현재시간 Instant로 구해오기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 2. SimpleDateFormat으로 시간 형태를 만든다.
        String writeDateTime = simpleDateFormat.format(now); // 3. simpleDateFormat을 이용해서 writeDateTime을 만든다.
        
        this.content = dto.getContent();
        this.writeDatetime = writeDateTime;
        this.userEmail = email;
        this.boardNumber = boardNumber;
    }

}
