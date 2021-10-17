package com.example.pa2_tpintegrador_grupo3.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_aplicaciones;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_detalles;
import com.example.pa2_tpintegrador_grupo3.fragments.Detalles_dispositivo_dispositivo;

public class PagerAdapter extends FragmentPagerAdapter {
    int numOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Detalles_dispositivo_detalles();
            case 1:
                return new Detalles_dispositivo_dispositivo();
            case 2:
                return new Detalles_dispositivo_aplicaciones();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
