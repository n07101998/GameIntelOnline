package com.example.mathfastgame.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetDatabaseOpenHelper{
    public static final String DB_NAME = "gameIntel_fix_batabase.db";
    String DB_PATH_SUFFIX = "/databases/";
    int ver=1;
    private Context context;
    public static SQLiteDatabase database;
    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public void processCopy() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(context,
                        "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }
        }else {
            Log.d("ffggf", "processCopy: ");
        }
    }
    public String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DB_NAME;
    }
    public void CopyDataBaseFromAsset()
    {
        try {
            InputStream myInput;
            myInput = context.getAssets().open(DB_NAME);
            String outFileName = getDatabasePath();
            File f = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

