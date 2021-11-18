package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.entidades.Estado;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
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
        String query = "INSERT INTO Notificacion (id_dispositivo_emisor, id_Usuario_Receptor, id_aplicacion, id_tipo_notificacion, id_estado, Tiempo_Solicitado, Eliminado) VALUES (";
        query += "\""+n.getDispositivoEmisor().getId()+"\",";
        query += "\""+n.getId_Usuario_Receptor().getId()+"\",";
        if(n.getAplicacion() != null){
            query += "\""+n.getAplicacion().getId()+"\",";
        }else{
            query += "null,";
        }
        query += "\""+n.getTipoNotificacion().getId()+"\",";
        query += "\""+n.getEstado().getId()+"\",";
        query += "\""+n.getTiempo_Solicitado()+"\",";
        query+="0)";
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
        manager.setQuery("UPDATE Notificacion SET ELIMINADO = 1 WHERE id = 1");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarNotificacionHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void rechazarNotificacion(Notificacion n){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("rechazarNotificacion");
        manager.setQuery("UPDATE Notificacion SET Id_Estado = 4 WHERE id = "+n.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer rechazarNotificacionHandler(Object obj){
        if(obj != null){
            if(obj != null){
                try {
                    Integer rs = (Integer)obj;
                    return rs;
                } catch (Exception ex){
                    ex.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public void aceptarNotificacion(Notificacion n){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("aceptarNotificacion");
        manager.setQuery("UPDATE Notificacion SET Id_Estado = 3 WHERE id = "+n.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer aceptarNotificacionHandler(Object obj){
        if(obj != null){
            if(obj != null){
                try {
                    Integer rs = (Integer)obj;
                    return rs;
                } catch (Exception ex){
                    ex.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }


    public void marcarNotifiacionesComoRecibidas(ArrayList<Notificacion> notifiaciones){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("marcarNotifiacionesComoRecibidas");
        String ids = "(";
        for(Notificacion n : notifiaciones) {
            ids+= n.getId()+",";
        }
        ids = ids.substring(0, ids.length() -1);
        ids += ")";

        manager.setQuery("UPDATE Notificacion SET Id_Estado = 5 WHERE id IN "+ids);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public void obtenerNotificacionPorIdDeUsuarioMaestro(Integer idUsuario){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerNotificacionPorIdDeUsuarioMaestro");
        String query = "SELECT n.Id as idNotificacion, d.Id as idDispositivoEmisor, d.Nombre as nombreDispositivoEmisor, n.Id_Usuario_Receptor as idUsuarioReceptor, a.Id as idAplicacion, a.Descripcion as nombreAplicacion, n.Id_Tipo_Notificacion as idTipo, n.Id_Estado as idEstado, n.Tiempo_Solicitado as tiempoSolicitado FROM Notificacion AS n " +
                "LEFT JOIN Aplicacion AS a ON n.Id_Aplicacion = a.Id " +
                "INNER JOIN Dispositivo AS d ON n.Id_Dispositivo_Emisor = d.Id " +
                "WHERE n.eliminado = 0 AND Id_Estado = 1 AND Id_Usuario_Receptor = " + idUsuario;
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Notificacion> obtenerNotificacionPorIdDeUsuarioMaestroHandler(Object obj){
        if(obj != null){
            try {
                ArrayList<Notificacion> result = new ArrayList<Notificacion>();
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    result.add(
                        new Notificacion(
                            rs.getInt("idNotificacion"),
                            new Dispositivo(rs.getInt("idDispositivoEmisor"), rs.getString("nombreDispositivoEmisor")),
                            new Usuario(rs.getInt("idUsuarioReceptor")),
                            new Aplicacion(rs.getInt("idAplicacion"), rs.getString("nombreAplicacion")),
                            new TipoNotificacion(rs.getInt("idTipo")),
                            new Estado(rs.getInt("idEstado"),""),
                            rs.getLong("tiempoSolicitado")
                        )
                    );
                }
                return result;
            } catch (Exception ex){
                ex.printStackTrace();
                return null;
            }
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
                        new Dispositivo(rs.getInt("id_dispositivo_emisor")),
                        new Usuario(rs.getInt("id_Usuario_Receptor")),
                        new Aplicacion(rs.getInt("id_aplicacion")),
                        new TipoNotificacion(rs.getInt("id_tipo_notificacion")),
                        new Estado(rs.getInt("id_estado"),""),
                        rs.getLong("Tiempo_Solicitado")
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
