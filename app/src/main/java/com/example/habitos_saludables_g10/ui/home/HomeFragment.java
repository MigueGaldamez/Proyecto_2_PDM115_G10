package com.example.habitos_saludables_g10.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.habitos_saludables_g10.ControlBD;
import com.example.habitos_saludables_g10.Habito;
import com.example.habitos_saludables_g10.HabitoInsertarActivity;
import com.example.habitos_saludables_g10.LoginActivity;
import com.example.habitos_saludables_g10.MenuPrincipalActivity;
import com.example.habitos_saludables_g10.R;
import com.example.habitos_saludables_g10.Sesion;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //poner el texto final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        return root;
    }

    public void irInsertarHabito(View v){
        Intent intent=getActivity().getPackageManager().getLaunchIntentForPackage("com.example.habitos_saludables_g10.HabitoInsertarActivity");
        startActivity(intent);
    }

}