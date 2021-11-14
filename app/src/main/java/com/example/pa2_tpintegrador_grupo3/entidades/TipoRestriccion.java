package com.example.pa2_tpintegrador_grupo3.entidades;

import java.io.Serializable;

public class TipoRestriccion implements Serializable {
	private Integer Id;
	private String Descripcion;

	public TipoRestriccion(Integer id, String descripcion) 
	{
		Id = id;
		Descripcion = descripcion;
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
}
