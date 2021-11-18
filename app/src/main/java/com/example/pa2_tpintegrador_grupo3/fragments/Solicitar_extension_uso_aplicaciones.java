package com.example.pa2_tpintegrador_grupo3.fragments;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.entidades.Aplicacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Solicitar_extension_uso_aplicaciones extends AppCompatDialogFragment {

    public Solicitar_extension_uso_aplicaciones() { }
    private Solicitar_extension_uso_aplicaciones.SolicitarExtensionAplicacion listener;
    private ArrayList<Aplicacion> aplicaciones;
    private  ArrayList<String> appSpinnerValues = new ArrayList<>();
    private Map<Integer,Integer> appIdsByIndex = new HashMap<Integer,Integer>();
    private Integer selectedAppId;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_solicitar_extension_uso_aplicaciones,null);
        Spinner tiempoDispSp = view.findViewById(R.id.spinnerTiempoAplicacion);

        Spinner spinner = view.findViewById(R.id.spinnerApps);


        for(Aplicacion app : this.aplicaciones){
            appSpinnerValues.add(app.getDescripcion());
            appIdsByIndex.put(appSpinnerValues.size()-1,app.getId());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, appSpinnerValues);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAppId = appIdsByIndex.get(position);
                System.out.println("APPID " + appIdsByIndex.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        builder.setView(view)
            .setTitle("Solicitar tiempo de Aplicacion")
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
                        Long tiempoSeleccionado = Long.valueOf(tiempoDispSp.getSelectedItem().toString());
                        tiempoSeleccionado = TimeUnit.MINUTES.toMillis(tiempoSeleccionado);
                        listener.guardarSolicitudAplicacion(selectedAppId,tiempoSeleccionado);
                        d.dismiss();
                    }
                });
            }
        });
        return d;
    }

    public void setListener(Solicitar_extension_uso_aplicaciones.SolicitarExtensionAplicacion listener){
        this.listener = listener;
    }

    public void setAplicaciones(ArrayList<Aplicacion> apps){
        this.aplicaciones = apps;
    }

    //Esta interface nos sirve para hacer llegar los datos del dialog de solicitud de tiempo de aplicacion
    public interface SolicitarExtensionAplicacion {
        void guardarSolicitudAplicacion(Integer idAplicacion, Long tiempo);
    }

}