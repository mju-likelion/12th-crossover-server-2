package org.example.crossoverserver2.planeletter.exception.advice;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.example.crossoverserver2.planeletter.dto.ErrorResponseDto;
import org.example.crossoverserver2.planeletter.exception.CustomException;
import org.example.crossoverserver2.planeletter.exception.DtoValidationException;
import org.example.crossoverserver2.planeletter.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //다형성을 사용한 커스텀 예외처리 핸들
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException customException){
        this.writeLog(customException);
        HttpStatus httpStatus = this.resolveHttpStatus(customException);
        return new ResponseEntity<>(ErrorResponseDto.res(customException), httpStatus);
    }

    //DTO Validation 예외처리 핸들
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException){
        //methodArgumentNotValidException의 BindingResult의 FieldError(유효성 검증 실패 필드)를 찾아서 저장
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if(fieldError == null){
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        //실패한 유효성 검증에 대한 에러 코드 가져옴
        ErrorCode validationErrorCode = ErrorCode.resolveValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode, detail);
        this.writeLog(dtoValidationException);
        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException),HttpStatus.BAD_REQUEST);
    }

    //예측 불가한 오류 핸들
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception){
        this.writeLog(exception);
        return new ResponseEntity<>(
                ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),exception),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }



    //커스텀 예외 출력
    private void writeLog(CustomException customException){
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();
        log.error("[{}]{}:{}", exceptionName, errorCode.getMessage(), detail);
    }

    //예측 불가한 오류 출력
    private void writeLog(Exception exception){
        String exceptionName = exception.getClass().getSimpleName();
        String message = exception.getMessage();

        log.error("[{}]:{}", exceptionName, message);
    }

    private HttpStatus resolveHttpStatus(CustomException customException){
        return  HttpStatus.resolve(Integer.parseInt(customException.getErrorCode().getCode().substring(0,3)));
    }


}
