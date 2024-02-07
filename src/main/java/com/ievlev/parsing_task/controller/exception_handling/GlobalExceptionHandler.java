package com.ievlev.parsing_task.controller.exception_handling;

import com.ievlev.parsing_task.dto.AppErrorStatusDto;
import com.ievlev.parsing_task.exception.GoogleSheetException;
import com.ievlev.parsing_task.exception.IncorrectConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentExcpetion(IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), illegalArgumentException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GoogleSheetException.class})
    public ResponseEntity<?> handleGoogleSheetException(GoogleSheetException e) {
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IncorrectConnectionException.class})
    public ResponseEntity<?> handleIncorrectConnectionException(IncorrectConnectionException e) {
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
