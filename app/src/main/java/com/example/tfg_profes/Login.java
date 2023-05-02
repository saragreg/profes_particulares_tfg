package com.example.tfg_profes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Login extends AppCompatActivity {
    private String contraRec="";
    String usuIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.POST_NOTIFICATIONS}, 11);
            }
        }
    }
    public void onclick_login(View v){
        TextView usu = findViewById(R.id.user);
        usuIntro = usu.getText().toString();
        System.out.println("usuario introducido: "+usuIntro);
        TextView contra = findViewById(R.id.contra);
        String contraIntro = contra.getText().toString();
        System.out.println("contra introducido: "+contraIntro);

        if (usuIntro.equals("") || contra.equals("")){
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else {

            TextView error = findViewById(R.id.error);

            Data inputData = new Data.Builder()
                    .putString("tipo", "login")
                    .putString("usuario", usuIntro)
                    .build();
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDWebService.class).setInputData(inputData).build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                contraRec = workInfo.getOutputData().getString("res");
                                //comprobamos que existe el usuario
                                if (contraRec.equals("mal")) {
                                    //el usuario no existe
                                    Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                                } else if (contraRec.equals(contraIntro)) {
                                    //se ha logeado correctamente

                                    obtenerDatosProfes(usuIntro);
                                    Toast.makeText(getApplicationContext(), "Se ha logeado correctamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    //la contraseña es incorrecta
                                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectas", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }

    }
    public void obtenerDatosProfes(String usuInt) {
        Data inputData = new Data.Builder()
                .putString("tipo", "infoLista")
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(conexionBDProfes.class).setInputData(inputData).build();
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            String usuarios = workInfo.getOutputData().getString("usu");
                            String nombre = workInfo.getOutputData().getString("nombre");
                            String precio = workInfo.getOutputData().getString("precio");
                            String punts = workInfo.getOutputData().getString("punt");


                            Intent intent = new Intent(Login.this, Menu.class);
                            intent.putExtra("usuario", usuInt);
                            intent.putExtra("usus",usuarios);
                            intent.putExtra("noms",nombre);
                            intent.putExtra("precios",precio);
                            intent.putExtra("punt",punts);
                            startActivity(intent);


                        }
                    }
                });
        WorkManager.getInstance(this).enqueue(otwr);


    }

}