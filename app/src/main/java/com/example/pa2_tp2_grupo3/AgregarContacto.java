package com.example.pa2_tp2_grupo3;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast; 
import com.example.pa2_tp2_grupo3.Validador; 

public class AgregarContacto extends MenuController {

    private Toolbar mtoolbar;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtTelefono;
    private EditText txtEmail;
    private EditText txtDireccion;
    private EditText txtFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        String [] opciones = {this.getString(R.string.tel_spinner_casa),this.getString(R.string.tel_spinner_trabajo),this.getString(R.string.tel_spinner_movil)};
        spinner1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opciones));
        spinner2.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opciones));

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtApellido = (EditText)findViewById(R.id.txtApellido);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtDireccion = (EditText)findViewById(R.id.txtDireccion);
        txtFechaNacimiento = (EditText)findViewById(R.id.txtFechaNacimiento);

        if(getIntent().getExtras() != null && getIntent().getExtras().getBoolean("guardadoConfirmado")){
            Toast.makeText(this,"Contacto guardado correctamente!",Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarMasDatos(View view){

        if(!Validador.validateEmpty(txtNombre.getText().toString())){
            if(Validador.validateString(txtNombre.getText().toString())){
                Toast.makeText(this, "No se permiten numeros en el campo Nombre.", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this, "EL campo Nombre no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;}

        if(!Validador.validateEmpty(txtApellido.getText().toString())){
            if(Validador.validateString(txtApellido.getText().toString())){
                Toast.makeText(this, "No se permiten numeros en el campo Apellido.", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this, "EL campo Apellido no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;}

        if(Validador.validateEmpty(txtDireccion.getText().toString())){
            Toast.makeText(this, "EL campo Direccion no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Validador.validateEmpty(txtTelefono.getText().toString())){
            Toast.makeText(this, "EL campo Telefono no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Validador.validateEmpty(txtEmail.getText().toString())){
            if(Validador.emailInvalido(txtEmail.getText().toString())){
                Toast.makeText(this, "No es un Email valido.", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this, "EL campo Email no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;}

        if(!Validador.validateEmpty(txtFechaNacimiento.getText().toString())){
            if(Validador.validateDate(txtFechaNacimiento.getText().toString())){
                Toast.makeText(this, "No es una Fecha valida.", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(this, "EL campo Fecha no puede estar vacio.", Toast.LENGTH_SHORT).show();
            return;}


        Intent i = new Intent(this, MasDatosDeContacto.class);
        i.putExtra("nombre",txtNombre.getText().toString());
        i.putExtra("apellido",txtApellido.getText().toString());
        i.putExtra("telefono",txtTelefono.getText().toString());
        i.putExtra("email",txtEmail.getText().toString());
        i.putExtra("direccion",txtDireccion.getText().toString());
        i.putExtra("fechaNacimiento",txtFechaNacimiento.getText().toString());
        i.putExtra("ubicacionEmail",spinner2.getSelectedItem().toString());
        i.putExtra("ubicacionTelefono",spinner1.getSelectedItem().toString());
        startActivity(i);
    }


}