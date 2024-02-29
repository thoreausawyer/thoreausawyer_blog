package com.thoreausawyer.boardback.dto.object;

import java.util.List;
import java.util.ArrayList;
import com.thoreausawyer.boardback.repository.resultSet.GetFavoriteListResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor@AllArgsConstructor
public class FavoriteListItem {
  
  private String email;
  private String nickname;
  private String profileImage;

  public FavoriteListItem(GetFavoriteListResultSet resultSet){
    this.email = resultSet.getEmail();
    this.nickname = resultSet.getNickname();
    this.profileImage = resultSet.getProfileImage();
  }


  // 이거 다시 생각해보기, 카피만들기?
  public static List<FavoriteListItem> copyList(List<GetFavoriteListResultSet> resultSets){
      List<FavoriteListItem> list = new ArrayList<>();
      for (GetFavoriteListResultSet resultSet: resultSets){
        FavoriteListItem favoriteListItem = new FavoriteListItem(resultSet);
        list.add(favoriteListItem);
      }
      return list;
  }

}
