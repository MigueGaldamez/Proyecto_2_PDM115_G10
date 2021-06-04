package com.example.habitos_saludables_g10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.habitos_saludables_g10.audio.AudioControl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HabitoInsertarActivity extends AudioControl {
    ControlBD helper;
    EditText editIdHabito;
    EditText editNomHabito;
    EditText editObjetivo;
    EditText editHora;
    Switch lun, mar, mie,jue,vie,sab,dom;
    //subir
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habito_insertar);
        editIdHabito = findViewById(R.id.editIdHabito);
        editNomHabito = findViewById(R.id.editNombre);
        editObjetivo = findViewById(R.id.editObjetivo);
        editHora = findViewById(R.id.editHora);
        lun = findViewById(R.id.idLun);
        mar = findViewById(R.id.idLun);
        mie = findViewById(R.id.idLun);
        jue = findViewById(R.id.idLun);
        vie = findViewById(R.id.idLun);
        sab = findViewById(R.id.idLun);
        dom = findViewById(R.id.idLun);
        helper = new ControlBD(this);
    }
    public void insertarHabito(View v) {
        if (!(editIdHabito.getText().toString().equals("") || editNomHabito.getText().toString().equals("") || editObjetivo.getText().toString().equals("") || editHora.getText().toString().equals(""))){
            Sesion sesion = new Sesion();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
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