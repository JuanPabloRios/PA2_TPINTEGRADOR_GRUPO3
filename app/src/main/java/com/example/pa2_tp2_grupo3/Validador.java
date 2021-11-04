package com.example.pa2_tp2_grupo3;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

public class Validador {


    public static boolean validateString(String cadena){

        for (int i = 0; i < cadena.length(); i++)
        {
            char caracter = cadena.toUpperCase().charAt(i);
            int valorASCII = (int)caracter;
            if (valorASCII != 165 && valorASCII != 32  && ( valorASCII < 65 || valorASCII > 90)) {
                return true; //Se ha encontrado un caracter que no es letra
            }
        }
        return false;
    }

    public static boolean validateDate(String fecha){
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

    //metodo para validar si es un email
    public static boolean emailInvalido(String cadena){
        if (!Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            return true;
        }return false;
    }

    public static boolean validateEmpty(String campo){
        if(TextUtils.isEmpty(campo)){
            return true;
        }
        return false;
    }

}
