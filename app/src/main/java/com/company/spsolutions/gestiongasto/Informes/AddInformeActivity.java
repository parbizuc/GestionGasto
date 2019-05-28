package com.company.spsolutions.gestiongasto.Informes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.Modelos.GastoInforme;
import com.company.spsolutions.gestiongasto.Modelos.Informe;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

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
    GastoInforme gastoInforme;
    Informe informe;
    AdapterAddInforme iAdapter;
    ArrayAdapter<Solicitud> adaptersp;
    LinearLayoutManager layoutManager;
    List<Gasto> gastos = new ArrayList<>();
    List<Solicitud> solicitudes = new ArrayList<>();
    EditText fechaIniET, fechaFinET, tituloET, notasET;
    TextView totalTV, empresaTV, rolTV, usuarioTV, textGTV;
    Button guardarBTN, enviarBTN;
    PresenterInformeImpl presenter;
    ImageButton finiIB, ffinIB;
    Spinner adelantoSP;
    Boolean isEditar;
    String fi, ff;
    String TAG = "HOLAAAAAAAAAAAAAAAAAAAAAAA";
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
        getSupportActionBar().hide();
        presenter = new PresenterInformeImpl(AddInformeActivity.this, this);
        isEditar = getIntent().hasExtra("informe");
        fechaFinET = findViewById(R.id.fechaFinai_et);
        fechaIniET = findViewById(R.id.fechaIniai_et);
        notasET = findViewById(R.id.notaai_et);
        tituloET = findViewById(R.id.tituloai_et);
        guardarBTN = findViewById(R.id.guardarai_btn);
        enviarBTN = findViewById(R.id.enviarai_btn);
        finiIB = findViewById(R.id.fechaIniai_ib);
        ffinIB = findViewById(R.id.fechaFinai_ib);
        totalTV = findViewById(R.id.totalai_tv);
        rolTV = findViewById(R.id.rolai_tv);
        textGTV = findViewById(R.id.textgastos_TV);
        usuarioTV = findViewById(R.id.usuarioai_tv);
        empresaTV = findViewById(R.id.empresaai_tv);
        adelantoSP = findViewById(R.id.adelantoai_sp);
        recyclerGastos = findViewById(R.id.listagai_rv);
        fi = "";
        ff = "";
        getDatos();
        recyclerGastos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerGastos.setLayoutManager(layoutManager);
        if(isEditar){
            informe = (Informe) getIntent().getSerializableExtra("informe");
            tituloET.setText(informe.getTitulo());
            fechaIniET.setText(convertDate(informe.getFechaInicio()));
            fechaFinET.setText(convertDate(informe.getFechaFin()));
            notasET.setText(informe.getComentario());
            totalTV.setText(informe.getMontoInforme());
            fi = informe.getFechaInicio();
            ff = informe.getFechaFin();
            //adelantoSP
        }
        adelantoSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    adelantoSP.setPrompt(solicitudes.get(position).getMotivo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adelantoSP.setPrompt("Selecciona el adelanto relacionado");
            }
        });
        empresaTV.setText(Empresa.getInstance().getNombre());
        usuarioTV.setText(Usuario.getInstance().getNombre());
        rolTV.setText(Usuario.getInstance().getRol());
    }

    private void getDatos() {
        presenter.searchGastos().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                gastos.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    Gasto gasto = document.toObject(Gasto.class);
                    gastos.add(gasto);
                }
                iAdapter = new AdapterAddInforme(gastos, getApplicationContext(), presenter);
                recyclerGastos.setAdapter(iAdapter);
                ViewCompat.setNestedScrollingEnabled(recyclerGastos, false);

            }
        });
        presenter.searchAdelantos().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                solicitudes.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    Solicitud motivo = document.toObject(Solicitud.class);
                    solicitudes.add(motivo);
                }
                solicitudes.add(0, new Solicitud());
                solicitudes.get(0).setMotivo("Sin Anticipo");
                adaptersp = new ArrayAdapter<Solicitud>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, solicitudes);
                adaptersp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adelantoSP.setGravity(View.TEXT_ALIGNMENT_CENTER);
                adelantoSP.setAdapter(adaptersp);
            }
        });

    }

    public void setListeners() {
        fechaIniET.setFocusable(false);
        fechaFinET.setFocusable(false);
        fechaFinET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker(fechaFinET);
            }
        });
        fechaIniET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker(fechaIniET);
            }
        });
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
        notasET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notasET.isFocused()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    notasET.clearFocus();
                }
            }
        });
        guardarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isEditar){
                sendData(false);
               }
               else{
                   editInfomre(false);}
            }

        });
        enviarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditar){
                sendData(true);
                }
                else{
                    editInfomre(true);}

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
    public void sendData(Boolean isEnviada) {
        String titulo = tituloET.getText().toString();
        String monto = totalTV.getText().toString();
        Solicitud adelanto = null;
        if (adelantoSP.getSelectedItemPosition() != 0) {
            adelanto = (Solicitud) adelantoSP.getSelectedItem();
        }
        String nota = notasET.getText().toString();
        if (isEnviada)
            presenter.addInforme(titulo, fi, ff, nota, monto, null, getDate(), "ENVIADO", adelanto, iAdapter.getItemsSelect());
        else
            presenter.addInforme(titulo, fi, ff, nota, monto, getDate(), null, "REGISTRADO", adelanto, iAdapter.getItemsSelect());
    }

    private void editInfomre(Boolean isEnviada) {
        String titulo = tituloET.getText().toString();
        String monto = totalTV.getText().toString();
        String nota = notasET.getText().toString();
        String fechaI = fechaIniET.getText().toString();
        String fechaF = fechaFinET.getText().toString();
        Solicitud adelanto = null;
       /* if (adelantoSP.getSelectedItemPosition() != 0) {
            adelanto = (Solicitud) adelantoSP.getSelectedItem();
        }*/

        if (isEnviada) {
            presenter.editData(titulo,fechaI,fechaF,nota,monto,informe.getFechaRegistro(),informe.getFechaEnvio(),"ENVIADO", iAdapter.getItemsSelect(), informe.getId());
           // Toast.makeText(this,iAdapter.getItemsSelect().toString() , Toast.LENGTH_SHORT).show();
        } else {
            presenter.editData(titulo,fechaI,fechaF,nota,monto,informe.getFechaRegistro(),informe.getFechaEnvio(),"REGISTRADO",iAdapter.getItemsSelect(),  informe.getId());

        }

    }

    private String getDate() {
        String date;
        Date d = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDate.format(d);
        return date;
    }

    private String convertDate(String fecha) {
        String newDate;
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
        newDate = format.format(date1);
        return newDate;

    }

    @Override
    public String getTotal() {
        return totalTV.getText().toString();
    }

    @Override
    public void changeTotal(String total) {
        totalTV.setText(total);
    }

    @Override
    public void setError(Integer type, String texto) {
        switch (type) {
            case 0:
                fechaIniET.setError(texto);
                break;
            case 1:
                fechaFinET.setError(texto);
                break;
            case 2:
                tituloET.setError(texto);
                break;
            case 3:
                textGTV.setError(texto);
                break;
            case 4:
                AlertDialog.Builder builder = new AlertDialog.Builder(AddInformeActivity.this);
                builder.setMessage(texto).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //algo
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }


    @Override
    public void changeActivity() {
        finish();
    }
}
