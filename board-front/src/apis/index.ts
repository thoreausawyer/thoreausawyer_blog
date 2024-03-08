//비워 놓으면 에러가 난다. 일단 export를 빈 것으로라도 해놓는다.
// export const tmp = "";

import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";
import { SignInResponseDto, SignUpResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import { GetSignInUserResponseDto, GetUserResonseDto, PatchNicknameResponseDto, PatchProfileImageResponseDto } from "./response/user";
import { PatchBoardRequsetDto, PostBoardRequestDto, PostCommentRequestDto } from "./request/board";
import { PostBoardResponseDto, GetBoardResponseDto, IncreaseViewCountResponseDto, GetFavoriteListResponseDto, PutFavoriteResponseDto, PostCommentResponseDto, DeleteBoardResponseDto, PatchBoardResponseDto, GetLatestBoardListResponseDto, GetTop3BoardListResponseDto, GetSearchBoardListResponseDto, GetUserBoardListResponseDto  } from "./response/board";
import GetCommentListResponseDto from "./response/board/get-comment-list.response.dto";
import { GetPopularListResponseDto, GetRelationListResponseDto } from "./response/search";
import { PatchNicknameRequestDto, PatchProfileImageRequestDto } from "./request/user";

const DOMAIN = 'http://localhost:4000';

const API_DOMAIN = `${DOMAIN}/api/v1`;

//자주 쓰이는 인증정보이니 메서드화 해서 계속해서 재사용.
const authorization = (accessToken: string) => {
    return { headers: { Authorization: `Bearer ${accessToken}` } }    
} 

const now = new Date().toLocaleString();

//함수로 만들어 사용
const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;
const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;

                                        // requestBody로 넘겨줄데이터를 받아오는데,
    // 데이터를 받아서 API요청을 보낸다.      // 그 데이터의 타입은 SignInRequestDto 타입이다.
export const signInRequest = async (requestBody: SignInRequestDto) => {
    // 자바스크립트와 타입스크립트는, 동작을 기다리지 않고 비동기로 작동하기 때문에, async로 동기함수로 만들어 주는 것.
    // await로 이 동작을 기다리겠다, 선언하는 것.
    // result에 값이 담기고 진행하겠다.   // post(어떠한 URL에 요청을 보낼 것인지, requestBody에는 무엇을 보낼 것인지->(우리가 외부에서 받아온 requestBody).)
    const result = await axios.post(SIGN_IN_URL(), requestBody)
        .then(response => { // post메서드 결과를 콜백으로, 리스폰스를 받아온다. 
            console.log("----------------- 5 -------------------");
            console.log("지금 시간 : " + now);
            console.log("----signInRequest axios.post 리스폰스 시작----");
            const responseBody: SignInResponseDto = response.data;
            return responseBody;
        })
        //에러가 발생했을 때
        .catch(error => { // post메서드 결과를 콜백으로, 리스폰스를 받아온다. 
            console.log("----------------- 5 -------------------");
            console.log("지금 시간 : " + now);
            console.log("----signInRequest axios.post 에러 시작----");
            if(!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
        
        console.log("----------------- 6 -------------------");
        console.log("---signInRequset -> result 반환----  result :" + result);
        
    return result; //결과를 받아서 내보내 줌.
}

export const signUpRequest = async (requestBody: SignUpRequestDto) => {
    const result = await axios.post(SIGN_UP_URL(), requestBody)
        .then(response => {
            const responseBody: SignUpResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response.data) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });    
    
    return result;
}

// 게시물 상세 불러오기 URL
const GET_BOARD_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}`;
// 최근 게시물 URL
const GET_LATEST_BOARD_LIST_URL = () => `${API_DOMAIN}/board/latest-list`
// Top 3 게시물 URL
const GET_TOP_3_BOARD_LIST_URL = () => `${API_DOMAIN}/board/top-3`
// 
const GET_SEARCH_BOARD_LIST_URL = (searchWord: string, preSearchWord: string | null) =>  `${API_DOMAIN}/board/search-list/${searchWord}${preSearchWord ? '/' + preSearchWord : ''}`;
//
const GET_USER_BOARD_LIST_URL = (email:string) => `${API_DOMAIN}/board/user-board-list/${email}`



// 조회수 하나씩 올리기 URL
const INCREASE_VIEW_COUNT_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}/increase-view-count`;
// 좋아요 리스트 URL
const GET_FAVORITE_LIST_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}/favorite-list`;
// 댓글 리스트 URL
const GET_COMMENT_LIST_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}/comment-list`;
// 게시물 작성 URL
const POST_BOARD_URL = () => `${API_DOMAIN}/board`;
// 댓글 작성 URL
const POST_COMMENT_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}/comment`;
// 게시물 수정 URL
const PATCH_BOARD_URL = (boardNumber: number | string ) => `${API_DOMAIN}/board/${boardNumber}`;
// 좋아요 기능 URL
const PUT_FAVORITE_URL = (boardNumber: number | string) => `${API_DOMAIN}/board/${boardNumber}/favorite`;
// 게시물 삭제 URL
const DELETE_BOARD_URL = (boardNumber: number | string) => `${API_DOMAIN}/board/${boardNumber}`;


export const getBoardRequest = async (boardNumber: number | string ) => {
    const result = await axios.get(GET_BOARD_URL(boardNumber))
        .then(response => {
            const responseBody: GetBoardResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if(!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
    return result;
}

export const getLatestBoardListRequest = async () => {
    const result = await axios.get(GET_LATEST_BOARD_LIST_URL())
    .then(response => {
        const responseBody: GetLatestBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody: ResponseDto = error.response.data;
        return responseBody;
    })
return result;
}

export const getTop3BoardListRequest = async () => {
    const result = await axios.get(GET_TOP_3_BOARD_LIST_URL())
    .then(response => {
        const responseBody: GetTop3BoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody: ResponseDto = error.response.data;
        return responseBody;
    })
return result;
}

export const getSearchBoardListRequest = async (searchWord: string, preSearchWord:string | null) => {
    const result = await axios.get(GET_SEARCH_BOARD_LIST_URL(searchWord,preSearchWord))
    .then(response => {
        const responseBody: GetSearchBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody: ResponseDto = error.response.data;
        return responseBody;
    })
return result;
}

export const getUserBoardListRequest = async (email:string) =>{
    const result = await axios.get(GET_USER_BOARD_LIST_URL(email))
    .then(response => {
        const responseBody: GetUserBoardListResponseDto = response.data;
        return responseBody;
    })
    .catch(error => {
        if(!error.response) return null;
        const responseBody: ResponseDto = error.response.data;
        return responseBody;
    })
return result;
}

export const increaseViewCountRequset = async (boardNumber: number | string) => {
    const result = await axios.get(INCREASE_VIEW_COUNT_URL(boardNumber))
        .then( response => {
            const responseBody: IncreaseViewCountResponseDto = response.data;
            return responseBody;
        })
        .catch( error => {
            if (!error.reponse) return null;
            const responseBody: ResponseDto = error.reponse.data;
            return responseBody;
        })
        return result;
}

export const getFavoriteListRequest = async (boardNumber: number | string) => {
    const result = await axios.get(GET_FAVORITE_LIST_URL(boardNumber))
        .then(response => {
            const responseBody: GetFavoriteListResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.reponse) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
        return result;
}

export const getCommentListRequest = async (boardNumber: number | string) => {
    const result = await axios.get(GET_COMMENT_LIST_URL(boardNumber))
        .then(response => {
            const responseBody: GetCommentListResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.reponse) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
        return result;
}

export const postBoardRequest = async (requestBody: PostBoardRequestDto, accessToken: string) => {
    const result = await axios.post(POST_BOARD_URL(), requestBody, authorization(accessToken))
        .then(response => {
            const responseBody: PostBoardResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const  responseBody: ResponseDto = error.reponse.data;
            return responseBody;
        })
        return result;
}

export const postCommentRequest = async (boardNumber: number | string , requsetBody: PostCommentRequestDto, accessToken: string) => {
    const result = await axios.post(POST_COMMENT_URL(boardNumber), requsetBody, authorization(accessToken) )
        .then(response =>{
            const responseBody: PostCommentResponseDto = response.data;
            return responseBody;
        })
        .catch(error => {
            if (!error.response) return null;
            const responseBody: ResponseDto = error.reponse.data;
            return responseBody;
        })
        return result;
}

export const patchBoardRequset = async (boardNumber: number | string, requsetBody: PatchBoardRequsetDto, accessToken: string) =>{
    const result = await axios.patch(PATCH_BOARD_URL(boardNumber), requsetBody, authorization(accessToken))
            .then(response =>{
                const responseBody: PatchBoardResponseDto = response.data;
                return responseBody;
            })
            .catch(error =>{
                if (!error.response) return null;
                const responseBody: ResponseDto = error.response.data;
                return responseBody;
            })
            return result;
}

export const putFavoriteRequest = async (boardNumber: number | string, accessToken: string) =>{
    const result = await axios.put(PUT_FAVORITE_URL(boardNumber), {}, authorization(accessToken))
        .then(response =>{
            const responseBody: PutFavoriteResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.reponse) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
        return result
}

export const deleteBoardRequest = async (boardNumber: number | string, accessToken: string) => {
        const result = await axios.delete(DELETE_BOARD_URL(boardNumber), authorization(accessToken))
        .then(response =>{
            const responseBody: DeleteBoardResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.reponse) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        })
        return result
}


// 검색

// 인기 검색어 리스트 URL
const GET_POPULAR_LIST_URL = () => `${API_DOMAIN}/search/popular-list`;
const GET_RELATION_LIST_URL = (searchWord: string) => `${API_DOMAIN}/search/${searchWord}/relation-list`;

export const getPopularListRequest = async () => {
    const result = await axios.get(GET_POPULAR_LIST_URL())
        .then(response => {
            const responseBody: GetPopularListResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
    }

    export const getRelationListRequest = async (searchWord: string) => {
        const result = await axios.get(GET_RELATION_LIST_URL(searchWord))
        .then(response => {
            const responseBody: GetRelationListResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
    }



// 로그인
const GET_USER_URL = (email: string) => `${API_DOMAIN}/user/${email}`;
const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user`;
const PATCH_NICKNAME_URL = () => `${API_DOMAIN}/user/nickname`;
const PATCH_PROFILE_IMAGE_URL = () => `${API_DOMAIN}/user/profile-image`;

