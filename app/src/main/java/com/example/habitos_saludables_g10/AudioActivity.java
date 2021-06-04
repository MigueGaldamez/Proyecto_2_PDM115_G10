package com.example.habitos_saludables_g10;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class AudioActivity extends AppCompatActivity {

    MediaPlayer Media,Media2,Media3;
    Button Play,Play2,Play3;
    Button Stop,Stop2,Stop3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Play=(Button) findViewById(R.id.play);
        Stop=(Button) findViewById(R.id.stop);
        Play.setOnClickListener(onClick);
        Stop.setOnClickListener(onClick);
        Media=MediaPlayer.create(getApplicationContext(), R.raw.audio1);

        //DOS
        Play2=(Button) findViewById(R.id.play2);
        Stop2=(Button) findViewById(R.id.stop2);
        Play2.setOnClickListener(onClick);
        Stop2.setOnClickListener(onClick);
        Media2=MediaPlayer.create(getApplicationContext(), R.raw.audio2);
        //TRES
        Play3=(Button) findViewById(R.id.play3);
        Stop3=(Button) findViewById(R.id.stop3);
        Play3.setOnClickListener(onClick);
        Stop3.setOnClickListener(onClick);
        Media3=MediaPlayer.create(getApplicationContext(), R.raw.audio3);
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId()==R.id.play){
                if (Media.isPlaying()){
                    Media.pause();
                    Play.setText("Play");
                }
                else{
                    Media.start();
                    Play.setText("Pause");
                }
            }
            if (v.getId()==R.id.stop)
            {
                Media.stop();
                Play.setText("Play");
                try{
                    Media.prepare();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }//SEGUNDO
            if (v.getId()==R.id.play2){
                if (Media2.isPlaying()){
                    Media2.pause();
                    Play2.setText("Play");
                }
                else{
                    Media2.start();
                    Play2.setText("Pause");
                }
            }
            if (v.getId()==R.id.stop2)
            {
                Media2.stop();
                Play2.setText("Play");
                try{
                    Media2.prepare();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }//TERCERO
            if (v.getId()==R.id.play3){
                if (Media3.isPlaying()){
                    Media3.pause();
                    Play3.setText("Play");
                }
                else{
                    Media3.start();
                    Play3.setText("Pause");
                }
            }
            if (v.getId()==R.id.stop3)
            {
                Media3.stop();
                Play3.setText("Play");
                try{
                    Media3.prepare();
                }
                catch(IllegalStateException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    };
}