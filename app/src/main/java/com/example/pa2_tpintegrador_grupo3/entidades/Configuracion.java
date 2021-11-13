package com.example.pa2_tpintegrador_grupo3.entidades;

import java.util.ArrayList;

public class Configuracion {
    private ArrayList<Restricciones> restricciones;
    private Dispositivo dispositivo;

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo datosDispositivo) {
        this.dispositivo = datosDispositivo;
    }

    public ArrayList<Restricciones> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(ArrayList<Restricciones> restricciones) {
        this.restricciones = restricciones;
    }
}
