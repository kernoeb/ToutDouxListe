package com.dutinfo.boisnard.tp12;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.dutinfo.boisnard.tp12.Tasks.Task;

import static com.dutinfo.boisnard.tp12.AdapterList.itemListener;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//    private Task task;

    public TextView intitule, description, duree, date, url;
//    private View view;

    public ViewHolder(@NonNull View view) {
        super(view);
//        this.view = view;
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        this.intitule = view.findViewById(R.id.intitule);
        this.description = view.findViewById(R.id.description);
        this.duree = view.findViewById(R.id.duree);
        this.date = view.findViewById(R.id.date);
        this.url = view.findViewById(R.id.url);
    }


    @Override
    public void onClick(View view) {
        itemListener.recyclerViewListClicked2(view, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
        itemListener.recyclerViewListClicked(view, getAdapterPosition());
        return true;
    }

}
