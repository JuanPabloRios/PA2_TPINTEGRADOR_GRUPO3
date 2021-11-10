package com.example.pa2_tpintegrador_grupo3;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.google.gson.*;

public class Utilidad extends AppCompatActivity {
    private static final String NOMBRE_ARCHIVO = "parentalWatcher.txt";
    private String tipoUsuario;

    public boolean validateString(String cadena){

        for (int i = 0; i < cadena.length(); i++)
        {
            char caracter = cadena.toUpperCase().charAt(i);
            int valorASCII = (int)caracter;
            if (valorASCII != 165 && valorASCII != 32  && ( valorASCII < 65 || valorASCII > 90)) {
                return true; //Se encontro un caracter distinto a Letra
            }
        }
        return false;
    }

    public boolean validateDate(String fecha){
        String[] arrsplit = fecha.split("/");
        Integer dia = Integer.parseInt(arrsplit[0]);
        Integer mes = Integer.parseInt(arrsplit[1]);
        if(dia > 31 || mes > 12){
            return  true;
        }
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fechaInicioDate = date.parse(fecha);
            Date fechaactual = new Date(System.currentTimeMillis());
            if(fechaInicioDate.before(fechaactual)){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            //mal formato de fecha
            return true;
        }
        return true;
    }
    //validar si es un email
    public boolean emailInvalido(String cadena){
        if (!Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            return true;
        }return false;
    }

    public boolean validateEmpty(String campo){
        if(TextUtils.isEmpty(campo)){
            return true;
        }
        return false;
    }


    public Integer validarTipoDispositivo(Context context) {
        FileInputStream file = null;
        try {
            file = context.openFileInput("parentalWatcher.txt");
            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader buffer = new BufferedReader(inputReader);
            String toJson;
            Gson gson = new Gson();
            while((toJson = buffer.readLine()) != null){
                Configuracion con = gson.fromJson(toJson, Configuracion.class);
                return con.getTipoDispositivo();
            }
            return -1;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("@@ ARCHIVO NO ENCONTRADO");
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("@@ IOException");
            return  null;
        } finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void guardarArchivoDeConfiguracion(Configuracion con){
        FileOutputStream file = null;
        try {
            Gson gson = new Gson();
            String objConf = gson.toJson(con);
            file = new FileOutputStream(new File(getFilesDir(),NOMBRE_ARCHIVO),true);
            String separator = System.getProperty("line.separator");
            OutputStreamWriter writer = new OutputStreamWriter(file);
            writer.append(objConf);
            writer.append(separator);
            writer.close();
        } catch (IOException e){
            Toast.makeText(this,"Error guardando archivo de configuracion!",Toast.LENGTH_SHORT).show();
        } finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
