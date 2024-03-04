import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import "./style.css";
import { useBoardStore, userLoginUserStore } from "stores";
import { useNavigate, useParams } from "react-router-dom";
import { MAIN_PATH } from "constant";
import { useCookies } from "react-cookie";
import { getBoardRequest } from "apis";
import { GetBoardResponseDto } from "apis/response/board";
import { ResponseDto } from "apis/response";
import { convertUrlsToFile } from "utils";

//          component: 게시물 수정 화면 컴포넌트          //
export default function BoardWrite() {

  //          state: 제목 영역 요소 참조 상태         //
  const titleRef = useRef<HTMLTextAreaElement | null>(null);
  // 2번
  //          state: 본문 영역 요소 참조 상태         //
  const contentRef = useRef<HTMLTextAreaElement | null>(null);
  //          state: 이미지 입력 요소 참조 상태         //
  const imageInputRef = useRef<HTMLInputElement | null>(null);

  //          state: 게시물 번호 path variable 상태         //
  const { boardNumber } = useParams();

  //          state: 게시물 상태(store이용해서, 전역으로)          //
  const { title, setTitle } = useBoardStore();
  const { content, setContent } = useBoardStore();
  const { boardImageFileList, setBoardImageFileList } = useBoardStore();
  // const { resetBoard } = useBoardStore(); // 사용 안함 // 게시물 수정 작업하고 나면

  //           state: 로그인 유저 상태         //
  const { loginUser } = userLoginUserStore();
  //           state: 쿠키 상태         //
  const [cookies, setCookies] = useCookies();
  //          state: 게시물 이미지 미리보기 URL 상태          //
  const [imageUrls, setImageUrls] = useState<string[]>([]);

  //          function: 네이게이트 함수         //
  const navigate = useNavigate();
  //          function: get board response 처리 함수         //
  const getBoardResponse = (responseBody: GetBoardResponseDto | ResponseDto | null) => {
    if (!responseBody) return;
      const { code } = responseBody;
      if ( code === 'NB') alert('존재하지 않는 게시물입니다.');
      if ( code === 'DBE') alert('데이터베이스 오류입니다.');
      if ( code !== 'SU') {
        navigate(MAIN_PATH());
        return;
      }

      const { title, content, boardImageList, writerEmail } = responseBody as GetBoardResponseDto;
      setTitle(title);
      setContent(content);
      setImageUrls(boardImageList);
      // boardImageList 처리 방법
      // convertUrlsToFile이란 함수를 만들어줌, utils에 폴더에
      // 파일 객체도 관리를 할 수 있다.
      convertUrlsToFile(boardImageList).then(boardImageFileList => setBoardImageFileList(boardImageFileList));

      if (!loginUser || loginUser.email !== writerEmail) {
        navigate(MAIN_PATH());
        return;
      }

      // 스크롤 없애는 방법
      if (!contentRef.current) return;
      contentRef.current.style.height = 'auto'; 
      contentRef.current.style.height = `${contentRef.current.scrollHeight}px`;

  }

  //          event handler: 제목 변경 이벤트 처리          //
  const onTitleChangeHandler = (event: ChangeEvent<HTMLTextAreaElement>) =>{
    const { value } = event.target;
    setTitle(value);

    if (!titleRef.current) return;
    // 스크롤 없애는 방법
    titleRef.current.style.height = 'auto'; 
    titleRef.current.style.height = `${titleRef.current.scrollHeight}px`;
  } 

  //          event handler: 내용 변경 이벤트 처리          //
  const onContentChangeHandler = (evnet: ChangeEvent<HTMLTextAreaElement>) =>{
    const { value } = evnet.target;
    setContent(value);
    
    if (!contentRef.current) return;
    // 스크롤 없애는 방법
    contentRef.current.style.height = 'auto'; 
    contentRef.current.style.height = `${contentRef.current.scrollHeight}px`;
  }

  //          event handler: 이미지 변경 이벤트 처리          //
  const onImageChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files || !event.target.files.length) return;
    const file = event.target.files[0];
    
    // 미리보기용 URL
    const imageUrl = URL.createObjectURL(file); //URL이라는 객체 미리보기 파일을 넣어주면, 임시 URL 뽑아올 수 있다.
    const newImageUrls = imageUrls.map(item => item); // map으로 복사하기
    newImageUrls.push(imageUrl); 
    setImageUrls(newImageUrls);
    
    // 실제 이미지 업로드를 할 URL
    const newBoardImageFileList = boardImageFileList.map(item => item); //복사
    newBoardImageFileList.push(file);
    setBoardImageFileList(newBoardImageFileList);

    // 같은 걸 넣어도 또 들어갈 수 있도록 current.value값을 ''(빈값)으로 만들어줘야 한다.
    if (!imageInputRef.current) return;
    imageInputRef.current.value = '';
  }

  //          event handler: 이미지 업로드 버튼 클릭 이벤트 처리          //
  const onImageUploadClickHandler = () => {
    if (!imageInputRef.current) return;
    imageInputRef.current.click();
  }
  //          event handler: 이미지 닫기 버튼 클릭 이벤트 처리          //
  const onImageCloseButtonClickHandler = (deleteIndex: number) => {
    if (!imageInputRef.current) return;
    imageInputRef.current.value = '';

    const newImageUrls = imageUrls.filter((url, index) => index !== deleteIndex); // 필터를 돌려서 인덱스가 delete인덱스 비교해서 같으면 안꺼내옴.
    setImageUrls(newImageUrls);

    const newBoardImageFileList = boardImageFileList.filter((file, index) => index !== deleteIndex); // 필터를 돌려서 인덱스가 delete인덱스 비교해서 같으면 안꺼내옴.
    setBoardImageFileList(newBoardImageFileList);
  }


  //          effect: 마운트 시 실행할 함수         //
  useEffect(()=>{
    const accessToken = cookies.accessToken;
    if (!accessToken) {
      navigate(MAIN_PATH());
      return
    }
    if (!boardNumber) return;
    getBoardRequest(boardNumber).then(getBoardResponse);
  },[boardNumber]);


  //          render: 게시물 수정 화면 컴포넌트 랜더링         //
  return (

  // 1번
  <div id="board-update-wrapper">
    <div className="board-update-container">
      <div className="board-update-box">
        <div className="board-update-title-box">
          <textarea ref={titleRef} className="board-update-title-textarea" rows={1} placeholder="제목을 작성해주세요." value={title} onChange={onTitleChangeHandler}/>
        </div>
        <div className="divider"></div>
        <div className="board-update-content-box">
          <textarea ref={contentRef} className="board-update-content-textarea" placeholder="본문을 작성해주세요." value={content} onChange={onContentChangeHandler} />
          <div className="icon-button" onClick={onImageUploadClickHandler}>
            <div className="icon image-box-light-icon"></div>
          </div>
          <input ref={imageInputRef} type="file" accept="image/*" style={{display:'none'}} onChange={onImageChangeHandler} />
        </div>
        <div className="board-update-images-box">
          {imageUrls.map((imageUrl,index) => 
          <div className="board-update-image-box">
            <img className="board-update-image" src={imageUrl}/>
            <div className="icon-button image-close" onClick={() => onImageCloseButtonClickHandler(index)}> 
              <div className="icon close-icon"></div>
            </div>
          </div>
          )}
        </div>

      </div>
    </div>
  </div>
  )
}
 