package com.example.pa2_tpintegrador_grupo3.DAO;

import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoRestriccion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RestriccionesDAO {

    InterfazDeComunicacion com;
    public RestriccionesDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }


    public void crearRestricciones(Restricciones res){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARRESTRICCIONES");
        String query = "INSERT INTO Restriccion (Id_dispositivo,Id_Tipo_Restriccion,Id_Aplicacion,Duracion_Minutos,Eliminado) VALUES (";
        query+= res.getDispositivo().getId();
        query+= res.getTipo_Restriccion().getId();
        query+= res.getAplicacion().getId();
        query+= "\""+res.getDuracion_Minutos()+"\",";
        query+= "\""+res.getEliminado()+"\",";
        query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearRestriccionesHandler(Object obj){
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


    public void obtenerRestriccionesPorId(Integer resId){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERRESTRICCIONESPORID");
        String query = "SELECT * FROM Restriccion WHERE Id = \""+resId+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Restricciones obtenerRestriccionesPorIdHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Restricciones(
                            rs.getInt("id"),
                            new Dispositivo(rs.getInt("Id_Dispositivo"),
                                    null,
                                    "",
                                    false),
                            new TipoRestriccion(rs.getInt("Id_Tipo_Restriccion"),""),
                            new Aplicacion(rs.getInt("Id_Aplicacion")),
                            rs.getInt("duracion_Minutos"),
                            rs.getBoolean("Activa"),
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


    public void obtenerTodasLasRestricciones(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODASLASRESTRICCIONES");
        manager.setQuery("SELECT * FROM Restriccion WHERE Eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Restricciones> obtenerTodasLasRestriccionesHandler(Object obj){
        if(obj != null){
            ArrayList<Restricciones> resultados = new ArrayList<Restricciones>();
            try{
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    resultados.add(
                            new Restricciones(
                                    rs.getInt("id"),
                                    new Dispositivo(rs.getInt("Id_Dispositivo"),
                                            null,
                                            "",
                                            false),
                                    new TipoRestriccion(rs.getInt("Id_Tipo_Restriccion"),""),
                                    new Aplicacion(rs.getInt("Id_Aplicacion")),
                                    rs.getInt("duracion_Minutos"),
                                    rs.getBoolean("Activa"),
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


    public void eliminarRestricciones(Integer resId){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARRESTRICCIONES");
        manager.setQuery("UPDATE Restriccion SET Eliminado = 1 WHERE Id = "+resId);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarRestriccionesHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void actualizarRestriccionesPorSolicitud(Notificacion solicitud){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("actualizarRestriccionesPorSolicitud");
        manager.setQuery("UPDATE Restriccion SET Duracion_Minutos = Duracion_Minutos + "+solicitud.getTiempo_Solicitado()+" WHERE Id_Dispositivo = "+solicitud.getDispositivoEmisor().getId()+" AND Id_Aplicacion = "+solicitud.getAplicacion().getId() +" AND Duracion_Minutos + "+solicitud.getTiempo_Solicitado() + " <= 86400000");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }


    public void modificarRestriccion(Restricciones res){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("modificarTiempoEnRestriccion");
        manager.setQuery("UPDATE Restriccion SET Id_Dispositivo = "+res.getDispositivo().getId()+", Id_Tipo_Restriccion = "+res.getTipo_Restriccion().getId()+", Id_Aplicacion = "+res.getAplicacion().getId()
                +", Duracion_Minutos = "+res.getDuracion_Minutos()+", Activa = "+res.isActiva()+" WHERE Id = "+res.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer modificarRestriccionHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void obtenerTodasLasRestriccionesPorIdDeDispositivo(Integer idDispositivo)
    {
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerTodasLasRestriccionesPorIdDeDispositivo");
        String query =
            "SELECT " +
                "r.Id AS idRestriccion," +
                "a.Id AS idApp," +
                "a.Nombre AS appName," +
                "a.Descripcion AS appLabel," +
                "a.Icono AS appIcon," +
                "r.Duracion_Minutos AS duracion," +
                "r.Activa AS estado," +
                "d.Id AS idDispositivo," +
                "t.Id AS idTipo," +
                "exd.Tiempo_Uso AS tiempoUso " +
            "FROM Restriccion AS r " +
            "INNER JOIN Aplicacion AS a ON r.Id_Aplicacion = a.Id " +
            "INNER JOIN Dispositivo AS d ON r.Id_Dispositivo = d.Id " +
            "INNER JOIN Tipo_Restriccion AS t ON r.Id_Tipo_Restriccion = t.Id " +
            "LEFT JOIN Estadisticas_x_dispositivo AS exd ON r.Id_Aplicacion = exd.Id_Aplicacion AND r.Id_Dispositivo = exd.Id_Dispositivo " +
            "WHERE r.Eliminado = 0 AND r.Id_Dispositivo = "+idDispositivo+" "+
            "ORDER BY a.Descripcion ASC";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Restricciones> obtenerTodasLasRestriccionesPorIdDeDispositivoHandler(Object obj) {
        if(obj != null) {
            ArrayList<Restricciones> resultados = new ArrayList<Restricciones>();
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next())
                {
                    resultados.add(
                        new Restricciones(
                            rs.getInt("idRestriccion"),
                            new Dispositivo(rs.getInt("idDispositivo"), null, "", false),
                            new TipoRestriccion(rs.getInt("idTipo"),""),
                            new Aplicacion(rs.getInt("idApp"), rs.getString("appName"), rs.getString("appLabel"), rs.getString("appIcon"),false),
                            rs.getInt("duracion"),
                            rs.getBoolean("estado"),
                            false
                        )
                    );
                }
                return resultados;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        return null;
    }
}