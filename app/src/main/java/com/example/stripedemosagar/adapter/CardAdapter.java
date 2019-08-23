package com.example.stripedemosagar.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stripedemosagar.models.CardPojo;
import com.example.stripedemosagar.interfaces.OnRecyclerClickListener;
import com.example.stripedemosagar.R;
import com.vinaygaba.creditcardview.CreditCardView;

import java.util.List;

import javax.inject.Inject;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private List<CardPojo> cardList;
    OnRecyclerClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CreditCardView card;

        public MyViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.card);
        }
    }

    @Inject
    public CardAdapter() {
    }

    public void setData(List<CardPojo> cardList, OnRecyclerClickListener listener) {
        this.cardList = cardList;
        this.listener=listener;
    }

    public void updateData(List<CardPojo> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CardPojo cardPojo = cardList.get(position);
        if (cardPojo.getName() != null)
            holder.card.setCardName(cardPojo.getName());
        holder.card.setExpiryDate(cardPojo.getExp_month() + "/" + cardPojo.getExp_year());
        holder.card.setCardNumber(cardPojo.getLast4());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}