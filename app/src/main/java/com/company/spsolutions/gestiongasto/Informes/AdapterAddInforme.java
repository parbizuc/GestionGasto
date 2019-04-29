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

import java.util.ArrayList;
import java.util.List;

import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 22/04/2019.
 */
public class AdapterAddInforme extends RecyclerView.Adapter<AdapterAddInforme.CardHolder> {
    List<Gasto> itemsGasto;
    Context contexto;
    PresenterInformeImpl presenter;
    List<Gasto> itemsSelect = new ArrayList<>();

    public AdapterAddInforme(List<Gasto> itemsGasto, Context contexto,PresenterInformeImpl presenter) {
        this.itemsGasto = itemsGasto;
        this.contexto = contexto;
        this.presenter = presenter;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        final Gasto itemGasto = itemsGasto.get(i);
        registroHolder.nombreTV.setText(itemGasto.getNombreProveedor());
        registroHolder.gastoCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registroHolder.gastoCB.isChecked()) {
                    itemsSelect.add(itemGasto);
                    presenter.total(itemGasto.getMontoGasto(),true);
                }else{
                    itemsSelect.remove(itemGasto);
                    presenter.total(itemGasto.getMontoGasto(),false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsGasto.size();
    }

    public List<Gasto> getItemsSelect() {
        return itemsSelect;
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
