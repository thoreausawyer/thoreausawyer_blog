package com.thoreausawyer.boardback.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.ImageEntity;

import jakarta.transaction.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{
    
    List<ImageEntity> findByBoardNumber(Integer boardNumber);

    // delete 작업
    @Transactional //하나의 트랜잭션 내에서 실행, 성공하면 commit, 실패하면 rollback
    void deleteByBoardNumber(Integer boardNumber);
}
