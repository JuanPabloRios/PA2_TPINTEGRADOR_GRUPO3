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
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;

public class PrincipalSubordinadoControlador  extends AppCompatActivity implements InterfazDeComunicacion {

    private AplicacionDAO appDao = new AplicacionDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_subordinado);
        Utilidad ut = new Utilidad();
        ArrayList<Aplicacion> aplicacionesInstaladas = new ArrayList<Aplicacion>();
        for(Utilidad.InfoObject app : ut.getInstalledApps(this)){
            aplicacionesInstaladas.add( new Aplicacion(app.appname,app.packagename,app.icon));
        }
        //appDao.insertarAplicaciones(aplicacionesInstaladas);
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "insertarAplicaciones":
                Integer respuesta =AplicacionDAO.insertarAplicacionesHandler(res.getData());
                //ACA DETENER SPINNER
                if(respuesta > 0){
                    Toast.makeText(this,"TODO GENIAL",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }
}
