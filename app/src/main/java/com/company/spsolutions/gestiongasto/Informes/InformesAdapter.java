package com.company.spsolutions.gestiongasto.Informes;

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
import com.company.spsolutions.gestiongasto.Modelos.Informe;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.AddSolicitudActivity;
import com.company.spsolutions.gestiongasto.SolicitudGasto.ReviewActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class InformesAdapter extends RecyclerView.Adapter<InformesAdapter.CardHolder> {
    List<Informe> itemInformes;
    Context contexto;
    public InformeService service;

    public InformesAdapter(List<Informe> itemsSolicitud, Context contexto) {
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
       System.out.println("onBindViewHolder start");
       final Informe itemInforme = itemInformes.get(i);
        System.out.println("00 itemInforme: "+itemInforme+"");
        registroHolder.nombreTV.setText(itemInforme.getTitulo());
        registroHolder.fechasTV.setText(itemInforme.getFechaInicio());
        registroHolder.fechafTV.setText(itemInforme.getFechaFin());
        registroHolder.dineroTotalTV.setText(itemInforme.getMontoInforme());
        registroHolder.nusuarioTV.setText(itemInforme.getNombreUsuario());
        if(itemInforme.getMonedaInforme().equals("peso")){
         registroHolder.monedaLabelTV.setText("MXN");
        }
        //registroHolder.labelInformeTV.setText(itemInforme.getEstado());

        registroHolder.registroCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((itemInforme.getEstado() != null)) {
                    Intent editar = new Intent(contexto, AddInformeActivity.class);
                    System.out.println("01 itemInforme: "+itemInforme);
                    editar.putExtra("informe",itemInforme);
                    contexto.startActivity(editar);
                }
            }
        });

        System.out.println("itemInforme.getEstado(): " + itemInforme.getEstado() + "");

        if (itemInforme.getEstado()!= null && itemInforme.getEstado().equals("ENVIADO")) {
            System.out.println("Ocultar boton");
            registroHolder.borrarInf_btn.setVisibility(View.GONE);
        }else{
            registroHolder.borrarInf_btn.setOnClickListener(new View.OnClickListener() {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                System.out.println("Si borrar registro " + itemInforme.getId());
                                service = new InformeService();
                                //borar informe
                                CollectionReference cr = service.connectFirebase();
                                System.out.println("service.connect(): " + cr.document(itemInforme.getId()).delete());
                                //borrar gasto - informe
                                final CollectionReference gi = service.connectGastosI();
                                System.out.println("result gi: "+gi.whereEqualTo("idInforme",itemInforme.getId()).addSnapshotListener(
                                        new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                                                for (DocumentSnapshot document : snapshots.getDocuments()) {
                                                    Gasto gasto = document.toObject(Gasto.class);
                                                    System.out.println("gastoInforme.getId()(): "+gasto.getId()+"");
                                                    gi.document(gasto.getId()).delete();
                                                }
                                            }
                                            }
                                ));
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                System.out.println("no borrar registro " + itemInforme.getId());
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
        public TextView fechafTV;
        public TextView dineroTotalTV;
        public TextView nusuarioTV;
        public  TextView monedaLabelTV;
        //public TextView labelInformeTV;
        public CardView registroCV;
        public Button borrarInf_btn;

        public CardHolder(View card) {
            super(card);
            nombreTV = card.findViewById(R.id.nombrei_tv);
            fechasTV = card.findViewById(R.id.fechai_tv);
            dineroTotalTV = card.findViewById(R.id.dineroi_tv);
            fechafTV = card.findViewById(R.id.fechaf_tv);
            nusuarioTV = card.findViewById(R.id.nusuario_tv);
            monedaLabelTV = card.findViewById(R.id.monedainforme_tv);
            //labelInformeTV = card.findViewById(R.id.labelInforme_tv);
            registroCV = card.findViewById(R.id.informe_cv);
            borrarInf_btn= card.findViewById(R.id.borrarInf_btn);
        }
    }

    public void refreshSolicitudes(List<Informe> informes) {
        this.itemInformes.clear();
        this.itemInformes.addAll(informes);
        notifyDataSetChanged();
    }

}
