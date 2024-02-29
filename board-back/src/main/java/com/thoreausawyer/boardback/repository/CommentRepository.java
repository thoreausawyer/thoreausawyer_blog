package com.thoreausawyer.boardback.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.CommentEntity;
import com.thoreausawyer.boardback.repository.resultSet.GetCommentListResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Integer>{
    
    @Query(
        value = 
        "SELECT " +
        "U.nickname AS nickname, " +
        "U.profile_image AS profileImage, " +
        "C.write_datetime AS writeDatetime, " +
        "C.content AS content " +
        "FROM comment AS C " +
        "INNER JOIN user AS U " +
        "ON C.user_email = U.email " +
        "WHERE C.board_number = ?1 " +
        "ORDER BY write_datetime DESC",
        nativeQuery=true
    )

    List<GetCommentListResultSet> getCommentList(Integer boardNumber);
}
