package org.example.crossoverserver2.planeletter.exception;

import org.example.crossoverserver2.planeletter.dto.ErrorResponseDto;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;

public class DtoValidationException extends CustomException{

    public DtoValidationException(ErrorCode errorCode, String detail){
        super(errorCode,detail);
    }
}
