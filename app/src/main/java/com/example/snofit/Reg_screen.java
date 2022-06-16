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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class Reg_screen extends AppCompatActivity implements View.OnClickListener{

    Button button_reg;
    EditText editTextTextPersonName, editTextNumber2, editTextDate, editTextNumber;
    TextView textView10;
    CheckBox checkBox, checkBox2, checkBox3 , checkBox4;

    DB_CAL db_cal;
    DB db_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_screen);

        button_reg = (Button) findViewById(R.id.button_reg);
        button_reg.setOnClickListener(this);

        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextNumber2 = (EditText) findViewById(R.id.editTextNumber2);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);

        textView10 = (TextView) findViewById(R.id.textView10);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox6);

        db_user = new DB(this);
        db_cal = new DB_CAL(this);

    }

    @Override
    public void onClick(View view) {
        String name = editTextTextPersonName.getText().toString();
        String years = editTextNumber2.getText().toString();
        String height = editTextDate.getText().toString();
        String weight = editTextNumber.getText().toString();
        String gender = "";
        String des_w = "";

        float cal = 0;
        float fats = 0;
        float squ = 0;
        float carb = 0;

        String cal_s = "";
        String fats_s = "";
        String squ_s = "";
        String carb_s = "";


        SQLiteDatabase database = db_user.getWritableDatabase();
        SQLiteDatabase database_1 = db_cal.getWritableDatabase();

        Cursor cursor = database.query(DB.TABLE_NAME, null, null, null, null, null, null);

        ContentValues contentValues = new ContentValues();
        ContentValues contentValues1 = new ContentValues();


        if (name.length() < 3) {
            textView10.setText("Заполните поле Имя");
        }
        else {
            if (years.length() == 0) {
                textView10.setText("Заполните поле Возраст");
            }
            else if (years.length() >= 3) {
                textView10.setText("Некорректный Возраст");
            }
            else if (years.length() < 2) {
                textView10.setText("Некорректный Возраст");
            }
            else {
                if (height.length() <= 2 || height.length() >= 4){
                    textView10.setText("Некорректный Рост");
                }
                else if (height.length() == 0)
                    textView10.setText("Заполните поле Возраст");
                else {
                    if (weight.length() < 2 || weight.length() >= 4) {
                        textView10.setText("Некорректный Вес");
                    }
                    else if (weight.length() == 0)
                        textView10.setText("Заполните поле Возраст");
                    else {
                        if (checkBox.isChecked() && checkBox2.isChecked()) {
                            textView10.setText("Некорректный Пол");
                        }
                        else {
                            if (checkBox.isChecked()) {
                                gender = "man";

                                contentValues.put(DB.COLUMN_NAME, name);
                                contentValues.put(DB.COLUMN_YEARS, years);
                                contentValues.put(DB.COLUMN_HEIGHT, height);
                                contentValues.put(DB.COLUMN_WEIGHT, weight);

                                if (checkBox3.isChecked() && checkBox4.isChecked()) {
                                    textView10.setText("Некорректный Режим веса");
                                }
                                else {
                                    if (checkBox3.isChecked()) {
                                        des_w = "15";
                                        contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                        database.insert(DB.TABLE_NAME, null, contentValues);

                                        if (cursor.moveToFirst()) {

                                            cursor.moveToFirst();
                                            int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                            int deswIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);

                                            float weight_f = cursor.getFloat(weightIndex);
                                            float desw_f = cursor.getFloat(deswIndex);

                                            cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                            fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                            squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                            carb = (float) (((cal * 50.0)/ 100.0) / 4.0);

                                            cal_s = Float.toString(cal);
                                            fats_s = Float.toString(fats);
                                            squ_s = Float.toString(squ);
                                            carb_s = Float.toString(carb);

                                            contentValues1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                            contentValues1.put(DB_CAL.COLUMN_FATS, fats_s);
                                            contentValues1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                            contentValues1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                            database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues1);
                                        }

                                        Intent intent = new Intent(Reg_screen.this, App_screen.class);
                                        startActivity(intent);
                                    }
                                    else if (checkBox4.isChecked()) {
                                        des_w = "-15";
                                        contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                        database.insert(DB.TABLE_NAME, null, contentValues);

                                        if (cursor.moveToFirst()) {

                                            cursor.moveToFirst();
                                            int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                            int deswIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);

                                            float weight_f = cursor.getFloat(weightIndex);
                                            float desw_f = cursor.getFloat(deswIndex);

                                            cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                            fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                            squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                            carb = (float) (((cal * 50.0)/ 100.0) / 4.0);

                                            cal_s = Float.toString(cal);
                                            fats_s = Float.toString(fats);
                                            squ_s = Float.toString(squ);
                                            carb_s = Float.toString(carb);

                                            contentValues1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                            contentValues1.put(DB_CAL.COLUMN_FATS, fats_s);
                                            contentValues1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                            contentValues1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                            database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues1);
                                        }

                                        Intent intent = new Intent(Reg_screen.this, App_screen.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        textView10.setText("Заполните поле Режим веса");
                                    }
                                }
                                }
                            else if (checkBox2.isChecked()) {
                                gender = "woman";

                                contentValues.put(DB.COLUMN_NAME, name);
                                contentValues.put(DB.COLUMN_YEARS, years);
                                contentValues.put(DB.COLUMN_HEIGHT, height);
                                contentValues.put(DB.COLUMN_WEIGHT, weight);
                                contentValues.put(DB.COLUMN_GENDERS, gender);

                                if (checkBox3.isChecked() && checkBox4.isChecked()) {
                                    textView10.setText("Некорректный Режим веса");
                                }
                                else {
                                    if (checkBox3.isChecked()) {
                                        des_w = "10";
                                        contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                        database.insert(DB.TABLE_NAME, null, contentValues);

                                        if (cursor.moveToLast()) {

                                            cursor.moveToLast();
                                            int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                            int deswIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);

                                            float weight_f = cursor.getFloat(weightIndex);
                                            float desw_f = cursor.getFloat(deswIndex);

                                            cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                            fats = (float) (((cal * 25.0) / 100.0) / 9.0);
                                            squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                            carb = (float) (((cal * 45.0) / 100.0) / 4.0);

                                            cal_s = Float.toString(cal);
                                            fats_s = Float.toString(fats);
                                            squ_s = Float.toString(squ);
                                            carb_s = Float.toString(carb);

                                            contentValues1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                            contentValues1.put(DB_CAL.COLUMN_FATS, fats_s);
                                            contentValues1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                            contentValues1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                            database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues1);
                                        }

                                        Intent intent = new Intent(Reg_screen.this, App_screen.class);
                                        startActivity(intent);
                                    }
                                    else if (checkBox4.isChecked()) {
                                        des_w = "-10";
                                        contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                        database.insert(DB.TABLE_NAME, null, contentValues);

                                        if (cursor.moveToLast()) {

                                            cursor.moveToLast();
                                            int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                            int deswIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);

                                            float weight_f = cursor.getFloat(weightIndex);
                                            float desw_f = cursor.getFloat(deswIndex);

                                            cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                            fats = (float) (((cal * 25.0) / 100.0) / 9.0);
                                            squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                            carb = (float) (((cal * 45.0) / 100.0) / 4.0);

                                            cal_s = Float.toString(cal);
                                            fats_s = Float.toString(fats);
                                            squ_s = Float.toString(squ);
                                            carb_s = Float.toString(carb);

                                            contentValues1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                            contentValues1.put(DB_CAL.COLUMN_FATS, fats_s);
                                            contentValues1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                            contentValues1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                            database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues1);
                                        }

                                        Intent intent = new Intent(Reg_screen.this, App_screen.class);
                                        startActivity(intent);

                                    }
                                    else {
                                        textView10.setText("Заполните поле Режим веса");
                                    }
                                }

                            }
                            else {
                                textView10.setText("Заполните поле Пол");
                            }
                        }
                    }
                }
            }
        }
    }
}