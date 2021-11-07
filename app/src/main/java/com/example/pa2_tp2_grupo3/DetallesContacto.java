package com.example.pa2_tp2_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetallesContacto extends AppCompatActivity {
    private String datosContacto;
    private Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_contacto);
        datosContacto = getIntent().getStringExtra("datosContacto");
        Gson gson = new Gson();
        contacto = gson.fromJson(datosContacto, Contacto.class);
        ((TextView)findViewById(R.id.txtDetalleNombre)).setText(contacto.getNombre());
        ((TextView)findViewById(R.id.txtDetalleApellido)).setText(contacto.getApellido());
        ((TextView)findViewById(R.id.txtDetalleTelefono)).setText(contacto.getTelefono() +" " + contacto.getUbicacionTelefono());
        ((TextView)findViewById(R.id.txtDetalleEmail)).setText(contacto.getEmail()+" " + contacto.getUbicacionEmail());
        ((TextView)findViewById(R.id.txtDetalleDireccion)).setText(contacto.getDireccion());
        ((TextView)findViewById(R.id.txtDetalleFechaNacimiento)).setText(contacto.getFechaNacimiento());
        ((TextView)findViewById(R.id.txtDetalleNivel)).setText(contacto.getNivelEstudios());
        ((TextView)findViewById(R.id.txtDetalleIntereses)).setText(contacto.getIntereses());
        ((TextView)findViewById(R.id.txtDetallesRecibirInfo)).setText(contacto.getRecibirInformacion() ? "Si":"No");
    }

    public void volver(View view)
    {
        startActivity(new Intent(this, ListadoDeContactos.class));
    }
}