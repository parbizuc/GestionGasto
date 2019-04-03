package com.company.spsolutions.gestiongasto.Gastos;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.spsolutions.gestiongasto.R;

public class AddGastoActivity extends AppCompatActivity implements PresenterGastos {
    /* AddGastosActivity es la activity mostrada al querer añadir o editar un gasto
    * initComponents se encarga de inicializar los views
    * setlistener controla los listeners a cada view que lo necesite
    * loadImage se encarga de manejar la carga de la imagen asi como el zoom
    * sendData envia los datos al presentador
    * comboRecurrente maneja toda la lógica en caso de que exista cargos recurrentes
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgasto);
    }

    public void initComponents(){}
    public void setListeners(){}
    public void loadImage(){}
    public void sendData(){}
    public void comboRecurrente(){}

    @Override
    public void displayError() {

    }
}
