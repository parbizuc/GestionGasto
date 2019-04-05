package com.company.spsolutions.gestiongasto.Informes;

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
 * Created by coralRodriguez on 29/03/2019.
 */
public class InformesAdapter extends RecyclerView.Adapter<InformesAdapter.CardHolder> {
    List<ItemInforme> itemInformes;
    Context contexto;

    public InformesAdapter(List<ItemInforme> itemsSolicitud, Context contexto) {
        this.itemInformes = itemsSolicitud;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada informe
     * 2. Al dar click en algún informe se debera poder editar
     * 3. Controlar el perfil del usuario y modificar el estatus por el creador de ese informe
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        ItemInforme itemInforme = itemInformes.get(i);
        registroHolder.nombreTV.setText(itemInforme.nombre);
        registroHolder.fechasTV.setText(itemInforme.fechas);
        registroHolder.dineroTotalTV.setText(itemInforme.dineroTotal);
        if ((itemInforme.estatus == null)) {
            registroHolder.labelInformeTV.setVisibility(View.GONE);
        } else {
            registroHolder.labelInformeTV.setText(itemInforme.estatus);
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
        return itemInformes.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_informe, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView nombreTV;
        public TextView fechasTV;
        public TextView dineroTotalTV;
        public TextView labelInformeTV;
        public CardView registroCV;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.nombrei_tv);
            fechasTV = card.findViewById(R.id.fechai_tv);
            dineroTotalTV = card.findViewById(R.id.dineroi_tv);
            labelInformeTV = card.findViewById(R.id.labelInforme_tv);
            registroCV = card.findViewById(R.id.informe_cv);
        }
    }

}
