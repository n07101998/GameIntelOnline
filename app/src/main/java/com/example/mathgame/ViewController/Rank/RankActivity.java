package com.example.mathgame.ViewController.Rank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mathgame.R;
import com.example.mathgame.Util.Util;
import com.example.mathgame.ViewController.HomeActivity;
import com.example.mathgame.ViewController.MainActivity;


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
