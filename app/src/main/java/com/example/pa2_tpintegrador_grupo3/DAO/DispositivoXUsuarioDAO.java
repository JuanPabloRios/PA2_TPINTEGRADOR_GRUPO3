package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.DispositivoXUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DispositivoXUsuarioDAO 
{
	InterfazDeComunicacion com;
	public DispositivoXUsuarioDAO(InterfazDeComunicacion ic)
	{
		this.com = ic;
	}

	public void crearDispositivoXUsuario(DispositivoXUsuario us)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("CREARDISPOSITIVOXUSUARIO");
		String query = "INSERT INTO Dispositivo_x_usuario (Id_Dispositivo, Id_Usuario) VALUES (";
			query+= "\""+us.getUsuario().getId()+"\",";
			query+= us.getDispositivo().getId();
			query+=")";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer crearDispositivoXUsuarioHandler(Object obj)
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

	public void obtenerIDPorUsuario(Integer idUsuario)
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERIDPORUSUARIO");
		String query = "SELECT * FROM Dispositivo_x_usuario WHERE Id_Usuario = \""+idUsuario+"\" AND Eliminado = 0";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}
    
	public static DispositivoXUsuario obtenerIDPorUsuarioHandler(Object obj)
	{
		if(obj != null)
		{
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					/*return new DispositivoXUsuario(
						rs.getInt("id"),
						new Dispositivo(rs.getInt("Id_Dispositivo"), 0, "", false),
						new Usuario(rs.getInt("Id_Usuario"),"","","",false, new TipoUsuario(0,"")),
						rs.getString("Descripcion"),
						rs.getBoolean("Eliminado")
						);*/
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

	public void obtenerIDPorDispositivo(Integer idDispositivo)
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERIDPORDISPOSITIVO");
		String query = "SELECT * FROM Dispositivo_x_usuario WHERE Id_Dispositivo = \""+idDispositivo+"\" AND Eliminado = 0";
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}
    
	public static DispositivoXUsuario obtenerIDPorDispositivoHandler(Object obj)
	{
		if(obj != null)
		{
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					/*return new DispositivoXUsuario(
						rs.getInt("id"),
						new Dispositivo(rs.getInt("Id_Dispositivo"), 0, "", false),
						new Usuario(rs.getInt("Id_Usuario"),"","","",false, new TipoUsuario(0,"")),
						rs.getString("Descripcion"),
						rs.getBoolean("Eliminado")
						);

					 */
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

	//NOS DEJA OBTENER TODOS LOS DISPOSITIVOS POR USUARIO
	public void obtenerTodosLosDispositivosXUsuarios()
	{
		SelectManager manager = new SelectManager();
		manager.setIdentificador("OBTENERTODOSLOSDISPOSITIVOSXUSUARIOS");
		manager.setQuery("SELECT * FROM Dispositivo_x_usuario WHERE Eliminado = 0");
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static ArrayList<DispositivoXUsuario> obtenerTodosLosDispositivosXUsuariosHandler(Object obj)
	{
		if(obj != null)
		{
			ArrayList<DispositivoXUsuario> resultados = new ArrayList<DispositivoXUsuario>();
			try
			{
				ResultSet rs = (ResultSet)obj;
				while(rs.next())
				{
					/*resultados.add(
						new DispositivoXUsuario(
							rs.getInt("id"),
							new Dispositivo(rs.getInt("Id_Dispositivo"), 0, "", false),
							new Usuario(rs.getInt("Id_Usuario"),"","","",false, new TipoUsuario(0,"")),
							rs.getString("Descripcion"),
							rs.getBoolean("Eliminado")
						)
					);

					 */
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

	//GUARDAMOS LOS DISPOSITIVOS X USUUARIO
	public void insertarDispositivosXUsuarios(ArrayList<DispositivoXUsuario> disXusu)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("insertarDispositivosXUsuarios");
		String query = "INSERT INTO Dispositivo_x_usuario (Id_Dispositivo, Id_Usuario) VALUES ";
		for(DispositivoXUsuario app : disXusu)
		{
			query+= "(\""+app.getDispositivo().getId()+"\",";
			query+= "\""+app.getUsuario().getId()+"\",";
			query+="),";
		}
		query = query.substring(0, query.length() -1);
		manager.setQuery(query);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer insertarDispositivosXUsuariosHandler(Object obj)
	{
		if(obj != null) 
		{
			return (Integer)obj;
		}
		return null;
	}

	public void eliminarDispositivoXUsuario(Integer idDisXUsu)
	{
		UpsertManager manager = new UpsertManager();
		manager.setIdentificador("ELIMINARDISPOSITIVOXUSUARIO");
		manager.setQuery("UPDATE Dispositivo_x_usuario SET Eliminado = 1 WHERE Id = "+idDisXUsu);
		DBQueryManager mg = new DBQueryManager(this.com, manager);
		mg.execute();
	}

	public static Integer eliminarDispositivoXUsuarioHandler(Object obj)
	{
		if(obj != null)
		{
			return (Integer)obj;
		}
		return null;
	}
}