package com.example.pa2_tpintegrador_grupo3.conexion;

public class DataDB {
    //Información de la BD
    public static String host="remotemysql.com";
    public static String port="3306";
    public static String nameBD="hphJbQZrGW";
    public static String user="hphJbQZrGW";
    public static String pass="KDJCzzOZBT";

    //Información para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/"+nameBD;
    public static String driver = "com.mysql.jdbc.Driver";
}
