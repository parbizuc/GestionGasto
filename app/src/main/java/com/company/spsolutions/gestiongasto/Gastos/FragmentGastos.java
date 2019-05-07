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

import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class FragmentGastos extends Fragment implements PresenterGastos {
    private RecyclerView recyclerSolicitud;
    private GastosAdapter gAdapter;
    private LinearLayoutManager layoutManager;
    private static String SECTION_NUMBER;
    PresenterGastosImpl presenter;
    List<Gasto> datos = new ArrayList<>();
    List<Gasto> datosRegistrados = new ArrayList<>();
    List<Gasto> datosProcesados = new ArrayList<>();

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

        presenter = new PresenterGastosImpl(getContext(), this);
        //List<Gasto> datos;
        final CollectionReference dbGastos = presenter.connectGastosDB();
        View rootView = inflater.inflate(R.layout.fg_gastos, container, false);
        recyclerSolicitud = rootView.findViewById(R.id.recycler_gastos);
        recyclerSolicitud.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSolicitud.setLayoutManager(layoutManager);
        // datos = getArguments().getInt(SECTION_NUMBER) == 1 ? getDataRegistrados() : getDataInformados();
        gAdapter = new GastosAdapter(datos, getContext());
        // recyclerSolicitud.setAdapter(gAdapter);
        dbGastos.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                datosProcesados.clear();
                datosRegistrados.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    Gasto gasto = document.toObject(Gasto.class);
                    if (gasto.getEstado().equals("INFORMADO")) {
                        datosProcesados.add(gasto);

                    } else {
                        datosRegistrados.add(gasto);
                    }
                }
                datos = getArguments().getInt(SECTION_NUMBER) == 1 ? datosRegistrados : datosProcesados;
                gAdapter.refreshGastos(datos);
                recyclerSolicitud.setAdapter(gAdapter);
                gAdapter.notifyDataSetChanged();


            }
        });

        return rootView;
    }

    @Override
    public void displayTicketResults(String amount, String date) {

    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setError(Integer type, String text) {

    }

    @Override
    public void displayProgress(Boolean isDisplayed, String texto) {

    }

    /*
        public List<Gasto> getDataRegistrados() {
            List<Gasto> solicitudes = new ArrayList<Gasto>();
            solicitudes.add(new Gasto("cod","codEmpresa","Nombre Empresa",   "Proveedor", "fechaGasto", "MontoGasto","moneda"));
            solicitudes.add(new Gasto("cod","codEmpresa","Nombre Empresa",   "Proveedor", "fechaGasto", "MontoGasto","moneda"));
            return solicitudes;
        }

        public List<Gasto> getDataInformados() {
            List<Gasto> solicitudes = new ArrayList<Gasto>();
            solicitudes.add(new Gasto("cod","codEmpresa","Nombre Empresa",   "Proveedor", "fechaGasto", "MontoGasto","moneda"));
            return solicitudes;
        }
    */
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
