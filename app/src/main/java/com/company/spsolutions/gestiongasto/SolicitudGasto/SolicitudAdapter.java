package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.R;

import java.util.List;

/**
 * Created by coralRodriguez on 01/04/2019
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.CardHolder> {
    List<ItemSolicitud> itemsSolicitud;
    Context contexto;

    public SolicitudAdapter(List<ItemSolicitud> itemsSolicitud, Context contexto) {
        this.itemsSolicitud = itemsSolicitud;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        ItemSolicitud itemSolicitud = itemsSolicitud.get(i);
        registroHolder.nombreTV.setText(itemSolicitud.nombre);
        registroHolder.puestoTV.setText(itemSolicitud.puesto);
        registroHolder.fechaTV.setText(itemSolicitud.fecha);
        registroHolder.dineroTV.setText(itemSolicitud.dineroSolicitado);
        if ((itemSolicitud.estatus == null)) {
            registroHolder.labelSolicitudTV.setVisibility(View.GONE);
        } else {
            registroHolder.labelSolicitudTV.setText(itemSolicitud.estatus);
        }
        registroHolder.registroCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto, "MANDAR A EDITAR" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsSolicitud.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_solicitudes, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView nombreTV;
        public TextView puestoTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public TextView labelSolicitudTV;
        public CardView registroCV;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.nombre_tv);
            puestoTV = card.findViewById(R.id.puesto_tv);
            fechaTV = card.findViewById(R.id.fecha_tv);
            dineroTV = card.findViewById(R.id.dinero_tv);
            labelSolicitudTV = card.findViewById(R.id.labelSolicitud_tv);
            registroCV = card.findViewById(R.id.solicitudCV);
        }
    }

    /*
     * 1. Mandar a la pantalla para editar una solicitud (crear intent)
     * 2. Pasar los datos a la nueva actividad
     */
    public void editSolicitud() {

    }

}