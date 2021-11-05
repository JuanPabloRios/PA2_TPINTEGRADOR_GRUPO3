package com.example.pa2_tpintegrador_grupo3.entidades;

public class Aplicacion
{
	private Integer Id;
	private String Descripcion;
	private boolean Eliminado;

	public Aplicacion(Integer id, String descripcion, boolean eliminado)
	{
		Id = id;
		Descripcion = descripcion;
		Eliminado = eliminado;
	}
}