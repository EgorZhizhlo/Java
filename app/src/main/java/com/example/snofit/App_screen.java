package com.example.snofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class App_screen extends AppCompatActivity {

    TextView textView_cal, textView_fats, textView_carb, textView_squ;

    Button button_cha_norm, button_add_pr;

    DB_CAL db_cal;
    DB_eat_today db_eat_today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_app);

        button_cha_norm = findViewById(R.id.button2);
        button_add_pr = findViewById(R.id.button3);

        textView_cal = (TextView) findViewById(R.id.textView18);
        textView_fats = (TextView) findViewById(R.id.textView19);
        textView_carb = (TextView) findViewById(R.id.textView23);
        textView_squ = (TextView) findViewById(R.id.textView22);

        db_cal = new DB_CAL(this);
        db_eat_today = new DB_eat_today(this);

        SQLiteDatabase database_1 = db_cal.getWritableDatabase();
        SQLiteDatabase database_2 = db_eat_today.getWritableDatabase();

        Cursor cursor_1 = database_1.query(DB_CAL.TABLE_NAME1, null, null, null, null, null, null);
        Cursor cursor_2 = database_2.query(DB_eat_today.TABLE_NAME1, null, null, null, null, null, null);


        if (cursor_1.moveToFirst()) {
            cursor_1.moveToLast();
            int calIndex = cursor_1.getColumnIndex(DB_CAL.COLUMN_CALORIES);
            int fatsIndex = cursor_1.getColumnIndex(DB_CAL.COLUMN_FATS);
            int squIndex = cursor_1.getColumnIndex(DB_CAL.COLUMN_SQUIRRELS);
            int carbIndex = cursor_1.getColumnIndex(DB_CAL.COLUMN_CARBOHYDRATES);

            float cal = cursor_1.getFloat(calIndex);
            float fats = cursor_1.getFloat(fatsIndex);
            float squ = cursor_1.getFloat(squIndex);
            float carb = cursor_1.getFloat(carbIndex);

            float count_cal = 0;
            float count_fats = 0;
            float count_squ = 0;
            float count_carb = 0;
            float gr = 0;

            if (cursor_2.moveToFirst()) {
                cursor_2.moveToFirst();
                int eat_calIndex = cursor_2.getColumnIndex(DB_eat_today.COLUMN_EAT_CAL);
                int eat_fatsIndex = cursor_2.getColumnIndex(DB_eat_today.COLUMN_EAT_FATS);
                int eat_squIndex = cursor_2.getColumnIndex(DB_eat_today.COLUMN_EAT_SQUIR);
                int eat_carbIndex = cursor_2.getColumnIndex(DB_eat_today.COLUMN_EAT_CARB);
                int eat_grIndex = cursor_2.getColumnIndex(DB_eat_today.COLUMN_EAT_Gr);
                do {
                    gr = cursor_2.getFloat(eat_grIndex);
                    count_cal += (cursor_2.getFloat(eat_calIndex) * (gr / 100));
                    count_fats += (cursor_2.getFloat(eat_fatsIndex) * (gr / 100));
                    count_squ += (cursor_2.getFloat(eat_squIndex) * (gr / 100));
                    count_carb += (cursor_2.getFloat(eat_carbIndex) * (gr / 100));
                } while (cursor_2.moveToNext());
            }

            DecimalFormat df = new DecimalFormat("#####.##");

            textView_cal.setText(df.format(cal - count_cal));
            textView_fats.setText(df.format(fats - count_fats));
            textView_carb.setText(df.format(carb - count_carb));
            textView_squ.setText(df.format(squ - count_squ));
        }
        button_cha_norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(App_screen.this, Change_activ.class);
                startActivity(intent);
            }
        });

        button_add_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App_screen.this, Add_prod_act.class);
                startActivity(intent);
            }
        });


    }
}