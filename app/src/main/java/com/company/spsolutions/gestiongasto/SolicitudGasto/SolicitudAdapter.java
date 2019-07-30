package com.company.spsolutions.gestiongasto.SolicitudGasto;

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

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;
import java.text.DecimalFormat;

/**
 * Created by coralRodriguez on 01/04/2019
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.CardHolder> {
    List<Solicitud> itemsSolicitud;
    Context contexto;
    DecimalFormat formateador = new DecimalFormat("###,###.##");
    private SolicitudService service;

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
        final Solicitud itemSolicitud = itemsSolicitud.get(i);
        registroHolder.nombreTV.setText(itemSolicitud.getNombreUsuario());
        if (itemSolicitud.getMotivo().length() > 39) {
            registroHolder.motivorsTV.setText(itemSolicitud.getMotivo().substring(0, 36) + "...");
        } else {
            registroHolder.motivorsTV.setText(itemSolicitud.getMotivo());
        }
        registroHolder.fechaTV.setText(itemSolicitud.getFechaInicio());
        registroHolder.fechaIITV.setText(itemSolicitud.getFechaFin());
        System.out.println("Importe con formato ->" + formateador.format(Integer.parseInt(itemSolicitud.getImporte())));
        String montoFormato = formateador.format(Integer.parseInt(itemSolicitud.getImporte()));
        if (!montoFormato.contains(".")) {
            montoFormato = montoFormato + ".00";
        }
        registroHolder.dineroTV.setText(montoFormato);//("$" + itemSolicitud.getImporte());
        if (itemSolicitud.getMoneda().equals("peso")) {
            registroHolder.labelSolicitudTV.setText("MXN");
        } else {
            registroHolder.labelSolicitudTV.setText(itemSolicitud.getMoneda());
        }

        /*
        if ((itemSolicitud.getEstado() == null)) {
            registroHolder.labelSolicitudTV.setVisibility(View.GONE);
        } else {
            registroHolder.labelSolicitudTV.setText(itemSolicitud.getEstado());
        }*/
        registroHolder.registroCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((itemSolicitud.getEstado() == null)) {
                    Intent editar = new Intent(contexto, AddSolicitudActivity.class);
                    editar.putExtra("solicitud", itemSolicitud);
                    contexto.startActivity(editar);
                } else {
                    Intent editar = new Intent(contexto, ReviewActivity.class);
                    editar.putExtra("solicitud", itemSolicitud);
                    contexto.startActivity(editar);
                }
            }
        });
        System.out.println("itemSolicitud.getEstado(): " + itemSolicitud.getEstado() + "");

        if (itemSolicitud.getEstado()!= null && itemSolicitud.getEstado().equals("POR APROBAR")) {
            System.out.println("Ocultar boton");
            registroHolder.borrarSol_btn.setVisibility(View.GONE);
        }else{
        registroHolder.borrarSol_btn.setOnClickListener(new View.OnClickListener() {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        System.out.println("Si borrar registro " + itemSolicitud.getId());
                        service = new SolicitudService();
                        CollectionReference cr = service.connect();
                        System.out.println("service.connect(): " + cr.document(itemSolicitud.getId()).delete());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                         //No button clicked
                         System.out.println("no borrar registro " + itemSolicitud.getId());
                         break;
                }
                }
             };

            public void onClick(View v) {
                // your handler code here
                System.out.println("Boton borrar solicitud");
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
        return itemsSolicitud.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_solicitudes, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView nombreTV;
        public TextView motivorsTV;
        public TextView fechaTV;
        public TextView dineroTV;
        public TextView labelSolicitudTV;
        public CardView registroCV;
        public TextView fechaIITV;
        public Button borrarSol_btn;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.nombre_tv);
            motivorsTV = card.findViewById(R.id.motivors_tv);
            fechaTV = card.findViewById(R.id.fecha_tv);
            dineroTV = card.findViewById(R.id.dinero_tv);
            labelSolicitudTV = card.findViewById(R.id.labelSolicitud_tv);
            registroCV = card.findViewById(R.id.solicitudCV);
            fechaIITV = card.findViewById(R.id.fechaII_tv);
            borrarSol_btn = card.findViewById(R.id.borrarSol_btn);
        }
    }
    public void refreshSolicitudes(List<Solicitud> solicitudes) {
        this.itemsSolicitud.clear();
        this.itemsSolicitud.addAll(solicitudes);
        notifyDataSetChanged();
    }

}