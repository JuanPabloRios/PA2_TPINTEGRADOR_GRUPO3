package com.example.pa2_tpintegrador_grupo3.Controladores;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.NotificacionDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.util.concurrent.TimeUnit;

public class AdministracionSolicitudControlador extends AppCompatActivity implements InterfazDeComunicacion {
    private Notificacion notificacion;
    private NotificacionDAO notificacionDAO = new NotificacionDAO(this);

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        notificacion = (Notificacion)getIntent().getSerializableExtra("notificacion");
        cargarPantalla();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administracion_solicitud);
        notificacion = (Notificacion)getIntent().getSerializableExtra("notificacion");
        cargarPantalla();
    }

    private void cargarPantalla(){

        TextView nombreDispositivo = findViewById(R.id.nombreDispSolicitud);
        nombreDispositivo.setText(this.notificacion.getDispositivoEmisor().getNombre());
        TextView minutosSolicitud = findViewById(R.id.minutosDispSolicitud);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(this.notificacion.getTiempo_Solicitado());
        minutosSolicitud.setText(minutos + " minutos");
        TextView nombreAplicacion = findViewById(R.id.nobmreAplicacionSolicitud);
        LinearLayout nombreApp = findViewById(R.id.layoutNombreApp);
        TextView tituloSolicitud = findViewById(R.id.tituloSolicitud);
        if(this.notificacion.getTipoNotificacion().getId() == 2){
            nombreApp.setVisibility(View.VISIBLE);
            tituloSolicitud.setText("Solicitud extension de uso de aplicacion");
            nombreAplicacion.setText(this.notificacion.getAplicacion().getDescripcion());
        } else {
            nombreApp.setVisibility(View.GONE);
            tituloSolicitud.setText("Solicitud extension de uso de dispositivo");
        }
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "rechazarNotificacion":
                Integer a = NotificacionDAO.rechazarNotificacionHandler(res.getData());
                //ACA DETENER SPINNER
                if(a != null){
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager nMgr = (NotificationManager) this.getSystemService(ns);
                    nMgr.cancel(notificacion.getId());
                    Toast.makeText(this,"Solicitud Rechazada",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case "aceptarNotificacion":
                Integer b = NotificacionDAO.aceptarNotificacionHandler(res.getData());
                //ACA DETENER SPINNER
                if(b != null){
                    String ns = Context.NOTIFICATION_SERVICE;
                    NotificationManager nMgr = (NotificationManager) this.getSystemService(ns);
                    nMgr.cancel(notificacion.getId());
                    Toast.makeText(this,"Solicitud Aceptada",Toast.LENGTH_SHORT).show();
                    if(notificacion.getTipoNotificacion().getId() == 1){
                        DispositivoDAO dispositivoDAO = new DispositivoDAO(this);
                        dispositivoDAO.sumarTiempoDeUsoDeDispositivo(notificacion.getDispositivoEmisor(),notificacion.getTiempo_Solicitado());
                    } else{
                        RestriccionesDAO restriccionesDAO = new RestriccionesDAO(this);
                        restriccionesDAO.actualizarRestriccionesPorSolicitud(notificacion);
                    }
                    finish();
                }
                break;
        }
    }

    public void rechazar(View view){
        notificacionDAO.rechazarNotificacion(notificacion);

    }

    public void aceptar(View view){
        notificacionDAO.aceptarNotificacion(notificacion);
    }

}
