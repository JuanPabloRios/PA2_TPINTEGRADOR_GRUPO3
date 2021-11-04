package com.example.pa2_tp2_grupo3;

public class Contacto {

    private String nombre;
    private String apellido;
    private String email;
    private String ubicacionEmail;
    private String telefono;
    private String ubicacionTelefono;
    private String direccion;
    private String fechaNacimiento;
    private String nivelEstudios;
    private String intereses;
    private Boolean recibirInformacion;

    public Contacto(
            String nombre,
            String apellido,
            String email,
            String ubicacionEmail,
            String telefono,
            String ubicacionTelefono,
            String direccion,
            String fechaNacimiento,
            String nivelEstudios,
            String intereses,
            Boolean recibirInformacion
    ){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.ubicacionEmail = ubicacionEmail;
        this.telefono = telefono;
        this.ubicacionTelefono = ubicacionTelefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.nivelEstudios = nivelEstudios;
        this.intereses = intereses;
        this.recibirInformacion = recibirInformacion;
    }

    public String getNombre(){
        return  this.nombre;
    }

    public String getEmail(){
        return  this.email;
    }

    public String getApellido(){
        return  this.apellido;
    }

    public String getUbicacionEmail(){
        return  this.ubicacionEmail;
    }

    public String getTelefono(){
        return  this.telefono;
    }

    public String getUbicacionTelefono(){
        return  this.ubicacionTelefono;
    }

    public String getDireccion(){
        return  this.direccion;
    }
    public String getFechaNacimiento(){
        return  this.fechaNacimiento;
    }
    public String getNivelEstudios(){
        return  this.nivelEstudios;
    }
    public String getIntereses(){
        return  this.intereses;
    }

    public Boolean getRecibirInformacion(){
        return  this.recibirInformacion;
    }

    @Override
    public String toString(){
        return "{\"nombre\": \""+this.nombre+
                "\", \"apellido\": \""+this.apellido+
                "\",\"email\": \""+this.email+
                "\",\"telefono\": \""+this.telefono+
                "\",\"direccion\": \""+this.direccion+
                "\",\"fechaNacimiento\": \""+this.fechaNacimiento+
                "\",\"nivelEstudios\": \""+this.nivelEstudios+
                "\",\"intereses\": \""+this.intereses+
                "\",\"recibirInformacion\": "+this.recibirInformacion+
                ",\"ubicacionEmail\":\""+this.ubicacionEmail+
                "\",\"ubicacionTelefono\": \""+this.ubicacionTelefono+"\"}";
    }
}
