package com.example.pa2_tpintegrador_grupo3.conexion;
import android.os.AsyncTask;

import com.example.pa2_tpintegrador_grupo3.interfaces.DatabaseProcess;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

public class DBQueryManager extends AsyncTask<String, Void, String> {

    InterfazDeComunicacion com;
    DatabaseProcess dbp;

    public DBQueryManager(InterfazDeComunicacion ic, DatabaseProcess dbp){
        this.com = ic;
        this.dbp = dbp;
    }

    @Override
    protected String doInBackground(String... strings) {
        dbp.ejecutarConsulta();
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        this.com.operacionConBaseDeDatosFinalizada(dbp.obtenerResultado());
    }
}
