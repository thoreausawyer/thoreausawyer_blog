import React, { useState,KeyboardEvent, useRef, ChangeEvent, useEffect } from "react";
import "./style.css";
import InputBox from "components/InputBox";
import { SignInRequestDto, SignUpRequestDto } from "apis/request/auth";
import { signInRequest, signUpRequest } from "apis";
import { SignInResponseDto, SignUpResponseDto } from "apis/response/auth";
import { ResponseDto } from "apis/response";
import { useCookies } from "react-cookie";
import { MAIN_PATH } from "constant";
import { useNavigate } from "react-router-dom";
import { Address, useDaumPostcodePopup } from "react-daum-postcode";

  //          component: 인증 화면 컴포넌트          //
  export default function Authentication() {
    
    //          state: 화면 상태          //
    // 두 가지의 값(상태)만 가질 수 있도록 만들어줌
    const [view, setView] = useState<'sign-up'|'sign-in'>('sign-in');

    //          state: 쿠키 상태          //
    const [cookies, setCookies] = useCookies();

    //          function: 네이게이트 함수         //
    const navigator = useNavigate();
    
    //          component: sign in card 컴포넌트          //
    const SignInCard = () =>{

      //          state: 이메일 요소 참조 상태          //
      const emailRef = useRef<HTMLInputElement | null> (null);
      //          state: 패스워드 요소 참조 상태          //
      const passwordRef = useRef<HTMLInputElement | null> (null);

      
      //          state: 이메일 상태          //
      const [email, setEmail] = useState<string>('');
      //          state: 패스워드 상태          //
      const [password, setPassword] = useState<string>('');
      //          state: 패스워드 타입 상태          //
      const [passwordType, setPasswordType] = useState<'text'|'password'>('password');
      //          state: 패스워드 버튼 아이콘 상태          //
      const [passwordButtonIcon, setPasswordButtonIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>('eye-light-off-icon');
      //          state: 에러 상태          //
      const [error, setError] = useState<boolean>(false);

      //          function: sign in response 처리함수           //
      const signInResponse = (responseBody: SignInResponseDto | ResponseDto | null) => {
        const now2 = new Date().toLocaleString();

        console.log("----------------- 7 -------------------");
        console.log("지금 시간 : " + now2);
        console.log("---signInResponse 시작----");
        
        if (!responseBody){ // null인 경우, 백엔드의 서버가 안 켜진 경우 거의 null처리 됨.
          // console.log("---네트워크 이상입니다???----");
          alert('네트워크 이상입니다.');
          return;
        }

        const { code } = responseBody;
        // console.log("여기까지도 오나????11111");
        // console.log(code);
        if( code === 'DBE') alert('데이터베이스 오류입니다.');
        if( code === 'SF' || code === 'VF') setError(true);
        if( code !== 'SU') return;
        // console.log("여기까지도 오나????2222");
        //성공적으로 잘일어났다면, 

        // as의 역할
        // responseBody 변수를 SignInResponseDto 타입으로 타입 단언하여 
        // TypeScript 컴파일러에게 해당 객체가 SignInResponseDto의 인스턴스임을 명시적으로 알려준다. 
        //이를 통해 해당 객체를 SignInResponseDto 타입의 속성에 접근할 수 있게 된다.
        const { token, expirationTime } = responseBody as SignInResponseDto; //정확히 SignInResponseDto로 명시해줌
        const now   = new Date().getTime(); // 현재 시간
        //현재 시간에서 3600초(1시간)를 더해줘서 완료시간을 만들어줌
        const expires = new Date(now + expirationTime * 1000); // 만료 시간
        
        // 쿠키에 token과 만료시간, Main페이지의 경로를 넣는다
        console.log("----------------- 8 -------------------");
        console.log("지금 시간 : " + now2);
        console.log("---setCookies 시작----");
        // console.log(token.toString);
        setCookies('accessToken', token, {expires, path: MAIN_PATH()});
        // 그리고 navigator()함수를 이용해서 페이지를 이동시킨다.
        navigator(MAIN_PATH());
      }
      //          event handler: 이메일 변경 이벤트 처리         //
      const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) =>{
        setError(false);
        const {value} = event.target;
        setEmail(value);
      }
      //          event handler: 비밀번호 변경 이벤트 처리         //
      const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) =>{
        setError(false);
        const {value} = event.target;
        setPassword(value);
      }
      //          event handler: 로그인 버튼 클릭 이벤트 처리         //
      const onSignInButtonClickHandler = () => { //요청을 보낼 requestBody를 만들어줌. 사용자가 입력한 값들이 signInRequest(매개변수)로 실행됨.
        const requestBody: SignInRequestDto = { email, password}; //사용자가 입력한 값들이 SignInRequestDto 타입으로 실려서 requestBody에 담긴 후 api 요청됨.
        signInRequest(requestBody).then(signInResponse); //결과가 받아진다면 .then(~~~) signInResponse란 이름으로 작업을 처리하겠다.(위쪽에 signInResponse란 function 생성.)
      }
      //          event handler: 회원가입 링크 클릭 이벤트 처리         //
      const onSignUpButtonClickHandler = () => {
        setView('sign-up');
      }

      //          event handler: 패스워드 버튼 클릭 이벤트 처리         //
      const onPasswordButtonClickHandler = () => {
        // console.log(passwordType);
        if(passwordType === 'text'){
          setPasswordType('password');
          setPasswordButtonIcon('eye-light-off-icon');
        }
        else{
          setPasswordType('text');
          setPasswordButtonIcon('eye-light-on-icon');
        }
      }
      //          event handler: 이메일 인풋 키 다운 이벤트 처리         //
      const onEmailKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) =>{
        if(event.key !== 'Enter') return;
        if(!passwordRef.current) return;
        passwordRef.current.focus();
      }
      //          event handler: 패스워드 키 다운 이벤트 처리         //
      const onPasswordKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) =>{
        if(event.key !== 'Enter') return;
        onSignInButtonClickHandler();//로그인 클릭 버튼이 눌린 것처럼 작동하게
      }
      //          render: sign In card 컴포넌트 랜더링          //
      return (
        <div className="auth-card">
          <div className="auth-card-box">
            <div className="auth-card-top">
              <div className="auth-card-title-box">
                <div className="auth-card-title">{'로그인'}</div>  
              </div>  
              {/* Ref를 할 수 있는 것은, InputBox에 forwardRef로 만들었기 때문에 */}
              <InputBox ref={emailRef} label="이메일 주소" type="text" placeholder="이메일 주소를 입력해주세요." error={error} value={email} onChange={onEmailChangeHandler} onKeyDown={onEmailKeyDownHandler} />
              <InputBox ref={passwordRef} label="패스워드" type={passwordType} placeholder="비밀번호를 입력해주세요." error={error} value={password} onChange={onPasswordChangeHandler} icon={passwordButtonIcon} onButtonClick={onPasswordButtonClickHandler} onKeyDown={onPasswordKeyDownHandler}/>
            </div>  
            <div className="auth-card-bottom">
              {/* 에러박스는 에러가 error가 true일때만 작동해라. */}
              {error && 
              <div className="auth-sign-in-error-box">
                <div className="auth-sign-in-error-message">
                  {'이메일 주소 또는 비밀번호를 잘못 입력했습니다. \n입력하신 내용을 다시 확인해주세요.'}
                </div>
              </div>
              }
              <div className="black-large-full-button" onClick={onSignInButtonClickHandler}>{'로그인'}</div>
              <div className="auth-description-box">
                <div className="auth-description">{'신규 사용자이신가요? '}<span className="auth-description-link" onClick={onSignUpButtonClickHandler}>{'회원가입'}</span></div>
              </div>  
            </div>  
          </div>          
        </div>
      )
    }
    //          component: sign up card 컴포넌트          //
    const SignUpCard = () =>{

      //          state: 이메일 요소 참조 상태         //
      const emailRef = useRef<HTMLInputElement | null>(null);
      //          state: 이메일 요소 참조 상태         //
      const passwordRef = useRef<HTMLInputElement | null>(null);
      //          state: 이메일 요소 참조 상태         //
      const passwordCheckRef = useRef<HTMLInputElement | null>(null);
      //          state: 닉네임 요소 참조 상태         //
      const nicknameRef = useRef<HTMLInputElement | null>(null);
      //          state: 핸드폰 번호 요소 참조 상태         //
      const telNumberRef = useRef<HTMLInputElement | null>(null);
      //          state: 주소 요소 참조 상태         //
      const addressRef = useRef<HTMLInputElement | null>(null);
      //          state: 상세주소 요소 참조 상태         //
      const addressDetailRef = useRef<HTMLInputElement | null>(null);
      //          state: 개인 정보 동의 참조 상태         //
      const [agreedPersonal, setAgreedPersonal] = useState<boolean>(false);

      //          state: 페이지 번호 상태         //
      const [page, setPage] = useState<1|2>(1);

      //          state: 이메일 상태         //
      const [email,setEmail] = useState<string>('');
      //          state: 패스워드 상태         //
      const [password,setPassword] = useState<string>('');
      //          state: 패스워드 확인 상태         //
      const [passwordCheck,setPasswordCheck] = useState<string>('');
      //          state: 닉네임 상태         //
      const [nickname,setNickname] = useState<string>('');
      //          state: 핸드폰 번호 상태         //
      const [telNumber,setTelNumber] = useState<string>('');
      //          state: 주소 상태         //
      const [address, setAddress] = useState<string>('');
      //          state: 상세 주소 상태         //
      const [addressDetail, setAddressDetail] = useState<string>('');

      //          state: 패스워드 타입 상태         //
      const [passwordType, setPasswordType] = useState<'text'|'password'>('password');
      //          state: 패스워드 확인 타입 상태         //
      const [passwordCheckType, setPasswordCheckType] = useState<'text'|'password'>('password');


      //          state: 이메일 에러 상태         //
      const [isEmailError,setEmailError] = useState<boolean>(false);
      //          state: 패스워드 에러 상태         //
      const [isPasswordError,setPasswordError] = useState<boolean>(false);
      //          state: 패스워드 확인 에러 상태         //
      const [isPasswordChecklError,setPasswordCheckError] = useState<boolean>(false);
      //          state: 닉네임 에러 메세지 상태         //
      const [isNicknameError, setNicknameError] = useState<boolean>(false);
      //          state: 핸드폰 번호 에러 메세지 상태         //
      const [isTelNumberError, setTelNumberError] = useState<boolean>(false);
      //          state: 주소 에러 메세지 상태         //
      const [isAddressError, setAddressError] = useState<boolean>(false);
      //          state: 개인 정보 동의 에러 메세지 상태         //
      const [isAgreedPersonalError, setAgreedPersonalError] = useState<boolean>(false);

      //          state: 이메일 에러 메세지 상태         //
      const [emailErrorMessage, setEmailErrorMessage] = useState<string>('');
      //          state: 패스워드 에러 메세지 상태         //
      const [passwordErrorMessage, setPasswordErrorMessage] = useState<string>('');
      //          state: 패스워드 확인 에러 메세지 상태         //
      const [passwordChecklErrorMessage, setPasswordCheckErrorMessage] = useState<string>('');
      //          state: 닉네임 에러 메세지 상태         //
      const [nicknameErrorMessage, setNicknameErrorMessage] = useState<string>('');
      //          state: 핸드폰 번호 에러 메세지 상태         //
      const [TelNuberErrorMessage, setTelNumberErrorMessage] = useState<string>('');
      //          state: 주소 에러 메세지 상태         //
      const [AddressErrorMessage, setAddressErrorMessage] = useState<string>('');


      //          state: 패스워드 버튼 아이콘 상태          //
      const [passwordButtonIcon, setPasswordButtonIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>('eye-light-off-icon');
      //          state: 패스워드 확인 버튼 아이콘 상태          //
      const [passwordCheckButtonIcon, setPasswordCheckButtonIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>('eye-light-off-icon');
      
      //          function: 다음 주소 검색 팝업 오픈 함수         //
      const open = useDaumPostcodePopup(); //다음 포스트 팝업
      
      //          function: sign up response 처리 함수         //
      const signUpResponse = (responseBody: SignUpResponseDto | ResponseDto | null) => {
        if (!responseBody) {
          alert('네트워크 이상입니다.')
          return;
        }
        const { code } = responseBody;
        if ( code === 'DE'){
          setEmailError(true);
          setEmailErrorMessage('중복되는 이메일 주소입니다.');
        }
        if ( code === 'DN'){
          setNicknameError(true);
          setNicknameErrorMessage('중복되는 닉네임입니다.');
        }
        if ( code === 'DT'){
          setTelNumberError(true);
          setTelNumberErrorMessage('중복되는 핸드폰 번호입니다.');
        }
        if ( code === 'VF') alert('모든 값을 입력해주세요.');
        if ( code === 'DE') alert('데이터베이스 오류입니다.');

        if ( code !== "SU") return;

        setView('sign-in');
      }
      //          event handler: 이메일 변경 이벤트 처리          //
      const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setEmail(value);
        setEmailError(false);
        setEmailErrorMessage('')
      };
      //          event handler: 패스워드 변경 이벤트 처리          //
      const onPasswordChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setPassword(value);
        setPasswordError(false);
        setPasswordErrorMessage('')
      };
      //          event handler: 패스워드 확인 변경 이벤트 처리          //
      const onPasswordCheckChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setPasswordCheck(value);
        setPasswordCheckError(false);
        setPasswordCheckErrorMessage('')
      };
      //          event handler: 닉네임 변경 이벤트 처리          //
      const onNicknameChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setNickname(value);
        setNicknameError(false);
        setNicknameErrorMessage('')
      };
      //          event handler: 핸드폰 번호 변경 이벤트 처리          //
      const onTelNumbereChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setTelNumber(value);
        setTelNumberError(false);
        setTelNumberErrorMessage('')
      };
      //          event handler: 주소 변경 이벤트 처리          //
      const onAddressChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setAddress(value);
        setAddressError(false);
        setAddressErrorMessage('')
      };
      //          event handler: 상세주소 변경 이벤트 처리          //
      const onAddressDetailChangeHandler = (event:ChangeEvent<HTMLInputElement>) => {
        const {value} = event.target;
        setAddressDetail(value);
      };
      //          event handler: 개인 정보 동의 체크 박스 버튼 클릭 이벤트 처리          //
      const onAgreedPersonalClickHandler = () => {
        setAgreedPersonal(!agreedPersonal);
        setAgreedPersonalError(false);
      }
      //          event handler: 패스워드 버튼 클릭 이벤트 처리          //
      const onPasswordButtonClickHandler = () =>{
        if (passwordButtonIcon === 'eye-light-off-icon'){
          setPasswordButtonIcon('eye-light-on-icon');
          setPasswordType('text');
        }
        else{
          setPasswordButtonIcon('eye-light-off-icon');
          setPasswordType('password');
        }
      }
      //          event handler: 패스워드 확인 버튼 클릭 이벤트 처리          //
      const onPasswordCheckButtonClickHandler = () =>{
        if (passwordCheckButtonIcon === 'eye-light-off-icon'){
          setPasswordCheckButtonIcon('eye-light-on-icon');
          setPasswordCheckType('text');
        }
        else{
          setPasswordCheckButtonIcon('eye-light-off-icon');
          setPasswordCheckType('password');
        }
      }
      //          event handler: 주소 버튼 클릭 이벤트 처리          //
      const onAddressButtonClickHandler = () => {
        open( { onComplete } ); //open() 함수로 매개변수를 보내야한다. 완료 됐을 적에 onComplete 함수를 실행해라
      }
      //          event handler: 다음 단계 버튼 클릭 이벤트 처리          //
      const onNextButtonClickHandler = () =>{
        const emailParttern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; //이메일 형식의 패턴
        const isEmailParttern = emailParttern.test(email);
        if (!isEmailParttern){
          setEmailError(true);
          setEmailErrorMessage('이메일 주소 양식이 맞지 않습니다.')
        }
        const isCheckedPassword = password.trim().length >= 8;
        if (!isCheckedPassword){
          setPasswordError(true);
          setPasswordErrorMessage('비밀번호는 8자 이상 입력해주세요.');
        }
        const isEqualPassword = password === passwordCheck;
        if (!isEqualPassword){
          setPasswordCheckError(true)
          setPasswordCheckErrorMessage('비밀번호가 일치하지 않습니다.')
        }
        if (!isEmailParttern || !isCheckedPassword || !isEqualPassword ) return;
        setPage(2);
      }
      //          event handler: 회원가입 버튼 클릭 이벤트 처리          //
      const onSignUpButtonClickHandler = () => {
        // 한번 더 Input 양식 검증 // 도중에 에러나서 다 날아갈 수도 있을 때 대비!
        const emailParttern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/; //이메일 형식의 패턴
        const isEmailParttern = emailParttern.test(email);
        if (!isEmailParttern){
          setEmailError(true);
          setEmailErrorMessage('이메일 주소 양식이 맞지 않습니다.')
        }
        const isCheckedPassword = password.trim().length >= 8;
        if (!isCheckedPassword){
          setPasswordError(true);
          setPasswordErrorMessage('비밀번호는 8자 이상 입력해주세요.');
        }
        const isEqualPassword = password === passwordCheck;
        if (!isEqualPassword){
          setPasswordCheckError(true)
          setPasswordCheckErrorMessage('비밀번호가 일치하지 않습니다.')
        }
        if (!isEmailParttern || !isCheckedPassword || !isEqualPassword ) {
          setPage(1);
          return;
        }
        const hasNickname = nickname.trim().length > 0;
        if (!hasNickname) {
          setNicknameError(true);
          setNicknameErrorMessage('닉네임을 입력해주세요.')
        }
        const telNumberPattern = /^[0-9]{11,13}$/;
        const isTelNumberParttern = telNumberPattern.test(telNumber);
        if (!isTelNumberParttern) {
          setTelNumberError(true);
          setTelNumberErrorMessage('숫자만 입력해주세요.');
        }
        const hasAddress = address.trim().length > 0;
        if (!hasAddress) {
          setAddressError(true);
          setAddressErrorMessage('주소를 입력해주세요.');
        }
        if (!agreedPersonal) setAgreedPersonalError(true);

        if (!hasNickname || !isTelNumberParttern || !agreedPersonal) return;

        const requestBody: SignUpRequestDto = { // 클라이언트가 입력한 정보를 보내줌
          email, password, nickname, telNumber, address, addressDetail, agreedPersonal
        }

        signUpRequest(requestBody).then(signUpResponse);

      }

      //          event handler: 로그인 링크 클릭 이벤트 처리          //
      const onSignInLinkClickHandler = () => {
        setView('sign-in');
      }
      //          event handler: 이메일 키다운 이벤트 처리          //
      const onEmailKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        if (!passwordRef.current) return;
        passwordRef.current.focus();
      }
      //          event handler: 패스워드 키다운 이벤트 처리          //
      const onPasswordKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        if (!passwordCheckRef.current) return;
        passwordCheckRef.current.focus();
      }
      //          event handler: 패스워드 확인 키다운 이벤트 처리          //
      const onPasswordCheckKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        onNextButtonClickHandler();
      }
      //          event handler: 닉네임 키다운 이벤트 처리          //
      const onNicknameKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        if (!telNumberRef.current) return;
        telNumberRef.current.focus();
      }
      //          event handler: 핸드폰 번호 키다운 이벤트 처리          //
      const onTelNumberKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        onAddressButtonClickHandler();
      }
      //          event handler: 주소 키다운 이벤트 처리          //
      const onAddressKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        if (!addressDetailRef.current) return;
        addressDetailRef.current.focus();
      }
      //          event handler: 상세주소 키다운 이벤트 처리          //
      const onAddressDetailKeyDownHandler = (event:KeyboardEvent<HTMLInputElement>) => {
        if (event.key !== 'Enter') return;
        onSignUpButtonClickHandler();
      }

      //          event handler: 다음 주소 검색 완료 이벤트 처리          //
      const onComplete = (data:Address) =>{ //postcode의 address // data로부터 address를 받아올 수 있다
        const { address } = data;
        setAddress(address);
        setAddressError(false);
        setAddressErrorMessage('')
        if (!addressDetailRef.current) return;
        addressDetailRef.current.focus(); // addressDetail에 포커스가 맞춰지게 됨
      }

      //          effect: 페이지가 변경될 때 마다 실행될 함수         //
      useEffect(() => {
        if (page === 2){
          if (!nicknameRef.current) return;
        }
      }, [page])
      
      //          render: sign up card 컴포넌트 랜더링          //
      return (
        <div className="auth-card">
          <div className="auth-card-box">
            <div className="auth-card-top">
              <div className="auth-card-title-box">
                <div className="auth-card-title">{"회원가입"}</div>
                <div className="auth-card-page">{`${page}/2`}</div>
              </div>
              {page === 1 && (
              <>
              <InputBox ref={emailRef} label="이메일 주소*" type="text" placeholder="이메일 주소를 입력해주세요." value={email} onChange={onEmailChangeHandler} error={isEmailError} message={emailErrorMessage} onKeyDown={onEmailKeyDownHandler}/>
              <InputBox ref={passwordRef} label="비밀번호*" type={passwordType} placeholder="비밀번호를 입력해주세요." value={password} onChange={onPasswordChangeHandler} error={isPasswordError} message={passwordErrorMessage} icon={passwordButtonIcon} onButtonClick={onPasswordButtonClickHandler} onKeyDown={onPasswordKeyDownHandler}/>
              <InputBox ref={passwordCheckRef} label="비밀번호 확인*" type={passwordCheckType} placeholder="비밀번호를 다시 입력해주세요." value={passwordCheck} onChange={onPasswordCheckChangeHandler} error={isPasswordChecklError} message={passwordChecklErrorMessage} icon={passwordCheckButtonIcon} onButtonClick={onPasswordCheckButtonClickHandler} onKeyDown={onPasswordCheckKeyDownHandler} />
              </>
              )}
              {page === 2 && (
              <>
              <InputBox ref={nicknameRef} label="닉네임*" type="text" placeholder="닉네임을 입력해주세요." value={nickname} onChange={onNicknameChangeHandler} error={isNicknameError} message={nicknameErrorMessage} onKeyDown={onNicknameKeyDownHandler} />
              <InputBox ref={telNumberRef} label="휴대폰 번호*" type="text" placeholder="핸드폰 번호를 입력해주세요." value={telNumber} onChange={onTelNumbereChangeHandler} error={isTelNumberError} message={TelNuberErrorMessage} onKeyDown={onTelNumberKeyDownHandler} />
              <InputBox ref={addressRef} label="주소*" type="text" placeholder="우편번호 찾기" value={address} onChange={onAddressChangeHandler} error={isAddressError} message={AddressErrorMessage} icon="expand-right-light-icon" onButtonClick={onAddressButtonClickHandler} onKeyDown={onAddressKeyDownHandler} />
              <InputBox ref={addressDetailRef} label="상세 주소" type="text" placeholder="상세 주소를 입력해주세요." value={addressDetail} onChange={onAddressDetailChangeHandler} error={false} onKeyDown={onAddressDetailKeyDownHandler}/>
              </>
              )}
            </div>
            <div className="auth-card-bottom">
              {page === 1 && (
                <div className="black-large-full-button" onClick={onNextButtonClickHandler}>{"다음단계"}</div>
                )}
              {page === 2 && (
                <>
              <div className="auth-consent-box">
                <div className="auth-check-box" onClick={onAgreedPersonalClickHandler}>
                  <div className={`icon ${agreedPersonal ? 'black-checkbox-icon' : 'white-checkbox-icon'}`}></div>
                </div>
                <div className={ isAgreedPersonalError ? "auth-consent-title-error" : "auth-consent-title"}>{"개인정보동의"}</div>
                <div className="auth-consent-link">{"더보기 >"}</div>
              </div>
              <div className="black-large-full-button" onClick={onSignUpButtonClickHandler}>{"회원가입"}</div>
              
              </>
              )}
              <div className="auth-description-box">
                <div className="auth-description">{"이미 계정이 있으신가요? "}<span className="auth-description-link" onClick={onSignInLinkClickHandler} >{"로그인"}</span></div>
              </div>
            </div>
          </div>
        </div>
      )
    }

  //          render: 인증 화면 컴포넌트 랜더링          //
  return (
  <div id="auth-wrapper">
    <div className="auth-container">
      <div className="auth-jumbotron-box">
        <div className="auth-jumbotron-contents">
          {/* <div className="auth-logo-icon"></div> */}
          <div className="auth-jumbotron-text-box">
            {/* <div className="auth-jumbotron-text">{"Hello,"}</div> */}
            {/* <div className="auth-jumbotron-text">{"I'm Web Developer Park Donghoon."}</div> */}
            <div className="auth-jumbotron-text">{"안녕하세요,"}</div>
            <div className="auth-jumbotron-text">{"신입 개발자 박동훈입니다."}</div>
          </div>
        </div>
      </div>
      {view === 'sign-in' && <SignInCard/>}
      {view === 'sign-up' && <SignUpCard/>}
    </div>
  </div>
  )
}
