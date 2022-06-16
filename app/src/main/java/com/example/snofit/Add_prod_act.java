package com.example.snofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add_prod_act extends AppCompatActivity {

    EditText editTextTextName, editTextTextCal, editTextTextFats, editTextTextSquir,
            editTextTextCarb, editTextTextGr;

    Button butt;

    TextView textView;

    DB_eat_today db_eat_today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prod);

        editTextTextName = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextTextCal = (EditText) findViewById(R.id.editTextNumber3);
        editTextTextFats = (EditText) findViewById(R.id.editTextNumber9);
        editTextTextSquir = (EditText) findViewById(R.id.editTextNumber10);
        editTextTextCarb = (EditText) findViewById(R.id.editTextNumber11);
        editTextTextGr = (EditText) findViewById(R.id.editTextNumber12);

        butt = (Button) findViewById(R.id.button);

        textView = (TextView) findViewById(R.id.textView24);

        db_eat_today = new DB_eat_today(this);

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase database = db_eat_today.getWritableDatabase();


                ContentValues contentValues = new ContentValues();

                String Name = editTextTextName.getText().toString();
                String Cal = editTextTextCal.getText().toString();
                String Fats = editTextTextFats.getText().toString();
                String Squir = editTextTextSquir.getText().toString();
                String Carb = editTextTextCarb.getText().toString();
                String Gr = editTextTextGr.getText().toString();

                if (Name.length() == 0 || Name.length() >= 20) {
                    textView.setText("Заполните поле Name");
                }
                else {
                    if (Cal.length() == 0 || Integer.parseInt(Cal) > 800) {
                        textView.setText("Заполните поле Cal.");
                    }
                    else {
                        if (Fats.length() == 0 || Integer.parseInt(Fats) > 100) {
                            textView.setText("Заполните поле Fats");
                        }
                        else {
                            if (Squir.length() == 0 || Integer.parseInt(Squir) > 100) {
                                textView.setText("Заполните поле Squir.");
                            }
                            else {
                                if (Carb.length() == 0 || Integer.parseInt(Carb) >= 100) {
                                    textView.setText("Заполните поле Carb.");
                                }
                                else {
                                    if (Gr.length() == 0 || Integer.parseInt(Gr) > 10000) {
                                        textView.setText("Заполните поле Absol.");
                                    }
                                    else {
                                        contentValues.put(DB_eat_today.COLUMN_NAME, Name);
                                        contentValues.put(DB_eat_today.COLUMN_EAT_CAL, Cal);
                                        contentValues.put(DB_eat_today.COLUMN_EAT_FATS, Fats);
                                        contentValues.put(DB_eat_today.COLUMN_EAT_SQUIR, Squir);
                                        contentValues.put(DB_eat_today.COLUMN_EAT_CARB, Carb);
                                        contentValues.put(DB_eat_today.COLUMN_EAT_Gr, Gr);

                                        database.insert(DB_eat_today.TABLE_NAME1, null, contentValues);

                                        Intent intent = new Intent(Add_prod_act.this, App_screen.class);
                                        startActivity(intent);
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