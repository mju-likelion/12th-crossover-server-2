package org.example.crossoverserver2.planeletter.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    SIZE("4000", "길이가 유효하지 않습니다."),
    PATTERN("4001","형식에 맞지 않습니다."),
    NOT_BLANK("4002", "필수값이 공백입니다."),
    LENGTH("4003", "길이가 유효하지 않습니다"),
    EMAIL("4005", "이메일 형식이 유효하지 않습니다."),

    USER_UNAUTHORIZED("4010", "로그인에 실패했습니다."),



    EMAIL_CONFLICT("4090", "이미 존재하는 이메일입니다."),
    USERID_CONFLICT("4091","이미 존재하는 유저아이디입니다");




    private final String code;
    private final String message;


    public static ErrorCode resolveValidationErrorCode(String code){
        return switch (code){
            case "SIZE" -> SIZE;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }

}
