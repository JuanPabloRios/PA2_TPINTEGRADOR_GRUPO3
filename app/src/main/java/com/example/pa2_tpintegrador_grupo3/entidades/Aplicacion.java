package com.example.pa2_tpintegrador_grupo3.entidades;

public class Aplicacion
{
	private Integer Id;
	private String Nombre;
	private String Descripcion;
	private String Icono;
	private boolean Eliminado;

	public Aplicacion(Integer id, String nombre, String descripcion, String icono, boolean eliminado)
	{
		Id = id;
		Nombre = nombre;
		Descripcion = descripcion;
		Icono = icono;
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

	public String getNombre()
	{
		return Nombre;
	}

	public void setNombre(String nombre) { Nombre = nombre; }

	public String getDescripcion()
	{
		return Descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		Descripcion = descripcion;
	}

	public String getIcono()
	{
		return Icono;
	}

	public void setIcono(String icono)
	{
		Icono = icono;
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