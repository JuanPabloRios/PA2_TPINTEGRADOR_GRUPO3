package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        if(stats != null){
            for(Estadistica es : stats){

            }
        }
    }
}