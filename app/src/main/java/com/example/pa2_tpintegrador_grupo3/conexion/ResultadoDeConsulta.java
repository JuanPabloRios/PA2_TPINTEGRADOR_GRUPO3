package com.example.pa2_tpintegrador_grupo3.conexion;

public class ResultadoDeConsulta {

    private Object data;
    private String identificador;

    public Object getData() {
        return data;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public ResultadoDeConsulta (){ }

    public ResultadoDeConsulta (Object obj, String id){
        this.data = obj;
        this.identificador = id;
    }
}
