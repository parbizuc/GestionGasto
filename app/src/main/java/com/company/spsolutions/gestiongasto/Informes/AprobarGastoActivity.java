package com.company.spsolutions.gestiongasto.Informes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;
/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class AprobarGastoActivity extends AppCompatActivity implements PresenterInforme{
    /* Esta clase se encarga de soportar la vista para aprobar gastos y finalmente aprobar el informe
    * initComponents() crea las instancias de las vistas
    * onClick() se encarga de crear la logica al apretar los botones para aprobar el gasto
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobargasto);
    }
}
