package com.example.mathgame.ViewController.Rank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mathgame.Model.User;
import com.example.mathgame.Network.APIServer;
import com.example.mathgame.Network.RetrofitClient;
import com.example.mathgame.R;
import com.example.mathgame.Util.Util;
import com.example.mathgame.ViewController.HomeActivity;
import com.example.mathgame.ViewController.MainActivity;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RankActivity extends AppCompatActivity {
    ListView lvRank;
    RankAdapter adapter;
    View headerView;
    ImageButton btnPlay,btnHome;
    ArrayList<User> arrTop=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        init();
        getTopUser();

    }

    private void init() {
        headerView= LayoutInflater.from(this).inflate(R.layout.header_rank,null);
        lvRank = findViewById(R.id.lv_rank);
        lvRank.addHeaderView(headerView);
        adapter = new RankAdapter(arrTop, this);
        lvRank.setAdapter(adapter);
        btnHome=findViewById(R.id.btn_home);
        btnPlay=findViewById(R.id.btn_play);
        addEvents();
    }

    private void getTopUser() {
       Util.showCatLoading().show(getSupportFragmentManager(),"");
        RetrofitClient.getInstace().create(APIServer.class).getTopUser().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Util.showCatLoading().dismiss();
                arrTop.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Util.showCatLoading().dismiss();
                Util.showToast("Không có kết nối internet!",RankActivity.this);
                Log.d("ffr", "onFailure: " + t.toString());

            }
        });
    }


    private void addEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RankActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RankActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
