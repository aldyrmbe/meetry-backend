package com.meetry.backend.web.exception;


import com.meetry.backend.web.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleInvalidParameter(BaseException exception) {
        BaseResponse response = BaseResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .status("BAD_REQUEST")
            .message(exception.getMessage())
            .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseResponse> handleInvalidParameter(UnauthorizedException exception) {
        BaseResponse response = BaseResponse.builder()
            .code(HttpStatus.UNAUTHORIZED.value())
            .status("UNAUTHORIZED")
            .message("User Unauthorized")
            .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
