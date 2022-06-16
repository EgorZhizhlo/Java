package com.example.snofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_eat_today  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME1 = "DB_eat_today.db";
    public static final int DATABASE_VERSION1 = 7;
    public static final String TABLE_NAME1 = "eat_today";

    public static final String COLUMN_ID1 = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EAT_CAL = "eat_cal";
    public static final String COLUMN_EAT_FATS = "eat_fats";
    public static final String COLUMN_EAT_SQUIR = "eat_squir";
    public static final String COLUMN_EAT_CARB = "eat_carb";
    public static final String COLUMN_EAT_Gr = "gramm";

    public DB_eat_today(@Nullable Context context) {
        super(context, DATABASE_NAME1, null, DATABASE_VERSION1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME1 + " (" +
                COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EAT_CAL + " TEXT, " +
                COLUMN_EAT_FATS + " TEXT, " +
                COLUMN_EAT_SQUIR + " TEXT, " +
                COLUMN_EAT_CARB + " TEXT, " +
                COLUMN_EAT_Gr + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(sqLiteDatabase);
    }
}