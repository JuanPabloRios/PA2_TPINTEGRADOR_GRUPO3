package com.example.pa2_tpintegrador_grupo3.entidades;

public class Aplicacion
{
	private Integer Id;

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getIcono() {
		return Icono;
	}

	public void setIcono(String icono) {
		Icono = icono;
	}

	private String Nombre;
	private String Icono;
	private String Descripcion;
	private boolean Eliminado;

	public Aplicacion(Integer id)
	{
		Id = id;
	}

	public Aplicacion(String descripcion, String Nombre, String Icono) {
		Descripcion = descripcion;
		this.Nombre = Nombre;
		this.Icono = Icono;
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