package com.company.spsolutions.gestiongasto.Gastos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class FragmentGastosActuales extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_informeactual, container, false);
    }

}
