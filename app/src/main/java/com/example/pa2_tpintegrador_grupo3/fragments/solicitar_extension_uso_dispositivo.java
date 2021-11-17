package com.example.pa2_tpintegrador_grupo3.fragments;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;

public class solicitar_extension_uso_dispositivo extends AppCompatDialogFragment {

    public solicitar_extension_uso_dispositivo() {
        // Required empty public constructor
    }
    private Integer idDispositivo;

    private SolicitarExtensionDispositivoListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_solicitar_extension_uso_dispositivo,null);
        Spinner tiempoDispSp = view.findViewById(R.id.spinnerTiempos);

        builder.setView(view)
            .setTitle("Solicitar tiempo dispositivo")
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  public void onClick(DialogInterface dialogInterface, int i) { }})
            .setPositiveButton("Solicitar",null);

        Dialog d = builder.create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) d).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        //Si hace click en registrar, validamos los campos y agregamos el parqueo
                        System.out.println("tiempoDispSp " + tiempoDispSp.getSelectedItem().toString());
                        System.out.println("idDispositivo " + solicitar_extension_uso_dispositivo.this.idDispositivo);
                        Long tiempoSeleccionado = Long.valueOf(tiempoDispSp.getSelectedItem().toString());
                        listener.guardarSolicitudDispositivo(solicitar_extension_uso_dispositivo.this.idDispositivo,tiempoSeleccionado);
                    }
                });
            }
        });


        return d;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public void setListener(SolicitarExtensionDispositivoListener listener){
        this.listener = listener;
    }

    //Esta interface nos sirve para hacer llegar los datos del parque al Fragment que inicio el dialog
    public interface SolicitarExtensionDispositivoListener {
        void guardarSolicitudDispositivo(Integer idDispositivo, Long tiempo);
    }
}