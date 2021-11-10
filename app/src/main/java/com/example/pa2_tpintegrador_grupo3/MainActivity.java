package com.example.pa2_tpintegrador_grupo3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pa2_tpintegrador_grupo3.Controladores.DetallesDispositivoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.DispositivosVinculadosControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.PrincipalSubordinadoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.RegistrarUsuarioControlador;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InterfazDeComunicacion
{
    //Nos indica si el inicio de sesion es para setear el dispositivo como subordinado
    //de lo contrario sera seteado como maestro
    private Boolean esSubordinado = false;
    private Boolean primerInicio = true;
    private EditText txtNombreUsuario;
    private EditText txtPassword;
    private UsuarioDAO usDao = new UsuarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombreUsuario = (EditText) findViewById(R.id.nombreUsuarioLogin);
        txtPassword = (EditText) findViewById(R.id.contraseniaLogin);
        Utilidad ut = new Utilidad();
        System.out.println("@@getInstalledApps " + ut.getInstalledApps(this));
        //BUSCAMOS EL ARCHIVO DE CONFIGURACION

        Integer result = ut.validarTipoDispositivo(this);
        if(result != null){
            //SI EXISTE EL ARCHIVO Y EL USUARIO ES DE TIPO MAESTRO (1) PASAMOS A LA PANTALLA DE LOGIN
            primerInicio = false;
            if(result == 1) {
                LinearLayout l = (LinearLayout)findViewById(R.id.Login);
                l.setVisibility(View.VISIBLE);
                LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
                l2.setVisibility(View.GONE);
            } else if(result == 2) {
                //SI EXISTE EL ARCHIVO Y EL USUARIO ES DE TIPO SUBORDINADO (2) PASAMOS A LA PANTALLA DE INICIO DE SUBORDINADO
                startActivity(new Intent(this, PrincipalSubordinadoControlador.class));
            } else {
                Toast.makeText(this,"Error obteniendo tipo de usuario",Toast.LENGTH_SHORT);
            }
        }
        //SI NO EXISTE EL ARCHIVO QUEDAMOS EN LA PANTALLA DE SELECCION INICIAL QUE DARA PASO A LA CREACION DEL ARCHIVO DE CONFIGURACION
    }

    public void irARegistrarSubordinado(View view){
        esSubordinado = true;
        LinearLayout l = (LinearLayout)findViewById(R.id.Login);
        l.setVisibility(View.VISIBLE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
        l2.setVisibility(View.GONE);
    }
    public void irARegistrarMaestro(View view){
        LinearLayout l = (LinearLayout)findViewById(R.id.Login);
        l.setVisibility(View.VISIBLE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
        l2.setVisibility(View.GONE);
    }

    public void registrarUsuarioMaestro(View view) {
        startActivity(new Intent(this, RegistrarUsuarioControlador.class));
    }

    public void validarInicioSesionTipoUsuario(View view){
        if(validarCampos()){
            //ACA INICIAR SPINNER
            usDao.obtenerUsuarioPorNombreUsuario(txtNombreUsuario.getText().toString());
        }
    }

    public Boolean validarCampos(){
        //ACA VALIDAR EL CAMPO DE NOMBRE DE USUARIO (txtNombreUsuario) Y CONTRASENIA (txtPassword)
        return true;
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerUsuarioPorNombreUsuario":
                Usuario user = UsuarioDAO.obtenerUsuarioPorNombreUsuarioHandler(res.getData());
                //ACA DETENER SPINNER
                validarInicioSesion(user);
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }

    //Si el usuario recibido por parametro tiene ID, existe, chequeamos el nombre de usuario
    public void validarInicioSesion(Usuario us){
        if(us != null && us.getId() != null){
            Toast.makeText(this,"Usuario o contrasenia no validos",Toast.LENGTH_SHORT);
        } else {
            //SI ES EL PRIMER INICIO GUARDAMOS LA CONFIGURACION INICIAL DE TIPO DE DISPOSITIVO
            if(primerInicio){
                Utilidad utils = new Utilidad();
                Configuracion config = new Configuracion();
                config.setTipoDispositivo(esSubordinado ? 1 : 2);
                utils.guardarArchivoDeConfiguracion(config);
            }
            //SI ES SUBORDINADO VAMOS A LA PANTALLA INICIAL DE SUBORDINADO
            if(esSubordinado){
                startActivity(new Intent(this, PrincipalSubordinadoControlador.class));
            }else{
                //SI ES MAESTRO VAMOS A LA PANTALLA INICIAL DE MAESTRO
                startActivity(new Intent(this, DispositivosVinculadosControlador.class));
            }
        }
    }
}