package com.example.pa2_tpintegrador_grupo3.entidades;

public class BloqueoWrapper {

    private Long inicio;

    public BloqueoWrapper() {
        this.intentos = 0;
    }

    private Integer intentos;

    public Long getInicio() {
        return inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }
}
