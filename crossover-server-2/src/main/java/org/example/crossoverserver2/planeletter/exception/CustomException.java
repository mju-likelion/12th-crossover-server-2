package org.example.crossoverserver2.planeletter.exception;

import lombok.Getter;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String detail;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = null;
    }

    public CustomException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }

}
