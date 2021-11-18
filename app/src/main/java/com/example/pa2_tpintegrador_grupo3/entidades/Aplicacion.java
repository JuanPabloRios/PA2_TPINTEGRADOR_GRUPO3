package com.example.pa2_tpintegrador_grupo3.entidades;

import java.io.Serializable;

public class Aplicacion implements Serializable
{
	private Integer Id;
	private String Nombre;
	private String Descripcion;
	private String Icono;
	private boolean Eliminado;

	public Aplicacion(Integer id) {
		Id = id;
	}

	public Aplicacion(Integer id, String nombre, String descripcion, String icono, boolean eliminado) {
		Id = id;
		Nombre = nombre;
		Descripcion = descripcion;
		Icono = icono;
		Eliminado = eliminado;
	}

	public Aplicacion(String descripcion, String Nombre, String Icono) {
		this.Descripcion = descripcion;
		this.Nombre = Nombre;
		this.Icono = Icono;
	}

	public Aplicacion(String descripcion, String Icono) {
		this.Descripcion = descripcion;
		this.Icono = Icono;
	}
	public Aplicacion(Integer id, String descripcion) {
		this.Descripcion = descripcion;
		this.Id = id;
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