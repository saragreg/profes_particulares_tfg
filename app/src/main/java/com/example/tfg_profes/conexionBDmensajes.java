package com.example.tfg_profes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class conexionBDmensajes extends Worker {
    public conexionBDmensajes(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String usu = getInputData().getString("usuario");
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/mensajefcm.php?usuario="+usu;

        HttpURLConnection urlConnection = null;
        System.out.println("hemos llegau");
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();

            int statusCode = urlConnection.getResponseCode();
            System.out.println(statusCode);
            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line, result = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return Result.success();
    }
}
