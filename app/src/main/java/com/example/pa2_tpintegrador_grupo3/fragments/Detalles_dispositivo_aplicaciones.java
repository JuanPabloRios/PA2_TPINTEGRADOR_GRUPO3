package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pa2_tpintegrador_grupo3.R;

public class Detalles_dispositivo_aplicaciones extends Fragment {
    public Detalles_dispositivo_aplicaciones() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles_dispositivo_aplicaciones, container, false);
    }
}