package com.example.pa2_tpintegrador_grupo3.entidades;

public class Estado {
    private Integer id;
    private String descripcion;

    public Estado(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}