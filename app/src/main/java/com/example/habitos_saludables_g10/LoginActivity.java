package com.example.habitos_saludables_g10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private TextView info;
    private ImageView perfil;
    private LoginButton login;
    private CallbackManager callbackManager;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int SING_IN=1;
    int idC = 0;
    ControlBD helper;

    //LLENADOBASE
    private String cIdUsuario,cNomUsuario,cCorreoUsuario,cUrlImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        info = findViewById(R.id.info);
        perfil = findViewById(R.id.perfil);
        login = findViewById(R.id.login_button);
        helper = new ControlBD(this);

        login.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = new CallbackManager.Factory().create();
        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("Success");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    System.out.println("ERROR");
                                } else {
                                    System.out.println("Success");
                                    try {
                                        String jsonresult = String.valueOf(json);
                                        //System.out.println("JSON Result"+jsonresult);
                                        String name = json.getString("name");
                                        String str_email = json.getString("email");
                                        String str_id = json.getString("id");
                                        String imageURL ="https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1&access_token="+loginResult.getAccessToken().getToken()+"&type=normal";
                                        //System.out.println("token"+loginResult.getAccessToken().getToken());
                                        helper.abrir();
                                        boolean estado = helper.consultarSiUsuario(str_id);
                                        helper.cerrar();
                                        if(!estado)
                                        {
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            String date = sdf.format(new Date());
                                            helper.abrir();
                                            Usuario usuario = new Usuario();
                                            usuario.setUrlImagen(imageURL);
                                            usuario.setCorreo(str_email);
                                            usuario.setNomUsuario(name);
                                            usuario.setIdUsuario(str_id);
                                            usuario.setFechaUnion(date);
                                            helper.insertar(usuario);
                                            helper.cerrar();
                                        }
                                        helper.abrir();
                                        Sesion sesion = new Sesion();
                                        sesion.setIdUsuario(str_id);
                                        sesion.setIdSesion("SESI1");
                                        helper.insertar(sesion);
                                        helper.cerrar();
                                        Intent intent =new Intent(LoginActivity.this,MenuPrincipalActivity.class);
                                        intent.putExtra("idUsuario",str_id);
                                        startActivity(intent);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "error:" +error, Toast.LENGTH_SHORT).show();
            }
        });

        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        signInButton = findViewById(R.id.google_sing_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SING_IN);
                idC=1;
            }
        });

        helper.abrir();
        if(helper.consultarSesion("SESI2")!=null)
        {
            Intent intent =new Intent(LoginActivity.this,MenuPrincipalActivity.class);
            intent.putExtra("idUsuario","0");
            startActivity(intent);
            finish();
        }
        else if(helper.consultarSesion("SESI1")!=null)
        {
            Sesion sesion =helper.consultarSesion("SESI1");
            Intent intent =new Intent(LoginActivity.this,MenuPrincipalActivity.class);
            intent.putExtra("idUsuario",sesion.getIdUsuario());
            startActivity(intent);
            finish();
        }

        helper.cerrar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(idC==0) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else if(idC==1)
        {
            if(requestCode==SING_IN){
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if(result.isSuccess()){
                    Intent intent =new Intent(LoginActivity.this,MenuPrincipalActivity.class);
                    intent.putExtra("idUsuario","0");
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(this, "Inicio De sesion fallido", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
