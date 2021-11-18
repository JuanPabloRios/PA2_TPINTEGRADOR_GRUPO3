package com.example.pa2_tpintegrador_grupo3.Controladores;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pa2_tpintegrador_grupo3.DAO.DispositivoDAO;
import com.example.pa2_tpintegrador_grupo3.DetallesDispositivo;
import com.example.pa2_tpintegrador_grupo3.R;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceMaestro;
import com.example.pa2_tpintegrador_grupo3.Servicios.ServiceSubordinado;
import com.example.pa2_tpintegrador_grupo3.Utilidad;
import com.example.pa2_tpintegrador_grupo3.conexion.ResultadoDeConsulta;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.fragments.eliminar_subordinado;
import com.example.pa2_tpintegrador_grupo3.fragments.solicitar_extension_uso_dispositivo;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.util.ArrayList;

public class DispositivosVinculadosControlador extends AppCompatActivity implements InterfazDeComunicacion, eliminar_subordinado.eliminar_subordinadoListener {

    private Usuario user;
    private DispositivoDAO dispoDao = new DispositivoDAO(this);
    private Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispositivos_vinculados);
        //OBTENEMOS EL USUARIO ACTUAL
        this.user = (Usuario)getIntent().getSerializableExtra("usuario");
        //ACA INICIAR SPINNER
        dispoDao.obtenerTodosLosDispositivosPorUsuario(this.user);
    }

    @Override
    public void operacionConBaseDeDatosFinalizada(Object resultado) {
        ResultadoDeConsulta res = (ResultadoDeConsulta) resultado;
        switch (res.getIdentificador()){
            case "obtenerTodosLosDispositivosPorUsuario":
                ArrayList<Dispositivo> dispositivos = DispositivoDAO.obtenerTodosLosDispositivosPorUsuarioHandler(res.getData());
                //ACA DETENER SPINNER
                if(dispositivos != null && dispositivos.size() > 0){
                    cargarDispositivosVinculados(dispositivos);
                    Intent serviceIntent = new Intent(this, ServiceMaestro.class);
                    serviceIntent.putExtra("idUsuario",this.user.getId());
                    ContextCompat.startForegroundService(this,serviceIntent);
                } else {
                    TextView txtTitulo = findViewById(R.id.tituloDispositivosVinculados);
                    txtTitulo.setText("No existen dispositivos subordinados vinculados");
                }
                break;
            case "eliminarSubordinado":
                Integer dispEliminado = DispositivoDAO.eliminarSubordinadoHandler(res.getData());
                //ACA DETENER SPINNER
                if(dispEliminado != null){
                    Toast.makeText(this,"Dispositivo eliminado correctamente",Toast.LENGTH_SHORT).show();
                    LinearLayout tabla = findViewById(R.id.tablaVinculados);
                    tabla.removeAllViews();
                    dispoDao.obtenerTodosLosDispositivosPorUsuario(this.user);
                } else {
                    Toast.makeText(this,"Error al eliminar",Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                System.out.println("OTRO IDENTIFICADOR");
        }
    }


    public void cargarDispositivosVinculados(ArrayList<Dispositivo> dispositivos){
        eliminar_subordinado.eliminar_subordinadoListener list = this;
        LinearLayout tabla = findViewById(R.id.tablaVinculados);
        tabla.removeAllViews();
        for(Dispositivo d : dispositivos){
            Button button = new Button(this);
            button.setId(d.getId());
            button.setText("DISPOSITIVO: "+ d.getNombre() + "\n"+"TIEMPO USO: "+ Utilidad.obtenerHorasYMinutos(d.getTiempoUso()));
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,0));
            button.setGravity(Gravity.START);
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    eliminar_subordinado dialog = new eliminar_subordinado();
                    dialog.setListener(list);
                    dialog.setDispositivo(d);
                    dialog.show(getSupportFragmentManager(),null);
                    return false;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DispositivosVinculadosControlador.this.irADetalleDeDispositivo(d);
                }
            });
            tabla.addView(button);
        }
        if(dispositivos.isEmpty()){
            TextView txtTitulo = findViewById(R.id.tituloDispositivosVinculados);
            txtTitulo.setText("No existen dispositivos subordinados vinculados");
        }
    }

    public void irADetalleDeDispositivo(Dispositivo d){
        Intent i = new Intent(this, DetallesDispositivo.class);
        i.putExtra("usuario",this.user);
        i.putExtra("dispositivo",d);
        startActivity(i);
    }

    @Override
    public void eliminarSubordinado(Integer idDispositivo) {
        dispoDao.eliminarSubordinado(idDispositivo);
    }
}
