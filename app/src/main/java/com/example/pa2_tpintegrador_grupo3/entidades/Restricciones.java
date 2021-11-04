package com.example.pa2_tpintegrador_grupo3.entidades;




public class Restricciones {

    private Integer id;
    private Integer id_Dispositivo;
    private Integer id_Tipo_Restriccion;
    private Integer id_Aplicacion;
    private int duracion_Minutos;
    private boolean eliminado;

    public Restricciones() {
    }

    public Restricciones(Integer id, Integer id_Dispositivo, Integer id_Tipo_Restriccion, Integer id_Aplicacion, int duracion_Minutos, boolean eliminado) {
        this.id = id;
        this.id_Dispositivo = id_Dispositivo;
        this.id_Tipo_Restriccion = id_Tipo_Restriccion;
        this.id_Aplicacion = id_Aplicacion;
        this.duracion_Minutos = duracion_Minutos;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_Dispositivo() {
        return id_Dispositivo;
    }

    public void setId_Dispositivo(Integer id_Dispositivo) {
        this.id_Dispositivo = id_Dispositivo;
    }

    public Integer getId_Tipo_Restriccion() {
        return id_Tipo_Restriccion;
    }

    public void setId_Tipo_Restriccion(Integer id_Tipo_Restriccion) {
        this.id_Tipo_Restriccion = id_Tipo_Restriccion;
    }

    public Integer getId_Aplicacion() {
        return id_Aplicacion;
    }

    public void setId_Aplicacion(Integer id_Aplicacion) {
        this.id_Aplicacion = id_Aplicacion;
    }

    public int getDuracion_Minutos() {
        return duracion_Minutos;
    }

    public void setDuracion_Minutos(int duracion_Minutos) {
        this.duracion_Minutos = duracion_Minutos;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Restricciones{" +
                "id=" + id +
                ", id_Dispositivo=" + id_Dispositivo +
                ", id_Tipo_Restriccion=" + id_Tipo_Restriccion +
                ", id_Aplicacion=" + id_Aplicacion +
                ", duracion_Minutos=" + duracion_Minutos +
                '}';
    }
}
