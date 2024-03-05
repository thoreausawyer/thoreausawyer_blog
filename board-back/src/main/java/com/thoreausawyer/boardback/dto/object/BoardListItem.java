package com.thoreausawyer.boardback.dto.object;

import java.util.ArrayList;
import java.util.List;

import com.thoreausawyer.boardback.entity.BoardListViewEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {
  
  private int boardNumber;
  private String title;
  private String content;
  private String boardTitleImage;
  private int favoriteCount;
  private int commentCount;
  private int viewCount;
  private String writeDatetime;
  private String writerNickname;
  private String writerProfileImage;

  public BoardListItem(BoardListViewEntity boardListViewEntity){
    this.boardNumber = boardListViewEntity.getBoardNumber();
    this.title = boardListViewEntity.getTitle();
    this.content = boardListViewEntity.getContent();
    this.boardTitleImage = boardListViewEntity.getTitleImage();
    this.favoriteCount = boardListViewEntity.getFavoriteCount();
    this.commentCount = boardListViewEntity.getCommentCount();
    this.viewCount = boardListViewEntity.getViewCount();
    this.writeDatetime = boardListViewEntity.getWriteDatetime();
    this.writerNickname = boardListViewEntity.getWriterNickname();
    this.writerProfileImage = boardListViewEntity.getWriterProfileImage();
  }
  
  public static List<BoardListItem> getList(List<BoardListViewEntity> boardListViewEntities){
    List<BoardListItem> list = new ArrayList<>();
    for (BoardListViewEntity boardListViewEntity: boardListViewEntities){
      BoardListItem boardListItem = new BoardListItem(boardListViewEntity);
      list.add(boardListItem);
    }
    return list;
  }

  public static List<BoardListItem> getTop3List(List<BoardListViewEntity> boardListViewEntities){
    List<BoardListItem> list = new ArrayList<>();
    for (BoardListViewEntity boardListViewEntity: boardListViewEntities){
      BoardListItem boardListItem = new BoardListItem(boardListViewEntity);
      list.add(boardListItem);
    }
    return list;
  }



}
