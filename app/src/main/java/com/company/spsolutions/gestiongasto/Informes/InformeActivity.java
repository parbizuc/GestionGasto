package com.company.spsolutions.gestiongasto.Informes;
/**
 * Created by coralRodriguez on 27/03/19.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;

public class InformeActivity extends AppCompatActivity implements PresenterInforme {
    /* Esta clase controla la vista principal del informe de gastos
    * initComponents() se encarga de crear las instancias de los view y sus listeners
    * onClick es el metodo que se encarga de gestionar las acciones al dar click en los view
    *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
    }
    public void initComponents(){}
    public void onClick(){}
}
