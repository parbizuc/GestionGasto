package com.company.spsolutions.gestiongasto.SolicitudGasto;

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
public class FragmentSolicitudes extends Fragment {
    private RecyclerView recyclerSolicitud;
    private SolicitudAdapter sAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;

    public static FragmentSolicitudes newInstance(int sectionNumber) {
        FragmentSolicitudes fragment = new FragmentSolicitudes();
        Bundle args = new Bundle();
        args.putInt(SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<ItemSolicitud> datos;
        View rootView = inflater.inflate(R.layout.fg_solicitudes, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_solicitudes);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        datos = getArguments().getInt(SECTION_NUMBER) == 1 ? getDataRegistradas() : getDataProcesadas();
        sAdapter = new SolicitudAdapter(datos, rootView.getContext());
        recyclerSolicitud.setAdapter(sAdapter);
        return rootView;
    }

    public List<ItemSolicitud> getDataRegistradas() {
        List<ItemSolicitud> solicitudes = new ArrayList<ItemSolicitud>();
        solicitudes.add(new ItemSolicitud("Juan Perez", "ventas", "29/10/19", "$12,000"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$2,000"));
        return solicitudes;
    }

    public List<ItemSolicitud> getDataProcesadas() {
        List<ItemSolicitud> solicitudes = new ArrayList<ItemSolicitud>();
        solicitudes.add(new ItemSolicitud("Juan Perez", "ventas", "29/10/19", "$12,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$2,000", "Rechazada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$28,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose ", "diseño", "29/01/18", "$52,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$62,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "d", "29/01/18", "$2,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Juan Perez", "ventas", "29/10/19", "$12,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$2,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$28,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose ", "diseño", "29/01/18", "$52,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "diseño", "29/01/18", "$62,000", "Procesada"));
        solicitudes.add(new ItemSolicitud("Jose Perez", "d", "29/01/18", "$2,000", "Procesada"));
        return solicitudes;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentSolicitudes.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
