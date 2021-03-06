package com.example.pa2_tpintegrador_grupo3.Servicios;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.EstadisticaDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.rvalerio.fgchecker.AppChecker;
import static com.example.pa2_tpintegrador_grupo3.AppNotificacion.CHANNEL_ID;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServiceSubordinado extends Service implements InterfazDeComunicacion {

    private Handler handler = new Handler();
    private Configuracion config;
    private ArrayList<String> aplicacionesBloqueadas;
    private RestriccionesDAO resDao = new RestriccionesDAO(this);
    private Long tiempoUso = 0L;

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
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        final long EXECUTION_TIME = 200;
        final long EXECUTION_TIME_CONSULTADB = 10000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppChecker appChecker = new AppChecker();
                String packageName = appChecker.getForegroundApp(ServiceSubordinado.this);
                if(!packageName.equals("com.example.pa2_tpintegrador_grupo3")){
                    Boolean bloqueoDispositivoActivo = ServiceSubordinado.this.config.getDispositivo().isBloqueoActivo();
                    Boolean chequeoDeBloqueoDeApps = true;
                    if(bloqueoDispositivoActivo){
                        Dispositivo d = ServiceSubordinado.this.config.getDispositivo();
                        if(d.getTiempoUso() >= d.getTiempoAsignado()){
                            //Superamos el tiempo de uso
                            ServiceSubordinado.this.irAHome("Su dispositivo ya no cuenta con tiempo de uso disponible. Parental Watcher");
                            //No hace falta chequear las app, el dispositivo esta bloqueado
                            chequeoDeBloqueoDeApps = false;
                        } else {
                            Long horaInicio = d.getHoraInicio();
                            Long horaFin = d.getHoraFin();
                            Calendar now = Calendar.getInstance();
                            Integer horaActual = now.get(Calendar.HOUR_OF_DAY);
                            Integer minutoActual = now.get(Calendar.MINUTE);
                            Long horaActualEnMilisegundos = TimeUnit.HOURS.toMillis(horaActual) + TimeUnit.MINUTES.toMillis(minutoActual);

                            if(horaActualEnMilisegundos < horaInicio || horaActualEnMilisegundos > horaFin){
                                //Estamos fuera del rango horario de uso
                                ServiceSubordinado.this.irAHome("Su dispositivo se encuentra fuera del horario de uso permitido. Parental Watcher");
                                //No hace falta chequear las app, el dispositivo esta bloqueado
                                chequeoDeBloqueoDeApps = false;
                            }
                        }
                    }
                    if(chequeoDeBloqueoDeApps){
                        if(aplicacionesBloqueadas.contains(packageName)){
                            //Si la app esta en esta lista es por que se encuentra bloqueada
                            ServiceSubordinado.this.irAHome("Esta aplicacion se encuentra bloqueada. Parental Watcher");
                        }
                    }
                }
                handler.postDelayed(this, EXECUTION_TIME);
            }
        }, EXECUTION_TIME);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //REFRESCAMOS LAS RESTRICCIONES CON LA BASE DE DATOS
                resDao.obtenerTodasLasRestriccionesPorIdDeDispositivo(ServiceSubordinado.this.config.getDispositivo().getId());
                DispositivoDAO dispositivoDAO = new DispositivoDAO(ServiceSubordinado.this);
                //ACTUALZIAMOS LAS ESTADISTICAS DE NUESTRO DISPOSITIVO EN LA BASE DE DATOS
                dispositivoDAO.actualizarTiempoDeUso(ServiceSubordinado.this.config.getDispositivo().getId(), ServiceSubordinado.this.tiempoUso);
                //REFRESCAMOS LA CONFIGURACION DE NUESTRO DISPOSITIVO CON LA BASE DE DATOS (Buscamos nuevas restricciones a nivel dispositivo)
                dispositivoDAO.obtenerDispositivoPorId(ServiceSubordinado.this.config.getDispositivo().getId());
                handler.postDelayed(this, EXECUTION_TIME_CONSULTADB);
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

    public void irAHome(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private ArrayList<String> obtenerAplicacionesBloqueadas(){
        ArrayList<String> result = new ArrayList<String>();
        if(this.config != null && this.config.getRestricciones() != null){
            Utilidad ut = new Utilidad();
            List<Utilidad.AppUsageInfo> usoDeApps = ut.obtenerStadisticasDeUso(this);
            ArrayList<Estadistica> estadisticasParaAtualizar = new ArrayList<Estadistica>();
            for(Restricciones res : this.config.getRestricciones()){
                if(res.isActiva()){
                    Utilidad.AppUsageInfo usoApp = null;
                    for(Utilidad.AppUsageInfo uso : usoDeApps){
                        if(uso.packageName.equals(res.getAplicacion().getNombre())){
                            usoApp = uso;
                        }
                    }
                    if(usoApp != null && res.getDuracion_Minutos() < usoApp.timeInForeground){
                        result.add(res.getAplicacion().getNombre());
                    }
                }

                Boolean notFound = true;
                for(Utilidad.AppUsageInfo uso : usoDeApps){
                    if(uso.packageName.equals(res.getAplicacion().getNombre())){
                        if(res.getDispositivo() != null && res.getDispositivo().getId() != null &&
                                res.getAplicacion() != null && res.getAplicacion().getId() != null
                        ){
                            notFound = false;
                            Estadistica stat = new Estadistica(
                                    res.getDispositivo(),
                                    res.getAplicacion(),
                                    uso.timeInForeground
                            );
                            estadisticasParaAtualizar.add(stat);
                        }
                    }
                }

                if(notFound){
                    Estadistica stat = new Estadistica(
                            res.getDispositivo(),
                            res.getAplicacion(),
                            0L
                    );
                    estadisticasParaAtualizar.add(stat);
                }
            }
            if(!estadisticasParaAtualizar.isEmpty()){
                EstadisticaDAO estadisticaDAO = new EstadisticaDAO(this);
                estadisticaDAO.insertarEstadisticas(estadisticasParaAtualizar);
            }
            this.tiempoUso = usoDeApps.get(0).tiempoTotal;
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
                }
                break;
            case "obtenerDispositivoPorId":
                Dispositivo d = DispositivoDAO.obtenerDispositivoPorIdHandler(res.getData());
                if(d != null){
                    Utilidad ut = new Utilidad();
                    Configuracion con = ut.obtenerConfiguracion(this);
                    d.setUsuarioMaestro(con.getDispositivo().getUsuarioMaestro());
                    con.setDispositivo(d);
                    ut.guardarArchivoDeConfiguracion(this,con);
                    this.config = con;
                } else {
                    Utilidad ut = new Utilidad();
                    if(ut.eliminarConfiguracion(this)){
                        Toast.makeText(this,"El dispositivo maestro ha eliminado este subordinado.",Toast.LENGTH_SHORT).show();
                        this.stopSelf();
                    }
                }
                break;
        }
    }
}
