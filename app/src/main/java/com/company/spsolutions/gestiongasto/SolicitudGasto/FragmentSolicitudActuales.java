package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 01/04/2019
 */
public class FragmentSolicitudActuales extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_solicitudesactuales, container, false);
    }
}
