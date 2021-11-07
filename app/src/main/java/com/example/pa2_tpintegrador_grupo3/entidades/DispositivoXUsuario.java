package com.example.pa2_tpintegrador_grupo3.entidades;

public class DispositivoXUsuario {
    private Integer id;
    private Dispositivo dispositivo;
    private Usuario usuario;
    private String descripcion;
    private Boolean Eliminado;

    public DispositivoXUsuario(Integer id, Dispositivo dispositivo, Usuario usuario, String descripcion, Boolean eliminado) {
        this.id = id;
        this.dispositivo = dispositivo;
        this.usuario = usuario;
        this.descripcion = descripcion;
        Eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEliminado() {
        return Eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        Eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "DispositivoXUsuario{" +
                "id=" + id +
                ", dispositivo=" + dispositivo +
                ", usuario=" + usuario +
                ", descripcion='" + descripcion + '\'' +
                ", Eliminado=" + Eliminado +
                '}';
    }
}
