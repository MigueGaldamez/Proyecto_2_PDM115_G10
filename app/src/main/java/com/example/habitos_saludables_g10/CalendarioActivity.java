package com.example.habitos_saludables_g10;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CalendarioActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        tv = findViewById(R.id.textView);
    }


    public void abrirCalendario(View view){

        Calendar cal= Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        int mes= cal.get(Calendar.MONTH);
        int dia=cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd= new DatePickerDialog(com.example.habitos_saludables_g10.CalendarioActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha= dayOfMonth+"/"+(month+1)+"/"+anio;
                tv.setText((fecha));
            }
        }, anio, mes, dia);
        dpd.show();
    }
}