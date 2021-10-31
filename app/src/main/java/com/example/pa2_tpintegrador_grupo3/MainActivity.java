package com.example.pa2_tpintegrador_grupo3;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InterfazDeComunicacion {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_subordinado);

        UsuarioDAO usDao = new UsuarioDAO(this);
        usDao.obtenerTodosLosUsuarios();
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "OBTENERTODOSLOSUSUARIOS":
                ArrayList<Usuario> users = UsuarioDAO.obtenerTodosLosUsuariosHandler(res.getData());
                break;
            case "CREARUSUARIO":
                Usuario user = UsuarioDAO.creatUsarioHandler(res.getData());
                break;
            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }
}