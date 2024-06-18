package org.example.crossoverserver2.planeletter.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

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
