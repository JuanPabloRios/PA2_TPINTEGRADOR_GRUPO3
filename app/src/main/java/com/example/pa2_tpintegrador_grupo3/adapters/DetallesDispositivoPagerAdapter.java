package com.example.pa2_tpintegrador_grupo3.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_aplicaciones;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_detalles;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_dispositivo;

public class DetallesDispositivoPagerAdapter extends FragmentPagerAdapter {
    int numOfTabs;
    Integer idDispositivo;

    public DetallesDispositivoPagerAdapter(@NonNull FragmentManager fm, int behavior, Integer id) {
        super(fm, behavior);
        this.numOfTabs = behavior;
        this.idDispositivo = id;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Detalles_dispositivo_detalles(idDispositivo);
            case 1:
                return new Detalles_dispositivo_dispositivo(idDispositivo);
            case 2:
                return new Detalles_dispositivo_aplicaciones(idDispositivo);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
