package org.example.crossoverserver2.planeletter.exception;

import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;

public class NotFoundException extends CustomException{
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
