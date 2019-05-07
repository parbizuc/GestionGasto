package com.company.spsolutions.gestiongasto.Gastos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.R;

import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.CardHolder> {
    List<Gasto> itemsGasto;
    Context contexto;
    String estado;

    public GastosAdapter(List<Gasto> itemsGasto, Context contexto) {
        this.itemsGasto = itemsGasto;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada registro
     * 2. Al dar click en algún registro se debera poder editar
     */
    @Override
    public void onBindViewHolder(final CardHolder registroHolder, final int i) {
        Gasto itemGasto = itemsGasto.get(i);
       // registroHolder.nombreTV.setText(itemGasto.nombre);
       // Glide.with(contexto).load(itemGasto.imagen).into(registroHolder.fotoIV);
        registroHolder.monedaTV.setText(itemGasto.getMonedaGasto());
        registroHolder.provedorTV.setText(itemGasto.getNombreProveedor());
        registroHolder.fechaTV.setText(itemGasto.getFechaGasto());
        registroHolder.dineroTV.setText(itemGasto.getMontoGasto());
        //registroHolder.labelTV.setText(itemGasto.getMonedaGasto());
        estado = itemGasto.getEstado();
        registroHolder.labelTV.setText(itemGasto.getEstado());
        registroHolder.gastoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto, "MANDAR A EDITAR" + i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsGasto.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gasto, viewGroup, false);
        return new CardHolder(itemView);
    }

    public void refreshGastos(List<Gasto> datos) {
        this.itemsGasto.clear();
        this.itemsGasto.addAll(datos);
        notifyDataSetChanged();
    }
    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView monedaTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public TextView labelTV;
        public CardView gastoCV;
        public TextView provedorTV;

        public CardHolder(View card) {
            super(card);
            monedaTV = card.findViewById(R.id.moneda_tv);
            fechaTV = card.findViewById(R.id.fechag_tv);
            dineroTV = card.findViewById(R.id.dinerog_tv);
            gastoCV = card.findViewById(R.id.gasto_cv);
            labelTV = card.findViewById(R.id.labelGasto_tv);
            provedorTV = card.findViewById(R.id.proveedor_tv);
        }
    }


}
