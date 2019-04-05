package com.example.mathgame.Util;



import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathgame.Model.User;
import com.example.mathgame.R;
import com.example.mathgame.ViewController.HomeActivity;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Util {
    public static CatLoadingView dialogLoading=new CatLoadingView();
    public static CatLoadingView showCatLoading(){
        dialogLoading.setCancelable(false);
        return dialogLoading;
    }
    public static void showToast(String msg, Context context){
        Toast toast = new Toast(context);
        toast.setView(LayoutInflater.from(context).inflate(R.layout.toast_layout, null));
        toast.setDuration(Toast.LENGTH_LONG);
        TextView txtmsg = toast.getView().findViewById(R.id.txt_msg);
        txtmsg.setText(msg);
        toast.show();
    }
}
