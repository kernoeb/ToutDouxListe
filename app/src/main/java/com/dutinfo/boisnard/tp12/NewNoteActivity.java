package com.dutinfo.boisnard.tp12;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.dutinfo.boisnard.tp12.Tasks.Task;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialog;
import petrov.kristiyan.colorpicker.ColorPicker;

public class NewNoteActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText dateText;
    int colorC = -1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task").allowMainThreadQueries().build();

        final Bundle intent = getIntent().getExtras();
        if (intent != null) {
            if (intent.getBoolean("edit")) {
                TextView title = findViewById(R.id.textView);
                title.setText("Modifier la note");

                EditText intitule = findViewById(R.id.intituleId);
                intitule.setText(intent.getString("intitule"));

                EditText description = findViewById(R.id.descriptionId);
                description.setText(intent.getString("description"));

                EditText duree = findViewById(R.id.dureeId);
                duree.setText(intent.getString("duree"));

                EditText date = findViewById(R.id.dateId);
                date.setText(intent.getString("date"));

                EditText url = findViewById(R.id.url);
                url.setText(intent.getString("url"));

                Button color = findViewById(R.id.buttonColor);
                color.setBackgroundColor(intent.getInt("color"));
                colorC = intent.getInt("color");
            }
        }

        // Set up back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Date picker dialog
        this.dateText = findViewById(R.id.dateId);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        this.dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewNoteActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Auto write "https://" when focus on url input
        final EditText url = findViewById(R.id.url);
        url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (url.getText().toString().matches("")) {
                        url.setText("https://");
                    }
                }
            }
        });

        final EditText duree = findViewById(R.id.dureeId);
        duree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerDialogFragment pdf = new PickerDialogFragment();
                pdf.show(getFragmentManager(), "dialog");
            }
        });


        final Button colorPicker = findViewById(R.id.buttonCP);
        final Button buttonColor = findViewById(R.id.buttonColor);

        // List of colors
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("#cfff95");
        colors.add("#ffff8b");
        colors.add("#c3fdff");
        colors.add("#ffc1e3");
        colors.add("#ffffb3");
        colors.add("#ffddc1");
        colors.add("#ffc4ff");

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker cP = new ColorPicker(NewNoteActivity.this);

                cP.setTitle("Choisir une couleur");

                cP.setColors(colors);

                cP.disableDefaultButtons(true);

                cP.addListenerButton("Annuler", new ColorPicker.OnButtonListener() {
                    @Override
                    public void onClick(View v, int position, int color) {
                        cP.dismissDialog();
                    }
                });

                cP.addListenerButton("OK", new ColorPicker.OnButtonListener() {
                    @Override
                    public void onClick(View v, int position, int color) {
                        colorC = color;
                        buttonColor.setBackgroundColor(color);
                        cP.dismissDialog();
                    }
                });
                cP.show();

            }
        });


        // Save data button
        Button buttonGenerator = findViewById(R.id.button);
        buttonGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.intituleId);
                EditText et2 = findViewById(R.id.descriptionId);
                EditText et3 = findViewById(R.id.dureeId);
                EditText et4 = findViewById(R.id.dateId);
                EditText et5 = findViewById(R.id.url);

                // Check if the URL is valid
                if (url.getText().toString().matches("") || url.getText().toString().matches("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")) {

                    // Check if title and description not empty
                    if (!et.getText().toString().matches("") && !et2.getText().toString().matches("")) {

                        // Check if edit mode
                        if (intent != null && intent.getBoolean("edit")) {
                            System.out.println("NewNoteActivity -> " + intent.getInt("position"));
                            if (colorC != -1)
                                db.taskDAO().editAll(et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), colorC, et5.getText().toString(), (intent.getInt("id")));
                            else
                                db.taskDAO().editAll(et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), -1, et5.getText().toString(), (intent.getInt("id")));
                            finish();
                        } else {
                            if (colorC != -1)
                                db.taskDAO().insertAll(new Task(0, et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), colorC, false, et5.getText().toString()));
                            else
                                db.taskDAO().insertAll(new Task(0, et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), -1, false, et5.getText().toString()));
                            finish();
                        }
                    }

                } else
                    // Toast if URL no valid
                    Toast.makeText(getApplicationContext(), "URL non valide !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        Intent back = new Intent();
        setResult(RESULT_OK, back);
        super.finish();
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        this.dateText.setText(sdf.format(myCalendar.getTime()));
    }
}
