package com.example.pa2_tpintegrador_grupo3.fragments;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.UsuarioDAO;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Servicios.InputFilterMinMax;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;
import java.util.concurrent.TimeUnit;

public class Detalles_dispositivo_dispositivo extends Fragment implements InterfazDeComunicacion {
    private Integer idDispositivo;
    private View view;
    private Detalles_dispositivoViewModel detallesDispositivoViewModel;
    private Dispositivo dispositivo;
    public Detalles_dispositivo_dispositivo() { }
    public Detalles_dispositivo_dispositivo(Integer id) { this.idDispositivo = id;}
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_detalles_dispositivo_dispositivo, container, false);
        detallesDispositivoViewModel = new ViewModelProvider(requireActivity()).get(Detalles_dispositivoViewModel.class);
        dispositivo = detallesDispositivoViewModel.getDispositivo().getValue();
        Button button = (Button) view.findViewById(R.id.btnAceptarCambios);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Detalles_dispositivo_dispositivo.this.guardarConfiguracion(v);
            }
        });
        this.cargarDatosEnPantalla();
        return this.view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void cargarDatosEnPantalla(){
        TimePicker tpHoraInicio = this.view.findViewById(R.id.horaInicioPicker);
        tpHoraInicio.setIs24HourView(true);
        Long milis = this.dispositivo.getHoraInicio();
        Long horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
        Long minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tpHoraInicio.setHour(Math.toIntExact(horas));
            tpHoraInicio.setMinute(Math.toIntExact(minutos));
        }
        TimePicker tpHoraFin = this.view.findViewById(R.id.horaFinPicker);
        tpHoraFin.setIs24HourView(true);
        milis = this.dispositivo.getHoraFin();
        horas = TimeUnit.MILLISECONDS.toHours(milis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milis));
        minutos = TimeUnit.MILLISECONDS.toMinutes(milis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milis));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tpHoraFin.setHour(Math.toIntExact(horas));
            tpHoraFin.setMinute(Math.toIntExact(minutos));
        }

        EditText tiempoDeUso = this.view.findViewById(R.id.tiempoUsoDispositivo);
        tiempoDeUso.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "1440")});
        minutos = TimeUnit.MILLISECONDS.toMinutes(this.dispositivo.getTiempoAsignado());
        tiempoDeUso.setText(String.valueOf(minutos));

        Switch stActivo = this.view.findViewById(R.id.stActivo);
        stActivo.setChecked(this.dispositivo.isBloqueoActivo());
    }

    public void guardarConfiguracion(View view){
        Switch isActivo = this.view.findViewById(R.id.stActivo);
        this.dispositivo.setBloqueoActivo(isActivo.isChecked());

        EditText tiempoUso = this.view.findViewById(R.id.tiempoUsoDispositivo);
        this.dispositivo.setTiempoAsignado(TimeUnit.MINUTES.toMillis(Long.valueOf(tiempoUso.getText().toString())));

        TimePicker horaInicio = this.view.findViewById(R.id.horaInicioPicker);
        Long horaInicioMilis = TimeUnit.HOURS.toMillis(horaInicio.getCurrentHour()) + TimeUnit.MINUTES.toMillis(horaInicio.getCurrentMinute());
        this.dispositivo.setHoraInicio(horaInicioMilis);
        TimePicker horaFin = this.view.findViewById(R.id.horaFinPicker);
        Long horaFinMilis = TimeUnit.HOURS.toMillis(horaFin.getCurrentHour()) + TimeUnit.MINUTES.toMillis(horaFin.getCurrentMinute());
        this.dispositivo.setHoraFin(horaFinMilis);
        DispositivoDAO dispositivoDAO = new DispositivoDAO(this);
        dispositivoDAO.actualizarConfiguracionBloqueoDispositivo(this.dispositivo);
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "actualizarConfiguracionBloqueoDispositivo":
                Integer modificado = DispositivoDAO.actualizarConfiguracionBloqueoDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(modificado != null){
                    Toast.makeText(getContext(),"Guardado correctamente",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}