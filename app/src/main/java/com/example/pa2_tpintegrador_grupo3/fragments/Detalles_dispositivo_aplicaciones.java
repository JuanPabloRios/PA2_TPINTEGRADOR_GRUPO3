package com.example.pa2_tpintegrador_grupo3.fragments;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.style.BackgroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Utilidad;

import org.w3c.dom.Text;

public class Detalles_dispositivo_aplicaciones extends Fragment {
    public Detalles_dispositivo_aplicaciones() { }
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalles_dispositivo_aplicaciones, container, false);
        Utilidad ut = new Utilidad();
        LinearLayout tabla = view.findViewById(R.id.tablaDeAplicaciones);
        for(Utilidad.InfoObject app :ut.getInstalledApps(getActivity())){
            LinearLayout fila = new LinearLayout(getActivity());
            fila.setOrientation(LinearLayout.HORIZONTAL);

            byte[] decodedString = Base64.decode(app.icon, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imagen = new ImageView(getActivity());
            //imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imagen.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 120, 120, false));

            fila.addView(imagen);

            TextView texto = new TextView(getActivity());
            texto.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            texto.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
            texto.setText(app.appname);
            fila.addView(texto);

            tabla.addView(fila);
        }

        return view;
    }
}