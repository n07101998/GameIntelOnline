package com.example.mathfastgame.ViewController;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.mathfastgame.Database.AssetDatabaseOpenHelper;
import com.example.mathfastgame.Database.UserDataSource;
import com.example.mathfastgame.Model.User;
import com.example.mathfastgame.R;
import com.example.mathfastgame.Util.Util;
import com.example.mathfastgame.ViewController.Base.BaseActivity;
import com.example.mathfastgame.ViewController.Rank.RankActivity;

import java.util.Collections;
import java.util.Comparator;

public class HomeActivity extends BaseActivity {
    ImageButton btnPlay,btnRank,btnInfor;
    UserDataSource userDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        addEvents();
    }

    private void addEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, RankActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void init() {
        btnInfor=findViewById(R.id.btn_infor);
        btnPlay=findViewById(R.id.btn_play);
        btnRank=findViewById(R.id.btn_rank);
        AssetDatabaseOpenHelper db = new AssetDatabaseOpenHelper(this);
        db.processCopy();
        userDataSource = new UserDataSource(this);
        Util.arrUser = userDataSource.getListUser();
        Util.SortPoint();
        Log.d("fffr", "init: ");
    }
}
