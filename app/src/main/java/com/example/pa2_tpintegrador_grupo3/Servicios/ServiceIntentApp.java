package com.example.pa2_tpintegrador_grupo3.Servicios;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import static com.example.pa2_tpintegrador_grupo3.AppNotificacion.CHANNEL_ID;

import java.util.List;

public class ServiceIntentApp extends Service implements InterfazDeComunicacion {

    private Handler handler = new Handler();

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        final long EXECUTION_TIME = 500;
        final long EXECUTION_TIME_CONSULTADB = 15000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityManager mActivityManager = (ActivityManager)ServiceIntentApp.this.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
                ComponentName componentInfo = taskInfo.get(0).topActivity;
                componentInfo.getPackageName();
                mActivityManager.killBackgroundProcesses(componentInfo.getPackageName());

                //LEER DEL ARCHIVO LOCAL, OBTENER LAS RESTRICCIONES, HACER UNA LISTA DE LAS APPS BLOQUEADAS
                // USAR ESA LISTA PARA MINIMIZAR LAS APLICACIONES EN PANTALLA

                if(componentInfo.getPackageName().equals("com.google.android.apps.maps")){
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }

                handler.postDelayed(this, EXECUTION_TIME);
            }
        }, EXECUTION_TIME);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //ACA CONSULTAR A LA DB Y TRAER LA LISTA DE RESTRICCIONES Y GUARDAR EN EL ARCHIVO LOCAL

                handler.postDelayed(this, EXECUTION_TIME_CONSULTADB);
            }
        }, EXECUTION_TIME);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Parental Watcher")
                .setContentText("Parental Watcher se esta ejecutando")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {

    }
}
