package com.thoreausawyer.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.UserEntity;

@Repository //재화역전을 통하기 위해
public interface UserRepository extends JpaRepository<UserEntity,String>{

    //하나라도 존재한다면 True를 반환해준다
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    boolean existsByTelNumber(String telNumber);

    //쿼리메서드라고 하고, 메서드명을 규칙적으로 작업을 하면, select문 sql을 자동으로 JPA가 만들어준다.
    //email은 UNIQUE기 때문에 반드시 1개 또는 0개를 반환한다. 그래서 리스트로 받을 필요가 없다.
    UserEntity findByEmail(String email); 


}
