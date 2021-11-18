package com.example.pa2_tpintegrador_grupo3.fragments;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;

public class eliminar_subordinado extends AppCompatDialogFragment {

    private Dispositivo dispositivo;
    private eliminar_subordinadoListener listener;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_eliminar_subordinado,null);
        TextView nombreAppEliminar = view.findViewById(R.id.nombreAppEliminar);
        nombreAppEliminar.setText(dispositivo.getNombre());
        builder.setView(view)
                .setTitle("Eliminar subordinado")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {  public void onClick(DialogInterface dialogInterface, int i) { }})
                .setPositiveButton("Eliminar",null);

        Dialog d = builder.create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) d).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.eliminarSubordinado(dispositivo.getId());
                        d.dismiss();
                    }
                });
            }
        });
        return d;
    }

    public void setListener(eliminar_subordinadoListener listener){
        this.listener = listener;
    }

    public void setDispositivo(Dispositivo d){
        this.dispositivo = d;
    }

    //Esta interface nos sirve para hacer llegar los datos del dialog de solicitud de tiempo de aplicacion
    public interface eliminar_subordinadoListener {
        void eliminarSubordinado(Integer idDispositivo);
    }
}