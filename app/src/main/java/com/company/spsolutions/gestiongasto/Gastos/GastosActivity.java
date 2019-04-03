package com.company.spsolutions.gestiongasto.Gastos;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.SolicitudActivity;

public class GastosActivity extends AppCompatActivity implements PresenterGastos {
    /*
     * Esta clase es la vista principal de los gastos controla la vista de las gastos actuales y pasados
     * displayError se encarga de colocar error en caso que exista
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
    }

    @Override
    public void displayError() {

    }


    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static SolicitudActivity.PlaceholderFragment newInstance(int sectionNumber) {
            SolicitudActivity.PlaceholderFragment fragment = new SolicitudActivity.PlaceholderFragment();
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
            return SolicitudActivity.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // 2.
            return 2;
        }
    }
}
