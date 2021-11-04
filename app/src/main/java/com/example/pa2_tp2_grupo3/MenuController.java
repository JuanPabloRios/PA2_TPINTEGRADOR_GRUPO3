package com.example.pa2_tp2_grupo3;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

public class MenuController extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_en_activity, mimenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem opcion_menu){

        int id=opcion_menu.getItemId();
        if(id==R.id.agregarContacto){
            startActivity(new Intent(this, AgregarContacto.class));
            return true;
        }
        if(id==R.id.listaContactos){
            startActivity(new Intent(this, ListadoDeContactos.class));
            return true;
        }
        if(id==R.id.home){
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(opcion_menu);
    }
}
