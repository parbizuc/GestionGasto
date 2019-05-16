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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
    List<Solicitud> datosRegistrados = new ArrayList<>();
    List<Solicitud> datosProcesados = new ArrayList<>();

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
        View rootView = inflater.inflate(R.layout.fg_solicitudes, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_solicitudes);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        sAdapter = new SolicitudAdapter(datos, getContext());
        setListeners();
        return rootView;
    }

    private void setListeners() {
        if (presenter.getValorEstado() == true) {
            presenter.getQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                    datosProcesados.clear();
                    datosRegistrados.clear();
                    for (DocumentSnapshot document : snapshots.getDocuments()) {
                        Solicitud solicitud = document.toObject(Solicitud.class);
                        if (solicitud.getEstado() != null) {
                            datosProcesados.add(solicitud);
                        } else {
                            datosRegistrados.add(solicitud);
                        }
                    }
                    datos = getArguments().getInt(SECTION_NUMBER) == 1 ? datosRegistrados : datosProcesados;
                    sAdapter.refreshSolicitudes(datos);
                    recyclerSolicitud.setAdapter(sAdapter);
                    sAdapter.notifyDataSetChanged();
                }
            });
        } else if (presenter.getValorEstado() == false) {

            presenter.getQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                    datosProcesados.clear();
                    datosRegistrados.clear();

                    for (DocumentSnapshot document : snapshots.getDocuments()) {
                        Solicitud solicitud = document.toObject(Solicitud.class);
                        if (solicitud.getEstado() != null && solicitud.getEstado().equals("Por aprobar")) {
                            datosRegistrados.add(solicitud);

                        } else if (solicitud.getEstado() != null && solicitud.getEstado().equals("RECHAZADO") /*|| solicitud.getEstado().equals("RECHAZADO")*/){
                            datosProcesados.add(solicitud);
                        }
                        else if (solicitud.getEstado() != null && solicitud.getEstado().equals("APROBADO") /*|| solicitud.getEstado().equals("RECHAZADO")*/) {
                            datosProcesados.add(solicitud);
                        }

                    }
                    datos = getArguments().getInt(SECTION_NUMBER) == 1 ? datosRegistrados : datosProcesados;
                    sAdapter.refreshSolicitudes(datos);
                    recyclerSolicitud.setAdapter(sAdapter);
                    sAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setError(Integer type, String texto) {

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