export const getUserRequest = async (email:string) => {
    const result = await axios.get(GET_USER_URL(email))
        .then(response => {
            const responseBody: GetUserResonseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
}

export const getSignInUserRequset = async (accessToken: string) => {
                                                    // 인증정보를 Authorization에다가 포함시켜서 전달해줘야 한다. 여기에 옵션을 걸어주면 됨.
    const result = await axios.get(GET_SIGN_IN_USER_URL(), authorization(accessToken))
        .then(response => {
            const responseBody: GetSignInUserResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
    }

export const patchNicknameRequset = async(requestBody: PatchNicknameRequestDto, accessToken: string) => {
    const result = await axios.patch(PATCH_NICKNAME_URL(), requestBody, authorization(accessToken))
        .then(response => {
            const responseBody: PatchNicknameResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
}

export const patchProfileImageRequset = async(requestBody: PatchProfileImageRequestDto, accessToken: string) => {
    const result = await axios.patch(PATCH_PROFILE_IMAGE_URL(), requestBody, authorization(accessToken))
        .then(response => {
            const responseBody: PatchProfileImageResponseDto = response.data;
            return responseBody;
        })
        .catch(error =>{
            if (!error.response) return null;
            const responseBody: ResponseDto = error.response.data;
            return responseBody;
        });
        return result;
}

// 파일 업로드
const FILE_DOMAIN = `${DOMAIN}/file`;
const FILE_UPLOAD_URL = () => `${FILE_DOMAIN}/upload`
const multipartFormData = { headers: { 'Content-Type' : 'multipart/form-data' } };

export const fileUploadRequest = async (data: FormData) => {
const result = await axios.post(FILE_UPLOAD_URL(), data, multipartFormData)
    .then(response => {
        const responseBody: string = response.data;
        return responseBody;
    })
    .catch(error => {
        return null;
    })
return result;
}