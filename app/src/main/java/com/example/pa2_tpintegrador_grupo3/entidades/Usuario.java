package com.example.pa2_tpintegrador_grupo3.entidades;


public class Usuario {
    private Integer id;
    private String usuario;
    private String contrasenia;
    private String email;
    private boolean eliminado;
    private TipoUsuario tipoUsuario;

    public Usuario(){}
    public Usuario(Integer id, String usuario, String contrasenia, String email, boolean eliminado, TipoUsuario tipoUsuario) {
        this.id = id;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.eliminado = eliminado;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", email='" + email + '\'' +
                ", eliminado=" + eliminado +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
