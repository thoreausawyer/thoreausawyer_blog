package com.thoreausawyer.boardback.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.FavoriteEntity;
import com.thoreausawyer.boardback.entity.primaryKey.FavoritePk;
import com.thoreausawyer.boardback.repository.resultSet.GetFavoriteListResultSet;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>  {

    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);
    
    // 쿼리를 직접 작성하는 이유는, JOIN 때문이다, JPA에 JOIN을 해결하는 문법이 있지만, 복잡하므로 지금은 이렇게 가볍게 처리함.
    @Query(
        value= 
        "SELECT " + 
        "U.email AS email, " +
        "U.nickname AS nickname, " +
        "U.profile_image AS profileImage " +
        "FROM favorite AS F " +
        "INNER JOIN user AS U " +
        "ON F.user_email = U.email " +
        "WHERE F.board_number = ?1",
        nativeQuery=true 
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);
}
