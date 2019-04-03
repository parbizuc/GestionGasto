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
    public void initComponents(){}
    public void guardarSolicitud(){}
    public void mandarRevision(){}

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
