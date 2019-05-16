package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewActivity extends AppCompatActivity implements PresenterSolicitud, CuadrodeDialogo.FinalizarCuadro {

    CuadrodeDialogo cuadrodeDialogo;
    Solicitud solicitud;
    Context contexto;
    PresenterSolicitudImpl presenter;
    String fi, ff;
    TextView usuarioTV, fechaIniTV, fechaFinTV, motivoTV, descripcionTV, importeTV, centroCostosTV, estatusTV, fechaSolicitadoTV;
    Usuario usuario;
    Button rechazar_btn, aprobar_btn;
    String Tag = "HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterSolicitudImpl(ReviewActivity.this, this);
        contexto = this;
        setContentView(R.layout.activity_review);
        usuarioTV = findViewById(R.id.usuariors_tv);
        fechaIniTV = findViewById(R.id.fechaInirs_tv);
        fechaFinTV = findViewById(R.id.fechaFinrs_tv);
        motivoTV = findViewById(R.id.motivors_tv);
        descripcionTV = findViewById(R.id.descripcionrs_tv);
        importeTV = findViewById(R.id.importers_tv);
        centroCostosTV = findViewById(R.id.centrocostosrs_tv);
        fechaSolicitadoTV = findViewById(R.id.fechaSolicitadors_tv);
        estatusTV = findViewById(R.id.estatusrs_tv);
        fi = "";
        ff = "";
        rechazar_btn = findViewById(R.id.rechazar_btn);
        aprobar_btn = findViewById(R.id.aprobar_btn);
        usuario = Usuario.getInstance();
        solicitud = (Solicitud) getIntent().getSerializableExtra("solicitud");
        setData();
        setlistenersaprobar();
        setlistenerechazar();
    }

    private void setData() {

        descripcionTV.setText(solicitud.getDescripcion());
        motivoTV.setText(solicitud.getMotivo());
        centroCostosTV.setText(solicitud.getCentro());
        importeTV.setText(solicitud.getImporte());
        fi = solicitud.getFechaInicio();
        ff = solicitud.getFechaFin();
        fechaSolicitadoTV.setText(solicitud.getFechaEnviado());
        if (usuario.getRol().equals("usuario")) {
            estatusTV.setText(solicitud.getEstado());
            fechaIniTV.setText(solicitud.getFechaInicio());
            fechaFinTV.setText(solicitud.getFechaFin());
            rechazar_btn.setVisibility(View.GONE);
            aprobar_btn.setVisibility(View.GONE);
        } else if (usuario.getRol().equals("aprobador")) {
            estatusTV.setText(solicitud.getEstado());
            fechaIniTV.setText(solicitud.getFechaInicio());
            fechaFinTV.setText(solicitud.getFechaFin());
        } else {
            fechaIniTV.setVisibility(View.GONE);
            fechaFinTV.setVisibility(View.GONE);
            estatusTV.setVisibility(View.GONE);
        }
        usuarioTV.setText(solicitud.getNombreUsuario());
    }
    private String getDate() {
        String date;
        Date d = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDate.format(d);
        return date;
    }

    private void editSolicitudAprobar(){
       // estatusTV.setText("APROBADO");
        String descripcion = descripcionTV.getText().toString();
        String centro = centroCostosTV.getText().toString();
        String importe = importeTV.getText().toString();
        String motivo = motivoTV.getText().toString();
        String estatus = "APROBADO";
        Log.d(Tag, fi + " "+  ff +" "+  descripcion +" "+  centro +" "+  motivo +" "+  importe +" "+  "hola" +" "+  getDate() +" "+  estatus +" "+  solicitud.getId());

        //guardar a la bd
        presenter.editData(fi, ff,  descripcion , centro , motivo , importe ,solicitud.getFechaRegistro(),  getDate(), estatus, solicitud.getId());
        finish();

    }
    private void setlistenersaprobar() {
        aprobar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSolicitudAprobar();
            }
        });
    }
    private void setlistenerechazar() {
        rechazar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MensajeDialogo();
            }
        });
    }

    private void MensajeDialogo() {
       new CuadrodeDialogo(contexto, ReviewActivity.this);
    }

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

    @Override
    public void ResultadoCuadro(String Motivo) {
        String motivoRechazo = Motivo;
        String descripcion = descripcionTV.getText().toString();
        String centro = centroCostosTV.getText().toString();
        String importe = importeTV.getText().toString();
        String motivo = motivoTV.getText().toString();
        String estatus = "RECHAZADO";
        Log.d(Tag, fi + " "+  ff +" "+  descripcion +" "+  centro +" "+  motivo +" "+  importe +" "+  "hola" +" "+  getDate() +" "+  estatus +" "+  solicitud.getId());

        //guardar a la bd
        presenter.editDataRechazo(fi, ff,  descripcion , centro , motivo , importe ,solicitud.getFechaRegistro(),  getDate(), estatus, solicitud.getId(),motivoRechazo);
        finish();
    }
}

