package com.company.spsolutions.gestiongasto.Gastos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.CardHolder> {
    List<ItemGastos> itemsSolicitud;
    Context contexto;

    public GastosAdapter(List<ItemGastos> itemsSolicitud, Context contexto) {
        this.itemsSolicitud = itemsSolicitud;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        ItemGastos itemGasto = itemsSolicitud.get(i);
        registroHolder.nombreTV.setText(itemGasto.nombre);
        Picasso.get().load(itemGasto.imagen).into(registroHolder.fotoIV);
        registroHolder.fechaTV.setText(itemGasto.fecha);
        registroHolder.dineroTV.setText(itemGasto.dinero);
        registroHolder.labelTV.setVisibility(View.GONE);
        registroHolder.gastoCV.setOnClickListener(new View.OnClickListener() {
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
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gasto, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView nombreTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public ImageView fotoIV;
        public TextView labelTV;
        public CardView gastoCV;

        public CardHolder(View card) {
            super(card);
            fotoIV = card.findViewById(R.id.ticket_iv);
            nombreTV = card.findViewById(R.id.nombreg_tv);
            fechaTV = card.findViewById(R.id.fechag_tv);
            dineroTV = card.findViewById(R.id.dinerog_tv);
            gastoCV = card.findViewById(R.id.gasto_cv);
            labelTV = card.findViewById(R.id.labelGasto_tv);
        }
    }


}
