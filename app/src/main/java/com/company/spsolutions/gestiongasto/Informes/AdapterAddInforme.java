package com.company.spsolutions.gestiongasto.Informes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Gasto;

import java.util.List;

import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 22/04/2019.
 */
public class AdapterAddInforme extends RecyclerView.Adapter<AdapterAddInforme.CardHolder> {
    List<Gasto> itemsGasto;
    Context contexto;

    public AdapterAddInforme(List<Gasto> itemsGasto, Context contexto) {
        this.itemsGasto = itemsGasto;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(CardHolder registroHolder, final int i) {
        Gasto itemGasto = itemsGasto.get(i);
        registroHolder.nombreTV.setText(itemGasto.getNombre());
        registroHolder.gastoCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsGasto.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_checkgasto, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView nombreTV;
        public CheckBox gastoCB;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.gtitleai_tv);
            gastoCB = card.findViewById(R.id.checkgasto_cb);
        }
    }

    public void refreshSolicitudes(List<Gasto> gastos) {
        this.itemsGasto.clear();
        this.itemsGasto.addAll(gastos);
        notifyDataSetChanged();
    }


}
