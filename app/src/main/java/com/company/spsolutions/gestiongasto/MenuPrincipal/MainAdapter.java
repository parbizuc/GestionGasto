package com.company.spsolutions.gestiongasto.MenuPrincipal;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.spsolutions.gestiongasto.Gastos.GastosActivity;
import com.company.spsolutions.gestiongasto.Informes.InformeActivity;
import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.SolicitudActivity;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CardHolder> {
    List<ItemMenu> itemsMenu;
    Context contexto;

    public MainAdapter(List<ItemMenu> itemsMenu, Context contexto) {
        this.itemsMenu = itemsMenu;
        this.contexto = contexto;
    }

    /*
     * 1. Implementar los listeners para cada card
     */
    @Override
    public void onBindViewHolder(final CardHolder cardHolder, final int i) {
        ItemMenu itemMenu = itemsMenu.get(i);
        Glide.with(contexto).load(itemMenu.image).into(cardHolder.imageIV);
        cardHolder.titleTV.setText(itemMenu.title);
        cardHolder.cardViewCV.setOnClickListener(new View.OnClickListener() {
            /*Fata controlar el listener segun el rol*/
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
                        Intent sActivity = new Intent(contexto, SolicitudActivity.class);
                        contexto.startActivity(sActivity);
                        break;
                    case 1:
                        Intent gActivity = new Intent(contexto, GastosActivity.class);
                        contexto.startActivity(gActivity);
                        break;
                    case 2:
                        Intent iActivity = new Intent(contexto, InformeActivity.class);
                        contexto.startActivity(iActivity);
                        break;
                    default:
                        Toast.makeText(contexto, "mensaje" + i, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemsMenu.size();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView titleTV;
        public ImageView imageIV;
        public CardView cardViewCV;

        public CardHolder(View card) {
            super(card);
            titleTV = card.findViewById(R.id.textCard_tv);
            imageIV = card.findViewById(R.id.imgCard_iv);
            cardViewCV = card.findViewById(R.id.card_view);
        }
    }

}
