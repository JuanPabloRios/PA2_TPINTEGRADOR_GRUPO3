package com.example.pa2_tp2_grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MasDatosDeContacto extends AppCompatActivity {

    private static final String NOMBRE_ARCHIVO = "agendaTp2.txt";
    private String nombre;
    private String apellido;
    private String email;
    private String ubicacionEmail;
    private String telefono;
    private String ubicacionTelefono;
    private String direccion;
    private String fechaNacimiento;
    private RadioButton rbtnPrimarioInc;
    private RadioButton rbtnPrimarioComp;
    private RadioButton rbtnSecundarioInc;
    private RadioButton rbtnSecundarioComp;
    private RadioButton rbtnOtros;
    private CheckBox    cBoxDeporte;
    private CheckBox    cBoxMusica;
    private CheckBox    cBoxArte;
    private CheckBox    cBoxTecnologia;
    private Switch      swRecibirInfo;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_datos_de_contacto);
        rbtnPrimarioInc = (RadioButton)findViewById(R.id.rbtnPrimarioInc);
        rbtnPrimarioComp = (RadioButton)findViewById(R.id.rbtnPrimarioComp);
        rbtnSecundarioInc = (RadioButton)findViewById(R.id.rbtnSecundarioInc);
        rbtnSecundarioComp = (RadioButton)findViewById(R.id.rbtnSecundarioComp);
        rbtnOtros = (RadioButton)findViewById(R.id.rbtnOtros);
        cBoxDeporte = (CheckBox)findViewById(R.id.cBoxDeporte);
        cBoxMusica = (CheckBox)findViewById(R.id.cBoxMusica);
        cBoxArte = (CheckBox)findViewById(R.id.cBoxArte);
        cBoxTecnologia = (CheckBox)findViewById(R.id.cBoxTecnologia);
        swRecibirInfo = (Switch)findViewById(R.id.swRecibirInfo);
        nombre = getIntent().getStringExtra("nombre");
        apellido = getIntent().getStringExtra("apellido");
        email = getIntent().getStringExtra("email");
        ubicacionEmail = getIntent().getStringExtra("ubicacionEmail");
        ubicacionTelefono = getIntent().getStringExtra("ubicacionTelefono");
        telefono = getIntent().getStringExtra("telefono");
        direccion = getIntent().getStringExtra("direccion");
        fechaNacimiento = getIntent().getStringExtra("fechaNacimiento");

        btnGuardar = (Button)findViewById(R.id.button2);
    }

    public void guardarContacto(View view){

        String nivelEstudios = "";
        if(rbtnPrimarioInc.isChecked()){
            nivelEstudios = "Primario Incompleto";
        } else if(rbtnPrimarioComp.isChecked()){
            nivelEstudios = "Primario Completo";
        } else if(rbtnSecundarioInc.isChecked()){
            nivelEstudios = "Secundario Incompleto";
        } else if(rbtnSecundarioComp.isChecked()){
            nivelEstudios = "Secundario Completo";
        } else if(rbtnOtros.isChecked()){
            nivelEstudios = "Otros";
        }

        if(nivelEstudios.isEmpty()){
            Toast.makeText(this, "Debe seleccionar un Nivel de estudios.", Toast.LENGTH_SHORT).show();
            return;
        }
      
        btnGuardar.setEnabled(false); 
        FileOutputStream file = null;
        try {
            Boolean recibirInfo = swRecibirInfo.isChecked();
            ArrayList<String> intereses = new ArrayList<String>();
            if(cBoxDeporte.isChecked()){
                intereses.add("Deportes");
            }
            if(cBoxMusica.isChecked()){
                intereses.add("Musica");
            }
            if(cBoxArte.isChecked()){
                intereses.add("Arte");
            }
            if(cBoxTecnologia.isChecked()){
                intereses.add("Tecnologia");
            }

            Contacto contactoAGuardar = new Contacto(
                    nombre,
                    apellido,
                    email,
                    ubicacionEmail,
                    telefono,
                    ubicacionTelefono,
                    direccion,
                    fechaNacimiento,
                    nivelEstudios,
                    intereses.toString().replace("[", "").replace("]", ""),
                    recibirInfo
            );
            file = new FileOutputStream(new File(getFilesDir(),NOMBRE_ARCHIVO),true);
            String separator = System.getProperty("line.separator");
            OutputStreamWriter writer = new OutputStreamWriter(file);
            writer.append(contactoAGuardar.toString());
            writer.append(separator);
            writer.close();
            Intent i = new Intent(this, AgregarContacto.class);
            i.putExtra("guardadoConfirmado",true);
            startActivity(i);
        } catch (IOException e){
            Toast.makeText(this,"Error guardando contacto!",Toast.LENGTH_SHORT).show();
        } finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}