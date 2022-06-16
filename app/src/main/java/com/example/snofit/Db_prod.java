package com.example.snofit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_prod extends SQLiteOpenHelper {

    public static final String DATABASE_NAME1 = "DB_prod.db";
    public static final int DATABASE_VERSION1 = 7;
    public static final String TABLE_NAME1 = "call";

    public static final String COLUMN_ID1 = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CALORIES = "cal";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_SQUIRRELS = "squir";
    public static final String COLUMN_CARBOHYDRATES = "carb";

    public Db_prod(@Nullable Context context) {
        super(context, DATABASE_NAME1, null, DATABASE_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME1 + " (" +
                COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CALORIES + " TEXT, " +
                COLUMN_FATS + " TEXT, " +
                COLUMN_SQUIRRELS + " TEXT, " +
                COLUMN_CARBOHYDRATES + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(sqLiteDatabase);
    }
}
