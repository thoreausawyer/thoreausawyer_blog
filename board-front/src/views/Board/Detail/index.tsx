import React, { useEffect, useState } from "react";
import "./style.css";
import FavoriteItem from "components/FavoriteItem";
import { CommentListItem, FavoriteListItem } from "types/interface";
import { commentListMock, favoriteListMock } from "mocks";
import CommnetItem from "components/CommentItem";
import Pagination from "components/Pagination";

import defaultProfileImage from "assets/image/white-flower-tulip.png";

//          component: 게시물 상세 화면 컴포넌트          //
export default function BoardDetail() {
  
  //          component: 게시물 상세 상단 컴포넌트          //
  const BaordDetailTop = () => {

    //          state: more 버튼 상태         //
    const[showMore,setShowMore] = useState<boolean>(false);

    const onMoreButtonClickHandler = () =>{
      setShowMore(!showMore);
    }

    //          render: 게시물 상세 상단 화면 컴포넌트 랜더링          //
      return (
        <div id="board-detail-top">
          <div className="board-detail-top-header">
            <div className="board-detail-title">{"안녕하세요 소로소여 블로그 첫 포스트입니다."}</div>
            <div className="board-detail-top-sub-box">
              <div className="board-detail-write-info-box">
                <div className="board-detail-writer-profile-image" style={{backgroundImage: `url(${defaultProfileImage})`}}></div>
                <div className="board-detail-writer-nickname">{"소로소여"}</div>
                <div className="board-detail-info-divider">{"|"}</div>
                <div className="board-detail-write-date">{"2024.02.29"}</div>
              </div>
              <div className="icon-button">
                <div className="icon more-icon" onClick={onMoreButtonClickHandler}></div>
              </div>
              {showMore && 
              <div className="board-detail-more-box">
                <div className="board-detail-update-button">{"수정"}</div>
                <div className="divider"></div>
                <div className="board-detail-delete-button">{"삭제"}</div>
              </div>
              }
            </div>
          </div>
          <div className="divider"></div>
          <div className="board-detail-top-main">
            <div className="board-detail-main-text">{"안녕하세요, 처음 뵙겠습니다. 즐거운 시간이 되었으면 좋겠습니다."}</div>
            <img className="board-detail-main-image" src='https://mblogthumb-phinf.pstatic.net/MjAyMjAyMDdfMjEy/MDAxNjQ0MTk0Mzk2MzY3.WAeeVCu2V3vqEz_98aWMOjK2RUKI_yHYbuZxrokf-0Ug.sV3LNWlROCJTkeS14PMu2UBl5zTkwK70aKX8B1w2oKQg.JPEG.41minit/1643900851960.jpg?type=w800'/>
          </div>
        </div>
      )
    }

    //          component: 게시물 상세 하단 컴포넌트          //
    const BoardDetailBottom = () => {

      const [favoriteList, setFavoriteList] = useState<FavoriteListItem[]>([]);
      const [commentList, setCommentList] = useState<CommentListItem[]>([]);

      useEffect(()=>{
        setFavoriteList(favoriteListMock);
        setCommentList(commentListMock);
      },[]);

      

      //          render: 게시물 상세 하단 화면 컴포넌트 랜더링          //
        return (
          <div className="board-detail-bottom">
              <div className="board-detail-bottom-button-box">
                <div className="board-detail-bottom-button-group">
                  <div className="icon-button">
                    <div className="icon favorite-fill-icon"></div>
                  </div>
                  <div className="board-detail-bottom-button-text">{`좋아요 ${12}`}</div>
                  <div className="icon-button">
                      <div className="icon up-light-icon"></div>
                  </div>
                </div>
                <div className="board-detail-bottom-button-group">
                  <div className="icon-button">
                    <div className="icon comment-fill-icon"></div>
                  </div>
                  <div className="board-detail-bottom-button-text">{`댓글 ${12}`}</div>
                  <div className="icon-button">
                      <div className="icon up-light-icon"></div>
                  </div>
                </div>
              </div>
              <div className="board-detail-bottom-favorite-box">
                <div className="board-detail-bottom-favorite-container">
                  <div className="board-detail-bottom-favorite-title">{"좋아요 "}<span className="enphasis">{12}</span></div>
                  <div className="board-detail-bottom-favorite-contents">
                    {favoriteList.map(item => <FavoriteItem favoriteListItem={item} /> ) }
                  </div>
                </div>
              </div>
              <div className="board-detail-bottom-comment-box">
                <div className="board-detail-bottom-comment-container">
                  <div className="board-detail-bottom-comment-title">{'댓글 '}<span className="emphasis">{12}</span></div>
                  <div className="board-detail-bottom-comment-list-container">
                    {commentList.map(item => <CommnetItem commnetListItem={item} /> ) }
                  </div>
                </div>                
                <div className="divider"></div>                
                <div className="board-detail-bottom-comment-pagination-box">
                  <Pagination />
                </div>                
                <div className="board-detail-bottom-comment-input-container">
                  <div className="board-detail-bottom-comment-input-container">
                    <textarea className="board-detail-bottom-comment-textarea" placeholder="댓글을 작성해주세요."/>
                    <div className="board-detail-bottom-comment-button-box">
                      <div className="disable-button">{'댓글달기'}</div>
                    </div>
                  </div>
                </div>                
              </div>
          </div>
        )
    }

  
  //          render: 게시물 상세 화면 컴포넌트 랜더링          //
  return (
  <div id="board-detail-wrapper">
    <div className="board-detail-container">
      <BaordDetailTop/>
      <BoardDetailBottom/>
    </div>
  </div>
  )
}


