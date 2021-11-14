package com.example.pa2_tpintegrador_grupo3;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.pa2_tpintegrador_grupo3.Controladores.DispositivosVinculadosControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.PrincipalSubordinadoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.RegistrarUsuarioControlador;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoDispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements InterfazDeComunicacion
{
    //Nos indica si el inicio de sesion es para setear el dispositivo como subordinado
    //de lo contrario sera seteado como maestro
    private Boolean esSubordinado = false;
    private Boolean primerInicio = true;
    private EditText txtNombreUsuario;
    private EditText txtPassword;
    private UsuarioDAO usDao = new UsuarioDAO(this);
    private DispositivoDAO dispDao = new DispositivoDAO(this);
    private Usuario user;
    private Integer idNuevoDispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilidad ut = new Utilidad(); 
        //BUSCAMOS EL ARCHIVO DE CONFIGURACION 
        Integer result = ut.validarTipoDispositivo(this);
        setContentView(R.layout.activity_main);
        txtNombreUsuario = (EditText) findViewById(R.id.nombreUsuarioLogin);
        txtPassword = (EditText) findViewById(R.id.contraseniaLogin);
        requestUsageStatsPermission();
        if(result != null){
            primerInicio = false;
            if(result == 1) {
                //SI EXISTE EL ARCHIVO Y EL USUARIO ES DE TIPO MAESTRO (1) PASAMOS A LA PANTALLA DE LOGIN
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

    void requestUsageStatsPermission() {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && !hasUsageStatsPermission(this)) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    boolean hasUsageStatsPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), context.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
        return granted;
    }

    public void irARegistrarSubordinado(View view){
        esSubordinado = true;
        mostrarLogin();
    }

    public void irARegistrarMaestro(View view){
        mostrarLogin();
    }

    private void mostrarLogin(){
        LinearLayout l = (LinearLayout)findViewById(R.id.Login);
        l.setVisibility(View.VISIBLE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
        l2.setVisibility(View.GONE);
        EditText nombreDispositivo = findViewById(R.id.nombreDispositivo);
        if(esSubordinado){
            nombreDispositivo.setVisibility(View.VISIBLE);
        } else {
            nombreDispositivo.setVisibility(View.GONE);
        }
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
                this.user = UsuarioDAO.obtenerUsuarioPorNombreUsuarioHandler(res.getData());
                //ACA DETENER SPINNER
                validarInicioSesion(this.user);
                break;
            case "crearDispositivo":
                this.idNuevoDispositivo = DispositivoDAO.crearDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(this.idNuevoDispositivo > 0){
                    //ACA INICIAL EL SPINNER
                    Utilidad ut = new Utilidad();
                    Configuracion c = ut.obtenerConfiguracion(this);
                    c.getDispositivo().setId(this.idNuevoDispositivo);
                    ut.guardarArchivoDeConfiguracion(this,c);
                    dispDao.relacionarDispositivoConUsuario(this.idNuevoDispositivo,this.user.getId());
                } else {
                    Toast.makeText(this,"Error creando el dispositivo en la base de datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case "relacionarDispositivoConUsuario":
                Integer idNuevaRelacion = DispositivoDAO.relacionarDispositivoConUsuarioHandler(res.getData());
                //ACA DETENER SPINNER
                if(idNuevaRelacion > 0){
                    redireccionar(this.user);
                } else {
                    Toast.makeText(this,"Error creando relacion con dispositivo",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }

    //Si el usuario recibido por parametro tiene ID, existe, chequeamos la contrasenia y continuamos
    public void validarInicioSesion(Usuario us){
        System.out.println("validarInicioSesion");
        if(us != null && us.getId() != null){
            if(txtPassword.getText().toString().equals(us.getContrasenia())){
                //SI ES EL PRIMER INICIO GUARDAMOS LA CONFIGURACION INICIAL DE TIPO DE DISPOSITIVO
                if(primerInicio){
                    Utilidad utils = new Utilidad();
                    Configuracion config = new Configuracion();
                    config.setDispositivo(new Dispositivo());
                    config.getDispositivo().setImei(UUID.randomUUID().toString());
                    config.getDispositivo().setTipo_Dispositivo(new TipoDispositivo(1, "MAESTRO")); //1 si es MAESTRO

                    if(esSubordinado){
                        config.getDispositivo().setTipo_Dispositivo(new TipoDispositivo(2, "SUBORDINADO")); //2 si es SUBORDINADO
                    }
                    config.getDispositivo().setMarca(Build.MANUFACTURER);
                    config.getDispositivo().setModelo(Build.MODEL);
                    EditText nombreDispositivo = findViewById(R.id.nombreDispositivo);
                    config.getDispositivo().setNombre(nombreDispositivo.getText().toString());
                    System.out.println("DISPOSITIVO A INSERTAR " + config.getDispositivo());
                    if(utils.guardarArchivoDeConfiguracion(this,config)){
                        //ACA INICIAR EL SPINNER
                        dispDao.crearDispositivo(config.getDispositivo());
                    }
                } else {
                    redireccionar(us);
                }
            } else {
                Toast.makeText(this,"Usuario o contrasenia no validos",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Usuario o contrasenia no validos",Toast.LENGTH_SHORT).show();
        }
    }

    private void redireccionar(Usuario us){
        //SI ES SUBORDINADO VAMOS A LA PANTALLA INICIAL DE SUBORDINADO
        if(esSubordinado){
            System.out.println("SUBORDINADO");Intent i = new Intent(this, PrincipalSubordinadoControlador.class);
            i.putExtra("usuario",us);
            i.putExtra("primerInicio",primerInicio);
            startActivity(i);
        }else{
            System.out.println("MAESTRO");
            //SI ES MAESTRO VAMOS A LA PANTALLA INICIAL DE MAESTRO
            Intent i = new Intent(this, DispositivosVinculadosControlador.class);
            i.putExtra("usuario",us);
            i.putExtra("primerInicio",primerInicio);
            startActivity(i);
        }
    }
}