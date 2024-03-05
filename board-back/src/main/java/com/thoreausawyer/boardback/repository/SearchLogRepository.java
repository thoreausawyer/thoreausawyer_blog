package com.thoreausawyer.boardback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thoreausawyer.boardback.entity.SearchLogEntity;
import com.thoreausawyer.boardback.repository.resultSet.GetPopularListResultSet;
import com.thoreausawyer.boardback.repository.resultSet.GetRelationListResultSet;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Integer>{
    
    // GROUP BY를 쓰고 있기 때문에 직접 쿼리문을 작성해야 한다.
    @Query(
        value=
        "SELECT search_word as searchWord, count(search_word) AS count " +
        "FROM search_log " +
        "WHERE relation IS FALSE " +
        "GROUP BY search_word " +
        "ORDER BY count DESC " +
        "LIMIT 15;",
        nativeQuery=true
        )
        
    // SearchLogEntity에 search_log는 있는데, count가 없다 -> resultSet을 만들어서 가져와야한다.
    List<GetPopularListResultSet> getPopularList();

    @Query(
        value=
        "SELECT relation_word as searchWord, count(relation_word) AS count " +
        "FROM search_log " +
        "WHERE search_word = ?1 " +
        "AND relation_word IS NOT NULL " +
        "GROUP BY relation_word " +
        "ORDER BY count DESC " +
        "LIMIT 15 ",
        nativeQuery=true
    )

    List<GetRelationListResultSet> getRelationList(String searchWord);
}
