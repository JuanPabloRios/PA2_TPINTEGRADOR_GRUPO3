package com.example.pa2_tp2_grupo3;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import com.google.gson.*;

import org.w3c.dom.Text;

public class ListadoDeContactos extends MenuController {

    private static final String NOMBRE_ARCHIVO = "agendaTp2.txt";
    private Toolbar mtoolbar;
    private ListView table;
    private ArrayList contactos;
    private ArrayList contactosJsons;
    private ArrayAdapter<String> adaptador1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_de_contactos);
        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        table = (ListView)findViewById(R.id.tabla);
        contactos = new ArrayList();
        contactosJsons = new ArrayList();
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                irADetalleContacto(position);
            }
        });
        adaptador1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactos);
        table.setAdapter(adaptador1);
        FileInputStream file = null;
        try {
            file = openFileInput(NOMBRE_ARCHIVO);
            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader buffer = new BufferedReader(inputReader);
            String contactoJson;
            Gson gson = new Gson();
            while((contactoJson = buffer.readLine()) != null){
                Contacto con = gson.fromJson(contactoJson, Contacto.class);
                contactos.add(con.getNombre().toUpperCase(Locale.ROOT) + " " + con.getApellido().toUpperCase(Locale.ROOT) + " - " + con.getEmail().toUpperCase(Locale.ROOT));
                contactosJsons.add(contactoJson);
                adaptador1.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void irADetalleContacto(Integer position){
        Intent i = new Intent(this, DetallesContacto.class);
        i.putExtra("datosContacto",contactosJsons.get(position).toString());
        startActivity(i);
    }


}