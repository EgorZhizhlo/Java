package com.example.snofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button main_button;

    DB db_user;
    DB_day db_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_button = findViewById(R.id.main_button);

        db_user = new DB(this);
        db_day = new DB_day(this);


        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase database = db_user.getWritableDatabase();
                SQLiteDatabase database_1 = db_day.getWritableDatabase();
                ContentValues contentValues_2 = new ContentValues();

                contentValues_2.clear();

                Cursor cursor = database.query(DB.TABLE_NAME, null, null, null, null, null, null);
                Cursor cursor_1 = database_1.query(DB_day.TABLE_NAME, null, null, null, null, null, null);

                Date date = new Date();

                int day_i = 0;

                if (cursor.moveToFirst()) {
                    if (cursor_1.moveToFirst()) {
                        day_i = date.getDay();
                        System.out.println(day_i);
                        cursor_1.moveToLast();
                        int dayIndex = cursor_1.getColumnIndex(DB_day.COLUMN_day);
                        int day = cursor_1.getInt(dayIndex);
                        if (day_i != day) {
                            contentValues_2.put(DB_day.COLUMN_day, day_i);
                            database_1.delete(DB_day.TABLE_NAME, null, null);
                            database_1.insert(DB_day.TABLE_NAME, null, contentValues_2);
                            Intent intent = new Intent(MainActivity.this, App_screen.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, App_screen.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        contentValues_2.put(DB_day.COLUMN_day, day_i);
                        database_1.insert(DB_day.TABLE_NAME, null, contentValues_2);
                        Intent intent = new Intent(MainActivity.this, App_screen.class);
                        startActivity(intent);
                    }
                }
                else {
                    day_i = date.getDay();
                    System.out.println(day_i);
                    contentValues_2.put(DB_day.COLUMN_day, day_i);
                    database_1.insert(DB_day.TABLE_NAME, null, contentValues_2);
                    Intent intent = new Intent(MainActivity.this, Reg_screen.class);
                    startActivity(intent);
                }
            }
        });


    }
}