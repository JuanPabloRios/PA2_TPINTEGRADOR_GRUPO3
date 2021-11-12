package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Notificacion;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoNotificacionDAO 
{
	InterfazDeComunicacion com;
	public TipoNotificacionDAO(InterfazDeComunicacion ic)
	{
		this.com = ic;
	}

	public void obtenerTodosLosTipoNotificaciones()
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERTODOSLOSTIPONOTIFICACIONES");
		manager.setQuery("SELECT * FROM Tipo_Notificacion");
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<TipoNotificacion> obtenerTodosLosTipoNotificacionesHandler(Object obj)
	{
		if(obj != null)
		{
			ArrayList<TipoNotificacion> resultados = new ArrayList<TipoNotificacion>();
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					resultados.add(
						new TipoNotificacion(
							rs.getString("Descripcion")
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