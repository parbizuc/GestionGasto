package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class SolicitudActivity extends AppCompatActivity implements PresenterSolicitud {
    /*
    * Esta clase es la vista principal de las solicitudes controla la vista de las solicitudes actuales y pasadas
    * AddSolicitud es el método que gestiona si el usuario quiere editar o agregar una solicitud
    * displayError muestra un error en caso de ser necesario
    * displayloader muestra un loader para actualizar los datos
    * displaylabel hace cambios en el label de las solicitudes
    * add añade un nuevo registro que se trae desde el modelo
    * remove quita un registro que se trae en el modelo
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
    }

    public void addSolicitud(){

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

    public void add() {

    }

    public void remove() {

    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_solicitud, container, false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // 2.
            return 2;
        }
    }
}
