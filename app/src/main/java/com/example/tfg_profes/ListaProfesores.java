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
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaProfesores extends AppCompatActivity {
    ArrayList<String> noms= new ArrayList<String>();
    ArrayList<String> precios= new ArrayList<String>();
    ArrayList<String> punt= new ArrayList<String>();
    ArrayList<String> usus= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profesores);
        String usuarios = getIntent().getExtras().getString("usus");
        String nombre = getIntent().getExtras().getString("noms");
        String precio = getIntent().getExtras().getString("precios");
        String punts = getIntent().getExtras().getString("punt");
        String[] arrayu = usuarios.split(",");
        String[] arrayn = nombre.split(",");
        String[] arrayp = precio.split(",");
        String[] arraypp = punts.split(",");

        usus = new ArrayList<String>(Arrays.asList(arrayu));
        noms = new ArrayList<String>(Arrays.asList(arrayn));
        precios = new ArrayList<String>(Arrays.asList(arrayp));
        punt = new ArrayList<String>(Arrays.asList(arraypp));

        ListView lisprofes = findViewById(R.id.listView);
        AdaptadorProfesLista eladap = new AdaptadorProfesLista(getApplicationContext(), noms, precios, punt);
        lisprofes.setAdapter(eladap);
        lisprofes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                noms.remove(i);
                precios.remove(i);
                eladap.notifyDataSetChanged();
                return true;
            }
        });
        lisprofes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("el usuario que esta enviando es:"+usus.get(i));
                enviarnotificacion(usus.get(i));
            }
        });
    }
    private void enviarnotificacion(String usuIntro) {
        Data inputData = new Data.Builder()
                .putString("usuario",usuIntro)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDmensajes.class).setInputData(inputData).build();
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {

                        }
                    }
                });
        WorkManager.getInstance(getApplicationContext()).enqueue(otwr);
    }
}