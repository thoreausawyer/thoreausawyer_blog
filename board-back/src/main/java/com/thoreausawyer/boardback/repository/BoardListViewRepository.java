package com.thoreausawyer.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.BoardListViewEntity;

@Repository
public interface BoardListViewRepository extends JpaRepository<BoardListViewEntity,Integer>{

    List<BoardListViewEntity> findByOrderByWriteDatetimeDesc(); // 날짜기준 오름차순
    List<BoardListViewEntity> findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(String writeDatetime);
    
    // contains가 포함하고 있느냐, 검색어에 주로 쓰임
    List<BoardListViewEntity> findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(String title, String content);

    // 특정 유저 리스트
    List<BoardListViewEntity> findByWriterEmailOrderByWriteDatetimeDesc(String writerEmail);
}
