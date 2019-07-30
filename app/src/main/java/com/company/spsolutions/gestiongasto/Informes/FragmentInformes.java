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

import com.company.spsolutions.gestiongasto.Modelos.Informe;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.PresenterSolicitudImpl;
import com.company.spsolutions.gestiongasto.SolicitudGasto.SolicitudAdapter;
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
public class FragmentInformes extends Fragment implements PresenterInforme {
    private RecyclerView recyclerSolicitud;
    private InformesAdapter iAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;
    PresenterInformeImpl presenter;
    List<Informe> datos = new ArrayList<>();
    List<Informe> datosRegistrados = new ArrayList<>();
    List<Informe> datosProcesados = new ArrayList<>();


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
        presenter = new PresenterInformeImpl(getContext(), this);
        View rootView = inflater.inflate(R.layout.fg_informes, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_informes);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        iAdapter = new InformesAdapter(datos, getContext());
        setListeners();
        return rootView;
    }

private void setListeners() {
        System.out.println("setListeners en FragmentInformes");
        presenter.getQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                datosProcesados.clear();
                datosRegistrados.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    Informe informe = document.toObject(Informe.class);
                    System.out.println("setListeners->informe"+informe+"");
                    if (informe.getEstado() != null && informe.getEstado().equals("ENVIADO")){
                        datosProcesados.add(informe);
                    } else if  (informe.getEstado() != null && informe.getEstado().equals("INFORMADO")) {
                        datosRegistrados.add(informe);
                    }
                }
                datos = getArguments().getInt(SECTION_NUMBER) == 1 ? datosRegistrados : datosProcesados;
                iAdapter.refreshSolicitudes(datos);
                recyclerSolicitud.setAdapter(iAdapter);
                iAdapter.notifyDataSetChanged();
            }
        });

    }



    @Override
    public String getTotal() {
        return null;
    }

    @Override
    public void changeTotal(String total) {

    }

    @Override
    public void setError(Integer type, String message) {

    }

    @Override
    public void changeActivity() {

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
