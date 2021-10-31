package com.example.pa2_tpintegrador_grupo3.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private static Connection conexion;
    public static Connection obtenerConexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            return conexion;
        } catch (Exception e){
            return null;
        }
    }

    public static Statement obtenerStatement(){
        try {
            return conexion.createStatement();
        } catch (Exception e){
            return null;
        }
    }

    public static int ejecutarUpsert(String query){
        try{
            obtenerConexion();
            Statement st = obtenerStatement();
            return st.executeUpdate( query, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e){
            System.out.println("ERROR AL UPSERSEAR ");
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    public static ResultSet ejecutarSelect(String query){
        try{
            obtenerConexion();
            Statement st = obtenerStatement();
            return st.executeQuery(query);
        } catch (Exception e){
            return null;
        }
    }
}
