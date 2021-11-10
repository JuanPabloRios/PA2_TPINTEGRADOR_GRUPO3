package com.example.pa2_tpintegrador_grupo3.entidades;

public class Aplicacion
{
	private Integer Id;
	private String Descripcion;
	private boolean Eliminado;

	public Aplicacion(Integer id)
	{
		Id = id;
	}

	public Aplicacion(Integer id, String descripcion, boolean eliminado)
	{
		Id = id;
		Descripcion = descripcion;
		Eliminado = eliminado;
	}

	public Integer getId()
	{
		return Id;
	}

	public void setId(Integer id)
	{
		Id = id;
	}

	public String getDescripcion()
	{
		return Descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		Descripcion = descripcion;
	}

	public boolean isEliminado()
	{
		return Eliminado;
	}

	public void setEliminado(boolean eliminado)
	{
		Eliminado = eliminado;
	}
}