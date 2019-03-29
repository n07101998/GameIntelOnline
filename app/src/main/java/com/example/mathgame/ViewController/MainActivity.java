package com.example.mathgame.ViewController;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.mathgame.Database.GameDataSoucre;
import com.example.mathgame.Model.Game;
import com.example.mathgame.R;
import com.example.mathgame.ViewController.Base.BaseActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    ProgressBar pbCount;
    TextSwitcher txtQues;
    TextView txtPoint;
    ImageButton btnTrue, btnFalse;
    ImageView imgSound,imgMute;
    ArrayList<Game> arrData;
    Random random = new Random();
    int point = 0;
    int pos;
    int rangeRandom=50;
    int count=10;
    Timer timer;
    boolean isGameOver=false;
    MediaPlayer mediaPlayer;
    boolean isSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        init();
        setUpView();
        addEvents();
    }

    private void addEvents() {
        imgMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSound=true;
                imgMute.setVisibility(View.GONE);
                imgSound.setVisibility(View.VISIBLE);
            }
        });
        imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSound=false;
                imgMute.setVisibility(View.VISIBLE);
                imgSound.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGameOver=false;
    }

    private void setUpView() {
        txtQues.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(MainActivity.this);
                textView.setTextColor(Color.WHITE);
                Typeface type = Typeface.createFromAsset(getAssets(), "font/relay_black.ttf");
                textView.setTypeface(type);
                textView.setTextSize(70);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });
        GameDataSoucre gameDataSoucre = new GameDataSoucre(this);
        arrData = gameDataSoucre.getAllGameData();
        pos = random.nextInt(rangeRandom);
        txtQues.setText(arrData.get(pos).getQues());
    }

    private void init() {
        imgMute=findViewById(R.id.img_mute);
        imgSound=findViewById(R.id.img_sound);
        pbCount = findViewById(R.id.pb_count);
        txtQues = findViewById(R.id.txt_ques);
        txtPoint = findViewById(R.id.txt_point);
        btnFalse = findViewById(R.id.btn_false);
        btnTrue = findViewById(R.id.btn_true);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogic("1");
            }
        });
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               processLogic("0");
            }
        });
    }

    private void plusPoint() {
        point++;
        txtPoint.setText(point + "");
    }
    void processLogic(String answer){
        if (arrData.get(pos).getAnswer().trim().equals(answer)) {
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            count=11;
            processCountDown();
            plusPoint();
            pos = random.nextInt(rangeRandom);
            txtQues.setText(arrData.get(pos).getQues());
            if (mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer=null;
            }
            if (isSound){
                mediaPlayer=MediaPlayer.create(this,R.raw.score);
                mediaPlayer.start();
            }


        }else{
            isGameOver=true;
            processGameOver();
        }
    }

    private void processGameOver() {
        Intent intent=new Intent(MainActivity.this,GameOverActivity.class);
        intent.putExtra("point",point);
        startActivity(intent);
        finish();
    }

    void processCountDown(){
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        pbCount.setProgress(count);
                        if (count==0 && !isGameOver){
                            processGameOver();
                            isGameOver=true;
                        }
                    }
                });
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,0,150);
    }
}
