package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TipoUsuarioDAO 
{
	InterfazDeComunicacion com;
	public TipoUsuarioDAO(InterfazDeComunicacion ic)
	{
		this.com = ic;
	}

	public void obtenerTodosLosTipoUsuarios()
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERTODOSLOSTIPOUSUARIOS");
		manager.setQuery("SELECT * FROM Tipo_Usuario");
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<TipoUsuario> obtenerTodosLosTipoUsuariosHandler(Object obj)
	{
		if(obj != null)
		{
			ArrayList<TipoUsuario> resultados = new ArrayList<TipoUsuario>();
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					resultados.add(
						new TipoUsuario(
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