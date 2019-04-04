package com.company.spsolutions.gestiongasto.Informes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;

public class ResumenGastosActivity extends AppCompatActivity implements PresenterInforme.ResumenGastos {
    /* Esta clase es la encargada de soportar la vista de revision de gastos donde se vera todos los gastos
     * y un resumen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
    }

    /*
     * Inicializar componentes
     */
    public void initComponents() {
    }

    /*
    Mostrara el popUp para finalizar aprobacion
     */
    @Override
    public void showPopUp() {

    }
    /*
    * Hacer la logica del boton finalizar Aprobacion
     */
    public void finalizarAprobacion(){}
}
