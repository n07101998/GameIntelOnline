package com.example.mathgame.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
    public static String baseUrl="http://192.168.1.105/";
    public static Retrofit getInstace(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit==null){
            retrofit=new Retrofit
                    .Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
