package edu.hanu.mycart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cart.db";
    private static final int DB_VER = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//   create table
        db.execSQL("CREATE TABLE cart(" +
                "number INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id LONG NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "price INTEGER NOT NULL, " +
                "image VARCHAR(255) NOT NULL, " +
                "quantity INTEGER NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE cart");
        onCreate(db);
    }
}
