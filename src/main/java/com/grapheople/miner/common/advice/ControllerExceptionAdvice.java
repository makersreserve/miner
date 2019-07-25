package com.grapheople.miner.common.advice;

import com.grapheople.miner.common.wrapper.ResultResponse;
import com.grapheople.miner.common.exception.MinerException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ControllerExceptionAdvice {

    private Environment environment;

    @ExceptionHandler(MinerException.class)
    public ResponseEntity<ResultResponse> handleException(MinerException e) {
        ResultResponse resultResponse = new ResultResponse(e.getHttpStatus());
        resultResponse.setMessage(e.getMessage());
        resultResponse.setCode(e.getCode());
        log.debug("#### {}",resultResponse);
        return new ResponseEntity<>(resultResponse, e.getHttpStatus());
    }

    @ExceptionHandler({ValidationException.class, TypeMismatchException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultResponse handleValidateException(Exception e) {
        ResultResponse resultResponse = new ResultResponse(HttpStatus.BAD_REQUEST);
        resultResponse.setMessage(e.getMessage());
        resultResponse.setCode(40099);
        log.warn("#### {}", (Object) e.getStackTrace());

        return resultResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse handleException(Exception e) {
        log.error("#### {} ", e.getMessage());
        ResultResponse resultResponse = new ResultResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        resultResponse.setMessage(isProduction() ? "Server Error" : e.getMessage());
        for (StackTraceElement stack : e.getStackTrace()) {
            log.warn("#### {}", stack);
        }
        return resultResponse;
    }

    private boolean isProduction() {
        return Arrays.asList(environment.getActiveProfiles()).contains("production");
    }
}
