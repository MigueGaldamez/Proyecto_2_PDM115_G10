package com.example.habitos_saludables_g10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.habitos_saludables_g10.audio.AudioControl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HabitoInsertarActivity extends AudioControl {
    ControlBD helper;
    EditText editIdHabito;
    EditText editNomHabito;
    EditText editObjetivo;
    EditText editHora;
    Switch lun, mar, mie,jue,vie,sab,dom;
    Button establecerHora,alarma;
    //subir
    CheckBox alarmasi;
    Calendar calendar;
    int hora,minuto,hora2,minuto2;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habito_insertar);
        editIdHabito = findViewById(R.id.editIdHabito);
        editNomHabito = findViewById(R.id.editNombre);
        editObjetivo = findViewById(R.id.editObjetivo);
        editHora = findViewById(R.id.editHora);
        lun = findViewById(R.id.idLun);
        mar = findViewById(R.id.idMar);
        mie = findViewById(R.id.idMie);
        jue = findViewById(R.id.idJue);
        vie = findViewById(R.id.idVie);
        sab = findViewById(R.id.idSab);
        dom = findViewById(R.id.idDom);
        establecerHora = findViewById(R.id.botonHora);
        helper = new ControlBD(this);
        alarmasi = findViewById(R.id.alarmaSi);

        establecerHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minuto = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(HabitoInsertarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editHora.setText(String.format("%02d",hourOfDay)+":"+String.format("%02d",minute));
                        hora2 = hourOfDay;
                        minuto2 = minute;
                    }
                },hora,minuto,false);
                timePickerDialog.show();
            }
        });
    }
    public void CrearAlarma(){

        if(!editHora.getText().toString().isEmpty()&&!editNomHabito.getText().toString().isEmpty())
        {
            ArrayList<Integer> alarmDays= new ArrayList<Integer>();
            if(lun.isChecked()) {
                alarmDays.add(Calendar.MONDAY);}
            if(mar.isChecked()) {
                alarmDays.add(Calendar.TUESDAY);}
            if(mie.isChecked()) {
                alarmDays.add(Calendar.WEDNESDAY);}
            if(jue.isChecked()) {
                alarmDays.add(Calendar.THURSDAY);}
            if(vie.isChecked()) {
                alarmDays.add(Calendar.FRIDAY);}
            if(sab.isChecked()) {
                alarmDays.add(Calendar.SATURDAY);}
            if(dom.isChecked()) {
                alarmDays.add(Calendar.SUNDAY);}

            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR,hora2);
            intent.putExtra(AlarmClock.EXTRA_MINUTES,minuto2);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE,editNomHabito.getText().toString());
            intent.putExtra(AlarmClock.EXTRA_DAYS,alarmDays);
            if(intent.resolveActivity(getPackageManager())!=null) {
                startActivity(intent);
            }else
            {
                Toast.makeText(this, "No hay aplicacion para alarma", Toast.LENGTH_SHORT).show();
            }
        }

    }
    public void insertarHabito(View v) {
        if (!(editIdHabito.getText().toString().equals("") || editNomHabito.getText().toString().equals("") || editObjetivo.getText().toString().equals("") || editHora.getText().toString().equals(""))){
            Sesion sesion = new Sesion();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(new Date());
            helper.abrir();
            if(helper.consultarSesion("SESI2")!=null)
            {
                sesion =helper.consultarSesion("SESI2");
            }
            else if(helper.consultarSesion("SESI1")!=null)
            {
                sesion =helper.consultarSesion("SESI1");
            }
            helper.cerrar();
            Habito habito = new Habito();
            habito.setIdHabito(editIdHabito.getText().toString());
            habito.setIdUsuario(sesion.getIdUsuario());
            habito.setFechaCreacion(date);
            habito.setNomHabito(editNomHabito.getText().toString());
            habito.setObjetivo(editObjetivo.getText().toString());

            Horario horario = new Horario();
            horario.setHora(editHora.getText().toString());
            horario.setIdHorario(editIdHabito.getText().toString());
            horario.setIdHabito(editIdHabito.getText().toString());
            if(lun.isChecked()) {
                horario.setLun("1");
            }else{ horario.setLun("0");}

            if(mar.isChecked()) {
                horario.setMar("1");
            }else{ horario.setMar("0");}

            if(mie.isChecked()) {
                horario.setMie("1");
            }else{ horario.setMie("0");}

            if(jue.isChecked()) {
                horario.setJue("1");
            }else{ horario.setJue("0");}

            if(vie.isChecked()) {
                horario.setVie("1");
            }else{ horario.setVie("0");}

            if(sab.isChecked()) {
                horario.setSab("1");
            }else{ horario.setSab("0");}

            if(dom.isChecked()) {
                horario.setDom("1");
            }else{ horario.setDom("0");}

            String regInsertados;
            helper.abrir();
            regInsertados=helper.insertar(habito,horario);
            helper.cerrar();
            //Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            if(alarmasi.isChecked())
            {
                CrearAlarma();
            }
        }
        else Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();

    }
    public void limpiarTexto(View v) {
        editHora.setText("");
        editObjetivo.setText("");
        editNomHabito.setText("");
        editIdHabito.setText("");
    }

    //*************************************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filtro_voz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onClick(item.getActionView());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        String audioText =  getAudioText(requestCode,resultCode,data);
       // Toast.makeText(this, audioText, Toast.LENGTH_SHORT).show();
        activityStart(audioText);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy(){
        super.onDestroy();
    }

    public void activityStart(String parametroActivity){
        Intent intent;
        switch (parametroActivity){
            case "limpiar":
                limpiarTexto(new View(this));
                break;
            case "registrar":
                insertarHabito(new View(this));
                break;

        }

    }
    //****************************************************************************************************************************

}