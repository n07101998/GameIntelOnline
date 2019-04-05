package com.example.mathgame.Network;

import com.example.mathgame.Model.Game;
import com.example.mathgame.Model.User;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIServer {
    //api/json/get/cgcIUNqZlu?indent=2
    //lấy top 5 thế giới
    @GET("api/getTopUser.php")
    Call<ArrayList<User>> getTopUser();

    @GET("api/getAllUser.php")
    Call<ArrayList<User>> getAllUser();
    //thêm người vào top 5
    @FormUrlEncoded
    @POST("api/insertUser.php")
    Call<String> insertUser(@FieldMap HashMap<String,String> data);

    //lấy câu hỏi
    @GET("api/getAllQues.php")
    Call<ArrayList<Game>> getAllQues();
}
