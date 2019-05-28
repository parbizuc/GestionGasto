package com.company.spsolutions.gestiongasto.Informes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.Modelos.Informe;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.AddSolicitudActivity;
import com.company.spsolutions.gestiongasto.SolicitudGasto.ReviewActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class InformesAdapter extends RecyclerView.Adapter<InformesAdapter.CardHolder> {
    List<Informe> itemInformes;
    Context contexto;

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
       final Informe itemInforme = itemInformes.get(i);
        registroHolder.nombreTV.setText(itemInforme.getNombreEmpresa());
        registroHolder.fechasTV.setText(itemInforme.getFechaRegistro());
        registroHolder.dineroTotalTV.setText(itemInforme.getMontoInforme());
        registroHolder.labelInformeTV.setText(itemInforme.getEstado());

        registroHolder.registroCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((itemInforme.getEstado() != null)) {
                    Intent editar = new Intent(contexto, AddInformeActivity.class);
                    editar.putExtra("informe",itemInforme);
                    contexto.startActivity(editar);
                }
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

    public void refreshSolicitudes(List<Informe> informes) {
        this.itemInformes.clear();
        this.itemInformes.addAll(informes);
        notifyDataSetChanged();
    }

}
