package com.example.pa2_tpintegrador_grupo3.Controladores;

import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.MainActivity;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad; 
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion; 
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion; 
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.google.gson.Gson; 

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RegistrarUsuarioControlador extends AppCompatActivity implements InterfazDeComunicacion {

    private EditText txtNombreUsuario;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtCopiaPassoword;
    private UsuarioDAO usDao = new UsuarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        txtNombreUsuario = (EditText)findViewById(R.id.txtNombreUsuario);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtCopiaPassoword = (EditText)findViewById(R.id.txtCopiaPassoword);

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("guardadoConfirmado")){
            Toast.makeText(this,"Usuario Creado correctamente!",Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Error al crear Usuario!");
        }
    }

    public void validarIngresos(View view){
        //Primero validamos los campos ingresados.
        if(validarCampos()) {
            //Verificamos que no exista un usuario con el mismo email o nombre de usuario.
            usDao.obtenerUsuarioPorNombreUsuarioOEmail(txtNombreUsuario.getText().toString(),txtEmail.getText().toString());
            //ACA ACTIVAR SPINNER (EN EL METODO DE RESPUESTA DE BASE DE DATOS FRENAMOS EL SPINNER Y CONTINUAMOS
        } else {
            Toast.makeText(this,"Formato de datos incorrecto",Toast.LENGTH_SHORT).show();
        }
    }


    public boolean validarCampos(){
        Utilidad ut = new Utilidad();
        if(ut.validateEmpty(txtNombreUsuario.getText().toString())){
            txtNombreUsuario.setError("El campo Nombre no puede estar vacio.");
            return false;
        }

        if(ut.validateString(txtNombreUsuario.getText().toString())){
            txtNombreUsuario.setError("No se permiten numeros en el campo Nombre.");
            return false;
        }


        if(ut.validateEmpty(txtEmail.getText().toString())){
            txtEmail.setError("El campo Email no puede estar vacio.");
            return false;
        }

        if(ut.emailInvalido(txtEmail.getText().toString())){
            txtEmail.setError("No es un Email valido.");
            return false;
        }

        if(ut.validateEmpty(txtPassword.getText().toString())){
            txtPassword.setError("El campo Contrase??a no puede estar vacio.");
            return false;
        }

        if(ut.validateEmpty(txtCopiaPassoword.getText().toString())){
            txtCopiaPassoword.setError("EL campo Repetir Contrase??a no puede estar vacio.");
            return false;
        }

        if(!txtPassword.getText().toString().equals(txtCopiaPassoword.getText().toString())){
            txtPassword.setError("Las contrase??as deben ser iguales");
            return false;
        }
        return true;
    }

    //ESTE METODO RECIBE TODOS LOS RESULTADOS DE CONSULTAS A LA BASE DE DATOS.
    //USAMOS EL IDENTIFICADOR Y SU CORRESPONDIENTE HANDLER PARA OBTENER EL RESULTADO FINAL
    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "OBTENERUSUARIOPORNOMBREUSUARIOOEMAIL":
                Usuario user = UsuarioDAO.obtenerUsuarioPorNombreUsuarioOEmailHandler(res.getData());
                //ACA DETENER SPINNER
                usuarioExistenteHandler(user);
                break;
            case "CREARUSUARIO":
                Integer user2 = UsuarioDAO.crearUsuarioHandler(res.getData());
                //ACA DETENER SPINNER
                if(user2 != null && user2 > 0){
                    Toast.makeText(this,"Registrado correctamente",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class)); finish();
                    finish();
                } else {
                    Toast.makeText(this,"Ocurrio un problema al crear el usuario",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //Si el usuario recibido por parametro tiene ID, confirmamos que campo existe e indicamos el error
    public void usuarioExistenteHandler(Usuario us){
        if(us != null && us.getId() != null){
            if(txtNombreUsuario.getText().toString().equals(us.getUsuario())){
                txtNombreUsuario.setError("El nombre de usuario ya esta en uso");
            } else {
                txtEmail.setError("El email ya esta en uso");
            }
            Toast.makeText(this,"Usuario ya registrado",Toast.LENGTH_SHORT).show();
        } else {
            Usuario objUser = new Usuario();
            objUser.setUsuario(txtNombreUsuario.getText().toString());
            objUser.setContrasenia(txtPassword.getText().toString());
            objUser.setEmail(txtEmail.getText().toString());
            objUser.setTipoUsuario(new TipoUsuario(1,""));
            //ACA ACTIVAR EL SPINNER
            usDao.crearUsuario(objUser);
        }
    }
}
