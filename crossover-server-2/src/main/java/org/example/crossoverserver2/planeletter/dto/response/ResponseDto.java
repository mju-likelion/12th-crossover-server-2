package org.example.crossoverserver2.planeletter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final String statusCode;    //http 상태 코드
    private final String message;   //요청 결과 메세지
    private final T data;   //요청 결과 반환 데이터

    //반환 데이터가 없는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message) {
        return new ResponseDto<>(String.valueOf(statusCode.value()), message, null);
    }

    //반환 데이터가 있는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message, final T data) {
        return new ResponseDto<>(String.valueOf(statusCode.value()), message, data);
    }
}