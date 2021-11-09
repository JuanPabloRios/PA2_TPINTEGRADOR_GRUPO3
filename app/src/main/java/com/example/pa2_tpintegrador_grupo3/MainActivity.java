package com.example.pa2_tpintegrador_grupo3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pa2_tpintegrador_grupo3.Controladores.DetallesDispositivoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.DispositivosVinculadosControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.PrincipalSubordinadoControlador;
import com.example.pa2_tpintegrador_grupo3.Controladores.RegistrarUsuarioControlador;

public class MainActivity extends AppCompatActivity{
    private boolean esSubordinado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilidad ut = new Utilidad();
        String result = ut.validarTipoDispositivo(this);
        if(result != null){
            //falta agregar el resultado del metodo.. crear archivo para el mismo.
            if(result.equals("1")) {
                LinearLayout l = (LinearLayout)findViewById(R.id.Login);
                l.setVisibility(View.VISIBLE);
                LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
                l2.setVisibility(View.GONE);
            }else{
                startActivity(new Intent(this, PrincipalSubordinadoControlador.class));
            }
        }
    }

    public void irARegistrarSubordinado(View view){
        esSubordinado = true;
        LinearLayout l = (LinearLayout)findViewById(R.id.Login);
        l.setVisibility(View.VISIBLE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
        l2.setVisibility(View.GONE);
    }
    public void irARegistrarMaestro(View view){
        LinearLayout l = (LinearLayout)findViewById(R.id.Login);
        l.setVisibility(View.VISIBLE);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.Inicial);
        l2.setVisibility(View.GONE);
    }
    public void registrarUsuarioMaestro(View view) {
        startActivity(new Intent(this, RegistrarUsuarioControlador.class));
    }

    public void validarInicioSesionTipoUsuario(View view){
        if(!esSubordinado){
            startActivity(new Intent(this, DispositivosVinculadosControlador.class));
        }else{
            startActivity(new Intent(this, PrincipalSubordinadoControlador.class));
        }
    }

}