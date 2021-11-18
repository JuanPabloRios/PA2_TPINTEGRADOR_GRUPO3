package com.example.pa2_tpintegrador_grupo3.Controladores;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pa2_tpintegrador_grupo3.DAO.AplicacionDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.NotificacionDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceSubordinado;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estado;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.fragments.Solicitar_extension_uso_aplicaciones;
import com.example.pa2_tpintegrador_grupo3.fragments.solicitar_extension_uso_dispositivo;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PrincipalSubordinadoControlador  extends AppCompatActivity implements InterfazDeComunicacion, solicitar_extension_uso_dispositivo.SolicitarExtensionDispositivoListener , Solicitar_extension_uso_aplicaciones.SolicitarExtensionAplicacion {

    private final AplicacionDAO appDao = new AplicacionDAO(this);
    private final NotificacionDAO notiDao = new NotificacionDAO(this);
    private final UsuarioDAO usDao = new UsuarioDAO(this);
    private ArrayList<String> nombresAplicaciones;
    private Integer idDispositivo;
    private TextView emailMaestroTxtView;
    private TextView tiempoUsoTxtView;
    private Integer idMaestro;
    private ArrayList<Aplicacion> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_subordinado);
        Usuario user = (Usuario) getIntent().getSerializableExtra("usuario");
        Boolean primerInicio = (Boolean) getIntent().getSerializableExtra("primerInicio");

        ArrayList<Aplicacion> aplicacionesInstaladas = new ArrayList<Aplicacion>();
        this.nombresAplicaciones = new ArrayList<String>();
        this.emailMaestroTxtView = findViewById(R.id.emailMaestroTxtView);
        TextView marcaDispositivoTxtView = findViewById(R.id.marcaDispositivoTxtView);
        TextView modeloDispositivoTxtView = findViewById(R.id.modeloDispositivoTxtView);
        TextView nombreDispositivoTxtView = findViewById(R.id.nombreDispositivoTxtView);
        this.tiempoUsoTxtView = findViewById(R.id.tiempoUsoTxtView);

        Utilidad ut = new Utilidad();
        Configuracion c = ut.obtenerConfiguracion(this);
        this.idDispositivo = c.getDispositivo().getId();
        marcaDispositivoTxtView.setText(c.getDispositivo().getMarca());
        modeloDispositivoTxtView.setText(c.getDispositivo().getModelo());
        nombreDispositivoTxtView.setText(c.getDispositivo().getNombre());

        //POPUP SOLICITUD DE TIEMPO DE DISPOSITIVO
        solicitar_extension_uso_dispositivo.SolicitarExtensionDispositivoListener list = this;
        Button solicitarTiempoDispositivo = findViewById(R.id.btnTiempoDispositivo);
        solicitarTiempoDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMyServiceRunning(ServiceSubordinado.class)){
                    finish();
                } else {
                    solicitar_extension_uso_dispositivo dialog = new solicitar_extension_uso_dispositivo();
                    dialog.setListener(list);
                    dialog.setIdDispositivo(c.getDispositivo().getId());
                    dialog.show(getSupportFragmentManager(),null);
                }

            }
        });
        //---------------------------------------------------------------

        //CALCULAMOS EL TIEMPO TOTAL DE USO DEL DISPOSITIVO
        Long milis = ut.obtenerStadisticasDeUso(this).get(0).tiempoTotal;
        Long horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
        Long minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
        this.tiempoUsoTxtView.setText(horas + ":"+minutos);

        for(Utilidad.InfoObject app : ut.getInstalledApps(this)){
            aplicacionesInstaladas.add( new Aplicacion(app.appname,app.packagename,app.icon));
            this.nombresAplicaciones.add(app.packagename);
        }
        if(primerInicio != null && primerInicio){
            appDao.insertarAplicaciones(aplicacionesInstaladas);
        } else {
            appDao.obtenerAplicacionesPorNombre(nombresAplicaciones);
            Intent serviceIntent = new Intent(this, ServiceSubordinado.class);
            ContextCompat.startForegroundService(this,serviceIntent);
            DispositivoDAO dispositivoDAO = new DispositivoDAO(this);
            dispositivoDAO.obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivo(this.idDispositivo);
        }
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "insertarAplicaciones":
                Integer respuesta = AplicacionDAO.insertarAplicacionesHandler(res.getData());
                //ACA DETENER SPINNER
                if(respuesta != null){
                    //ACA INICIAR SPINNER
                    appDao.obtenerAplicacionesPorNombre(nombresAplicaciones);
                }
                break;
            case "obtenerAplicacionesPorNombre":
                ArrayList<Aplicacion> apps = AplicacionDAO.obtenerAplicacionesPorNombreHandler(res.getData());
                //ACA DETENER SPINNER
                if(apps != null && apps.size() > 0){
                    appDao.relacionarAplicacionesConDispositivo(apps,this.idDispositivo);
                    //POPUP SOLICITUD DE TIEMPO DE APLICACION
                    Solicitar_extension_uso_aplicaciones.SolicitarExtensionAplicacion list2 = this;
                    Button solicitarTiempoApp = findViewById(R.id.btntiempoAplicacion);
                    solicitarTiempoApp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!isMyServiceRunning(ServiceSubordinado.class)){
                                finish();
                            } else {
                                Solicitar_extension_uso_aplicaciones dialog = new Solicitar_extension_uso_aplicaciones();
                                dialog.setListener(list2);
                                dialog.setAplicaciones(apps);
                                dialog.show(getSupportFragmentManager(), null);
                            }
                        }
                    });
                    //---------------------------------------------------------------
                }
                break;
            case "relacionarAplicacionesConDispositivo":
                Integer relacionesCreadas = AplicacionDAO.relacionarAplicacionesConDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(relacionesCreadas != null){
                    RestriccionesDAO restriccionesDAO = new RestriccionesDAO(this);
                    restriccionesDAO.obtenerTodasLasRestriccionesPorIdDeDispositivo(this.idDispositivo);

                }
                break;
            case "obtenerTodasLasRestriccionesPorIdDeDispositivo":
                ArrayList<Restricciones> restricciones = RestriccionesDAO.obtenerTodasLasRestriccionesPorIdDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(restricciones != null && !restricciones.isEmpty()){
                    Utilidad ut = new Utilidad();
                    Configuracion config = ut.obtenerConfiguracion(this);
                    config.setRestricciones(restricciones);
                    ut.guardarArchivoDeConfiguracion(this,config);
                    Intent serviceIntent = new Intent(this, ServiceSubordinado.class);
                    ContextCompat.startForegroundService(this,serviceIntent);
                }
                break;
            case "obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivo":
                Dispositivo datosMaestro = DispositivoDAO.obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(datosMaestro != null){
                    emailMaestroTxtView.setText(datosMaestro.getUsuarioMaestro().getEmail());
                    idMaestro = datosMaestro.getUsuarioMaestro().getId();
                }
                break;
            case "CREARNOTIFICACION":
                Integer result = notiDao.crearNotificacionHandler(res.getData());
                //ACA DETENER SPINNER
                if(result != null){
                    //ACA INICIAR SPINNER
                    Toast.makeText(this,"Solicitud registrada correctamente",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void guardarSolicitudDispositivo(Integer idDispositivo, Long tiempo) {
        //GUARDAR EN LA DB tabla Notificaciones.

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(idDispositivo);
        TipoNotificacion tNoti = new TipoNotificacion(1);
        Estado estado = new Estado(1);
        Notificacion notificacion = new Notificacion();
        notificacion.setDispositivoEmisor(dispositivo);
        Usuario us = new Usuario(idMaestro);
        notificacion.setId_Usuario_Receptor(us);
        notificacion.setAplicacion(null);
        notificacion.setTipoNotificacion(tNoti);
        notificacion.setTiempo_Solicitado(tiempo);
        notificacion.setEstado(estado);

        notiDao.crearNotificacion(notificacion);

    }

    @Override
    public void guardarSolicitudAplicacion(Integer idAplicacion, Long tiempo) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setId(idDispositivo);
        TipoNotificacion tNoti = new TipoNotificacion(2);
        Estado estado = new Estado(1);
        Notificacion notificacion = new Notificacion();
        notificacion.setDispositivoEmisor(dispositivo);
        Usuario us = new Usuario(idMaestro);
        notificacion.setId_Usuario_Receptor(us);
        notificacion.setAplicacion(new Aplicacion(idAplicacion));
        notificacion.setTipoNotificacion(tNoti);
        notificacion.setTiempo_Solicitado(tiempo);
        notificacion.setEstado(estado);
        notiDao.crearNotificacion(notificacion);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
