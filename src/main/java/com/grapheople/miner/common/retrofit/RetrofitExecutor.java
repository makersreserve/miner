package com.grapheople.miner.common.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grapheople.miner.common.exception.MinerException;
import com.grapheople.miner.common.wrapper.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.http.HttpStatus;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Slf4j
public class RetrofitExecutor<T> {

    public T execute(Call<ResultResponse<T>> call) {
        try {
            Response<ResultResponse<T>> response = call.execute();
            if (response.isSuccessful()) {
                log.debug("#### {}", response.body());
                return response.body().getResult();
            }

            ResultResponse errorResponse = parseErrorBody(response.errorBody());
            HttpStatus status = HttpStatus.valueOf(errorResponse.getStatus());

            throw new MinerException(status, errorResponse.getCode(), errorResponse.getMessage());

        } catch (IOException e) {
            log.error("#### {}", e);
            throw new MinerException(e.getMessage());
        }
    }

    private ResultResponse parseErrorBody(ResponseBody errorBody) {
        try {
            return new ObjectMapper().readValue(errorBody.bytes(), ResultResponse.class);
        } catch (IOException e) {
            throw new MinerException(e.getMessage());
        }
    }
}
