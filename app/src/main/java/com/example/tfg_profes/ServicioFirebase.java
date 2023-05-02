package com.example.tfg_profes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.Manifest;

public class ServicioFirebase extends FirebaseMessagingService {
    public ServicioFirebase() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {

        System.out.println(remoteMessage.getNotification().getBody());
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal", NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
            elBuilder.setSmallIcon(R.drawable.libro)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setVibrate(new long[]{0, 1000, 500, 1000})
                    .setAutoCancel(true);
            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            elCanal.enableVibration(true);
        }
        elManager.notify(1, elBuilder.build());

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //hacer php con el update
    }
}
