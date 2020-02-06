package com.dutinfo.boisnard.tp12;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.dutinfo.boisnard.tp12.AdapterList.itemListener;

/**
 * ViewHolder for the RecyclerView
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public final TextView intitule;
    public final TextView description;
    public final TextView duree;
    public final TextView date;

    public ViewHolder(@NonNull View view) {
        super(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        this.intitule = view.findViewById(R.id.intitule);
        this.description = view.findViewById(R.id.description);
        this.duree = view.findViewById(R.id.duree);
        this.date = view.findViewById(R.id.date);
    }

    @Override
    public void onClick(View view) {
        itemListener.recyclerViewListClicked2(view, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        itemListener.recyclerViewListClicked(getAdapterPosition());
        return true;
    }

}
