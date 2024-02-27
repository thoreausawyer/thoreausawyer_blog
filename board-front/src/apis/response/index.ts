import ResponseDto from "./response.dto";

//인터페이스 구현부가 없기 때문에 type이라고 선언해야 내보낼 수 있다.
//반면에 enum은 열거형, 값 자체를 정의하고 있기 때문에 export할 필요가 없으며,
//interface는 데이터의 구조를 정의하기 때문에 type으로 구체화해야 합니다.
//interface는 실제 데이터를 가지고 있는 것이 아니라 데이터 구조를 정의하는 것이기 때문이다
export type { ResponseDto };
