import { useEffect, useState } from "react";

const usePagination = <T>(countPerPage: number) =>{ //제네릭 타입을, 매개타입 이라고 함
    
    // 전체 리스트 불러오기
    //                  state: 전체 객체 리스트 상태                    //
    // 타입은 다양하게 올 수 있기 때문에, board,comment 등등
    // usePagination을 호출 할때, 타입을 결정하도록 만들어 줌 -> 제네릭
    const [totalList, setTotalList] = useState<T[]>([]);  
    //                  state: 보여줄 객체 리스트 상태                    //
    const [viewList, setViewList] = useState<T[]>([]);
    //                  state: 현재 페이지 번호 상태                    //
    const [currentPage, setCurrentPage] = useState<number>(1); // 무조건 1번 페이지로 시작.
    
    //                  state: 전체 페이지 번호 리스트 상태                    //
    const [totalPageList, setTotalPageList] = useState<number[]>([1]); // 기본적으로 1번 페이지로 잡기.
    //                  state: 부여줄 페이지 번호 리스트 상태                    //
    const [viewPageList, setViewPageList] = useState<number[]>([1]); // 기본적으로 1번 페이지로 잡기.
    //                  state: 현재 섹션 상태                    //
    const [currentSection, setCurrentSection] = useState<number>(1);
    
    //                  state: 전체 섹션 상태                    //
    const [totalSection, setTotalSection] = useState<number>(1);

    //                  function: 보여줄 객체 리스트 추출 함수                  //
    const setView = () => {
        const FIRST_INDEX = countPerPage * (currentPage - 1); // 1이면,
        const LAST_INDEX = totalList.length > countPerPage * currentPage ? countPerPage * currentPage : totalList.length; // 3이 옴.
        const viewList = totalList.slice(FIRST_INDEX, LAST_INDEX);
        setViewList(viewList);
    }
    //                  function: 보여줄 페이지 리스트 추출 함수                  //
    const setViewPage = () =>{
        const FIRST_INDEX = 10 * (currentSection - 1);
        const LAST_INDEX = totalPageList.length > 10 * currentSection ? 10 * currentSection : totalPageList.length;
        const viewPageList = totalPageList.slice(FIRST_INDEX, LAST_INDEX);
        setViewPageList(viewPageList);
    };
    
    //                  effect: total list가 변경될 때마다 실행할 작업                  //
    useEffect(() => {
        const totalPage = Math.ceil(totalList.length / countPerPage); // Math.ceil은 올림 처리
        const totalPageList: number[] = [];
        for (let page = 1; page <= totalPage; page++) totalPageList.push(page);
        setTotalPageList(totalPageList);
        
        const totalSection = Math.ceil(totalList.length / (countPerPage * 10));
        setTotalSection(totalSection);

        setCurrentPage(1);
        setCurrentSection(1);

        setView(); // 객체 리스트
        setViewPage(); // 페이지 리스트
    }, [totalList])

    //                  effect: current page가 변경될 때마다 실행할 작업                  //
    useEffect(setView, [currentPage]); // setView가 바뀌면, setViewPage는 안 바뀌게 작업
    //                  effect: current section이 변경될 때마다 실행할 작업                  //
    useEffect(setViewPage, [currentPage]); // setViewPage가 바뀌면, setView가 바뀌게

    return {
        currentPage, // 현재 페이지가 어떤 페이지에 있는지 작업하기 위해서, 다른 호출부 작업하기 위해서,
        setCurrentPage, // 현재 페이지를 바꿔주는 작업을 화면해서 하기 위해, 
        currentSection, // 섹션을 바꾸는 행위도 화면해서 하기 위해,
        setCurrentSection, // 섹션을 바꾸는 행위도 화면해서 하기 위해,
        viewList, // 현재 보여줄 리스트를 전달해줘야, 화면에 보여짐.
        viewPageList, // 페이지 리스트를 전달해줘야 화면에서 작업 가능
        totalSection, // 전체 섹션이 몇개가 있는지 전달해줘야 함, 현재 섹션이 마지막 섹션과 동일하다고 한다면, 다음으로의 화살표를 동작 못하게 만들것임
        setTotalList // 1번. API 통해서 리스트를 가져온 다음에, 전체 리스트로 작업을 해서 위의 값들을 내보내 줄 것임. 필수로 필요함. 
    };
}

export default usePagination;