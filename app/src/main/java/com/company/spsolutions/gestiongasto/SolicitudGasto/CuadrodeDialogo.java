package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;

import org.w3c.dom.Text;

public class CuadrodeDialogo {

    public interface FinalizarCuadro
    {
        void ResultadoCuadro(String Motivo);
    }
    private FinalizarCuadro interfaz;


    final EditText ingresar;
    ReviewActivity reviewActivity;

    public CuadrodeDialogo(Context contexto, FinalizarCuadro actividad)
    {
        interfaz = actividad;

        final Dialog dialogo  = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.item_cuadrodialogo);

        ingresar = (EditText) dialogo.findViewById(R.id.Ingresar);
        final TextView cancelarBTN = (TextView) dialogo.findViewById(R.id.btn_cancelar);
        final TextView terminarBTN = (TextView) dialogo.findViewById(R.id.btn_terminar);

        cancelarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  dialogo.dismiss();
            }
        });

        terminarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoCuadro(ingresar.getText().toString());
                dialogo.dismiss();

            }
        });

        dialogo.show();
    }
}
