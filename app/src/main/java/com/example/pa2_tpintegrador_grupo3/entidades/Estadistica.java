package com.example.pa2_tpintegrador_grupo3.entidades;

public class Estadistica {
    private Dispositivo dispositivo;
    private Aplicacion aplicacion;
    private Long tiempo_Uso;

    public Estadistica(Dispositivo dispositivo, Aplicacion aplicacion, Long tiempo_Uso) {
        this.dispositivo = dispositivo;
        this.aplicacion = aplicacion;
        this.tiempo_Uso = tiempo_Uso;
    }

    public Estadistica(Aplicacion aplicacion, Long tiempo_Uso) {
        this.aplicacion = aplicacion;
        this.tiempo_Uso = tiempo_Uso;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public Long getTiempo_Uso() {
        return tiempo_Uso;
    }

    public void setTiempo_Uso(Long tiempo_Uso) {
        this.tiempo_Uso = tiempo_Uso;
    }
}
