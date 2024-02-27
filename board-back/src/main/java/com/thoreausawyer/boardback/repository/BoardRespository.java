package com.thoreausawyer.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.BoardEntity;

@Repository
public interface BoardRespository extends JpaRepository<BoardEntity,Integer>{

    
}
