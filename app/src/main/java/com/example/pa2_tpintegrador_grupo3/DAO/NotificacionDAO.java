package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class NotificacionDAO {

    InterfazDeComunicacion com;

    public NotificacionDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }

    public void crearNotificacion(Notificacion n){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARNOTIFICACION");
        String query = "INSERT INTO notificacion (id_dispositivo_emisor, id_dispositivo_receptor, id_aplicacion, id_tipo_notificacion, id_estado, eliminado) VALUES (";
        query += "\""+n.getDispositivoEmisor().getId()+"\",";
        query += "\""+n.getDispositivoReceptor().getId()+"\",";
        query += "\""+n.getAplicacion().getId()+"\",";
        query += "\""+n.getTipoNotificacion().getId()+"\",";
        query += "\""+n.getEstado().getId()+"\",";
        query += "\""+0;
        query +=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearNotificacionHandler(Object obj){
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

    public void eliminarNotificacion(){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARNOTIFICACION");
        manager.setQuery("UPDATE notificacion SET ELIMINADO = 1 WHERE id = 1");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarNotificacionHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void obtenerNotificacionPorId(Integer id){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERNOTIFICACIONPORID");
        String query = "SELECT * FROM Notificacion WHERE id = \""+id+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Notificacion obtenerNotificacionPorIdHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Notificacion(
                            rs.getInt("id"),
                            new Dispositivo(rs.getInt("id_dispositivo_emisor"),""),
                            new Dispositivo(rs.getInt("id_dispositivo_receptor"),""),
                            new Aplicacion(rs.getInt("id_aplicacion"),""),
                            new TipoNotificacion(rs.getInt("id_tipo_notificacion"),""),
                            new Estado(rs.getInt("id_estado"),""),
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

    public void obtenerTodasLasNotificaciones(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODASLASNOTIFICACIONES");
        manager.setQuery("SELECT * FROM notificacion WHERE eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Notificacion> obtenerTodasLasNotificacionesHandler(Object obj){
        if(obj != null){
            ResultSet  resultados = (ResultSet)obj;
        }
        return null;
    }
}
