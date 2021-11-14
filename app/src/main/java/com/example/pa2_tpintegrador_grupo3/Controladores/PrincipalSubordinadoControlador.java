package com.example.pa2_tpintegrador_grupo3.Controladores;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pa2_tpintegrador_grupo3.DAO.AplicacionDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceIntentApp;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class PrincipalSubordinadoControlador  extends AppCompatActivity implements InterfazDeComunicacion {

    private final AplicacionDAO appDao = new AplicacionDAO(this);
    private ArrayList<String> nombresAplicaciones;
    private Integer idDispositivo;
    private TextView emailMaestroTxtView;
    private TextView tiempoUsoTxtView;

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
            System.out.println(ut.obtenerConfiguracion(this).toString());
            Intent serviceIntent = new Intent(this, ServiceIntentApp.class);
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
                    Intent serviceIntent = new Intent(this, ServiceIntentApp.class);
                    ContextCompat.startForegroundService(this,serviceIntent);
                }
                break;
            case "obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivo":
                Dispositivo datosMaestro = DispositivoDAO.obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(datosMaestro != null){
                    emailMaestroTxtView.setText(datosMaestro.getUsuarioMaestro().getEmail());
                }
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    } 
}
