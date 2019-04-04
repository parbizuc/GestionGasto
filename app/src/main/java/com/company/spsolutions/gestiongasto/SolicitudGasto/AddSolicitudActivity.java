package com.company.spsolutions.gestiongasto.SolicitudGasto;
/**
 * Created by coralRodriguez on 28/03/19.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;

public class AddSolicitudActivity extends AppCompatActivity implements PresenterSolicitud {
    /* AddSolicitudActivity es el activity para controlar cuando el usuario quiere añadir o editar una solicitd
    * el método initComponents es el responsable de instanciar los componentes y sus listeners
    * guardarSolicitud es el método que sera llamado cuando el usuario quiera registrar su solicitud
    * el método mandarRevision se encarga de mandar la petición del usuario a revisar por el aprobador
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsolicitud);
    }
    /*
    * Crear instancia de los elementos
     */
    public void initComponents(){}

    /*
    * 1. Colocar los listeners a los botones de grabar y registrar
    * 2. Colocar los listeners al datepicker
     */
    public void setlisteners(){}

    /*
    * 1. Crear la logica para guardar una solicitud
     */
    public void guardarSolicitud(){}

    /*
    * Crear logica para mandar a revisar la solicirtud
     */
    public void mandarRevision(){}

    /*
    * Lanzar pop up
     */
    public void popUp(){}

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void displayLabel() {

    }

}
