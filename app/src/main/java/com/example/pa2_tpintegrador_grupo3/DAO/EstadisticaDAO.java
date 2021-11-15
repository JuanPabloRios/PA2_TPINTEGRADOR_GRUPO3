package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EstadisticaDAO {

    InterfazDeComunicacion com;
    public EstadisticaDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }

    public void insertarEstadisticas(ArrayList<Estadistica> estadisticas) {
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("insertarEstadisticas");
        //EN ESTA TABLA EL PRIMARY KEY ESTA FORMADO POR Id_Dispositivo y Id_Aplicacion POR ESTO USAMOS EL REPLACE
        //PARA QUE EN CASO DE EXISTIR MODIFIQUE EL REGISTRO Y SI NO EXISTE, LO INSERTA COMO NUEVO
        String query = "REPLACE INTO Estadisticas_x_dispositivo(Id_Dispositivo, Id_Aplicacion, Tiempo_Uso) VALUES ";
        for(Estadistica stat : estadisticas) {
            query+= "("+stat.getDispositivo().getId()+",";
            query+= ""+stat.getAplicacion().getId()+",";
            query+= stat.getTiempo_Uso();
            query+="),";
        }
        query = query.substring(0, query.length() -1);
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer insertarEstadisticasHandler(Object obj) {
        if(obj != null) {
            return (Integer)obj;
        }
        return null;
    }


    public void obtenerEstadisticasDeDispositivo(Dispositivo d) {
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerEstadisticasDeDispositivo");
        //EN ESTA TABLA EL PRIMARY KEY ESTA FORMADO POR Id_Dispositivo y Id_Aplicacion POR ESTO USAMOS EL REPLACE
        //PARA QUE EN CASO DE EXISTIR MODIFIQUE EL REGISTRO Y SI NO EXISTE, LO INSERTA COMO NUEVO
        String query =
            "SELECT " +
                "a.Descripcion AS appDescripcion," +
                "a.Icono AS appIcono," +
                "e.Tiempo_Uso AS tiempoUso " +
            "FROM Estadisticas_x_dispositivo as e " +
            "INNER JOIN Aplicacion AS a ON e.Id_Aplicacion = a.Id " +
            "WHERE e.Id_Dispositivo = "+d.getId() +" ORDER BY e.Tiempo_Uso DESC";
        System.out.println("@@@ query " + query);
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Estadistica> obtenerEstadisticasDeDispositivoHandler(Object obj) {
        if(obj != null) {
            ArrayList<Estadistica> resultado = new ArrayList<Estadistica>();
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    resultado.add(new Estadistica(
                        new Aplicacion(rs.getString("appDescripcion"),rs.getString("appIcono")),
                        rs.getLong("tiempoUso")
                    ));
                }
                return resultado;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
