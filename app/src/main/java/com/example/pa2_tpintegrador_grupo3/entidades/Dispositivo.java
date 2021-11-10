package com.example.pa2_tpintegrador_grupo3.entidades;


public class Dispositivo {

    private Integer id;
    private TipoDispositivo Tipo_Dispositivo;
    private String Imei;
    private boolean eliminado;

    public Dispositivo() {
    }

    public Dispositivo(Integer id, TipoDispositivo Tipo_Dispositivo, String imei, boolean eliminado) {
        this.id = id;
        this.Tipo_Dispositivo = Tipo_Dispositivo;
        this.Imei = imei;
        this.eliminado = eliminado;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDispositivo getTipo_Dispositivo() {
        return Tipo_Dispositivo;
    }

    public void setTipo_Dispositivo(TipoDispositivo tipo_Dispositivo) {
        Tipo_Dispositivo = tipo_Dispositivo;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", Tipo_Dispositivo=" + Tipo_Dispositivo +
                ", Imei='" + Imei + '\'' +
                '}';
    }
}
