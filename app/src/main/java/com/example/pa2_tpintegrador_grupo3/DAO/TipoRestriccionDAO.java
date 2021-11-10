package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoRestriccion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoRestriccionDAO 
{
	InterfazDeComunicacion com;
	public TipoRestriccionDAO(InterfazDeComunicacion ic)
	{
		this.com = ic;
	}

	public void eliminarTipoRestriccion(Integer idTipoRestriccion)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("ELIMINARTipoRestriccion");
		manager.setQuery("UPDATE TipoRestriccion SET Eliminado = 1 WHERE Id = "+idTipoRestriccion);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer eliminarTipoRestriccionHandler(Object obj)
	{
		if(obj != null)
		{
			return (Integer)obj;
		}
		return null;
	}
}