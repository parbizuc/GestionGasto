package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.company.spsolutions.gestiongasto.R;

public class EvaluarSolicitud extends AppCompatActivity implements PresenterSolicitud {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluarsolicitud);
    }
    /*
    * Inicializar los componentes textview y botones
     */
    public void initComponents(){}
    /*
    * Agregar las acciones de los botones aprobar y rechazar
     */
    public void setListeners(){}
    /*
    * Obtiene los datos del presentador y llena la informacion en la vista
    *  Oculta los botones si la solicitud ya esta aprobada o rechazada
     */
    public void getInfo(){}

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setError(Integer type, String texto) {

    }
}
