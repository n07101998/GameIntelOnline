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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.mathgame.Model.Game;
import com.example.mathgame.Network.APIServer;
import com.example.mathgame.Network.RetrofitClient;
import com.example.mathgame.R;
import com.example.mathgame.Util.AppConfig;
import com.example.mathgame.Util.Util;
import com.example.mathgame.ViewController.Base.BaseActivity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    ProgressBar pbCount;
    TextSwitcher txtQues;
    TextView txtPoint;
    ImageButton btnTrue, btnFalse;
    ArrayList<Game> arrData = new ArrayList<>();
    Random random = new Random();
    int point = 0;
    int pos;
    int rangeRandom;
    int count = 10;
    Timer timer;
    boolean isGameOver = false;
    MediaPlayer mediaPlayer;
    LinearLayout lnBg;
    int posBg = 0;
    public static int answerTrue = 1;
    public static int answerFalse = 0;


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
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogic(answerTrue);
            }
        });
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogic(answerFalse);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isGameOver = false;
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
        getAllQues();

    }

    private void getAllQues() {
        Util.showCatLoading().show(getSupportFragmentManager(), "");
        RetrofitClient.getInstane().create(APIServer.class).getAllQues().enqueue(new Callback<ArrayList<Game>>() {
            @Override
            public void onResponse(Call<ArrayList<Game>> call, Response<ArrayList<Game>> response) {
                Util.showCatLoading().dismiss();
                arrData.clear();
                arrData.addAll(response.body());
                rangeRandom = arrData.size();
                pos = random.nextInt(rangeRandom);
                txtQues.setText(arrData.get(pos).getQuestion());
            }

            @Override
            public void onFailure(Call<ArrayList<Game>> call, Throwable t) {
                Util.showCatLoading().dismiss();
                Util.showToast(t.toString(), MainActivity.this);

            }
        });
    }

    private void init() {
        pbCount = findViewById(R.id.pb_count);
        txtQues = findViewById(R.id.txt_ques);
        txtPoint = findViewById(R.id.txt_point);
        btnFalse = findViewById(R.id.btn_false);
        btnTrue = findViewById(R.id.btn_true);
        lnBg = findViewById(R.id.ln_bg);

    }

    private void plusPoint() {
        point++;
        txtPoint.setText(point + "");
    }

    void handleLogic(int answer) {
        // cho dùng phát âm thanh khi chuyển sang câu tiếp theo
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
        // nếu chọn đúng đáp án
        if (arrData.get(pos).getAnswer() == answer && !isGameOver) {
            //hủy luồng cũ
            if (timer != null && !isGameOver) {
                timer.cancel();
                timer = null;
            }
            count = 11;
            //khởi tạo lại timer
            processCountDown();
            // xử lý cộng điểm
            plusPoint();
            //random câu hỏi
            pos = random.nextInt(rangeRandom);
            txtQues.setText(arrData.get(pos).getQuestion());
            handleSound(R.raw.score);
            // đổi màu backgroup
            setColorBackgroup();
        } else {
            handleSound(R.raw.gameover);
            isGameOver = true;
            processGameOver();
        }
    }

    private void setColorBackgroup() {
        String[] arrBg = getResources().getStringArray(R.array.arrBackgroup);
        if (posBg == arrBg.length - 1)
            posBg = 0;
        lnBg.setBackgroundColor(Color.parseColor(arrBg[posBg]));
        posBg++;

    }

    // kiểm tra trạng thái âm thanh và phát âm thanh nếu issound ==true
    private void handleSound(int sound) {
        if (AppConfig.isSound(this)) {
            mediaPlayer = MediaPlayer.create(this, sound);
            mediaPlayer.start();
        }
    }

    private void processGameOver() {
        Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
        intent.putExtra("point", point);
        startActivity(intent);
        finish();
    }

    void processCountDown() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        pbCount.setProgress(count);
                        if (count == 0 && !isGameOver) {
                            isGameOver = true;
                            if (mediaPlayer != null) {
                                mediaPlayer.stop();
                                mediaPlayer = null;
                            }
                            if (AppConfig.isSound(MainActivity.this)) {
                                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gameover);
                                mediaPlayer.start();
                            }
                            processGameOver();

                        }
                    }
                });
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 170);

    }
}
