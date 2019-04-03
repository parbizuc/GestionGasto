package com.company.spsolutions.gestiongasto.Informes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;
/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class AddInformeActivity extends AppCompatActivity implements PresenterInforme {
    /* Esta clase controla la activity para editar o agregar un nuevo informe de gastos
    * sendData envia los datos al presentador para que los envie al servicio
    * total se engarga de ir actualizando el total segun los gastos agregados en el informe
    * adelantoPrevio gestiona la logica cuando el combo es activado o desactivado
    *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinforme);
    }
    public void adelantoPrevio(){}
    public void total(){}
    public void sendData(){}
}
