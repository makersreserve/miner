package com.grapheople.miner.common.wrapper;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResultResponse<T> {
    private int status;
    private int code;
    private String message;
    private T result;

    public ResultResponse() {
        this.status = HttpStatus.OK.value();
        this.message = "success";
    }

    public ResultResponse(T result) {
        this.status = HttpStatus.OK.value();
        this.message = "success";
        this.result = result;
    }

    public ResultResponse(HttpStatus httpStatus) {
        this.status = httpStatus.value();
    }

    public ResultResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus.value();
        this.message = message;
    }
}
