package com.example.snofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Change_activ extends AppCompatActivity {

    Button button_app;

    EditText editText_name, editText_age, editText_height, editText_weight;

    TextView textView_name, textView_age, textView_height, textView_weight, textView_gender, textView_er;

    CheckBox checkBox, checkBox2, checkBox3 , checkBox4;

    DB_CAL db_cal;
    DB db_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        button_app = findViewById(R.id.button_reg2);

        editText_name = (EditText) findViewById(R.id.editTextTextPersonName4);
        editText_age = (EditText) findViewById(R.id.editTextNumber6);
        editText_height = (EditText) findViewById(R.id.editTextNumber7);
        editText_weight = (EditText) findViewById(R.id.editTextNumber8);

        textView_name = (TextView) findViewById(R.id.textView33);
        textView_age = (TextView) findViewById(R.id.textView34);
        textView_height = (TextView) findViewById(R.id.textView36);
        textView_weight = (TextView) findViewById(R.id.textView35);
        textView_gender = (TextView) findViewById(R.id.textView26);
        textView_er = (TextView) findViewById(R.id.textView27);

        checkBox = (CheckBox) findViewById(R.id.checkBox7);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox8);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);

        db_user = new DB(this);
        db_cal = new DB_CAL(this);

        SQLiteDatabase database = db_user.getWritableDatabase();
        Cursor cursor = database.query(DB.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
            int ageIndex = cursor.getColumnIndex(DB.COLUMN_YEARS);
            int nameIndex = cursor.getColumnIndex(DB.COLUMN_NAME);
            int heightIndex = cursor.getColumnIndex(DB.COLUMN_HEIGHT);
            int genderIndex = cursor.getColumnIndex(DB.COLUMN_GENDERS);

            float weight_f = cursor.getFloat(weightIndex);
            float age_f = cursor.getFloat(ageIndex);
            String name_f = cursor.getString(nameIndex);
            float height_f = cursor.getFloat(heightIndex);
            String gender_f = cursor.getString(genderIndex);

            DecimalFormat df = new DecimalFormat("#####.##");

            textView_name.setText(name_f);
            textView_age.setText(df.format(age_f));
            textView_height.setText(df.format(height_f));
            textView_weight.setText(df.format(weight_f));
            textView_gender.setText(gender_f);
        }
        button_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float cal = 0;
                float fats = 0;
                float squ = 0;
                float carb = 0;

                String cal_s = "";
                String fats_s = "";
                String squ_s = "";
                String carb_s = "";

                String gender = "";
                String des_w = "";

                SQLiteDatabase database = db_user.getWritableDatabase();
                SQLiteDatabase database_1 = db_cal.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                ContentValues contentValues_1 = new ContentValues();

                String name_e = editText_name.getText().toString();
                String years_e = editText_age.getText().toString();
                String height_e = editText_height.getText().toString();
                String weight_e = editText_weight.getText().toString();

                if (name_e.length() <= 2 || name_e.length() > 20) {
                    textView_er.setText("Некорректное Имя");
                }
                else {
                    if (years_e.length() <= 1 || years_e.length() >= 3) {
                        textView_er.setText("Некорректный Возраст");
                    }
                    else {
                        if (height_e.length() <= 2 || height_e.length() >= 4) {
                            textView_er.setText("Некорректный Рост");
                        }
                        else {
                            if (weight_e.length() <= 1 || weight_e.length() >= 4) {
                                textView_er.setText("Некорректный Вес");
                            } else {
                                if (checkBox.isChecked() && checkBox2.isChecked()) {
                                    textView_er.setText("Некорректный Пол");
                                } else {
                                    if (checkBox.isChecked()) {
                                        gender = "man";
                                        if (checkBox3.isChecked() && checkBox4.isChecked()) {
                                            textView_er.setText("Некорректный Режим веса");
                                        } else {
                                            if (checkBox3.isChecked()) {
                                                database.delete(DB.TABLE_NAME, null, null);
                                                des_w = "15";

                                                contentValues.put(DB.COLUMN_NAME, name_e);
                                                contentValues.put(DB.COLUMN_YEARS, years_e);
                                                contentValues.put(DB.COLUMN_HEIGHT, height_e);
                                                contentValues.put(DB.COLUMN_WEIGHT, weight_e);
                                                contentValues.put(DB.COLUMN_GENDERS, gender);
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                                database.insert(DB.TABLE_NAME, null, contentValues);

                                                if (cursor.moveToLast()) {
                                                    cursor.moveToLast();
                                                    int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                                    float weight_f = cursor.getFloat(weightIndex);
                                                    int desIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);
                                                    float desw_f = cursor.getFloat(desIndex);

                                                    cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                                    fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                                    squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                                    carb = (float) (((cal * 50.0) / 100.0) / 4.0);

                                                    cal_s = Float.toString(cal);
                                                    fats_s = Float.toString(fats);
                                                    squ_s = Float.toString(squ);
                                                    carb_s = Float.toString(carb);

                                                    contentValues_1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_FATS, fats_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                                    database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues_1);
                                                    Intent intent = new Intent(Change_activ.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            } else if (checkBox4.isChecked()) {
                                                des_w = "-15";
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);
                                                contentValues.put(DB.COLUMN_NAME, name_e);
                                                contentValues.put(DB.COLUMN_YEARS, years_e);
                                                contentValues.put(DB.COLUMN_HEIGHT, height_e);
                                                contentValues.put(DB.COLUMN_WEIGHT, weight_e);
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                                database.insert(DB.TABLE_NAME, null, contentValues);

                                                if (cursor.moveToLast()) {
                                                    cursor.moveToLast();
                                                    int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                                    float weight_f = cursor.getFloat(weightIndex);
                                                    int desIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);
                                                    float desw_f = cursor.getFloat(desIndex);

                                                    cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                                    fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                                    squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                                    carb = (float) (((cal * 50.0) / 100.0) / 4.0);

                                                    cal_s = Float.toString(cal);
                                                    fats_s = Float.toString(fats);
                                                    squ_s = Float.toString(squ);
                                                    carb_s = Float.toString(carb);

                                                    contentValues_1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_FATS, fats_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                                    database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues_1);
                                                    Intent intent = new Intent(Change_activ.this, MainActivity.class);
                                                    startActivity(intent);
                                                }

                                            } else {
                                                textView_er.setText("Заполните поле Режим веса");
                                            }
                                        }
                                    } else if (checkBox2.isChecked()) {
                                        gender = "woman";
                                        if (checkBox3.isChecked() && checkBox4.isChecked()) {
                                            textView_er.setText("Некорректный Режим веса");
                                        } else {
                                            if (checkBox3.isChecked()) {
                                                database.delete(DB.TABLE_NAME, null, null);
                                                des_w = "10";

                                                contentValues.put(DB.COLUMN_NAME, name_e);
                                                contentValues.put(DB.COLUMN_YEARS, years_e);
                                                contentValues.put(DB.COLUMN_HEIGHT, height_e);
                                                contentValues.put(DB.COLUMN_WEIGHT, weight_e);
                                                contentValues.put(DB.COLUMN_GENDERS, gender);
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                                database.insert(DB.TABLE_NAME, null, contentValues);

                                                if (cursor.moveToFirst()) {
                                                    cursor.moveToLast();
                                                    int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                                    float weight_f = cursor.getFloat(weightIndex);
                                                    int desIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);
                                                    float desw_f = cursor.getFloat(desIndex);

                                                    cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                                    fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                                    squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                                    carb = (float) (((cal * 50.0) / 100.0) / 4.0);

                                                    cal_s = Float.toString(cal);
                                                    fats_s = Float.toString(fats);
                                                    squ_s = Float.toString(squ);
                                                    carb_s = Float.toString(carb);

                                                    contentValues_1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_FATS, fats_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                                    database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues_1);
                                                    Intent intent = new Intent(Change_activ.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            } else if (checkBox4.isChecked()) {
                                                des_w = "-10";
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);
                                                contentValues.put(DB.COLUMN_NAME, name_e);
                                                contentValues.put(DB.COLUMN_YEARS, years_e);
                                                contentValues.put(DB.COLUMN_HEIGHT, height_e);
                                                contentValues.put(DB.COLUMN_WEIGHT, weight_e);
                                                contentValues.put(DB.COLUMN_DESIRED_WEIGHT, des_w);

                                                database.insert(DB.TABLE_NAME, null, contentValues);

                                                if (cursor.moveToFirst()) {
                                                    cursor.moveToLast();
                                                    int weightIndex = cursor.getColumnIndex(DB.COLUMN_WEIGHT);
                                                    float weight_f = cursor.getFloat(weightIndex);
                                                    int desIndex = cursor.getColumnIndex(DB.COLUMN_DESIRED_WEIGHT);
                                                    float desw_f = cursor.getFloat(desIndex);

                                                    cal = (float) (((weight_f * 24.0) * (100.0 + desw_f)) / 100.0);
                                                    fats = (float) (((cal * 20.0) / 100.0) / 9.0);
                                                    squ = (float) (((cal * 30.0) / 100.0) / 4.0);
                                                    carb = (float) (((cal * 50.0) / 100.0) / 4.0);

                                                    cal_s = Float.toString(cal);
                                                    fats_s = Float.toString(fats);
                                                    squ_s = Float.toString(squ);
                                                    carb_s = Float.toString(carb);

                                                    contentValues_1.put(DB_CAL.COLUMN_CALORIES, cal_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_FATS, fats_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_SQUIRRELS, squ_s);
                                                    contentValues_1.put(DB_CAL.COLUMN_CARBOHYDRATES, carb_s);


                                                    database_1.insert(DB_CAL.TABLE_NAME1, null, contentValues_1);
                                                    Intent intent = new Intent(Change_activ.this, MainActivity.class);
                                                    startActivity(intent);
                                                }

                                            } else {
                                                textView_er.setText("Заполните поле Режим веса");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}