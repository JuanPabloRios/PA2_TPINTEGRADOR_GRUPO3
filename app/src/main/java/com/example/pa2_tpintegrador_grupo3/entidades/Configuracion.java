package com.example.pa2_tpintegrador_grupo3.entidades;

public class Configuracion {
    private Integer tipoDispositivo;
    private String identificadorDeDispositivo;
    private Integer idDispositivo;

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdentificadorDeDispositivo() {
        return identificadorDeDispositivo;
    }

    public void setIdentificadorDeDispositivo(String identificadorDeDispositivo) {
        this.identificadorDeDispositivo = identificadorDeDispositivo;
    }

    public Integer getTipoDispositivo() {
        return tipoDispositivo;
    }

    public void setTipoDispositivo(Integer tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }
}
