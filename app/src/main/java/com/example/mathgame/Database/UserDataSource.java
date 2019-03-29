package com.example.mathfastgame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.mathfastgame.Model.User;

import java.util.ArrayList;

public class UserDataSource {
    public static String TABLE_NAME="user";

    public UserDataSource(Context context) {
        AssetDatabaseOpenHelper.database=context.openOrCreateDatabase(AssetDatabaseOpenHelper.DB_NAME,Context.MODE_PRIVATE,null);
    }

    //thêm người chơi
    public long insertUser(User user){
        ContentValues values=new ContentValues();
        values.put("nameUser",user.getNameUser());
        values.put("point",user.getPoint());
        long kq=AssetDatabaseOpenHelper.database.insert(TABLE_NAME,null,values);
        return kq;
    }
    //lấy danh sách người chơi
    public ArrayList<User> getListUser(){
        ArrayList<User> arrUser =new ArrayList<>();
        //câu lệch truy vấn
       try {
           String sql="SELECT * FROM "+TABLE_NAME;
           Cursor cursor=AssetDatabaseOpenHelper.database.rawQuery(sql,null);
           arrUser.clear();
           while (cursor.moveToNext()){
               int id=cursor.getInt(0);
               String nameUser=cursor.getString(1);
               int point=cursor.getInt(2);
               User user=new User(id,point,nameUser);
               arrUser.add(user);
           }
           cursor.close();
       }catch (Exception e){
           Log.d("fggg", "getListUser: "+e.toString());
       }

        return arrUser;
    }
}
