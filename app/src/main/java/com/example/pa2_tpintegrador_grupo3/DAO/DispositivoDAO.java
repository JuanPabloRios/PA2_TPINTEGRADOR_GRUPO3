package com.example.pa2_tpintegrador_grupo3.DAO;

import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DispositivoDAO {

    InterfazDeComunicacion com;
    public DispositivoDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }


    public void crearDispositivo(Dispositivo dispo){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARDISPOSITIVO");
        String query = "INSERT INTO Dispositivo (id_Tipo_Dispositivo, Imei) VALUES (";
            query+= "\""+dispo.getId_Tipo_Dispositivo()+"\",";
            query+= "\""+dispo.getImei()+"\",";
            query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearDispositivoHandler(Object obj){
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


    public void obtenerDispositivoPorId(Integer dispoId){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERDISPOSITIVOPORID");
        String query = "SELECT * FROM Dispositivo WHERE id = \""+dispoId+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Dispositivo obtenerDispositivoPorId(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Dispositivo(
                        rs.getInt("id"),
                        rs.getInt("id_Tipo_Dispositivo"),
                        rs.getString("Imei"),
                        rs.getBoolean("eliminado")

                    );
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }



    public void obtenerTodosLosDispositivos(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOSDISPOSITIVOS");
        manager.setQuery("SELECT * FROM Dispositivo WHERE Eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Dispositivo> obtenerTodosLosDispositivosHandler(Object obj){
        if(obj != null){
            ArrayList<Dispositivo> resultados = new ArrayList<Dispositivo>();
            try{
                ResultSet rs = (ResultSet)obj;

                while(rs.next()) {
                    resultados.add(
                        new Dispositivo(
                                rs.getInt("id"),
                                rs.getInt("id_Tipo_Dispositivo"),
                                rs.getString("Imei"),
                                rs.getBoolean("eliminado")
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
    //---------------------------------------------------------------------------------------------

    public void eliminarDispositivo(Integer dispoId){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARDISPOSITIVO");
        manager.setQuery("UPDATE Dispositivo SET Eliminado = 1 WHERE Id = "+dispoId);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarDispositivoHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }
}
