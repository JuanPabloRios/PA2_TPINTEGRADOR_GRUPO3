package com.example.pa2_tpintegrador_grupo3.DAO;

import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
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

    public void modificarTiempoEnRestriccion(Restricciones res){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("modificarTiempoEnRestriccion");
        manager.setQuery("UPDATE Restriccion SET Duracion_Minutos = "+res.getDuracion_Minutos()+" WHERE Id = "+res.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer modificarTiempoEnRestriccionHandler(Object obj){
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
                "SELECT r.Id as idRestriccion,"+
                    "a.Id as idApp,"+
                    "a.Nombre as appName,"+
                    "a.Descripcion as appLabel,"+
                    "a.Icono as appIcon,"+
                    "r.Duracion_Minutos as duracion,"+
                    "d.Id as idDispositivo,"+
                    "t.Id as idTipo "+
                "FROM Restriccion as r "+
                "INNER JOIN Aplicacion as a ON r.Id_Aplicacion = a.Id "+
                "INNER JOIN Dispositivo as d on r.Id_Dispositivo = d.Id "+
                "INNER JOIN Tipo_Restriccion as t on r.Id_Tipo_Restriccion = t.Id "+
                "WHERE r.Eliminado = 0 AND Id_Dispositivo = "+ idDispositivo+" "+
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