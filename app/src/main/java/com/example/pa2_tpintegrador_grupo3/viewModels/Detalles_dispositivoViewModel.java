package com.example.pa2_tpintegrador_grupo3.viewModels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pa2_tpintegrador_grupo3.entidades.Restricciones;

import java.util.ArrayList;

public class Detalles_dispositivoViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Restricciones>> restricciones = new MutableLiveData<ArrayList<Restricciones>>();
    private final MutableLiveData<Restricciones> restriccionModificada = new MutableLiveData<Restricciones>();

    public void setRestricciones(ArrayList<Restricciones> app ){
        this.restricciones.setValue(app);
    }

    public LiveData<ArrayList<Restricciones>> getRestricciones(){
        return restricciones;
    }

    public void setRestriccionModificada(Restricciones res ){
        this.restriccionModificada.setValue(res);
    }

    public LiveData<Restricciones> getRestriccionModificada(){
        return restriccionModificada;
    }
}
