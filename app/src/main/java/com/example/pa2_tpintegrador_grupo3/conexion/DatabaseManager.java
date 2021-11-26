package com.example.pa2_tpintegrador_grupo3.conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {
    private static Connection conexion;
    public static Connection obtenerConexion() {
        if(conexion == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("## Creating new connexion");
                conexion = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                return conexion;
            } catch (Exception e){
                return null;
            }
        } else {
            try{
                Boolean isValid = conexion.isValid(15);
                if(isValid){
                    return conexion;
                } else {
                    conexion = null;
                    System.out.println("## Connexion not valid recalling");
                    return obtenerConexion();
                }
            } catch (Exception e){
                conexion = null;
                System.out.println("## Exception recalling");
                return obtenerConexion();
            }
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
            if(query.toUpperCase().contains("UPDATE")){
                return st.executeUpdate(query);
            } else {
                if(st.executeUpdate( query, Statement.RETURN_GENERATED_KEYS) > 0){
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()){
                        return Integer.parseInt(rs.getString(1));
                    }
                }
            }
            return -1;
        } catch (Exception e){
            System.out.println("ERROR AL UPSERSETEAR");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static ResultSet ejecutarSelect(String query){
        try{
            obtenerConexion();
            Statement st = obtenerStatement();
            return st.executeQuery(query);
        } catch (Exception e){
            System.out.println("ERROR AL SELECCIONAR ");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }
}
