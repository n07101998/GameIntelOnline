package com.example.mathgame.ViewController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathgame.Database.AssetDatabaseOpenHelper;
import com.example.mathgame.Model.User;
import com.example.mathgame.Network.APIServer;
import com.example.mathgame.Network.RetrofitClient;
import com.example.mathgame.R;
import com.example.mathgame.Util.AppConfig;
import com.example.mathgame.Util.Util;
import com.example.mathgame.ViewController.Base.BaseActivity;
import com.example.mathgame.ViewController.Rank.RankActivity;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends BaseActivity {
    ImageButton btnPlay, btnRank, btnInfor;
    boolean isNetwok;
    ImageView imgSound, imgMute;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    if (isNetworkConnected()) {
                        isNetwok = true;
                    } else {
                        isNetwok = false;
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        addEvents();
        setUpReciever();
    }

    private void setUpReciever() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    private void addEvents() {
        sateSound();
        imgMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.setSound(true, HomeActivity.this);
                imgMute.setVisibility(View.GONE);
                imgSound.setVisibility(View.VISIBLE);
            }
        });
        imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.setSound(false, HomeActivity.this);
                imgMute.setVisibility(View.VISIBLE);
                imgSound.setVisibility(View.GONE);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetwok) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    warningNotNetwok();
                }

            }
        });
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetwok) {
                    Intent intent = new Intent(HomeActivity.this, RankActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    warningNotNetwok();
                }

            }
        });
    }

    private void sateSound() {
        if (AppConfig.isSound(this)) {
            imgMute.setVisibility(View.GONE);
            imgSound.setVisibility(View.VISIBLE);
        } else {
            imgMute.setVisibility(View.VISIBLE);
            imgSound.setVisibility(View.GONE);
        }
    }

    private void init() {
        imgMute = findViewById(R.id.img_mute);
        imgSound = findViewById(R.id.img_sound);
        btnInfor = findViewById(R.id.btn_infor);
        btnPlay = findViewById(R.id.btn_play);
        btnRank = findViewById(R.id.btn_rank);
        AssetDatabaseOpenHelper db = new AssetDatabaseOpenHelper(this);
        db.processCopy();


        Log.d("fffr", "init: ");
    }


    private void warningNotNetwok() {
        Util.showToast("Không có kết nối internet!",this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
