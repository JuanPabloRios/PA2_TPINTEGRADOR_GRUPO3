package com.example.pa2_tpintegrador_grupo3.entidades;


public class Dispositivo {

    private Integer id;
    private Integer id_Tipo_Dispositivo;
    private String Imei;
    private boolean eliminado;

    public Dispositivo() {
    }

    public Dispositivo(Integer id, Integer id_Tipo_Dispositivo, String imei, boolean eliminado) {
        this.id = id;
        this.id_Tipo_Dispositivo = id_Tipo_Dispositivo;
        this.Imei = imei;
        this.eliminado = eliminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_Tipo_Dispositivo() {
        return id_Tipo_Dispositivo;
    }

    public void setId_Tipo_Dispositivo(Integer id_Tipo_Dispositivo) {
        this.id_Tipo_Dispositivo = id_Tipo_Dispositivo;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", id_Tipo_Dispositivo=" + id_Tipo_Dispositivo +
                ", Imei='" + Imei + '\'' +
                '}';
    }
}
