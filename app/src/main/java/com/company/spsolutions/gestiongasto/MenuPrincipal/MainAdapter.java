package com.company.spsolutions.gestiongasto.MenuPrincipal;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.content.Context;
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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CardHolder> {
    List<ItemMenu> itemsMenu;
    Context contexto;

    public MainAdapter(List<ItemMenu> itemsMenu, Context contexto) {
        this.itemsMenu = itemsMenu;
        this.contexto = contexto;
    }

    @Override
    public void onBindViewHolder(CardHolder cardHolder, int i) {
        ItemMenu itemMenu = itemsMenu.get(i);
        Picasso.get().load(itemMenu.image).into(cardHolder.imageIV);
        cardHolder.titleTV.setText(itemMenu.title);
    }

    @Override
    public int getItemCount() {
        return itemsMenu.size();
    }

    /*
    * 1. Implementar los listeners para cada card
     */
    @Override
    public CardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto,"este es mensaje",Toast.LENGTH_LONG);
            }
        });
        return new CardHolder(itemView);
    }


    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView titleTV;
        public ImageView imageIV;

        public CardHolder(View card) {
            super(card);
            titleTV = card.findViewById(R.id.textCard_tv);
            imageIV = card.findViewById(R.id.imgCard_iv);
        }
    }

}
