package org.example.crossoverserver2.planeletter.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //BadRequestException
    SIZE("4000", "길이가 유효하지 않습니다."),
    PATTERN("4001","형식에 맞지 않습니다."),
    NOT_BLANK("4002", "필수값이 공백입니다."),
    LENGTH("4003", "길이가 유효하지 않습니다."),
    EMAIL("4004", "이메일 형식이 유효하지 않습니다."),

    //AuthorizedException
    USER_UNAUTHORIZED("4010", "로그인에 실패했습니다."),
    TOKEN_INVALID("4011","토큰이 유효하지 않습니다."),
    TOKEN_NOT_FOUND("4012", "토큰이 존재하지 않습니다."),

    //ForbiddenException
    NO_ACCESS("4030", "접근 권한이 없습니다."),


    //NotFoundException
    NOT_FOUND_USER("4040","유저를 찾을 수 없습니다."),
    NOT_FOUND_BOARD("4041","게시물을 찾을 수 없습니다."),
    NOT_FOUND_PAGE("4042", "존재하지 않는 페이지입니다."),

    //ConflictException
    EMAIL_CONFLICT("4090", "이미 존재하는 이메일입니다."),
    USERID_CONFLICT("4091","이미 존재하는 유저아이디입니다.");


    private final String code;
    private final String message;

    public static ErrorCode resolveValidationErrorCode(String code){
        return switch (code){
            case "Size" -> SIZE;
            case "Pattern" -> PATTERN;
            case "NotBlank" -> NOT_BLANK;
            case "Length" -> LENGTH;
            case "Email" -> EMAIL;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }

}
