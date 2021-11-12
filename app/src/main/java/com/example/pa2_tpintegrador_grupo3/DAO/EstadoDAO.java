package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Estado;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EstadoDAO {

    InterfazDeComunicacion com;
    public EstadoDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }


    public void crearEstado(Estado estado){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARESTADO");
        String query = "INSERT INTO Estado (Descripcion) VALUES (";
        query+= "\""+estado.getDescripcion()+"\",";
        query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearEstadoHandler(Object obj){
        if(obj != null){
            try {
                Integer rs = (Integer)obj;
                return rs;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void obtenerEstadoPorId(Integer id){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERESTADOPORID");
        String query = "SELECT * FROM Estado WHERE Id = \""+id+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Estado obtenerEstadoPorIdHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Estado(
                            rs.getInt("id"),
                            rs.getString("descripcion")
                    );
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void obtenerTodosLosEstados(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOSESTADOS");
        manager.setQuery("SELECT * FROM Estado WHERE Eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Estado> obtenerTodosLosEstadosHandler(Object obj){
        if(obj != null){
            ArrayList<Estado> resultados = new ArrayList<Estado>();
            try{
                ResultSet rs = (ResultSet)obj;

                while(rs.next()) {
                    resultados.add(
                            new Estado(
                                    rs.getInt("id"),
                                    rs.getString("Descripcion")
                            )
                    );
                }
                return resultados;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

}
