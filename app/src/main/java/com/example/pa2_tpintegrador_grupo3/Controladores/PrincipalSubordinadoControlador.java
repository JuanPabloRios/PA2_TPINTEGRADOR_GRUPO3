package com.example.pa2_tpintegrador_grupo3.Controladores;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pa2_tpintegrador_grupo3.DAO.AplicacionDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;

public class PrincipalSubordinadoControlador  extends AppCompatActivity implements InterfazDeComunicacion {

    private AplicacionDAO appDao = new AplicacionDAO(this);
    private Usuario user;
    private Boolean primerInicio = false;
    private ArrayList<Aplicacion> aplicacionesInstaladas;
    private ArrayList<String> nombresAplicaciones;
    private Integer idDispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_subordinado);
        this.user = (Usuario)getIntent().getSerializableExtra("usuario");
        this.primerInicio = (Boolean)getIntent().getSerializableExtra("primerInicio");
        Utilidad ut = new Utilidad();
        Configuracion c = ut.obtenerConfiguracion(this);
        this.idDispositivo = c.getIdDispositivo();
        this.aplicacionesInstaladas = new ArrayList<Aplicacion>();
        this.nombresAplicaciones = new ArrayList<String>();
        for(Utilidad.InfoObject app : ut.getInstalledApps(this)){
            this.aplicacionesInstaladas.add( new Aplicacion(app.appname,app.packagename,app.icon));
            this.nombresAplicaciones.add(app.packagename);
        }

        if(this.primerInicio != null && this.primerInicio){
            appDao.insertarAplicaciones(this.aplicacionesInstaladas);
        } else {
            System.out.println(ut.obtenerConfiguracion(this).toString());
        }
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "insertarAplicaciones":
                Integer respuesta = AplicacionDAO.insertarAplicacionesHandler(res.getData());
                //ACA DETENER SPINNER

                System.out.println("@@ insertarAplicaciones FINALIZADA " + respuesta);
                if(respuesta != null && respuesta != -1){
                    //ACA INICIAR SPINNER
                    appDao.obtenerAplicacionesPorNombre(nombresAplicaciones);
                }
                break;
            case "obtenerAplicacionesPorNombre":
                ArrayList<Aplicacion> apps = AplicacionDAO.obtenerAplicacionesPorNombreHandler(res.getData());
                //ACA DETENER SPINNER
                System.out.println("@@ obtenerAplicacionesPorNombre FINALIZADA " + apps.size());
                if(apps != null && apps.size() > 0){
                    appDao.relacionarAplicacionesConDispositivo(apps,this.idDispositivo);
                }
                break;
            case "relacionarAplicacionesConDispositivo":
                Integer relacionesCreadas = AplicacionDAO.relacionarAplicacionesConDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                System.out.println("@@ relacionarAplicacionesConDispositivo FINALIZADA " + relacionesCreadas);
                if(relacionesCreadas != null && relacionesCreadas != -1){
                    Toast.makeText(this,"TODO GENIAL",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }
}
