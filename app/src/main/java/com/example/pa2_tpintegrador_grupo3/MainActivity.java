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
import android.widget.TextView;
import android.widget.Toast;
import com.example.pa2_tpintegrador_grupo3.Controladores.DispositivosVinculadosControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.PrincipalSubordinadoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.RegistrarUsuarioControlador;
import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.BloqueoWrapper;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoDispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements InterfazDeComunicacion
{
    //Nos indica si el inicio de sesion es para setear el dispositivo como subordinado
    //de lo contrario sera seteado como maestro
    private Boolean esSubordinado = false;
    private Boolean primerInicio = true;
    private EditText txtNombreUsuario;
    private EditText txtPassword;
    private EditText nombreDispositivo;
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
        nombreDispositivo = (EditText) findViewById(R.id.nombreDispositivo);
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
                finish();
            } else {
                Toast.makeText(this,"Error obteniendo tipo de usuario",Toast.LENGTH_SHORT);
            }
        }
        //SI NO EXISTE EL ARCHIVO QUEDAMOS EN LA PANTALLA DE SELECCION INICIAL
        // QUE DARA PASO A LA CREACION DEL ARCHIVO DE CONFIGURACION
    }

    void mostrarSpinner(){
        LinearLayout spinner = findViewById(R.id.spinnerMain);
        spinner.setVisibility(View.VISIBLE);
        LinearLayout mainContainer = findViewById(R.id.mainContainer);
        mainContainer.setVisibility(View.GONE);
    }

    void ocultarSpinner(){
        LinearLayout spinner = findViewById(R.id.spinnerMain);
        spinner.setVisibility(View.GONE);
        LinearLayout mainContainer = findViewById(R.id.mainContainer);
        mainContainer.setVisibility(View.VISIBLE);
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
        TextView txtRegistrar = findViewById(R.id.txtRegistrar);
        EditText nombreDispositivo = findViewById(R.id.nombreDispositivo);
        if(esSubordinado){
            nombreDispositivo.setVisibility(View.VISIBLE);
            txtRegistrar.setVisibility(View.GONE);
        } else {
            txtRegistrar.setVisibility(View.VISIBLE);
            nombreDispositivo.setVisibility(View.GONE);
        }
    }
    public void registrarUsuarioMaestro(View view) {
        startActivity(new Intent(this, RegistrarUsuarioControlador.class));
    }

    public void validarInicioSesionTipoUsuario(View view){
        if(validarCampos()){
            mostrarSpinner();
            usDao.obtenerUsuarioPorNombreUsuario(txtNombreUsuario.getText().toString());
        }
    }

    public Boolean validarCampos(){
        //ACA VALIDAR EL CAMPO DE NOMBRE DE USUARIO (txtNombreUsuario) Y CONTRASENIA (txtPassword)
        Utilidad ut = new Utilidad();
        if(ut.validateEmpty(txtNombreUsuario.getText().toString())){
            txtNombreUsuario.setError("El campo Nombre no puede estar vacio.");
            return false;
        }
        if(ut.validateString(txtNombreUsuario.getText().toString())){
            txtNombreUsuario.setError("No se permiten numeros en el campo Nombre.");
            return false;
        }

        if(ut.validateEmpty(txtPassword.getText().toString())){
            txtPassword.setError("El campo Contraseña no puede estar vacio.");
            return false;
        }
        if(esSubordinado){
            //validar campo nombre de telefono
            if(ut.validateEmpty(nombreDispositivo.getText().toString())){
                nombreDispositivo.setError("El campo Nombre dispositvo no puede estar vacio.");
                return false;
            }

        }
        return true;
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerUsuarioPorNombreUsuario":
                this.user = UsuarioDAO.obtenerUsuarioPorNombreUsuarioHandler(res.getData());
                validarInicioSesion(this.user);
                break;
            case "crearDispositivo":
                this.idNuevoDispositivo = DispositivoDAO.crearDispositivoHandler(res.getData());
                if(this.idNuevoDispositivo > 0){
                    Utilidad ut = new Utilidad();
                    Configuracion c = ut.obtenerConfiguracion(this);
                    c.getDispositivo().setId(this.idNuevoDispositivo);
                    ut.guardarArchivoDeConfiguracion(this,c);
                    dispDao.relacionarDispositivoConUsuario(this.idNuevoDispositivo,this.user.getId());
                } else {
                    ocultarSpinner();
                    Toast.makeText(this,"Error creando el dispositivo en la base de datos",Toast.LENGTH_SHORT).show();
                }
                break;
            case "relacionarDispositivoConUsuario":
                Integer idNuevaRelacion = DispositivoDAO.relacionarDispositivoConUsuarioHandler(res.getData());
                if(idNuevaRelacion > 0){
                    if(esSubordinado && primerInicio){
                        Toast.makeText(this,"Equipo registrado como subordinado correctamente",Toast.LENGTH_SHORT).show();
                    }
                    redireccionar(this.user);
                } else {
                    ocultarSpinner();
                    Toast.makeText(this,"Error creando relacion con dispositivo",Toast.LENGTH_SHORT).show();
                }
                break;
            case "obtenerTodosLosDispositivosPorUsuario":
                ArrayList<Dispositivo> dispositivos = DispositivoDAO.obtenerTodosLosDispositivosPorUsuarioHandler(res.getData());
                boolean encontrado = false;

                //ACA DETENER SPINNER
                if(dispositivos != null || !dispositivos.isEmpty()) {
                    //poner el adderror en el campo
                    for(Dispositivo disp : dispositivos){
                        if(disp.getNombre().toUpperCase().equals(nombreDispositivo.getText().toString().toUpperCase())){
                            encontrado = true;
                            break;
                        }
                    }
                }
                if(!encontrado){
                    Utilidad utils = new Utilidad();
                    Configuracion config = new Configuracion();
                    config.setDispositivo(new Dispositivo());
                    config.getDispositivo().setImei(UUID.randomUUID().toString());
                    config.getDispositivo().setTipo_Dispositivo(new TipoDispositivo(2, "SUBORDINADO")); //2 si es SUBORDINADO
                    config.getDispositivo().setMarca(Build.MANUFACTURER);
                    config.getDispositivo().setModelo(Build.MODEL);
                    config.getDispositivo().setUsuarioMaestro(this.user);
                    EditText nombreDispositivo = findViewById(R.id.nombreDispositivo);
                    config.getDispositivo().setNombre(nombreDispositivo.getText().toString());
                    if(utils.guardarArchivoDeConfiguracion(this,config)){
                        dispDao.crearDispositivo(config.getDispositivo());
                    }
                } else {
                    ocultarSpinner();
                    nombreDispositivo.setError("El nombre del Subordinado ya esta en uso!");
                }
                break;
        }
    }

    //Si el usuario recibido por parametro tiene ID, existe, chequeamos la contrasenia y continuamos
    public void validarInicioSesion(Usuario us){
        if(us != null && us.getId() != null){
            Utilidad ut = new Utilidad();
            BloqueoWrapper bq = ut.obtenerArchivoDeReintentosFallidos(this);
            if(bq == null){
                bq = new BloqueoWrapper();
            }
            if(validarIntentos(bq)){
                if(txtPassword.getText().toString().equals(us.getContrasenia())){
                    //SI ES EL PRIMER INICIO GUARDAMOS LA CONFIGURACION INICIAL DE TIPO DE DISPOSITIVO
                    ut.eliminarArchivoDeReintentosFallidos(this);
                    if(primerInicio && esSubordinado){
                        dispDao.obtenerTodosLosDispositivosPorUsuario(us);
                    }else if(!esSubordinado && primerInicio){
                        Utilidad utils = new Utilidad();
                        Configuracion config = new Configuracion();
                        config.setDispositivo(new Dispositivo());
                        config.getDispositivo().setImei(UUID.randomUUID().toString());
                        config.getDispositivo().setTipo_Dispositivo(new TipoDispositivo(1, "MAESTRO")); //1 si es MAESTRO
                        config.getDispositivo().setMarca(Build.MANUFACTURER);
                        config.getDispositivo().setModelo(Build.MODEL);
                        config.getDispositivo().setUsuarioMaestro(us);
                        EditText nombreDispositivo = findViewById(R.id.nombreDispositivo);
                        config.getDispositivo().setNombre(nombreDispositivo.getText().toString());
                        if(utils.guardarArchivoDeConfiguracion(this,config)){
                            dispDao.crearDispositivo(config.getDispositivo());
                        }
                    } else {
                        redireccionar(us);
                    }
                } else {
                    ocultarSpinner();
                    bq.setIntentos(bq.getIntentos()+1);
                    if(bq.getIntentos() == 3){
                        bq.setInicio(System.currentTimeMillis());
                    }
                    ut.guardarArchivoReintentosFallidos(this,bq);
                    Toast.makeText(this,"Usuario o contrasenia no validos",Toast.LENGTH_SHORT).show();
                }
            } else {
                ocultarSpinner();
                Toast.makeText(this,"A superado los 3 intentos permitidos, debera esperar 30 minutos.",Toast.LENGTH_SHORT).show();
            }
        } else {
            ocultarSpinner();
            Toast.makeText(this,"Usuario o contrasenia no validos",Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean validarIntentos(BloqueoWrapper bq){
        if(bq.getIntentos() <= 2){
            return true;
        } else {
            if(System.currentTimeMillis() > (bq.getInicio()+30*60*1000)){
                Utilidad ut = new Utilidad();
                bq = new BloqueoWrapper();
                ut.guardarArchivoReintentosFallidos(this,bq);
                return true;
            }
        }
        return false;
    }

    private void redireccionar(Usuario us){
        //SI ES SUBORDINADO VAMOS A LA PANTALLA INICIAL DE SUBORDINADO
        if(esSubordinado){
            Intent i = new Intent(this, PrincipalSubordinadoControlador.class);
            i.putExtra("usuario",us);
            i.putExtra("primerInicio",primerInicio);
            startActivity(i);
            finish();
        }else{
            //SI ES MAESTRO VAMOS A LA PANTALLA INICIAL DE MAESTRO
            Intent i = new Intent(this, DispositivosVinculadosControlador.class);
            i.putExtra("usuario",us);
            i.putExtra("primerInicio",primerInicio);
            startActivity(i);
            finish();
        }
    }
}