package com.example.pa2_tpintegrador_grupo3.entidades;

public class Notificacion {

    private Integer Id;
    private Dispositivo DispositivoEmisor;
    private Dispositivo DispositivoReceptor;
    private Aplicacion Aplicacion;
    private TipoNotificacion TipoNotificacion;
    private Estado Estado;
    private boolean Eliminado;

    public Notificacion(Integer id, Dispositivo dispositivoEmisor, Dispositivo dispositivoReceptor, com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion aplicacion, com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion tipoNotificacion, com.example.pa2_tpintegrador_grupo3.entidades.Estado estado) {
        Id = id;
        DispositivoEmisor = dispositivoEmisor;
        DispositivoReceptor = dispositivoReceptor;
        Aplicacion = aplicacion;
        TipoNotificacion = tipoNotificacion;
        Estado = estado;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Dispositivo getDispositivoEmisor() {
        return DispositivoEmisor;
    }

    public void setDispositivoEmisor(Dispositivo dispositivoEmisor) {
        DispositivoEmisor = dispositivoEmisor;
    }

    public Dispositivo getDispositivoReceptor() {
        return DispositivoReceptor;
    }

    public void setDispositivoReceptor(Dispositivo dispositivoReceptor) {
        DispositivoReceptor = dispositivoReceptor;
    }

    public com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion getAplicacion() {
        return Aplicacion;
    }

    public void setAplicacion(com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion aplicacion) {
        Aplicacion = aplicacion;
    }

    public com.example.pa2_tpintegrador_grupo3.entidades.Estado getEstado() {
        return Estado;
    }

    public void setEstado(com.example.pa2_tpintegrador_grupo3.entidades.Estado estado) {
        Estado = estado;
    }

    public com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion getTipoNotificacion() {
        return TipoNotificacion;
    }

    public void setTipoNotificacion(com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion tipoNotificacion) {
        TipoNotificacion = tipoNotificacion;
    }

    public boolean isEliminado() {
        return Eliminado;
    }

    public void setEliminado(boolean eliminado) {
        Eliminado = eliminado;
    }
}
