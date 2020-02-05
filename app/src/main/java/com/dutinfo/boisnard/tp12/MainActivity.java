package com.dutinfo.boisnard.tp12;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * MainActivity of the app
 */
public class MainActivity extends AppCompatActivity implements AdapterList.RecyclerViewClickListener {

    // Second activity
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

        // Init ROOM database (SQLite)
        this.db = Room.databaseBuilder(this, AppDatabase.class, "task").allowMainThreadQueries().build();

        // Init ArrayList with local database
        this.tasks = new ArrayList<>(this.db.taskDAO().getAll());
        ArrayList<Task> printedTasks = new ArrayList<>(this.tasks);

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

        // Init adapter
        this.mAdapter = new AdapterList(printedTasks, this, getApplicationContext());
        this.recyclerView.setAdapter(mAdapter);

        this.recyclerView.addItemDecoration(new SpaceItemDecoration(10));

        // Swipe to remove tasks
        setUpRecyclerView();
    }

    /**
     * Sort tasks by the color
     */
    public void sortByColor(String color) {
        ArrayList<Task> printedTasks = new ArrayList<>(this.tasks);
        if (color != null) {
            ArrayList<Task> tmp = new ArrayList<>(printedTasks);
            for (Task task:tmp) {
                System.out.println("color  : "+Color.parseColor(color)+"|"+task.getColor());
                if (task.getColor() != Color.parseColor(color)) {
                    printedTasks.remove(task);
                }
            }
        }
        this.mAdapter = new AdapterList(printedTasks, this, getApplicationContext());
        this.recyclerView.setAdapter(mAdapter);
    }

    /**
     * Open new note activity
     */
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
            this.db.taskDAO().editCompleted(false, (this.tasks.get(position).getUid()));
            this.tasks.get(position).setCompleted(false);

            // Clean the item design
            intitule.setPaintFlags(0);
            description.setPaintFlags(0);
            date.setPaintFlags(0);
            duree.setPaintFlags(0);
            GradientDrawable drawable = (GradientDrawable) v.getBackground();
            drawable.setColor(this.tasks.get(position).getColor());
        } else {
            this.db.taskDAO().editCompleted(true, (this.tasks.get(position).getUid()));
            this.tasks.get(position).setCompleted(true);

            // Strike out the task
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

    /**
     * Remove a task
     *
     * @param position position of the task
     */
    public void removeElement(int position) {
        this.db.taskDAO().delete(this.tasks.get(position));
        this.tasks.remove(position);
        ArrayList<Task> printedTasks = new ArrayList<>(this.tasks);
        this.mAdapter = new AdapterList(printedTasks, this, getApplicationContext());
        this.recyclerView.setAdapter(mAdapter);
    }

    /**
     * Edit a task and open New Note Activity
     *
     * @param position position of the task
     */
    public void editElement(int position) {
        Intent intent = new Intent(this, NewNoteActivity.class);
        Task t = this.tasks.get(position);
        intent.putExtra("id", t.getUid());
        intent.putExtra("intitule", t.getIntitule());
        intent.putExtra("description", t.getDescription());
        intent.putExtra("duree", t.getDuree());
        intent.putExtra("date", t.getDate());
        intent.putExtra("url", t.getUrl());
        intent.putExtra("position", position);

        // Send boolean -> edit mode
        intent.putExtra("edit", true);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST);
    }

    /**
     * Open Webview Activity
     *
     * @param position position of the task
     */
    public void openWebview(int position) {
        final Intent intent = new Intent(this, MyWebViewClient.class);
        Task t = this.tasks.get(position);
        intent.putExtra("url", t.getUrl());
        startActivity(intent);
    }

    /**
     * Open a dialog when long press on app
     *
     * @param position position of the task
     */
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

    /**
     * Set up swipe to delete
     */
    private void setUpRecyclerView() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("#cfff95");
        colors.add("#ffff8b");
        colors.add("#c3fdff");
        colors.add("#ffc1e3");
        colors.add("#ffffb3");
        colors.add("#ffddc1");
        colors.add("#ffc4ff");
        colors.add("#ffffff");
        switch (item.getItemId()) {
            case R.id.filter:
                ColorPicker cP = new ColorPicker(this);

                cP.setColors(colors);

                cP.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        if (position >= 0 && position < colors.size())
                            sortByColor(colors.get(position));
                        else
                            sortByColor(null);
                    }

                    @Override
                    public void onCancel() {
                        sortByColor(null);
                    }
                });
                cP.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
