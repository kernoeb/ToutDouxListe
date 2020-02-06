package com.dutinfo.boisnard.tp12;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dutinfo.boisnard.tp12.tasks.Task;

import java.util.ArrayList;

/**
 * Adapter
 */
public class AdapterList extends RecyclerView.Adapter<ViewHolder> {

    public static RecyclerViewClickListener itemListener;
    private final ArrayList<Task> tasks;

    public AdapterList(ArrayList<Task> tasks, RecyclerViewClickListener itemListener) {
        AdapterList.itemListener = itemListener;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = this.tasks.get(position);
        holder.intitule.setText(task.getIntitule());
        holder.date.setText(task.getDate());
        holder.description.setText(task.getDescription() + "\n");
        holder.duree.setText(task.getDuree());
        if (task.getUrl().trim().length() == 0) {
            holder.itemView.findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
        }

        int currentColor;
        if (task.getColor() == -1) {
            currentColor = Color.WHITE;
        } else currentColor = task.getColor();

        holder.itemView.setBackgroundResource(R.drawable.layout_corner);
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
        drawable.setColor(currentColor);

        if (!task.isCompleted()) {
            holder.intitule.setPaintFlags(0);
            holder.description.setPaintFlags(0);
            holder.date.setPaintFlags(0);
            holder.duree.setPaintFlags(0);
            GradientDrawable d = (GradientDrawable) holder.itemView.getBackground();
            d.setColor(task.getColor());
        } else {
            // Strike out the text
            holder.intitule.setPaintFlags(holder.intitule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.description.setPaintFlags(holder.description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.date.setPaintFlags(holder.date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.duree.setPaintFlags(holder.duree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            GradientDrawable d = (GradientDrawable) holder.itemView.getBackground();
            d.setColor(Color.parseColor("#cfd8dc"));
        }
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    // Interface to catch short press and long press
    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(int position);

        void recyclerViewListClicked2(View v, int position);
    }
}