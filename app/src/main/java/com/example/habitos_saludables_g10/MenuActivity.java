package com.example.habitos_saludables_g10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ImageView imagen_perfil;
    private TextView nombre,id,email;
    private Button salirbtn;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private String idsesion;
    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        imagen_perfil = findViewById(R.id.perfil);
        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        id = findViewById(R.id.id);
        salirbtn = findViewById(R.id.singoutbtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idsesion = extras.getString("idUsuario");
            //The key argument here must match that used in the other activity
        }
        if (idsesion.equals("0")) {
            Toast.makeText(this, "Usuario de Gmail", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Usuario de Facebook", Toast.LENGTH_SHORT).show();
        }

        helper = new ControlBD(this);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        salirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()){
                                    gotoMainActivity();
                                }else{
                                    Toast.makeText(getApplicationContext(),"No se ha cerrado la sesion", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            helper.abrir();
            boolean estado = helper.consultarSiUsuario(account.getId());
            helper.cerrar();
            if(!estado)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());
                helper.abrir();
                Usuario usuario = new Usuario();
                usuario.setUrlImagen(account.getPhotoUrl().toString());
                usuario.setCorreo(account.getEmail());
                usuario.setNomUsuario(account.getDisplayName());
                usuario.setIdUsuario(account.getId());
                usuario.setFechaUnion(date);
                helper.insertar(usuario);
                helper.cerrar();
                Toast.makeText(this, "No estaba", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Shi estaba", Toast.LENGTH_SHORT).show();
            }
            helper.abrir();
            Usuario usuario = helper.consultarUsuario(account.getId());
            helper.cerrar();
            nombre.setText(usuario.getNomUsuario());
            email.setText(usuario.getCorreo());
            id.setText(usuario.getIdUsuario());
            Picasso.get().load(usuario.getUrlImagen()).placeholder(R.mipmap.ic_launcher).into(imagen_perfil);

        }else
        {
            helper.abrir();
            Usuario usuario = helper.consultarUsuario(idsesion);
            helper.cerrar();
            nombre.setText(usuario.getNomUsuario());
            email.setText(usuario.getCorreo());
            id.setText(usuario.getIdUsuario());
            Picasso.get().load(usuario.getUrlImagen()).placeholder(R.mipmap.ic_launcher).into(imagen_perfil);
        }
    }
    private void gotoMainActivity(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}