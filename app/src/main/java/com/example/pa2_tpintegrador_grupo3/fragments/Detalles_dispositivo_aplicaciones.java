package com.example.pa2_tpintegrador_grupo3.fragments;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.style.BackgroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Detalles_dispositivo_aplicaciones extends Fragment {

    private Integer idDispositivo;
    private View view;
    private Detalles_dispositivoViewModel detallesDispositivoViewModel;

    public Detalles_dispositivo_aplicaciones() { }
    public Detalles_dispositivo_aplicaciones(Integer id) { this.idDispositivo = id;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalles_dispositivo_aplicaciones, container, false);
        detallesDispositivoViewModel = new ViewModelProvider(requireActivity()).get(Detalles_dispositivoViewModel.class);
        ArrayList<Restricciones> apps = detallesDispositivoViewModel.getRestricciones().getValue();
        this.cargarAplicacionesEnPantalla(apps);
        return view;
    }

    public void cargarAplicacionesEnPantalla(ArrayList<Restricciones> restricciones){
        LinearLayout tabla = view.findViewById(R.id.tablaDeAplicaciones);
        for(Restricciones res :restricciones){
            LinearLayout fila = new LinearLayout(requireActivity());
            fila.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0));
            fila.setOrientation(LinearLayout.HORIZONTAL);

            byte[] decodedString = Base64.decode(res.getAplicacion().getIcono(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imagen = new ImageView(requireActivity());
            imagen.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 45, 45, false));
            fila.addView(imagen);

            TextView texto = new TextView(requireActivity());
            texto.setGravity(Gravity.CENTER_VERTICAL);
            texto.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
            texto.setText(res.getAplicacion().getDescripcion());
            texto.setPadding(5,0,0,0);
            fila.addView(texto);


            Switch s = new Switch(requireActivity());
            s.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,0));
            if(res.getDuracion_Minutos() == -1){
                s.setChecked(true);
            }
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    res.setDuracion_Minutos(b ? -1 : 1);
                    detallesDispositivoViewModel.setRestriccionModificada(res);
                }
            });
            fila.addView(s);
            tabla.addView(fila);
        }
    }
}