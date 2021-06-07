package com.example.habitos_saludables_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarActividadActivity extends AppCompatActivity {
    String idHabito;
    private Button calendario;
    TextView encabezado;
    ControlBD helper;
    EditText codigo,fecha,comentario;
    Habito habito;
    RegistroAdapter adapter;
    List<Registro> listaRegistro;
    List<Registro> miLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad);
        helper = new ControlBD(this);
        encabezado = findViewById(R.id.idhabitoR);
        Bundle extras = getIntent().getExtras();
        idHabito = extras.getString("idHabito");
        codigo = findViewById(R.id.idhabitoR2);
        fecha = findViewById(R.id.editfechaR);
        comentario = findViewById(R.id.editComentarioR);
        helper.abrir();
        habito = helper.consultarHabito(idHabito);
        helper.cerrar();
        encabezado.setText("Registrar actividad de habito: "+ idHabito+ " - " +habito.getNomHabito());
            //The key argument here must match that used in the other activity
        codigo.setText(idHabito);
        Sesion sesion = new Sesion();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        fecha.setText(date);
        llenarListaRegistro(idHabito);
        calendario = findViewById(R.id.calendarioVer);


        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.abrir();
                miLista = helper.getRegistroList(idHabito);
                helper.cerrar();

                Intent intent=new Intent(RegistrarActividadActivity.this,CalendarioActivity.class);
                intent.putExtra("miLista", (Serializable) miLista);
                startActivity(intent);
            }
        });


    }
    public void insertarRegistro(View v) {

            helper.cerrar();
            Registro registro = new Registro();
            registro.setIdHabito(idHabito);
            registro.setIdUsuario(habito.getIdUsuario());
            registro.setIdDia(idHabito+fecha.getText().toString());
            registro.setComentario(comentario.getText().toString());
            registro.setFecha(fecha.getText().toString());

            String regInsertados;
            helper.abrir();
            regInsertados=helper.insertar(registro);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            llenarListaRegistro(idHabito);


    }
    public void limpiarTextoR(View v) {
        comentario.setText("");
    }
    public class RegistroAdapter extends ArrayAdapter<Registro> {
        public RegistroAdapter(Context context, List<Registro> registros) {
            super(context, 0, registros);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Registro registro = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_registro, parent, false);
            }
            // Lookup view for data population
            TextView codigo = (TextView) convertView.findViewById(R.id.codigoRegistro);
            TextView fecha = (TextView) convertView.findViewById(R.id.fecha);
            TextView comentario = (TextView) convertView.findViewById(R.id.comentario);

            // Populate the data into the template view using the data object
            codigo.setText(registro.getIdDia());
            fecha.setText(registro.getFecha());
            comentario.setText(registro.getComentario());
            return convertView;
        }
    }
    public void llenarListaRegistro(String ids)
    {
        helper.abrir();
        listaRegistro = helper.getRegistroList(ids);
        helper.cerrar();


        if(listaRegistro.size() > 0){

            adapter= new RegistroAdapter(this, listaRegistro);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listRegistros);
            listView.setAdapter(adapter);
        }
        else{
            Toast.makeText(this, "No hay registros en la base" + ids, Toast.LENGTH_SHORT).show();
        }
    }


}