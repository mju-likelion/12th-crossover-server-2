package org.example.crossoverserver2.planeletter.exception;

import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;

public class AuthorizedException extends CustomException{
    public AuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizedException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
