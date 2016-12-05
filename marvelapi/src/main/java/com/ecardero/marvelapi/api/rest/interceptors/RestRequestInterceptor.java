package com.ecardero.marvelapi.api.rest.interceptors;

import com.ecardero.marvelapi.api.util.MD5Util;

import java.io.IOException;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Enrique Cardero on 04/12/2016.
 */

public class RestRequestInterceptor implements Interceptor {
    private String apiKeyPublic = "";
    private String apiKeyPrivate = "";

    public RestRequestInterceptor(String publicApiKey, String privateApiKey){
        apiKeyPublic = publicApiKey;
        apiKeyPrivate = privateApiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request.newBuilder().addHeader("Content-Type", "application/json").build();

        Date now = new Date();
        String timestamp = String.valueOf(now.getTime());

        HttpUrl url = request.url().newBuilder()
                .addEncodedQueryParameter("apikey", apiKeyPublic)
                .addEncodedQueryParameter("ts", timestamp)
                .addEncodedQueryParameter("hash", MD5Util.hash(apiKeyPublic, apiKeyPrivate, timestamp))
                .build();

        request = request.newBuilder().url(url).build();

        return chain.proceed(request);
    }
}
