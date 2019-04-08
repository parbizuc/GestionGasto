package com.company.spsolutions.gestiongasto.Gastos;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.company.spsolutions.gestiongasto.R;

public class AddGastoActivity extends AppCompatActivity implements PresenterGastos {
    /* AddGastosActivity es la activity mostrada al querer añadir o editar un gasto
     * initComponents se encarga de inicializar los views
     * setlistener controla los listeners a cada view que lo necesite
     * loadImage se encarga de manejar la carga de la imagen asi como el zoom
     * sendData envia los datos al presentador
     * comboRecurrente maneja toda la lógica en caso de que exista cargos recurrentes
     */
    Spinner monedaSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgasto);
        initComponents();

    }

    /*
     * Crear instancia  de los checkbox,edittext,textview,etc
     */
    public void initComponents() {
        //Spinner MONEDAS
        monedaSP = findViewById(R.id.moneda_sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.monedas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedaSP.setAdapter(adapter);
    }

    /*
     * Agregar los listeners a los componentes
     * Se recomienda hacer un metodo por cada lógica de un listener
     */

    public void setListeners() {
    }

    /*
     * Agregar la logica para cargar la imagen e interactuar con ella (zoom)
     */
    public void loadImage() {
    }

    /*
     * Implementar la logica para el boton de guardar
     * Este boton debe mandar al presentador el nuevo gasto y notificar a la vista
     */
    public void sendData() {
    }

    /*
     *Realizar la lógica que requiera el checkbox para gastos recurrentes
     */
    public void comboRecurrente() {
    }

    @Override
    public void displayError() {

    }
}
