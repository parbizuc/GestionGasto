package com.company.spsolutions.gestiongasto.Gastos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.CardHolder> {
    List<Gasto> itemsGasto;
    Context contexto;
    String estado;
    private GastoService service;

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
        final Gasto itemGasto = itemsGasto.get(i);
       // registroHolder.nombreTV.setText(itemGasto.nombre);
       // Glide.with(contexto).load(itemGasto.imagen).into(registroHolder.fotoIV);
        //registroHolder.monedaTV.setText(itemGasto.getMonedaGasto());
        if(itemGasto.getNombreProveedor().length()<15){
            registroHolder.provedorTV.setText(itemGasto.getNombreProveedor());
        }else {
            registroHolder.provedorTV.setText(new String(itemGasto.getNombreProveedor()).substring(0, 15));
        }
        registroHolder.categoriaTV.setText(itemGasto.getCategoria());
        registroHolder.fechaTV.setText(itemGasto.getFechaGasto());
        registroHolder.dineroTV.setText(itemGasto.getMontoGasto());
        //registroHolder.labelTV.setText(itemGasto.getMonedaGasto());
        //estado = itemGasto.getEstado();
        registroHolder.labelTV.setText(itemGasto.getMonedaGasto());
        //--Editar Gasto
        registroHolder.gastoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(contexto, "MANDAR A EDITAR" + i, Toast.LENGTH_SHORT).show();
                Intent editar = new Intent(contexto, AddGastoActivity.class);
                editar.putExtra("gasto",itemGasto);
                contexto.startActivity(editar);
            }
        });

        System.out.println("itemGasto.getEstado(): " + itemGasto.getEstado() + "");

        if (itemGasto.getEstado()!= null && itemGasto.getEstado().equals("INFORMADO")) {
            System.out.println("Ocultar boton");
            registroHolder.borrarGasto_btn.setVisibility(View.GONE);
        }else{
            registroHolder.borrarGasto_btn.setOnClickListener(new View.OnClickListener() {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                System.out.println("Si borrar registro " + itemGasto.getId());
                                service = new GastoService();
                                CollectionReference cr = service.connectGastosDB();
                                System.out.println("service.connect(): " + cr.document(itemGasto.getId()).delete());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                System.out.println("no borrar registro " + itemGasto.getId());
                                break;
                        }
                    }
                };

                public void onClick(View v) {
                    // your handler code here
                    System.out.println("Boton borrar gasto");
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setMessage("¿Esta seguro de elimiar este registro?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
            );
        }
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
        //public TextView monedaTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public TextView labelTV;
        public CardView gastoCV;
        public TextView provedorTV;
        public TextView categoriaTV;
        public Button borrarGasto_btn;

        public CardHolder(View card) {
            super(card);
            //monedaTV = card.findViewById(R.id.moneda_tv);
            fechaTV = card.findViewById(R.id.fechag_tv);
            dineroTV = card.findViewById(R.id.dinerog_tv);
            gastoCV = card.findViewById(R.id.gasto_cv);
            labelTV = card.findViewById(R.id.labelGasto_tv);
            provedorTV = card.findViewById(R.id.proveedor_tv);
            categoriaTV = card.findViewById(R.id.categoria_tv);
            borrarGasto_btn = card.findViewById(R.id.borrargasto_btn);
        }
    }


}
