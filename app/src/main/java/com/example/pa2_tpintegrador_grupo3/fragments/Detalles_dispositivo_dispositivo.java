package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pa2_tpintegrador_grupo3.R;

public class Detalles_dispositivo_dispositivo extends Fragment {
    private Integer idDispositivo;

    public Detalles_dispositivo_dispositivo() { }
    public Detalles_dispositivo_dispositivo(Integer id) { this.idDispositivo = id;}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles_dispositivo_dispositivo, container, false);
    }
}