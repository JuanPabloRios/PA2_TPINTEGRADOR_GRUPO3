package com.example.pa2_tpintegrador_grupo3.fragments;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.DetallesDispositivo;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Servicios.InputFilterMinMax;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
            fila.setPadding(5,5,5,5);
            fila.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0));
            fila.setOrientation(LinearLayout.HORIZONTAL);

            byte[] decodedString = Base64.decode(res.getAplicacion().getIcono(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imagen = new ImageView(requireActivity());
            imagen.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 95, 95, false));
            imagen.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,0));
            fila.addView(imagen);

            TextView texto = new TextView(requireActivity());
            texto.setGravity(Gravity.CENTER_VERTICAL);
            texto.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
            texto.setText(res.getAplicacion().getDescripcion());
            texto.setPadding(5,0,0,0);
            fila.addView(texto);

            EditText duracion = new EditText(requireActivity());
            duracion.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,0));
            duracion.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "1440")});
            duracion.setId(res.getId());
            duracion.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(res.getDuracion_Minutos())));
            duracion.addTextChangedListener(new TextWatcher() {
                private Timer timer = new Timer();
                private final long DELAY = 1000;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }
                @Override
                public void onTextChanged(final CharSequence s, int start, int before,
                                          int count) {
                    if(timer != null)
                        timer.cancel();
                }
                @Override
                public void afterTextChanged(final Editable s) {
                    //avoid triggering event when text is too short
                    if (s.length()  != 0) {

                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        actualizarRestriccion(res);
                                    }
                                });
                            }

                        }, DELAY);
                    }
                }
            });
            fila.addView(duracion);

            @SuppressLint("UseSwitchCompatOrMaterialCode") Switch s = new Switch(requireActivity());
            s.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,0));
            s.setChecked(res.isActiva());
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    res.setActiva(b);
                    actualizarRestriccion(res);
                }
            });
            fila.addView(s);
            tabla.addView(fila);
        }
    }

    public void actualizarRestriccion(Restricciones res){
        EditText tiempo = Detalles_dispositivo_aplicaciones.this.view.findViewById(res.getId());
        if(tiempo.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(),"El tiempo diponible para la aplicacion ("+res.getAplicacion().getDescripcion()+") no puede estar vacio",Toast.LENGTH_SHORT).show();
            return;
        }
        long value = TimeUnit.MINUTES.toMillis(Long.valueOf(tiempo.getText().toString()));
        res.setDuracion_Minutos(value);
        detallesDispositivoViewModel.setRestriccionModificada(res);
    }
}