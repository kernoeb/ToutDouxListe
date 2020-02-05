package com.dutinfo.boisnard.tp12;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.dutinfo.boisnard.tp12.Tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterList.RecyclerViewClickListener {

    static final int SECOND_ACTIVITY_REQUEST = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Task> tasks = new ArrayList<>();
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = Room.databaseBuilder(this, AppDatabase.class, "task").allowMainThreadQueries().build();

        this.tasks = new ArrayList<>(this.db.taskDAO().getAll());
//        Collections.reverse(this.tasks);


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });


        this.recyclerView = findViewById(R.id.recyclerView);

        this.layoutManager = new LinearLayoutManager(getApplicationContext());


        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mAdapter = new AdapterList(this.tasks, this, getApplicationContext());
        this.recyclerView.setAdapter(mAdapter);

        this.recyclerView.addItemDecoration(new SpaceItemDecoration(10));

        setUpRecyclerView();
    }


    public void openNewActivity() {
        Intent intent = new Intent(this, NewNoteActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST);
    }


    @Override
    public void recyclerViewListClicked2(View v, int position) {
        TextView intitule = v.findViewById(R.id.intitule);
        TextView description = v.findViewById(R.id.description);
        TextView duree = v.findViewById(R.id.duree);
        TextView date = v.findViewById(R.id.date);

        if (this.tasks.get(position).isCompleted()) {
            System.out.println("COMPLÉTÉ > NON COMPLÉTÉ" + position);
            this.db.taskDAO().editCompleted(false, (position + 1));
            this.tasks.get(position).setCompleted(false);

            intitule.setPaintFlags(0);
            description.setPaintFlags(0);
            date.setPaintFlags(0);
            duree.setPaintFlags(0);
            GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(this.tasks.get(position).getColor());
        } else {
            System.out.println("NON COMPLÉTÉ > COMPLÉTÉ " + position);
            this.db.taskDAO().editCompleted(true, (position + 1));
            this.tasks.get(position).setCompleted(true);


            intitule.setPaintFlags(intitule.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            date.setPaintFlags(date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            duree.setPaintFlags(duree.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(Color.parseColor("#cfd8dc"));
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        openDialog(position);
    }

    public void removeElement(int position) {
        this.db.taskDAO().delete(this.tasks.get(position));
        this.tasks.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void editElement(int position) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        Task t = this.tasks.get(position);
        intent.putExtra("intitule", t.getIntitule());
        intent.putExtra("description", t.getDescription());
        intent.putExtra("duree", t.getDuree());
        intent.putExtra("date", t.getDate());
        intent.putExtra("url", t.getUrl());
        intent.putExtra("position", position);
        intent.putExtra("edit", true);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST);
    }

    public void openWebview(int position) {
        final Intent intent = new Intent(this, MyWebViewClient.class);
        Task t = this.tasks.get(position);
        intent.putExtra("url", t.getUrl());
        startActivity(intent);
    }

    public void openDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Que voulez-vous faire ?");

        if (this.tasks.get(position).getUrl() != null && this.tasks.get(position).getUrl().matches("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")) {
            String[] actions = {"Modifier", "Supprimer", "Ouvrir l'URL"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(which);
                    switch (which) {
                        case 0:
                            dialog.dismiss();
                            editElement(position);
                            break;
                        case 1:
                            dialog.dismiss();
                            removeElement(position);
                            break;
                        case 2:
                            dialog.dismiss();
                            openWebview(position);
                            break;
                    }
                }
            });
        } else {
            String[] actions = {"Modifier", "Supprimer"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(which);
                    switch (which) {
                        case 0:
                            dialog.dismiss();
                            editElement(position);
                            break;
                        case 1:
                            dialog.dismiss();
                            removeElement(position);
                            break;
                    }
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();
    }

    private void setUpRecyclerView() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


}
