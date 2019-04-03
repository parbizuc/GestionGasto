package com.company.spsolutions.gestiongasto.Informes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;
/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class BalanceGastosActivity extends AppCompatActivity {
    /* Esta clase es la encargada de soportar la vista del balance de gastos donde se vera finalmente
     lo que resta o se debe segun el informe
    *
    *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balancegasto);
    }
}
