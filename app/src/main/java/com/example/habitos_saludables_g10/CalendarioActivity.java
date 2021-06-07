package com.example.habitos_saludables_g10;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;


import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarioActivity extends AppCompatActivity {
    TextView tv;
    CustomCalendar customCalendar;
    List<Registro> miLista;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        miLista = (List<Registro>) getIntent().getSerializableExtra("miLista");
        //   tv = findViewById(R.id.textView);
        customCalendar=findViewById(R.id.custom_calendar);
        HashMap<Object, Property>  descHashMap = new HashMap<>();

        Property defaultProperty= new Property();

        defaultProperty.layoutResource=R.layout.defaul_view;

        //ini
        defaultProperty.dateTextViewResource=R.id.text_view;
        //
        descHashMap.put("default", defaultProperty);
        //
        Property currentProperty =new Property();
        currentProperty.layoutResource=R.layout.current_view;
        currentProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("current", currentProperty);

        //for
        Property presentProperty=new Property();
        presentProperty.layoutResource= R.layout.present_view;
        presentProperty.dateTextViewResource=R.id.text_view;
        descHashMap.put("present", presentProperty);

        //forabsent
        Property absenProperty =new Property();
        absenProperty.layoutResource=R.layout.absent_view;
        absenProperty.dateTextViewResource= R.id.text_view;
        descHashMap.put("absent", absenProperty);

        //set desc has map
        customCalendar.setMapDescToProp(descHashMap);


        //ini
        HashMap<Integer, Object> dateHashMap= new HashMap<>();
        //Initilize calendar
        Calendar calendar=Calendar.getInstance();
        //put values
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");

        for (int i=0; i< miLista.size();i++) {
            String nume=miLista.get(i).getFecha();
            String[] parts = nume.split("-");
            int dia = Integer. parseInt(parts[0]);
            dateHashMap.put(dia,"present");
        }



        //setd dtae
        customCalendar.setDate(calendar, dateHashMap);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                //get string date
                String sDate= selectedDate.get(Calendar.DAY_OF_MONTH)
                        +"/"+selectedDate.get(Calendar.MONTH)
                        +"/"+selectedDate.get(Calendar.YEAR);
                //display
                Toast.makeText(getApplicationContext(),sDate, Toast.LENGTH_SHORT).show();
            }
        });



    }
  /*
//Codigo para crear un calendario normal
    public void abrirCalendario(View view) {

        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(com.example.habitos_saludables_g10.CalendarioActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month + 1) + "/" + anio;
                tv.setText((fecha));
            }

        }, anio, mes, dia);

        dpd.show();

    }*/
}