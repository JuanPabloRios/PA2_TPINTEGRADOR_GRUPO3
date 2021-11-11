package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;

import java.util.ArrayList;

public class Detalles_dispositivo_detalles extends Fragment {

    private Integer idDispositivo;

    public Detalles_dispositivo_detalles() { }
    public Detalles_dispositivo_detalles(Integer id) { this.idDispositivo = id;}

    private Detalles_dispositivoViewModel detallesDispositivoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles_dispositivo_detalles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstaceState){
        super.onViewCreated(view,savedInstaceState);
    }
}