package com.grapheople.miner.domain.test.controller;

import com.grapheople.miner.common.wrapper.ResultResponse;
import com.grapheople.miner.domain.test.retrofit.RetrofitExampleHandler;
import com.grapheople.miner.domain.test.retrofit.ShoppingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final RetrofitExampleHandler retrofitExampleHandler;


    @GetMapping("retrofit")
    public ResultResponse<ShoppingItem> testRetrofit() {
        return new ResultResponse<>(retrofitExampleHandler.getItem(502459L));
    }
}
