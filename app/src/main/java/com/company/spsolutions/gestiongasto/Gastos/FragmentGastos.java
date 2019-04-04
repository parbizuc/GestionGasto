package com.company.spsolutions.gestiongasto.Gastos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.spsolutions.gestiongasto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class FragmentGastos extends Fragment {
    private RecyclerView recyclerSolicitud;
    private GastosAdapter gAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;

    public static FragmentGastos newInstance(int sectionNumber) {
        FragmentGastos fragment = new FragmentGastos();
        Bundle args = new Bundle();
        args.putInt(SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<ItemGastos> datos;
        View rootView = inflater.inflate(R.layout.fg_gastos, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_gastos);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        datos = getArguments().getInt(SECTION_NUMBER) == 1 ? getDataRegistrados() : getDataInformados();
        gAdapter = new GastosAdapter(datos, rootView.getContext());
        recyclerSolicitud.setAdapter(gAdapter);
        return rootView;
    }

    public List<ItemGastos> getDataRegistrados() {
        List<ItemGastos> solicitudes = new ArrayList<ItemGastos>();
        solicitudes.add(new ItemGastos("Boleto de avión GDL",   "$12,000", "29/01/19", "https://www.eldictamen.mx/wp-content/uploads/2019/01/img_3409.jpg"));
        solicitudes.add(new ItemGastos("Hotel GDL",   "$3,000", "29/01/19", "https://www.eldictamen.mx/wp-content/uploads/2019/01/img_3409.jpg"));
        return solicitudes;
    }

    public List<ItemGastos> getDataInformados() {
        List<ItemGastos> solicitudes = new ArrayList<ItemGastos>();
        solicitudes.add(new ItemGastos("Comida con clientes",   "$2,000", "02/01/19", "https://www.eldictamen.mx/wp-content/uploads/2019/01/img_3409.jpg"));
        return solicitudes;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentGastos.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
