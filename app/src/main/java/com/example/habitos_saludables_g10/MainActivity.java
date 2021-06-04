package com.example.habitos_saludables_g10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.habitos_saludables_g10.audio.AudioControl;


public class MainActivity extends AudioControl {

    String[] menu={"ITEM 1","ITEM 2", "ITEM 3", "ITEM 4","ITEM 5", "ITEM 5","ITEM 6", "ITEM 7", "ITEM 8", "ITEM 9","ITEM 10", "ITEM 11"};
    String[] activities={"1","2", "3","4","5", "6","7","8", "9"};

    ListView lvVoiceReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filtro_voz,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onClick(item.getActionView());
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     String audioText =  getAudioText(requestCode,resultCode,data);
            Toast.makeText(MainActivity.this, audioText, Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy(){
        super.onDestroy();
    }
}