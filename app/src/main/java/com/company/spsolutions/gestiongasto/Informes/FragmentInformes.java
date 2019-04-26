package com.company.spsolutions.gestiongasto.Informes;

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
 * Created by coralRodriguez on 01/04/2019
 */
public class FragmentInformes extends Fragment {
    private RecyclerView recyclerSolicitud;
    private InformesAdapter iAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;

    public static FragmentInformes newInstance(int sectionNumber) {
        FragmentInformes fragment = new FragmentInformes();
        Bundle args = new Bundle();
        args.putInt(SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<ItemInforme> datos;
        View rootView = inflater.inflate(R.layout.fg_informes, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_informes);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        datos = getArguments().getInt(SECTION_NUMBER) == 1 ? getDataRegistrados() : getDataInformados();
        iAdapter = new InformesAdapter(datos, rootView.getContext());
        recyclerSolicitud.setAdapter(iAdapter);
        return rootView;
    }

    public List<ItemInforme> getDataRegistrados() {
        List<ItemInforme> informes = new ArrayList<ItemInforme>();
        informes.add(new ItemInforme("Compra de RAM", "29/10/19 - 30/10/19", "$1,600"));
        return informes;
    }

    public List<ItemInforme> getDataInformados() {
        List<ItemInforme> solicitudes = new ArrayList<ItemInforme>();
        solicitudes.add(new ItemInforme("Viaje a Cancún", "29/10/19", "$24,000", "POR APROBAR"));
        solicitudes.add(new ItemInforme("Viaje a Guadalajara", "30/01/19", "$12,000", "POR APROBAR"));
        return solicitudes;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentInformes.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
