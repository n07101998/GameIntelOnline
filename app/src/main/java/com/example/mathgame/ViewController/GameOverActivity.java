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
import android.widget.Toast;


import com.example.mathgame.Database.UserDataSource;
import com.example.mathgame.Model.User;
import com.example.mathgame.R;
import com.example.mathgame.Util.Util;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GameOverActivity extends AppCompatActivity {

    TextView txtPoint,txtTop;
    ImageButton btnReplay,btnBack,btnShare;
    UserDataSource userDataSource;
    int point;
    Button btnok;
    EditText edtNameUser;
    ShareDialog shareDialog;
    int top;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_game_over);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.mathfastgame",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }  catch (NoSuchAlgorithmException e) {
            Log.d("ggfg", "onCreate: "+e.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("ggfg", "onCreate: "+e.toString());
        }
        init();
    }

    private void init() {
        point=getIntent().getIntExtra("point",-1);
        txtPoint=findViewById(R.id.txt_point);
        txtTop=findViewById(R.id.txt_top);
        btnReplay=findViewById(R.id.btn_replay);
        btnBack=findViewById(R.id.btn_back);
        btnShare=findViewById(R.id.btn_share);
        txtPoint.setText(point+"");
        addEvents();
        userDataSource=new UserDataSource(this);
        if (Util.arrUser.size()<=5){
            addUser();
        }else {
            processTop();
        }
        for (int i = 0; i < Util.arrUser.size() ; i++) {
            if (point>Util.arrUser.get(i).getPoint()){
                top=(i+1);
                txtTop.setText(top+"");

                break;

            }else {
                Log.d("fgg", "init: ");
                top=Util.arrUser.size();
                txtTop.setText(top+"");
            }
        }



    }

    private void processTop() {
        for (int i = 0; i < 5; i++) {
            if (point>Util.arrUser.get(i).getPoint()){
                addUser();
                break;

            }
        }
    }

    private void addUser() {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.infor_user);
        dialog.setCancelable(false);
        btnok=dialog.findViewById(R.id.btn_ok);
        edtNameUser=dialog.findViewById(R.id.edt_name_user);
        dialog.show();
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=new User();
                user.setNameUser(edtNameUser.getText().toString());
                user.setPoint(point);
                if (userDataSource.insertUser(user)>0){
                    Toast.makeText(GameOverActivity.this,"thêm thành công",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(GameOverActivity.this,"xảy ra lỗi",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameOverActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();


            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        shareDialog=new ShareDialog(this);
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
