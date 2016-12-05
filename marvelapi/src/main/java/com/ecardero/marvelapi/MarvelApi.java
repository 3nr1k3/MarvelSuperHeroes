package com.ecardero.marvelapi;

import com.ecardero.marvelapi.api.exceptions.MarvelApiNotInitializedException;
import com.ecardero.marvelapi.api.util.MD5Util;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelApi {
    private Retrofit _retrofit;
    private static MarvelApi _marvelApi = null;

    private String _publicApiKey;
    private String _privateApiKey;

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();


    public static <T> T getService(Class<T> serviceClass) throws MarvelApiNotInitializedException{
        if(_marvelApi == null)
            throw new MarvelApiNotInitializedException();

        return _marvelApi.getRestAdapter().create(serviceClass);
    }

    private Retrofit getRestAdapter(){
        return _retrofit;
    }

    private MarvelApi(Builder builder){
        _publicApiKey = builder.publicApiKey;
        _privateApiKey = builder.privateApiKey;

        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                request.newBuilder().addHeader("Content-Type", "application/json").build();

                Date now = new Date();
                String timestamp = String.valueOf(now.getTime());

                HttpUrl url = request.url().newBuilder()
                        .addEncodedQueryParameter("apikey", _publicApiKey)
                        .addEncodedQueryParameter("ts", timestamp)
                        .addEncodedQueryParameter("hash", MD5Util.hash(_publicApiKey, _privateApiKey, timestamp))
                        .build();

                request = request.newBuilder().url(url).build();

                return chain.proceed(request);
            }
        });


        _retrofit = new Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();
    }

    public static class Builder{
        private String publicApiKey;
        private String privateApiKey;

        public Builder(){

        }

        public Builder withApiKeys(String publicKey, String privateKey){
            publicApiKey = publicKey;
            privateApiKey = privateKey;
            return this;
        }

        public MarvelApi build(){
            MarvelApi api = new MarvelApi(this);
            _marvelApi = api;
            return api;
        }
    }
}
