package com.thoreausawyer.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.BoardEntity;
import com.thoreausawyer.boardback.repository.resultSet.GetBoardResultSet;

@Repository
public interface BoardRespository extends JpaRepository<BoardEntity,Integer>{

    BoardEntity findByBoardNumber(Integer boardNumber);

    // Join릏 통해 데이터를 가져와야하는 것은(하나의 엔터티로 둘을 커버할 수 없기에), 실제 SQL쿼리문을 작성해서 테이터를 가져온다.
    
    @Query(
        value = 
        "SELECT " +
        "B.board_number AS boardNumber, " +
        "B.title AS title, " +
        "B.content AS content, " +
        "B.write_datetime AS writeDatetime, " +
        "B.writer_email AS writerEmail, " +
        "U.nickname AS writerNickname, " +
        "U.profile_image AS writerProfile_Image " +
        "FROM board AS B " +
        "INNER JOIN user AS U " +
        "ON B.writer_email = U.email " +
        "WHERE board_number = ?1 ", // < ?1 >은 첫번째 매개변수값을 이곳으로 넣겠다고 지정하는것
        nativeQuery=true
    ) 
    GetBoardResultSet getBoard(Integer boardNumber);
}
