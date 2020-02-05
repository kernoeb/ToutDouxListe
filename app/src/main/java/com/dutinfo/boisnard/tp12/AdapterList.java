package com.dutinfo.boisnard.tp12;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.dutinfo.boisnard.tp12.Tasks.Task;

import java.util.ArrayList;

public class AdapterList extends RecyclerView.Adapter<ViewHolder> {

    public static RecyclerViewClickListener itemListener;
    private ArrayList<Task> tasks;
    private Context context;

    public AdapterList(ArrayList<Task> tasks, RecyclerViewClickListener itemListener, Context context) {
        AdapterList.itemListener = itemListener;
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder mv = new ViewHolder(item);
        return mv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = this.tasks.get(position);
        holder.intitule.setText(task.getIntitule());
        holder.date.setText(task.getDate());
        holder.description.setText(task.getDescription() + "\n");
        holder.duree.setText(task.getDuree());

        int currentColor;
        if (task.getColor() == -1) {
            currentColor = Color.WHITE;
        } else currentColor = task.getColor();

        holder.itemView.setBackgroundResource(R.drawable.layout_corner);
        GradientDrawable drawable = (GradientDrawable) holder.itemView.getBackground();
        drawable.setColor(currentColor);

//        AppDatabase db = Room.databaseBuilder(this.context, AppDatabase.class, "task").allowMainThreadQueries().build();


        if (!task.isCompleted()) {
//        if (!db.taskDAO().isCompleted(position)) {
            System.out.println("BONJOUR A TOUS");

            System.out.println("NON COMPLÉTÉ " + position);
            holder.intitule.setPaintFlags(0);
            holder.description.setPaintFlags(0);
            holder.date.setPaintFlags(0);
            holder.duree.setPaintFlags(0);
            GradientDrawable d = (GradientDrawable) holder.itemView.getBackground();
            d.setColor(task.getColor());

        } else {
            System.out.println("COMPLÉTÉ " + position);

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

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
        void recyclerViewListClicked2(View v, int position);
//        void sortByColor(View v, int position);
    }
}