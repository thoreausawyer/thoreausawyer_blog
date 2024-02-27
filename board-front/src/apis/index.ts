//비워 놓으면 에러가 난다. 일단 export를 빈 것으로라도 해놓는다.
// export const tmp = "";

import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";
import { SignInResponseDto, SignUpResponseDto } from "./response/auth";
import { ResponseDto } from "./response";
import { GetSignInUserResponseDto } from "./response/user";

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

console.log(SIGN_IN_URL())
            
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

const GET_SIGN_IN_USER_URL = () => `${API_DOMAIN}/user`;

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