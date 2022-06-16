package com.example.snofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_CAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME1 = "DB_cal.db";
    public static final int DATABASE_VERSION1 = 7;
    public static final String TABLE_NAME1 = "call";

    public static final String COLUMN_ID1 = "id";
    public static final String COLUMN_CALORIES = "Calories";
    public static final String COLUMN_FATS = "Fats";
    public static final String COLUMN_SQUIRRELS = "Squirrels";
    public static final String COLUMN_CARBOHYDRATES = "Carbohydrates";

    public DB_CAL(@Nullable Context context) {
        super(context, DATABASE_NAME1, null, DATABASE_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME1 + " (" +
                COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

