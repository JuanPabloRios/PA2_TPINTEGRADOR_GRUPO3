package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pa2_tpintegrador_grupo3.R;

public class Solicitar_extension_uso_aplicaciones extends Fragment {

    public Solicitar_extension_uso_aplicaciones() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitar_extension_uso_aplicaciones, container, false);
        return view;
    }
}