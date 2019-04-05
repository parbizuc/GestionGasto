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

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coralRodriguez on 01/04/2019
 */
public class FragmentSolicitudes extends Fragment implements PresenterSolicitud {
    private RecyclerView recyclerSolicitud;
    private SolicitudAdapter sAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;
    PresenterSolicitudImpl presenter;
    List<Solicitud> datos = new ArrayList<>();

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
        presenter = new PresenterSolicitudImpl(getContext(), this);
        DatabaseReference dbSolicitud = presenter.connect();
        View rootView = inflater.inflate(R.layout.fg_solicitudes, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_solicitudes);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        dbSolicitud.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Solicitud solicitud = dataSnapshot.getValue(Solicitud.class);
                    datos.add(solicitud);
                }
                datos = getArguments().getInt(SECTION_NUMBER) == 1 ? presenter.getDataRegistradas(datos) : presenter.getDataProcesadas(datos);
                sAdapter = new SolicitudAdapter(datos, getContext());
                recyclerSolicitud.setAdapter(sAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void displayLabel() {

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
