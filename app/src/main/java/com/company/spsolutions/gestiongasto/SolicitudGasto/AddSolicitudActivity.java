package com.company.spsolutions.gestiongasto.SolicitudGasto;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSolicitudActivity extends AppCompatActivity implements PresenterSolicitud {
    /* AddSolicitudActivity es el activity para controlar cuando el usuario quiere añadir o editar una solicitd
     * el método initComponents es el responsable de instanciar los componentes y sus listeners
     * guardarSolicitud es el método que sera llamado cuando el usuario quiera registrar su solicitud
     * el método mandarRevision se encarga de mandar la petición del usuario a revisar por el aprobador
     */
    EditText fechaIniET, fechaFinET, motivoET, descripcionET, importeET, centroCostosET;
    Solicitud solicitud;
    TextView usuarioTV;
    Button grabarBTN, enviarBTN;
    Usuario usuario;
    Empresa empresa;
    PresenterSolicitudImpl presenter;
    ImageButton finiIB, ffinIB;
    String fi, ff;
    Boolean isEditar;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsolicitud);
        initComponents();
        setlisteners();
    }

    /*
     * Crear instancia de los elementos
     */
    private void initComponents() {
        presenter = new PresenterSolicitudImpl(AddSolicitudActivity.this, this);
        isEditar = getIntent().hasExtra("solicitud");
        fechaFinET = findViewById(R.id.fechaFinas_et);
        fechaIniET = findViewById(R.id.fechaInias_et);
        motivoET = findViewById(R.id.motivo_et);
        descripcionET = findViewById(R.id.descripcionas_et);
        importeET = findViewById(R.id.importeas_et);
        centroCostosET = findViewById(R.id.centrocostos_et);
        usuarioTV = findViewById(R.id.usuarioas_tv);
        grabarBTN = findViewById(R.id.grabar_btn);
        enviarBTN = findViewById(R.id.enviara_btn);
        finiIB = findViewById(R.id.fechaInias_ib);
        ffinIB = findViewById(R.id.fechaFinas_ib);
        fi = "";
        ff = "";
        usuario = Usuario.getInstance();
        empresa = Empresa.getInstance();
        if (isEditar) {
            solicitud = (Solicitud) getIntent().getSerializableExtra("solicitud");
            descripcionET.setText(solicitud.getDescripcion());
            motivoET.setText(solicitud.getMotivo());
            centroCostosET.setText(solicitud.getCentro());
            importeET.setText(solicitud.getImporte());
            fechaIniET.setText(convertDate(solicitud.getFechaInicio()));
            fechaFinET.setText(convertDate(solicitud.getFechaFin()));
            fi = solicitud.getFechaInicio();
            ff = solicitud.getFechaFin();
        }
        usuarioTV.setText(usuario.getNombre());
    }

    /*
     * 1. Colocar los listeners a los botones de grabar y registrar
     * 2. Colocar los listeners al datepicker
     */
    private void setlisteners() {
        enviarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditar) {
                    editSolicitud(false);
                } else {
                    addSolicitud(false);
                }
            }
        });
        grabarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditar) {
                    editSolicitud(true);
                } else {
                    addSolicitud(true);
                }
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
    }


    /*
     * 1. Crear la logica para guardar una solicitud
     * 2. Crear logica para mandar a revisar la solicitud
     */
    private void addSolicitud(Boolean isRegistrada) {
        String descripcion = descripcionET.getText().toString();
        String motivo = motivoET.getText().toString();
        String centro = centroCostosET.getText().toString();
        String importe = importeET.getText().toString();
        if (isRegistrada) {
            presenter.sendData(fi, ff, descripcion, centro, motivo, empresa.getMoneda(), importe, getDate(), null, null, usuario.getNombre(), usuario.getId(), usuario.getIdEmpresa(), usuario.getPais());
        } else {
            presenter.sendData(fi, ff, descripcion, centro, motivo, empresa.getMoneda(), importe, null, getDate(), "Por aprobar", usuario.getNombre(), usuario.getId(), usuario.getIdEmpresa(), usuario.getPais());
        }

    }

    private void editSolicitud(Boolean isRegistrada) {
        String descripcion = descripcionET.getText().toString();
        String motivo = motivoET.getText().toString();
        String centro = centroCostosET.getText().toString();
        String importe = importeET.getText().toString();
        if (isRegistrada) {
            presenter.editData(fi, ff, descripcion, centro, motivo, importe, getDate() ,null, null, solicitud.getId());
        } else {
            presenter.editData(fi, ff, descripcion, centro, motivo, importe, solicitud.getFechaRegistro(), getDate(), "Por aprobar", solicitud.getId());
        }

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

    private String getDate() {
        String date;
        Date d = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDate.format(d);
        return date;
    }

    private void showDataPicker(final EditText fechaET) {
        DatePickerDialog recogerFecha = new DatePickerDialog(AddSolicitudActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //0 = enero
                final int mesActual = month + 1;
                //Antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
                if (fechaET.getId() == R.id.fechaInias_et) {
                    fi = anio + "-" + mesFormateado + "-" + diaFormateado;
                } else {
                    ff = anio + "-" + mesFormateado + "-" + diaFormateado;
                }
                fechaET.setText(diaFormateado + "/" + mesFormateado + "/" + anio);
            }
        }, anio, mes, dia);
        recogerFecha.show();
    }

    /*
     * Lanzar pop up
     */
    private void popUp() {
    }


    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void displayLabel(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddSolicitudActivity.this);
        builder.setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //algo
            }
        });
        builder.show();

    }

    @Override
    public void changeActivity() {
        finish();
        /*Intent returnA = new Intent(AddSolicitudActivity.this,SolicitudActivity.class);
        startActivity(returnA);*/
    }

}
