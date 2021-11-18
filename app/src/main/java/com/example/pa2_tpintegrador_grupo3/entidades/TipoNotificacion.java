package com.example.pa2_tpintegrador_grupo3.entidades;

import java.io.Serializable;

public class TipoNotificacion implements Serializable {

    private Integer Id;
    private String Descripcion;

    public TipoNotificacion(String descripcion) {
        Descripcion = descripcion;
    }

    public TipoNotificacion(Integer id) {
        this.Id = id;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
