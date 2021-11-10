package com.example.pa2_tpintegrador_grupo3.entidades;




public class Restricciones {

    private Integer id;
    private Dispositivo Dispositivo;
    private TipoRestriccion Tipo_Restriccion;
    private Aplicacion Aplicacion;
    private int duracion_Minutos;
    private boolean eliminado;

    public Restricciones() {
    }

    public Restricciones(Integer id, Dispositivo dispositivo, TipoRestriccion tipo_Restriccion, Aplicacion aplicacion, int duracion_Minutos, boolean eliminado) {
        this.id = id;
        this.Dispositivo = dispositivo;
        this.Tipo_Restriccion = tipo_Restriccion;
        this.Aplicacion = aplicacion;
        this.duracion_Minutos = duracion_Minutos;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dispositivo getDispositivo() {
        return Dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        Dispositivo = dispositivo;
    }

    public TipoRestriccion getTipo_Restriccion() {
        return Tipo_Restriccion;
    }

    public void setTipo_Restriccion(TipoRestriccion tipo_Restriccion) {
        Tipo_Restriccion = tipo_Restriccion;
    }

    public Aplicacion getAplicacion() {
        return Aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        Aplicacion = aplicacion;
    }

    public int getDuracion_Minutos() {
        return duracion_Minutos;
    }

    public void setDuracion_Minutos(int duracion_Minutos) {
        this.duracion_Minutos = duracion_Minutos;
    }

    public boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Restricciones{" +
                "id=" + id +
                ", Dispositivo=" + Dispositivo +
                ", Tipo_Restriccion=" + Tipo_Restriccion +
                ", Aplicacion=" + Aplicacion +
                ", duracion_Minutos=" + duracion_Minutos +
                ", eliminado=" + eliminado +
                '}';
    }
}