package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AplicacionDAO 
{
	InterfazDeComunicacion com;
	public AplicacionDAO(InterfazDeComunicacion ic)
	{
		this.com = ic;
	}

	public void crearAplicacion(Aplicacion us)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("CREARAPLICACION");
		String query = "INSERT INTO Aplicacion (Nombre, Descripcion, Icono) VALUES (";
			query+= "\""+us.getNombre()+"\",";
			query+= "\""+us.getDescripcion()+"\",";
			query+= us.getIcono();
			query+=")";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer crearAplicacionHandler(Object obj)
	{
		if(obj != null)
		{
			try
			{
				Integer rs = (Integer)obj;
				return rs;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}

	public void obtenerAplicacionPorNombre(String nombreAplicacion)
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERAPLICACIONPORNOMBRE");
		String query = "SELECT * FROM Aplicacion WHERE Nombre = \""+nombreAplicacion+"\" AND Eliminado = 0";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}
    
	public static Aplicacion obtenerAplicacionPorNombreHandler(Object obj)
	{
		if(obj != null)
		{
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					return new Aplicacion(
						rs.getInt("id"),
						rs.getString("Nombre"),
						rs.getString("Descripcion"),
						rs.getString("Icono"),
						rs.getBoolean("Eliminado")
					);
				}
				return null;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}


	public void obtenerAplicacionesPorNombre(ArrayList<String> nombresLst) {
		SelectManager manager = new SelectManager();
		manager.setIdentificador("obtenerAplicacionesPorNombre");
		String nombres = "";
		for(String n : nombresLst){
			nombres+= "'"+n+"',";
		}
		nombres = nombres.substring(0, nombres.length() -1);
		String query = "SELECT * FROM Aplicacion WHERE Nombre IN ("+nombres+") AND Eliminado = 0";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<Aplicacion> obtenerAplicacionesPorNombreHandler(Object obj) {
		if(obj != null) {
			ArrayList<Aplicacion> res = new ArrayList<Aplicacion>();
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next()) {
					res.add(new Aplicacion(
						rs.getInt("id"),
						rs.getString("Nombre"),
						rs.getString("Descripcion"),
						rs.getString("Icono"),
						rs.getBoolean("Eliminado")
					));
				}
				return res;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return null;
	}


	public void obtenerTodasLasAplicaciones()
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERTODASLASAPLICACIONES");
		manager.setQuery("SELECT * FROM Aplicacion WHERE Eliminado = 0");
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<Aplicacion> obtenerTodasLasAplicacionesHandler(Object obj) {
		if(obj != null) {
			ArrayList<Aplicacion> resultados = new ArrayList<Aplicacion>();
			try {
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					resultados.add(
						new Aplicacion(
							rs.getInt("id"),
							rs.getString("Nombre"),
							rs.getString("Descripcion"),
							rs.getString("Icono"),
							rs.getBoolean("Eliminado")
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

	public void relacionarAplicacionesConDispositivo(ArrayList<Aplicacion> aplicaciones, Integer idDispositivo) {
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("relacionarAplicacionesConDispositivo");
		String query = "REPLACE INTO Restriccion (Id_Dispositivo, Id_Tipo_Restriccion, Id_Aplicacion, Duracion_Minutos, Activa) VALUES ";
		for(Aplicacion app : aplicaciones) {
			query+= "("+idDispositivo+",";
			query+= "2,";
			query+= app.getId()+",";
			query+= 0+",";
			query+= "false";
			query+="),";
		}
		query = query.substring(0, query.length() -1);
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer relacionarAplicacionesConDispositivoHandler(Object obj) {
		if(obj != null) {
			return (Integer)obj;
		}
		return null;
	}

	public void insertarAplicaciones(ArrayList<Aplicacion> aplicaciones) {
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("insertarAplicaciones");
		//AGREGAMOS EL IGNORE YA QUE ES PROBABLE QUE YA EXISTAN LAS APPS, DE ESTA MANERA NO LAS CREAMOS SI NO ES NECESARIO
		String query = "INSERT IGNORE INTO Aplicacion (Nombre, Descripcion, Icono) VALUES ";
		for(Aplicacion app : aplicaciones) {
			query+= "(\""+app.getNombre()+"\",";
			query+= "\""+app.getDescripcion()+"\",";
			query+= "\""+app.getIcono()+"\"";
			query+="),";
		}
		query = query.substring(0, query.length() -1);
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer insertarAplicacionesHandler(Object obj) {
		if(obj != null) {
			return (Integer)obj;
		}
		return null;
	}

	public void eliminarAplicacion(Integer idAplicacion)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("ELIMINARAplicacion");
		manager.setQuery("UPDATE Aplicacion SET Eliminado = 1 WHERE Id = "+idAplicacion);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer eliminarAplicacionHandler(Object obj)
	{
		if(obj != null)
		{
			return (Integer)obj;
		}
		return null;
	}
}