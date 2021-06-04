package com.example.habitos_saludables_g10.audio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitos_saludables_g10.MainActivity;
import com.example.habitos_saludables_g10.R;

import java.text.Normalizer;
import java.util.ArrayList;

public class AudioControl extends AppCompatActivity implements View.OnClickListener {



    static final int check=1111;


    public void onClick(View v) {

            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
            startActivityForResult(i, check);
    }

    public String getAudioText(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String audioText="";
        // TODO Auto-generated method stub
        if (requestCode==check && resultCode==RESULT_OK){

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            audioText = results.get(results.size()-1);
            audioText = cleanString(audioText);
           //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
        }
        else audioText = "Sin resultados";
        return audioText;
    }

    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
        return texto;
    }

    public void onDestroy(){
        super.onDestroy();
    }
}
