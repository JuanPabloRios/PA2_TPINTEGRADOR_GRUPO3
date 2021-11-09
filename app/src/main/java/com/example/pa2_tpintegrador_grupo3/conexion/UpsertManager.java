package com.example.pa2_tpintegrador_grupo3.conexion;
import com.example.pa2_tpintegrador_grupo3.interfaces.DatabaseProcess;

public class UpsertManager implements DatabaseProcess {
    private String query;
    private ResultadoDeConsulta resultado;

    public void setIdentificador(String id) {
        this.resultado.setIdentificador(id);
    }

    public void setQuery(String quey) {
        this.query = quey;
    }

    public UpsertManager(){
        this.resultado = new ResultadoDeConsulta();
    }

    public UpsertManager(String q, String identificador){
        this.query = q;
        this.resultado = new ResultadoDeConsulta();
        this.resultado.setIdentificador(identificador);
    }

    @Override
    public Object ejecutarConsulta() {
        this.resultado.setData(DatabaseManager.ejecutarUpsert(this.query));
        return this.resultado;
    }

    @Override
    public Object obtenerResultado() {
        return this.resultado;
    }
}