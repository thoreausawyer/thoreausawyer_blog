package com.thoreausawyer.boardback.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thoreausawyer.boardback.dto.response.ResponseDto;
import com.thoreausawyer.boardback.dto.response.search.GetPopularListResponseDto;
import com.thoreausawyer.boardback.dto.response.search.GetRelationListResponseDto;
import com.thoreausawyer.boardback.repository.SearchLogRepository;
import com.thoreausawyer.boardback.service.SearchService;
import com.thoreausawyer.boardback.repository.resultSet.GetPopularListResultSet;
import com.thoreausawyer.boardback.repository.resultSet.GetRelationListResultSet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImplement implements SearchService {

    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {

        List<GetPopularListResultSet> resultSets = new ArrayList<>();

        try {

            // 결과를 꺼내오기 위해서, SearchLogRepository를 써야함.
            resultSets = searchLogRepository.getPopularList();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetPopularListResponseDto.success(resultSets);

    }

    @Override
    public ResponseEntity<? super GetRelationListResponseDto> getRelationList(String searchWord) {
       
        List<GetRelationListResultSet> resultSets = new ArrayList<>();
       
        try {

            resultSets = searchLogRepository.getRelationList(searchWord);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRelationListResponseDto.success(resultSets);
    }
    
}
