package com.example.pa2_tpintegrador_grupo3.entidades;

public class Notificacion {

    private Integer Id;
    //private Dispositivo DispositivoEmisor;
    //private Dispositivo DispositivoReceptor;
    //private Aplicacion Aplicacion;
    private TipoNotificacion TipoNotificacion;
    //private Estado Estado;
    private boolean Eliminado;

    //NOTA: descomentar propiedades y crear constructor cuando existan las clases


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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
