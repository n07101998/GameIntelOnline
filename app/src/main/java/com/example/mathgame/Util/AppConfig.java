package com.example.mathgame.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    public static String NAME_SHARED="sound";
    public static String KEY_SOUND="sound";
    //lấy ra đối tượng edtor
    public static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME_SHARED,Context.MODE_PRIVATE);
        return sharedPreferences.edit();
    }

    public static SharedPreferences getSharedPrefrences(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(NAME_SHARED,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void setSound(boolean isSound,Context context){
        getEditor(context).putBoolean(KEY_SOUND,isSound).apply();

    }
    public static boolean isSound(Context context){
        return getSharedPrefrences(context).getBoolean(KEY_SOUND,true);
    }
}
