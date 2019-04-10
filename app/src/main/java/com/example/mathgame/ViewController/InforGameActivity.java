package com.example.mathgame.ViewController;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.mathgame.R;
import com.example.mathgame.ViewController.Base.BaseActivity;

public class InforGameActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_game);
        Toolbar toolbar=findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Freaking Math");
        toolbar.setNavigationIcon(R.mipmap.comeback);
        loadWeb();
    }
    void loadWeb(){
        WebView wvAbout = findViewById(R.id.wv_about);
        wvAbout.loadUrl("file:android_asset/freaking_math/index.html");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
