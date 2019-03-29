package com.example.mathfastgame.ViewController.Rank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mathfastgame.Database.UserDataSource;
import com.example.mathfastgame.Model.User;
import com.example.mathfastgame.R;
import com.example.mathfastgame.Util.Util;
import com.example.mathfastgame.ViewController.HomeActivity;
import com.example.mathfastgame.ViewController.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RankActivity extends AppCompatActivity {
    ListView lvRank;
    RankAdapter adapter;
    View headerView;
    ImageButton btnPlay,btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        init();
    }

    private void init() {
        headerView= LayoutInflater.from(this).inflate(R.layout.header_rank,null);
        lvRank = findViewById(R.id.lv_rank);
        lvRank.addHeaderView(headerView);
        adapter = new RankAdapter(Util.arrUser, this);
        lvRank.setAdapter(adapter);
        btnHome=findViewById(R.id.btn_home);
        btnPlay=findViewById(R.id.btn_play);
        addEvents();
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
