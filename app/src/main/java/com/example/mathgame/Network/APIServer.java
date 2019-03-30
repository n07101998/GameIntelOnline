package com.example.mathgame.Network;

import com.example.mathgame.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServer {
    //api/json/get/cgcIUNqZlu?indent=2
    @GET("php/server.php")
    Call<ArrayList<User>> getTopUser();
}
