package com.example.pa2_tpintegrador_grupo3.entidades;
import java.io.Serializable;

public class Notificacion implements Serializable {
    //private static final long serialVersionUID = -172079496589283616L;
    private Integer Id;
    private Dispositivo DispositivoEmisor;
    private Usuario Id_Usuario_Receptor;
    private Aplicacion Aplicacion;
    private TipoNotificacion TipoNotificacion;
    private Estado Estado;
    private boolean Eliminado;
    private Long Tiempo_Solicitado;

    public Notificacion(){}
    public Notificacion(Integer id, Dispositivo dispositivoEmisor, Usuario id_Usuario_Receptor, com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion aplicacion, com.example.pa2_tpintegrador_grupo3.entidades.TipoNotificacion tipoNotificacion, com.example.pa2_tpintegrador_grupo3.entidades.Estado estado, long tiempoSolicitado) {
        Id = id;
        DispositivoEmisor = dispositivoEmisor;
        Id_Usuario_Receptor = id_Usuario_Receptor;
        Aplicacion = aplicacion;
        TipoNotificacion = tipoNotificacion;
        Estado = estado;
        Tiempo_Solicitado = tiempoSolicitado;
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

    public Usuario getId_Usuario_Receptor() {
        return Id_Usuario_Receptor;
    }

    public void setId_Usuario_Receptor(Usuario id_Usuario_Receptor) {
        Id_Usuario_Receptor = id_Usuario_Receptor;
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

    public Long getTiempo_Solicitado() {
        return Tiempo_Solicitado;
    }

    public void setTiempo_Solicitado(Long tiempo_Solicitado) {
        Tiempo_Solicitado = tiempo_Solicitado;
    }

    public boolean isEliminado() {
        return Eliminado;
    }

    public void setEliminado(boolean eliminado) {
        Eliminado = eliminado;
    }
}
