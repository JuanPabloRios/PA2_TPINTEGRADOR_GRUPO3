package com.example.pa2_tpintegrador_grupo3.Servicios;
import static com.example.pa2_tpintegrador_grupo3.AppNotificacion.CHANNEL_ID;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.NotificacionDAO;
import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;

public class ServiceMaestro extends Service implements InterfazDeComunicacion {
    private Handler handler = new Handler();
    private Integer idUsuario;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Bundle extras = intent.getExtras();
        if(extras != null) {
            idUsuario = extras.getInt("idUsuario");
        }
        final long EXECUTION_TIME = 7000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chequearNotificaciones();
                handler.postDelayed(this, EXECUTION_TIME);
            }
        }, EXECUTION_TIME);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Parental Watcher")
            .setContentText("Parental Watcher se esta ejecutando")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .build();
        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerNotificacionPorIdDeUsuarioMaestro":

                ArrayList<Notificacion> notificaciones = NotificacionDAO.obtenerNotificacionPorIdDeUsuarioMaestroHandler(res.getData());
                //ACA DETENER SPINNER
                if(notificaciones != null && notificaciones.size() > 0){
                    NotificacionDAO notificacionDAO = new NotificacionDAO(this);
                    notificacionDAO.marcarNotifiacionesComoRecibidas(notificaciones);
                    crearNotificaciones(notificaciones);
                }
                break;
        }
    }

    private void chequearNotificaciones(){
        if(this.idUsuario != null){
            NotificacionDAO notificacionDAO = new NotificacionDAO(this);
            notificacionDAO.obtenerNotificacionPorIdDeUsuarioMaestro(this.idUsuario);
        }
    }

    private void crearNotificaciones(ArrayList<Notificacion> notificaciones){
        Utilidad ut = new Utilidad();
        String titulo = "Parental Watcher";
        String channelID = CHANNEL_ID;
        for(Notificacion n : notificaciones) {
            String mensaje = "";
            if(n.getTipoNotificacion().getId() == 1){
                mensaje = "Solicitud de tiempo de dispositivo ";
            } else {
                mensaje = "Solicitud de tiempo de aplicacion ";
            }

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent intent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Integer id = n.getId();
            ut.enviarNotificacionCustom(this,titulo,mensaje,channelID,intent,id);
        }
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
}
