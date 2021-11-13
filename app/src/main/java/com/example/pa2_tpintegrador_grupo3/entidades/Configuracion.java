package com.example.pa2_tpintegrador_grupo3.entidades;

import java.util.ArrayList;

public class Configuracion {
    private Integer tipoDispositivo;
    private String identificadorDeDispositivo;
    private Integer idDispositivo;

    public ArrayList<Restricciones> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(ArrayList<Restricciones> restricciones) {
        this.restricciones = restricciones;
    }

    private ArrayList<Restricciones> restricciones;

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
