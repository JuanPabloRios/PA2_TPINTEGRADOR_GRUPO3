package com.example.pa2_tpintegrador_grupo3;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.example.pa2_tpintegrador_grupo3.DAO.EstadisticaDAO;
import com.example.pa2_tpintegrador_grupo3.DAO.RestriccionesDAO;
import com.example.pa2_tpintegrador_grupo3.adapters.DetallesDispositivoPagerAdapter;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Estadistica;
import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import com.example.pa2_tpintegrador_grupo3.viewModels.Detalles_dispositivoViewModel;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class DetallesDispositivo extends AppCompatActivity implements InterfazDeComunicacion {
    private ViewPager viewPager;
    private Usuario user;
    private Dispositivo dispositivo;
    public DetallesDispositivoPagerAdapter pagerAdapter;
    private Detalles_dispositivoViewModel detallesDispositivoViewModel;
    private RestriccionesDAO restricDao = new RestriccionesDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_dispositivo);
        this.dispositivo = (Dispositivo) getIntent().getSerializableExtra("dispositivo");
        this.user = (Usuario)getIntent().getSerializableExtra("usuario");
        detallesDispositivoViewModel = new ViewModelProvider(this).get(Detalles_dispositivoViewModel.class);
        detallesDispositivoViewModel.setDispositivo(this.dispositivo);
        detallesDispositivoViewModel.getRestriccionModificada().observe( this, new Observer<Restricciones>() {
            @Override
            public void onChanged(Restricciones res) {
                restricDao.modificarRestriccion(res);
            }
        });
        //ACA HACEMOS LA LLAMADA A LA BASE DE DATOS
        restricDao.obtenerTodasLasRestriccionesPorIdDeDispositivo(this.dispositivo.getId());
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerTodasLasRestriccionesPorIdDeDispositivo":
                ArrayList<Restricciones> restricciones = RestriccionesDAO.obtenerTodasLasRestriccionesPorIdDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(restricciones != null && restricciones.size() > 0){
                    //CARGAMOS EN EL VIEWMODEL TODOS LOS DETALLES DE LAS APLICACIONES Y COMPLETAMOS LA CARGA DE LA PANTALLA
                    detallesDispositivoViewModel.setRestricciones(restricciones);
                    EstadisticaDAO estadisticaDAO = new EstadisticaDAO(this);
                    estadisticaDAO.obtenerEstadisticasDeDispositivo(this.dispositivo);
                }
                break;
            case "modificarRestriccion":
                Integer resModificacionMinutos = RestriccionesDAO.modificarRestriccionHandler(res.getData());
                //ACA DETENER SPINNER
                if(resModificacionMinutos != null && resModificacionMinutos != -1){
                    Toast.makeText(this,"Cambios guardados correctamente",Toast.LENGTH_SHORT).show();
                }
                break;
            case "obtenerEstadisticasDeDispositivo":
                ArrayList<Estadistica> estadisticas = EstadisticaDAO.obtenerEstadisticasDeDispositivoHandler(res.getData());
                //ACA DETENER SPINNER
                if(estadisticas != null && !estadisticas.isEmpty()){
                    detallesDispositivoViewModel.setEstaditicas(estadisticas);
                    this.completarCarga();
                }
                break;
        }
    }

    public void completarCarga(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new DetallesDispositivoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this.dispositivo.getId());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
