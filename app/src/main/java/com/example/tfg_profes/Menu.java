package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends AppCompatActivity {
    String usus;
    String noms;
    String precios;
    String punt;
    String usuario;
    ImageButton lisprofes,perfilbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        usuario=getIntent().getExtras().getString("usuario");
        usus = getIntent().getExtras().getString("usus");
        noms = getIntent().getExtras().getString("noms");
        precios = getIntent().getExtras().getString("precios");
        punt = getIntent().getExtras().getString("punt");

        lisprofes=findViewById(R.id.listabtn);
        lisprofes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, ListaProfesores.class);
                intent.putExtra("usus",usus);
                intent.putExtra("noms",noms);
                intent.putExtra("precios",precios);
                intent.putExtra("punt",punt);
                startActivity(intent);
            }
        });
        perfilbtn=findViewById(R.id.perfilbtn);
        perfilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Perfil.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });



    }


}