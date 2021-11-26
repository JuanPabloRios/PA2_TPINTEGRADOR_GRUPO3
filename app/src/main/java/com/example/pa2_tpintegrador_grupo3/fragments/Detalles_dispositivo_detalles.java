package com.example.pa2_tpintegrador_grupo3.fragments;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Detalles_dispositivo_detalles extends Fragment {

    private Integer idDispositivo;
    private View view;
    public Detalles_dispositivo_detalles() { }
    public Detalles_dispositivo_detalles(Integer id) { this.idDispositivo = id;}
    private Detalles_dispositivoViewModel detallesDispositivoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalles_dispositivo_detalles, container, false);
        detallesDispositivoViewModel = new ViewModelProvider(requireActivity()).get(Detalles_dispositivoViewModel.class);
        Dispositivo dispositivo = detallesDispositivoViewModel.getDispositivo().getValue();
        ArrayList<Estadistica> estadisticas = detallesDispositivoViewModel.getEstadisticas().getValue();
        this.cargarDetallesDeDispositivoEnPantalla(dispositivo,estadisticas);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstaceState){
        super.onViewCreated(view,savedInstaceState);
    }

    private void cargarDetallesDeDispositivoEnPantalla(Dispositivo d,ArrayList<Estadistica> stats){

        TextView txtMarca = view.findViewById(R.id.txtMarca);
        txtMarca.setText(d.getMarca());
        TextView txtModelo = view.findViewById(R.id.txtModelo);
        txtModelo.setText(d.getModelo());
        TextView txtNombre = view.findViewById(R.id.txtNombre);
        txtNombre.setText(d.getNombre());
        TextView txtTiempoUsado = view.findViewById(R.id.txtTiempoUsado);
        Long milis = d.getTiempoUso();
        Long horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
        Long minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
        txtTiempoUsado.setText(horas + ":"+minutos);

        TextView txtEstado = view.findViewById(R.id.txtEstado);

        if(d.isBloqueoActivo()) {
            TextView txtUsoRestante = view.findViewById(R.id.txtUsoRestante);
            milis = d.getTiempoAsignado() - d.getTiempoUso();
            horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
            minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
            if(d.getTiempoAsignado() <= d.getTiempoUso()){
                txtEstado.setText("BLOQUEADO");
                milis = d.getTiempoAsignado();
                horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
                minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));
                txtTiempoUsado.setText(horas + ":"+minutos);
                horas = 0L;
                minutos = 0L;
            }
            txtUsoRestante.setText(horas + ":"+minutos);
        }

        if(stats != null){
            LinearLayout tabla = view.findViewById(R.id.tablaEstadisticas);
            for(Estadistica es : stats){
                LinearLayout fila = new LinearLayout(requireActivity());
                fila.setPadding(5,5,5,5);
                fila.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0));
                fila.setOrientation(LinearLayout.HORIZONTAL);

                byte[] decodedString = Base64.decode(es.getAplicacion().getIcono(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView imagen = new ImageView(requireActivity());
                imagen.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 110, 110, false));
                imagen.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,0));
                fila.addView(imagen);

                TextView texto = new TextView(getContext());
                texto.setGravity(Gravity.CENTER_VERTICAL);
                texto.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
                texto.setPadding(5,0,0,0);
                Long milisegundos = es.getTiempo_Uso();
                Long stHoras = TimeUnit.MILLISECONDS.toHours(milisegundos) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milisegundos));
                Long stMinutos = TimeUnit.MILLISECONDS.toMinutes(milisegundos) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milisegundos));
                texto.setText(es.getAplicacion().getDescripcion() +": "+stHoras+":"+stMinutos);
                fila.addView(texto);
                tabla.addView(fila);
            }
        }
    }
}