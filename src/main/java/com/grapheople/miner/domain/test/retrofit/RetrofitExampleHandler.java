package com.grapheople.miner.domain.test.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.grapheople.miner.common.retrofit.RetrofitExecutor;
import com.grapheople.miner.common.retrofit.RetrofitFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class RetrofitExampleHandler {
    private RetrofitExampleService retrofitExampleService;

    public RetrofitExampleHandler(@Value("${shopping.domain}") String domain,
                           RetrofitFactory<RetrofitExampleService> factory) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.retrofitExampleService = factory.createService(domain, RetrofitExampleService.class, JacksonConverterFactory.create(mapper));
    }

    public ShoppingItem getItem(Long itemId) {
        return new RetrofitExecutor<ShoppingItem>().execute(retrofitExampleService.getItem(itemId));
    }
}
