package com.example.pa2_tpintegrador_grupo3.DAO;

import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoDispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoDispositivoDAO {

    InterfazDeComunicacion com;
    public TipoDispositivoDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }


    public void crearTipoDispostivo(TipoDispositivo tDispo){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARTIPODISPOSITIVO");
        String query = "INSERT INTO TipoDispositivo (Descripcion) VALUES (";
            query+= "\""+tDispo.getDescripcion()+"\",";
            query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearTipoDispostivoHandler(Object obj){
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

     public void obtenerTipoDispositivoPorId(Integer id){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTIPODISPOSITIVOPORID");
        String query = "SELECT * FROM TipoDispositivo WHERE Id = \""+id+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static TipoDispositivo obtenerTipoDispositivoPorIdHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new TipoDispositivo(
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

    public void obtenerTodosLosTipoDispositivo(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOTIPODISPOSITIVO");
        manager.setQuery("SELECT * FROM TipoDispositivo WHERE Eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<TipoDispositivo> obtenerTodosLosTipoDispositivoHandler(Object obj){
        if(obj != null){
            ArrayList<TipoDispositivo> resultados = new ArrayList<TipoDispositivo>();
            try{
                ResultSet rs = (ResultSet)obj;

                while(rs.next()) {
                    resultados.add(
                        new TipoDispositivo(
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
