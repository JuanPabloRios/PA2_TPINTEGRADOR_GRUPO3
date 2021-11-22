package com.example.pa2_tpintegrador_grupo3.receivers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceMaestro;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceSubordinado;
import com.example.pa2_tpintegrador_grupo3.Utilidad;

public class ReceptorReinicioCompletado extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utilidad ut = new Utilidad();
        //BUSCAMOS EL ARCHIVO DE CONFIGURACION
        Integer result = ut.validarTipoDispositivo(context);
        if(result != null){
            if(result == 1) {
                //SI EXISTE EL ARCHIVO Y EL USUARIO ES DE TIPO MAESTRO (1)
                Intent serviceIntent = new Intent(context, ServiceMaestro.class);
                serviceIntent.putExtra("idUsuario",ut.obtenerConfiguracion(context).getDispositivo().getUsuarioMaestro().getId());
                ContextCompat.startForegroundService(context,serviceIntent);
            } else if(result == 2) {
                //SI EXISTE EL ARCHIVO Y EL USUARIO ES DE TIPO SUBORDINADO
                Intent serviceIntent = new Intent(context, ServiceSubordinado.class);
                ContextCompat.startForegroundService(context,serviceIntent);
            }
        }
    }
}
