package com.grapheople.miner.domain.test.retrofit;

import com.grapheople.miner.common.wrapper.ResultResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitExampleService {
    @GET("/api/v1/channels/4/items/{itemId}")
    Call<ResultResponse<ShoppingItem>> getItem(@Path("itemId") Long itemId);
}
