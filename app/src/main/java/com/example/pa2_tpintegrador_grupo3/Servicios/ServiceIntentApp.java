package com.example.pa2_tpintegrador_grupo3.Servicios;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.rvalerio.fgchecker.AppChecker;

import static com.example.pa2_tpintegrador_grupo3.AppNotificacion.CHANNEL_ID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ServiceIntentApp extends Service implements InterfazDeComunicacion {

    private Handler handler = new Handler();
    private Configuracion config;
    private ArrayList<String> aplicacionesBloqueadas;
    private RestriccionesDAO resDao = new RestriccionesDAO(this);

    @Override
    public void onCreate(){
        super.onCreate();
        Utilidad ut = new Utilidad();
        this.config = ut.obtenerConfiguracion(this);
        aplicacionesBloqueadas = obtenerAplicacionesBloqueadas();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        final long EXECUTION_TIME = 200;
        final long EXECUTION_TIME_CONSULTADB = 10000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppChecker appChecker = new AppChecker();
                String packageName = appChecker.getForegroundApp(ServiceIntentApp.this);
                if(aplicacionesBloqueadas.contains(packageName)){
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
                resDao.obtenerTodasLasRestriccionesPorIdDeDispositivo(ServiceIntentApp.this.config.getIdDispositivo());
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

    private ArrayList<String> obtenerAplicacionesBloqueadas(){
        ArrayList<String> result = new ArrayList<String>();
        for(Restricciones res : this.config.getRestricciones()){
            if(res.getDuracion_Minutos() == -1){
                result.add(res.getAplicacion().getNombre());
            }
        }
        return result;
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
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerTodasLasRestriccionesPorIdDeDispositivo":
                ArrayList<Restricciones> result = RestriccionesDAO.obtenerTodasLasRestriccionesPorIdDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(result != null && result.size() > 0){
                    Utilidad ut = new Utilidad();
                    Configuracion con = ut.obtenerConfiguracion(this);
                    con.setRestricciones(result);
                    ut.guardarArchivoDeConfiguracion(this,con);
                    this.config = con;
                    this.aplicacionesBloqueadas = obtenerAplicacionesBloqueadas();
                    System.out.println("NUEVAS RESTRICCIONES OBTENIDAS " + this.aplicacionesBloqueadas.size());
                }
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }
}
