package com.example.tfg_profes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Spinner idiomas;
    private String idiomSel;
    private boolean idiomacargado = false;
    Activity a = this;
    private String idioma ="es";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                String token = task.getResult();
                System.out.println("el token de la app es:" + token);

            }
        });

        if (savedInstanceState != null) {
            idiomSel=savedInstanceState.getString("idiomaActual");
            System.out.println("idioma "+idiomSel);
            if (idiomSel.equals("Castellano")){
                setIdioma(MainActivity.this,"es");
            } else if (idiomSel.equals("Euskara")) {
                setIdioma(MainActivity.this,"eu");
            }else if (idiomSel.equals("English")){
                setIdioma(MainActivity.this,"en");
            }
        }
        Bundle extras= getIntent().getExtras();
        if (extras != null){
            idioma= extras.getString("idioma");
            setIdioma(MainActivity.this,idioma);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idiomas=findViewById(R.id.spinnerIdioms);


        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.idiomas, android.R.layout.simple_spinner_item);
        idiomas.setAdapter(adapter);
        idiomas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(136,0,21));
                //((TextView) adapterView.getChildAt(0)).setTextSize(35);
                idiomSel = adapterView.getItemAtPosition(i).toString();

                if (idiomSel.equals("Castellano")){
                    System.out.println("ha elegido castelllano");
                    finish();
                    startActivity(getIntent().putExtra("idioma","es"));

                } else if (idiomSel.equals("Euskara")) {
                    System.out.println("ha elegido eu");
                    finish();
                    startActivity(getIntent().putExtra("idioma","eu"));

                }else if (idiomSel.equals("English")){
                    System.out.println("ha elegido en");
                    finish();
                    startActivity(getIntent().putExtra("idioma","en"));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void btn_login(View v){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void btn_reg(View v){
        Intent intent = new Intent(MainActivity.this, RegistroUSuario.class);
        startActivity(intent);
    }
    public void setIdioma(Activity activity, String idiomCod){

            Locale locale = new Locale(idiomCod);
            Locale.setDefault(locale);
            Resources resources = activity.getResources();
            Configuration config = resources.getConfiguration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mySpinner", idiomas.getSelectedItemPosition());
        outState.putString("idiomaActual", idiomSel);
        // do this for each or your Spinner
    }

}