package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by coralRodriguez on 01/04/2019
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.CardHolder> {
    List<Solicitud> itemsSolicitud;
    Context contexto;

    public SolicitudAdapter(List<Solicitud> itemsSolicitud, Context contexto) {
        this.itemsSolicitud = itemsSolicitud;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        Solicitud itemSolicitud = itemsSolicitud.get(i);
        registroHolder.nombreTV.setText(itemSolicitud.getNombreUsuario());
        registroHolder.descripcionTV.setText(itemSolicitud.getDescripción());
        registroHolder.fechaTV.setText(itemSolicitud.getFechaInicio());
        registroHolder.dineroTV.setText(itemSolicitud.getImporte());
        if ((itemSolicitud.getEstado() == null)) {
            registroHolder.labelSolicitudTV.setVisibility(View.GONE);
        } else {
            registroHolder.labelSolicitudTV.setText(itemSolicitud.getEstado());
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
        public TextView descripcionTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public TextView labelSolicitudTV;
        public CardView registroCV;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.nombre_tv);
            descripcionTV = card.findViewById(R.id.descripcion_tv);
            fechaTV = card.findViewById(R.id.fecha_tv);
            dineroTV = card.findViewById(R.id.dinero_tv);
            labelSolicitudTV = card.findViewById(R.id.labelSolicitud_tv);
            registroCV = card.findViewById(R.id.solicitudCV);
        }
    }

    public void refreshSolicitudes(List<Solicitud> solicitudes) {
        this.itemsSolicitud.clear();
        this.itemsSolicitud.addAll(solicitudes);
        notifyDataSetChanged();
    }


    /*
     * 1. Mandar a la pantalla para editar una solicitud (crear intent)
     * 2. Pasar los datos a la nueva actividad
     */
    public void editSolicitud() {

    }

}