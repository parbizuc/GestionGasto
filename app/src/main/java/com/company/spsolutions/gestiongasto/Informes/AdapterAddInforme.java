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
        if(itemGasto.getNombreProveedor().length()>25){
            registroHolder.nombreTV.setText(itemGasto.getNombreProveedor().substring(0,25));
        }else {
            registroHolder.nombreTV.setText(itemGasto.getNombreProveedor());
        }
        if(itemGasto.getEstado().equals("REGISTRADO")) {
            registroHolder.gastoCB.setChecked(false);
        }
        if(itemGasto.getEstado().equals("INFORMADO")){
            registroHolder.gastoCB.setChecked(true);
            itemsSelect.add(itemGasto);
        }
        if(itemGasto.getEstado().equals("ENVIADO")){
            registroHolder.gastoCB.setChecked(true);
            registroHolder.gastoCB.setEnabled(false);
        }
        registroHolder.montoChecTV.setText("$"+itemGasto.getMontoGasto());
        System.out.println("nombre provedor -->"+itemGasto.getNombreProveedor()+"");
        System.out.println("itemGasto.getEstado() -->"+itemGasto.getEstado()+"");
            registroHolder.gastoCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (registroHolder.gastoCB.isChecked()) {
                        System.out.println("Valor Cambiado a true");
                        itemGasto.setEstado("REGISTRADO");
                        if(itemsSelect.contains(itemGasto)){
                            itemsSelect.remove(itemGasto);
                        }
                        itemGasto.setEstado("INFORMADO");
                        itemsSelect.add(itemGasto);
                        presenter.total(itemGasto.getMontoGasto(),true);
                    }else{
                        System.out.println("Valor Cambiado a false");
                        itemGasto.setEstado("INFORMADO");
                        if(itemsSelect.contains(itemGasto)){
                            itemsSelect.remove(itemGasto);
                        }
                        itemGasto.setEstado("REGISTRADO");
                        itemsSelect.add(itemGasto);
                        presenter.total(itemGasto.getMontoGasto(),false);
                    }
                    System.out.println("itemsSelect.toArray()-> "+itemsSelect.toArray());
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
        public TextView montoChecTV;
        public CheckBox gastoCB;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.gtitleai_tv);
            gastoCB = card.findViewById(R.id.checkgasto_cb);
            montoChecTV=card.findViewById(R.id.montoChec_tv);
        }
    }

    public void refreshSolicitudes(List<Gasto> gastos) {
        this.itemsGasto.clear();
        this.itemsGasto.addAll(gastos);
        notifyDataSetChanged();
    }


}
