package com.example.habitos_saludables_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.HashMap;
import java.util.Locale;

public class videoActivity extends AppCompatActivity {
    private Button btnPlay,btnPlay2;
    private Button btnPause,btnPause2;
    private Button btnStop,btnStop2;
    private VideoView video,video2;

    TextToSpeech tts,tts2;
    Button BtnPlay,BtnPlay2;
    String textoLeer,textoLeer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //VIDEO
        video= findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        video.setVideoURI(Uri.parse(path));
        //VIDEO2
        video2= findViewById(R.id.videoView2);
        String path2 = "android.resource://" + getPackageName() + "/" + R.raw.video2;
        video2.setVideoURI(Uri.parse(path2));
        //Obtenemos los tres botones de la interfaz
        btnPlay= findViewById(R.id.buttonPlay);
        btnPause= findViewById(R.id.buttonPause);

        btnPlay2= findViewById(R.id.buttonPlay2);
        btnPause2= findViewById(R.id.buttonPause2);

        textoLeer="Concejos para obtener mejores resultados Levántate más temprano. Hacer ejercicio al iniciar el día es un hábito que motiva a mantenerlo";
        textoLeer2="Indicaciones para realizar un mejor ejercicio Levántate más temprano. Hacer ejercicio al iniciar el día es un hábito que motiva a mantenerlo";
        //otros
        BtnPlay = (Button) findViewById(R.id.btnText2SpeechPlay);
        BtnPlay2 = (Button) findViewById(R.id.btnText2SpeechPlay2);

        tts2 = new TextToSpeech(this,OnInit);
        tts = new TextToSpeech(this,OnInit);
        BtnPlay.setOnClickListener(onClick);
        BtnPlay2.setOnClickListener(onClick);

        //Y les asignamos el controlador de eventos
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      video.start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video.pause();
            }
        });
        //Y les asignamos el controlador de eventos
        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video2.start();
            }
        });
        btnPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video2.pause();
            }
        });
    }
    TextToSpeech.OnInitListener OnInit = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            // TODO Auto-generated method stub
            if (TextToSpeech.SUCCESS==status){
                tts.setLanguage(new Locale("spa","ESP"));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "TTS no disponible", Toast.LENGTH_SHORT).show();
            }
        }
    };
    View.OnClickListener onClick=new View.OnClickListener() {
        @SuppressLint("SdCardPath")
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId()==R.id.btnText2SpeechPlay){
                tts.speak(textoLeer, TextToSpeech.QUEUE_ADD, null);
            }
            if (v.getId()==R.id.btnText2SpeechPlay2){
                tts.speak(textoLeer2, TextToSpeech.QUEUE_ADD, null);
            }
        }
    };
    public void onDestroy(){
        tts.shutdown();
        super.onDestroy();
    }

}