package com.example.pa2_tpintegrador_grupo3.entidades;


public class Dispositivo {

    private Integer id;
    private TipoDispositivo Tipo_Dispositivo;
    private String Imei;
    private String marca;
    private String modelo;
    private String nombre;
    private boolean eliminado;
    private Usuario usuarioMaestro;

    public Usuario getUsuarioMaestro() {
        return usuarioMaestro;
    }

    public void setUsuarioMaestro(Usuario usuarioMaestro) {
        this.usuarioMaestro = usuarioMaestro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Dispositivo() { }

    public Dispositivo(Integer id) {
        this.id = id;
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