package com.company.spsolutions.gestiongasto.Informes;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    RecyclerView recyclerGastos;
    AdapterAddInforme sAdapter;
    LinearLayoutManager layoutManager;
    List<Gasto> datos = new ArrayList<>();
    EditText fechaIniET, fechaFinET, tituloET, notasET;
    TextView total;
    Button grabarBTN, enviarBTN;
    PresenterInformeImpl presenter;
    ImageButton finiIB, ffinIB;
    Spinner adelantoSP;
    String fi, ff;
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinforme);
        initComponents();
        setListeners();
    }

    public void initComponents() {
        prueba();
        presenter = new PresenterInformeImpl(AddInformeActivity.this, this);
        fechaFinET = findViewById(R.id.fechaFinai_et);
        fechaIniET = findViewById(R.id.fechaIniai_et);
        notasET = findViewById(R.id.notaai_et);
        tituloET = findViewById(R.id.tituloai_et);
        grabarBTN = findViewById(R.id.guardarai_btn);
        enviarBTN = findViewById(R.id.enviarai_btn);
        finiIB = findViewById(R.id.fechaIniai_ib);
        ffinIB = findViewById(R.id.fechaFinai_ib);
        total = findViewById(R.id.totalai_tv);
        fi = "";
        ff = "";
        recyclerGastos = findViewById(R.id.listagai_rv);
        recyclerGastos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerGastos.setLayoutManager(layoutManager);
        sAdapter = new AdapterAddInforme(datos, getApplicationContext());
        recyclerGastos.setAdapter(sAdapter);

        adelantoSP = findViewById(R.id.adelantoai_sp);
        ArrayAdapter<Gasto> adapter = new ArrayAdapter<Gasto>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, datos);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        adelantoSP.setAdapter(adapter);
    }

    public void prueba (){
        datos.add(new Gasto("nombre 1"));
        datos.add(new Gasto("nombre 2"));
        datos.add(new Gasto("nombre 3"));
        datos.add(new Gasto("nombre 4"));
        datos.add(new Gasto("nombre 5"));
    }


    public void setListeners() {
        finiIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker(fechaIniET);
            }
        });

        ffinIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker(fechaFinET);
            }
        });
    }

    private void showDataPicker(final EditText fechaET) {
        DatePickerDialog recogerFecha = new DatePickerDialog(AddInformeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //0 = enero
                final int mesActual = month + 1;
                //Antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
                if (fechaET.getId() == R.id.fechaIniai_et) {
                    fi = anio + "-" + mesFormateado + "-" + diaFormateado;
                } else {
                    ff = anio + "-" + mesFormateado + "-" + diaFormateado;
                }
                fechaET.setText(diaFormateado + "/" + mesFormateado + "/" + anio);
            }
        }, anio, mes, dia);
        recogerFecha.show();
    }

    public void adelantoPrevio() {
    }

    public void total() {
    }

    public void sendData() {
    }
}
