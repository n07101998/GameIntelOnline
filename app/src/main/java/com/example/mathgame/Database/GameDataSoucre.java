package com.example.mathfastgame.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mathfastgame.Model.Game;

import java.util.ArrayList;

import static com.example.mathfastgame.Database.AssetDatabaseOpenHelper.DB_NAME;
import static com.example.mathfastgame.Database.AssetDatabaseOpenHelper.database;

public class GameDataSoucre {
    public static String TABLE_NAME="game";
    public static String sqlSelectAll="SELECT * FROM "+TABLE_NAME;

    public GameDataSoucre(Context context) {
        database=context.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
    }

    public ArrayList<Game> getAllGameData(){
        ArrayList<Game> arrData=new ArrayList<>();
        Cursor cursor=database.rawQuery(sqlSelectAll,null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String ques=cursor.getString(1);
            String answer=cursor.getString(2);
            Game game=new Game(id,ques,answer);
            arrData.add(game);
        }
        cursor.close();
        return arrData;
    }
}
