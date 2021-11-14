package com.example.pa2_tpintegrador_grupo3.Servicios;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.pa2_tpintegrador_grupo3.DAO.EstadisticaDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.rvalerio.fgchecker.AppChecker;
import static com.example.pa2_tpintegrador_grupo3.AppNotificacion.CHANNEL_ID;
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
                resDao.obtenerTodasLasRestriccionesPorIdDeDispositivo(ServiceIntentApp.this.config.getDispositivo().getId());
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
        if(this.config != null && this.config.getRestricciones() != null){
            Utilidad ut = new Utilidad();
            List<Utilidad.AppUsageInfo> usoDeApps = ut.obtenerStadisticasDeUso(this);
            ArrayList<Estadistica> estadisticasParaAtualizar = new ArrayList<Estadistica>();
            for(Restricciones res : this.config.getRestricciones() ){
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
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }
}
