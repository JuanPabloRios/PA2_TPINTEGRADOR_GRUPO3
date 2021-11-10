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

	public void obtenerTodosLosTipoRestricciones()
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERTODOSLOSTIPORESTRICCIONES");
		manager.setQuery("SELECT * FROM Tipo_Restriccion");
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<TipoRestriccion> obtenerTodosLosTipoRestriccionesHandler(Object obj)
	{
		if(obj != null)
		{
			ArrayList<TipoRestriccion> resultados = new ArrayList<TipoRestriccion>();
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					resultados.add(
						new TipoRestriccion(
							rs.getInt("id"),
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