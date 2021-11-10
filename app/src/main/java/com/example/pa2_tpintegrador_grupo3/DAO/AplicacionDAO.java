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