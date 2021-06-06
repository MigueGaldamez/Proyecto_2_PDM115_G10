package com.example.habitos_saludables_g10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ControlBD {
    private static final String[]camposUsuario = new String [] {"idUsuario","nomUsuario","fechaUnion","correo","urlImagen"};
    private static final String[]camposHabito = new String [] {"idHabito","nomHabito","fechaCreacion","objetivo","idUsuario"};
    private static final String[]camposHorario = new String [] {"idHorario","lun","mar","mie","jue","vie","sab","dom","hora","idHabito"};
    private static final String[]camposDiasRealizado = new String [] {"idDia","idHabito","idUsuario","fecha","comentario"};
    private static final String[]camposSesion =new String[]{"idSesion","idUsuario"};

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ControlBD(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
        setDBHelper(DBHelper);
    }

    public DatabaseHelper getDBHelper() {
        return DBHelper;
    }

    public void setDBHelper(DatabaseHelper DBHelper) {
        this.DBHelper = DBHelper;
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "capacitacion.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL("CREATE TABLE usuario(idUsuario VARCHAR(400) NOT NULL PRIMARY KEY,nomUsuario VARCHAR(100),fechaUnion VARCHAR(40),correo VARCHAR(300),urlImagen VARCHAR(600));");
                db.execSQL("CREATE TABLE habito(idHabito VARCHAR(6) NOT NULL PRIMARY KEY,nomHabito VARCHAR(30),fechaCreacion VARCHAR(40),objetivo VARCHAR(50),idUsuario VARCHAR(400));");
                db.execSQL("CREATE TABLE horario(idHorario VARCHAR(6) NOT NULL PRIMARY KEY,lun VARCHAR(1),mar VARCHAR(1),mie VARCHAR(1), jue VARCHAR(1), vie VARCHAR(1), sab VARCHAR(1), dom VARCHAR(1), hora VARCHAR(40), idHabito VARCCHAR(6) );");
                db.execSQL("CREATE TABLE diaRealizado(idDia VARCHAR(6) NOT NULL PRIMARY KEY,idHabito VARCHAR(6), idUsuario VARCHAR(400), fecha VARCHAR(40), comentario VARCHAR(100));");
                db.execSQL("CREATE TABLE sesion(idSesion VARCHAR(6) NOT NULL PRIMARY KEY, idUsuario VARCHAR(400))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
    public void abrir() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar() {
        DBHelper.close();
    }

    public String insertar(Usuario usuario) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues usu = new ContentValues();
        usu.put("idUsuario", usuario.getIdUsuario());
        usu.put("nomUsuario", usuario.getNomUsuario());
        usu.put("fechaUnion", usuario.getFechaUnion());
        usu.put("urlImagen", usuario.getUrlImagen());
        usu.put("correo", usuario.getCorreo());

        contador = db.insert("usuario", null, usu);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public String insertar(Habito habito,Horario horario) {

        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues habi = new ContentValues();
        habi.put("idHabito", habito.getIdHabito());
        habi.put("fechaCreacion", habito.getFechaCreacion());
        habi.put("nomHabito", habito.getNomHabito());
        habi.put("objetivo", habito.getObjetivo());
        habi.put("idUsuario", habito.getIdUsuario());
        contador = db.insert("habito", null, habi);

        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            contador=0;
            //   private String idHorario, lun, mar, mie,jue,vie,sab,dom,hora,idHabito;

            ContentValues hora = new ContentValues();
            hora.put("idHorario", horario.getIdHorario());
            hora.put("lun", horario.getLun());
            hora.put("mar", horario.getMar());
            hora.put("mie", horario.getMie());
            hora.put("jue", horario.getJue());
            hora.put("vie", horario.getVie());
            hora.put("sab", horario.getSab());
            hora.put("dom", horario.getDom());
            hora.put("hora", horario.getHora());
            hora.put("idHabito", horario.getIdHabito());
            contador = db.insert("horario", null, hora);

            if (contador == -1 || contador == 0) {
                regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
            }
            else
            {
                regInsertados = regInsertados + contador;
            }
        }
        return regInsertados;
    }

    public String insertar(Sesion sesion) {
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues sesi = new ContentValues();
        sesi.put("idUsuario", sesion.getIdUsuario());
        sesi.put("idSesion", sesion.getIdSesion());

        contador = db.insert("sesion", null, sesi);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public String insertar(Registro registro) {
        //p private String idDia,idHabito,idUsuario,fecha,comentario;
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;
        ContentValues sesi = new ContentValues();
        sesi.put("idDia", registro.getIdDia());
        sesi.put("idHabito", registro.getIdHabito());
        sesi.put("idUsuario", registro.getIdUsuario());
        sesi.put("fecha", registro.getFecha());
        sesi.put("comentario", registro.getComentario());

        contador = db.insert("diaRealizado", null, sesi);
        if (contador == -1 || contador == 0) {
            regInsertados = "Error solo se permite un registro por dia.";
        } else {
            regInsertados = regInsertados + contador;
        }
        return regInsertados;
    }
    public String eliminar(Sesion sesion) {
        String regAfectados = "filas afectadas= ";
        int contador = 0;
        String where = "idUsuario='" +sesion.getIdUsuario() + "'";
        contador += db.delete("sesion", where, null);
        regAfectados += contador;
        return regAfectados;
    }
    public boolean consultarSesionG(String id) {
        String[] idSesion = {id};
        Cursor cursor = db.query("sesion", camposSesion, "idUsuario = ?", idSesion, null, null, null);
        if (cursor.moveToFirst()) {
            Sesion sesion = new Sesion();
            sesion.setIdUsuario(cursor.getString(1));
            sesion.setIdSesion(cursor.getString(0));
            return true;
        } else {
            return false;
        }
    }
    public Sesion consultarSesion(String id) {
        String[] idSesion = {id};
        Cursor cursor = db.query("sesion", camposSesion, "idSesion = ?", idSesion, null, null, null);
        if (cursor.moveToFirst()) {
            Sesion sesion = new Sesion();
            sesion.setIdUsuario(cursor.getString(1));
            sesion.setIdSesion(cursor.getString(0));
            return sesion;
        } else {
            return null;
        }
    }
    public Habito consultarHabito(String id) {
        String[] idSesion = {id};
        Cursor cursor = db.query("habito", camposHabito, "idHabito = ?", idSesion, null, null, null);
        if (cursor.moveToFirst()) {
            Habito habito = new Habito();
            habito.setIdHabito(cursor.getString(0));
            habito.setNomHabito(cursor.getString(1));
            habito.setFechaCreacion(cursor.getString(2));
            habito.setObjetivo(cursor.getString(3));
            habito.setIdUsuario(cursor.getString(4));

            return habito;
        } else {
            return null;
        }
    }
    public Horario consultarHorario(String id) {
        String[] idSesion = {id};
        Cursor cursor = db.query("horario", camposHorario, "idHabito = ?", idSesion, null, null, null);
        if (cursor.moveToFirst()) {
            Horario horario = new Horario();
            horario.setIdHorario(cursor.getString(0));
            horario.setLun(cursor.getString(1));
            horario.setMar(cursor.getString(2));
            horario.setMie(cursor.getString(3));
            horario.setJue(cursor.getString(4));
            horario.setVie(cursor.getString(5));
            horario.setSab(cursor.getString(6));
            horario.setDom(cursor.getString(7));
            horario.setHora(cursor.getString(8));
            horario.setIdHabito(cursor.getString(9));
            return horario;
        } else {
            return null;
        }
    }

    public boolean consultarSiUsuario(String id) {
        String[] idUsuario = {id};
        Cursor cursor = db.query("usuario", camposUsuario, "idUsuario = ?", idUsuario, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public Usuario consultarUsuario(String id) {
        String[] idUsuario = {id};
        Cursor cursor = db.query("usuario", camposUsuario, "idUsuario = ?", idUsuario, null, null, null);
        if (cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(cursor.getString(0));
            usuario.setNomUsuario(cursor.getString(1));
            usuario.setFechaUnion(cursor.getString(2));
            usuario.setCorreo(cursor.getString(3));
            usuario.setUrlImagen(cursor.getString(4));
            return usuario;
        } else {
            return null;
        }
    }
    public List<Habito> getHabitoList(String id){

        //String sql = " SELECT * FROM habito";
        String[] idFacultad = {id};
        Cursor cursor = db.query("habito", camposHabito, "idUsuario = ?", idFacultad, null, null, null);

        List<Habito> listaHabito = new ArrayList<Habito>();
        //Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){

            do {
                String codigo = cursor.getString(0);
                String nombre = cursor.getString(1);
                String fecha = cursor.getString(2);
                String objetivo = cursor.getString(3);
                String usuario = cursor.getString(4);

                listaHabito.add(new Habito(codigo,nombre,fecha,objetivo,usuario));
            }while (cursor.moveToNext());
        }

        return listaHabito;
    }
    public List<Registro> getRegistroList(String id){

        //String sql = " SELECT * FROM habito";
        String[] idFacultad = {id};
        Cursor cursor = db.query("diaRealizado", camposDiasRealizado, "idHabito = ?", idFacultad, null, null, null);

        List<Registro> listaRegistro = new ArrayList<Registro>();
        //Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
// private String idDia,idHabito,idUsuario,fecha,comentario;
            do {
                String idDia = cursor.getString(0);
                String idHabito = cursor.getString(1);
                String idUsuario = cursor.getString(2);
                String fecha = cursor.getString(3);
                String comentario = cursor.getString(4);

                listaRegistro.add(new Registro(idDia,idHabito,idUsuario,fecha,comentario));
            }while (cursor.moveToNext());
        }

        return listaRegistro;
    }
}
