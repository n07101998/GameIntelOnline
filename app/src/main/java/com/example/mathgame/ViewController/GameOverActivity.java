package com.example.mathgame.ViewController;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.mathgame.Model.Game;
import com.example.mathgame.Model.User;
import com.example.mathgame.Network.APIServer;
import com.example.mathgame.Network.RetrofitClient;
import com.example.mathgame.R;
import com.example.mathgame.Util.Util;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameOverActivity extends AppCompatActivity {
    TextView txtPoint, txtTop;
    ImageButton btnReplay, btnBack, btnShare;
    int point;
    ShareDialog shareDialog;
    Dialog dialog;
    int top;
    Button btnok;
    EditText edtNameUser;
    ArrayList<User> arrAllUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_game_over);
        getKeyHash();
        init();
        getAllUser();
        addEvents();
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mathfastgame",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {
            Log.d("ggfg", "onCreate: " + e.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("ggfg", "onCreate: " + e.toString());
        }
    }

    private void init() {
        point = getIntent().getIntExtra("point", -1);
        txtPoint = findViewById(R.id.txt_point);
        txtTop = findViewById(R.id.txt_top);
        btnReplay = findViewById(R.id.btn_replay);
        btnBack = findViewById(R.id.btn_back);
        btnShare = findViewById(R.id.btn_share);
        txtPoint.setText(point + "");
    }

    private void processTop() {
        // thêm vào top 5
        for (int i = 0; i < 5; i++) {
            if (point > arrAllUser.get(i).getPoint()) {
                addUser();
                break;
            }
        }
        // xử lý top
        for (int i = 0; i < arrAllUser.size(); i++) {
            if (point > arrAllUser.get(i).getPoint()) {
                top = (i + 1);
                txtTop.setText(top + "");

                break;

            } else {
                Log.d("fgg", "init: ");
                top = arrAllUser.size();
                txtTop.setText(top + "");
            }
        }
    }

    void getAllUser() {
        Util.showCatLoading().show(getSupportFragmentManager(), "");
        RetrofitClient.getInstane().create(APIServer.class).getAllUser().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Util.showCatLoading().dismiss();
                arrAllUser.addAll(response.body());
                processTop();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Util.showCatLoading().dismiss();
                Util.showToast("Xảy ra lỗi", GameOverActivity.this);
            }
        });
    }

    private void addUser() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.infor_user);
        dialog.setCancelable(false);
        btnok = dialog.findViewById(R.id.btn_ok);
        edtNameUser = dialog.findViewById(R.id.edt_name_user);
        dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNameUser.getText().toString().isEmpty()) {
                    User user = new User();
                    user.setUsername(edtNameUser.getText().toString());
                    user.setPoint(point);
                    HashMap<String, String> data = new HashMap<>();
                    data.put("username", user.getUsername());
                    data.put("point", user.getPoint() + "");
                    insertUser(data);
                } else {
                    Util.showToast("Nhập đầy đủ thông!", GameOverActivity.this);
                }

            }
        });
    }

    private void insertUser(HashMap<String, String> data) {
        RetrofitClient.getInstane().create(APIServer.class).insertUser(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().trim().equals("success")) {
                    dialog.dismiss();
                    Util.showToast("Thêm thành công", GameOverActivity.this);
                } else {
                    Util.showToast("xảy ra lỗi", GameOverActivity.this);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ydh", "onFailure: " + t.toString());
            }
        });
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();


            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        shareDialog = new ShareDialog(this);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }


}
