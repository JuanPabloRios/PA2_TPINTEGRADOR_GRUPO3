package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAO {
    //En esta interfaz enviamos la clase padre que llama al metodo, y recibe los datos en la implementacion del metodo operacionConBaseDeDatosFinalizada()
    InterfazDeComunicacion com;

    public UsuarioDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }

    public void crearUsuario(){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARUSUARIO");
        manager.setQuery("INSERT INTO usuario (usuario, password, email) VALUES (\"JuanPablo\",\"Pass\",\"jp@jp.com\")");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public void eliminarUsuario(){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARUSUARIO");
        manager.setQuery("UPDATE usuario SET ELIMINADO = 1 WHERE id = 1");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public void obtenerTodosLosUsuarios(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOSUSUARIOS");
        manager.setQuery("SELECT * FROM usuario WHERE eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Usuario> obtenerTodosLosUsuariosHandler(Object obj){
        if(obj != null){
            ResultSet  resultados = (ResultSet)obj;
        }
        return null;
    }
}
