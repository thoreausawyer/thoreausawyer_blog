import { BoardListItem } from "types/interface";
import ResponseDto from "../response.dto";

export default interface GetPopularListResponseDto extends ResponseDto{
    popularWordList: string[];
}