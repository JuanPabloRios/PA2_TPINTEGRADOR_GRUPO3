package com.example.pa2_tpintegrador_grupo3.entidades;




public class Restricciones {

    private Integer id;
    private Dispositivo Dispositivo;
    private TipoRestriccion Tipo_Restriccion;
    private Aplicacion Aplicacion;
    private long duracion_Minutos;
    private boolean Activa;
    private boolean eliminado;

    public Restricciones() { }

    public Restricciones(Integer id, Dispositivo dispositivo, TipoRestriccion tipo_Restriccion, Aplicacion aplicacion, int duracion_Minutos, boolean activa, boolean eliminado) {
        this.id = id;
        this.Dispositivo = dispositivo;
        this.Tipo_Restriccion = tipo_Restriccion;
        this.Aplicacion = aplicacion;
        this.duracion_Minutos = duracion_Minutos;
        this.eliminado = eliminado;
        this.Activa = activa;
    }

    public Restricciones(Aplicacion aplicacion, long duracion_Minutos) {
        this.Aplicacion = aplicacion;
        this.duracion_Minutos = duracion_Minutos;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean activa) {
        Activa = activa;
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

    public long getDuracion_Minutos() {
        return duracion_Minutos;
    }

    public void setDuracion_Minutos(long duracion_Minutos) {
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