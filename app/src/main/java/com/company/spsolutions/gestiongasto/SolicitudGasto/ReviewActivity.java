package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;

public class ReviewActivity extends AppCompatActivity {

    Solicitud solicitud;
    TextView usuarioTV, fechaIniTV, fechaFinTV, motivoTV, descripcionTV, importeTV, centroCostosTV, estatusTV, fechaSolicitadoTV;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        usuario = Usuario.getInstance();
        solicitud = (Solicitud) getIntent().getSerializableExtra("solicitud");
        setData();
    }

    private void setData() {
        descripcionTV.setText(solicitud.getDescripcion());
        motivoTV.setText(solicitud.getMotivo());
        centroCostosTV.setText(solicitud.getCentro());
        importeTV.setText(solicitud.getImporte());
        fechaSolicitadoTV.setText(solicitud.getFechaEnviado());
        if (usuario.getRol().equals("usuario")) {
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


}
