import ResponseCode from "./response-code.enum";

// enum은 타입 선언 없이 그냥 내보낼 수 있다.
// enum은 열거형, 값 자체를 정의하고 있기 때문에 export할 필요가 없으며,
// 구현부가 존재하기 때문에.
//interface는 데이터의 구조를 정의하기 때문에 type으로 구체화해야 합니다.
export { ResponseCode };
