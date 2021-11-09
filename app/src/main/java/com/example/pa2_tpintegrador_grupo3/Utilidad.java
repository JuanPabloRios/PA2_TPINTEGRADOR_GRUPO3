package com.example.pa2_tpintegrador_grupo3;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidad extends AppCompatActivity {
    private static final String NOMBRE_ARCHIVO = "parentalWatcher.txt";

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


    public String validarTipoDispositivo(Context context) {
        FileInputStream file = null;
        try {

            file = context.openFileInput("parentalWatcher.txt");


            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader buffer = new BufferedReader(inputReader);
            String contactoJson;
            String tipoUser;
            if(buffer.readLine() != null){
                tipoUser= "MAESTRO";
            }else{
                tipoUser= "SUBORDINADO";
            }
            //Gson gson = new Gson();
           /* int cont = 0;
            while((contactoJson = buffer.readLine()) != null){
                //Contacto con = gson.fromJson(contactoJson, Contacto.class);
                //contactos.add(con.getNombre());
                //adaptador1.notifyDataSetChanged();
                //cont++;
            }*/

            return tipoUser;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
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

}
