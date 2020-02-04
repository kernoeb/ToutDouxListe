package com.dutinfo.boisnard.tp12;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.dutinfo.boisnard.tp12.Tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            if (intent.getBoolean("edit")) {
                EditText intitule = findViewById(R.id.intituleId);
                intitule.setText(intent.getString("intitule"));
//                db.taskDAO().editIntitule(intent.getString("intitule"), intent.getInt("position"));

                EditText description = findViewById(R.id.descriptionId);
                description.setText(intent.getString("description"));
//                db.taskDAO().editIntitule(intent.getString("description"), intent.getInt("position"));


                EditText duree = findViewById(R.id.dureeId);
                duree.setText(intent.getString("duree"));
//                db.taskDAO().editIntitule(intent.getString("duree"), intent.getInt("position"));

                EditText date = findViewById(R.id.dateId);
                date.setText(intent.getString("date"));
//                db.taskDAO().editIntitule(intent.getString("date"), intent.getInt("position"));
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DATE PICKER DIALOG
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

        final Button colorPicker = findViewById(R.id.buttonCP);


        final Button buttonColor = findViewById(R.id.buttonColor);


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
                ColorPicker cP = new ColorPicker(NewNoteActivity.this);

                cP.setColors(colors);

                cP.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        colorC = color;
                        buttonColor.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {
                        // put code
                    }
                });
                cP.show();

            }
        });


        Button buttonGenerator = findViewById(R.id.button);
        buttonGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.intituleId);
                EditText et2 = findViewById(R.id.descriptionId);
                EditText et3 = findViewById(R.id.dureeId);
                EditText et4 = findViewById(R.id.dateId);
                EditText et5 = findViewById(R.id.url);
                if (!et.getText().toString().matches("") && !et2.getText().toString().matches("")) {

                    if (colorC != -1)
                        db.taskDAO().insertAll(new Task(0, et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), colorC, false, et5.getText().toString()));
                    else
                        db.taskDAO().insertAll(new Task(0, et.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), -1, false, et5.getText().toString()));

                    finish();
                }
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
