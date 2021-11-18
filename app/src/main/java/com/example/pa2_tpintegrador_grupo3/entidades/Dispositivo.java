package com.example.pa2_tpintegrador_grupo3.entidades;
import java.io.Serializable;

public class Dispositivo implements Serializable  {

    private Integer id;
    private TipoDispositivo Tipo_Dispositivo;
    private String Imei;
    private String marca;
    private String modelo;
    private String nombre;
    private boolean eliminado;
    private Usuario usuarioMaestro;
    private Long tiempoUso;
    private Long tiempoAsignado;
    private Long horaInicio;
    private Long horaFin;
    private boolean bloqueoActivo;

    public boolean isBloqueoActivo() { return bloqueoActivo; }

    public void setBloqueoActivo(boolean bloqueoActivo) { this.bloqueoActivo = bloqueoActivo; }

    public Long getTiempoAsignado() { return tiempoAsignado; }

    public void setTiempoAsignado(Long tiempoAsignado) { this.tiempoAsignado = tiempoAsignado; }

    public Long getHoraInicio() { return horaInicio; }

    public void setHoraInicio(Long horaInicio) { this.horaInicio = horaInicio; }

    public Long getHoraFin() { return horaFin; }

    public void setHoraFin(Long horaFin) { this.horaFin = horaFin; }

    public Long getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(Long tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoDispositivo getTipo_Dispositivo() {
        return Tipo_Dispositivo;
    }

    public void setTipo_Dispositivo(TipoDispositivo tipo_Dispositivo) { Tipo_Dispositivo = tipo_Dispositivo; }

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

    public Dispositivo(Integer id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Dispositivo(Integer id, TipoDispositivo Tipo_Dispositivo, String imei, boolean eliminado) {
        this.id = id;
        this.Tipo_Dispositivo = Tipo_Dispositivo;
        this.Imei = imei;
        this.eliminado = eliminado;
    }
    public Dispositivo(
            Integer id,
            TipoDispositivo Tipo_Dispositivo,
            String imei,
            String marca,
            String modelo,
            String nombre,
            Long tiempoUso,
            Long tiempoAsignado,
            Long horaInicio,
            Long horaFin,
            boolean bloqueoActivo,
            boolean eliminado
    ) {
        this.id = id;
        this.Tipo_Dispositivo = Tipo_Dispositivo;
        this.Imei = imei;
        this.eliminado = eliminado;
        this.marca = marca;
        this.modelo = modelo;
        this.nombre = nombre;
        this.tiempoUso = tiempoUso;
        this.tiempoAsignado = tiempoAsignado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.bloqueoActivo = bloqueoActivo;
    }

    public Dispositivo(Integer id, TipoDispositivo Tipo_Dispositivo, String imei, String marca, String modelo, String nombre, Long tiempoUso, boolean eliminado) {
        this.id = id;
        this.Tipo_Dispositivo = Tipo_Dispositivo;
        this.Imei = imei;
        this.eliminado = eliminado;
        this.marca = marca;
        this.modelo = modelo;
        this.nombre = nombre;
        this.tiempoUso = tiempoUso;
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