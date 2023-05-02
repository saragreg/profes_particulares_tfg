package com.example.tfg_profes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class conexionBDinsertImage extends Worker {
    String uri_env;
    String usu;
    public conexionBDinsertImage(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String tipo = getInputData().getString("tipo");

        switch (tipo) {
            case "insertarImagen":
                usu = getInputData().getString("usuario");
                String uri = getInputData().getString("uri");
                insertImagen(usu,uri);
                return Result.success();
            case "fotoPerfil":
                usu = getInputData().getString("usuario");
                fotoPerfil(usu);
                Data resultados= new Data.Builder()
                        .putString("uri",uri_env)
                        .build();

                return Result.success(resultados);
            default:
                return Result.failure();
        }


    }

    private void fotoPerfil(String usu) {
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/foto_perfil.php?usuario="+usu;
        System.out.println("url: "+url);
        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line, result = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    uri_env = jsonArray.getJSONObject(i).getString("uri");
                }


            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void insertImagen(String usu, String uri) {
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/insertar_imagenes.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usu);
            parametrosJSON.put("uri", uri);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON);
            out.close();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line, result = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    String nomuri = jsonArray.getJSONObject(i).getString("uri");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
