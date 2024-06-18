package org.example.crossoverserver2.planeletter.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //NotFoundException
    NOT_FOUND_BOARD("4041","게시물을 찾을 수 없습니다."),

    //ForbiddenException
    NO_ACCESS("4030", "접근 권한이 없습니다."),

    //AlreadyExistException
    ALREADY_EXIST("4090","중복된 리소스입니다."),

    SIZE("4000", "길이가 유효하지 않습니다.");


    private final String code;
    private final String message;


    public static ErrorCode resolveValidationErrorCode(String code){
        return switch (code){
            case "SIZE" -> SIZE;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }

}
