package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ManageNoteActivity extends AppCompatActivity {
    Note noteToUpdate;
    NotesAdapter notesAdapter;
    Spinner priorityDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);
        this.notesAdapter = new NotesAdapter(this);

        String action = getIntent().getStringExtra("ACTION");

        priorityDropdown = Utils.getPriorityDropdown((Spinner)findViewById(R.id.priorityUpdateInput), this);

        if (action.equals("UPDATE")) {
            String noteName = getIntent().getStringExtra("NOTE_TO_UPDATE");
            this.noteToUpdate = notesAdapter.getNote(noteName);
            this.setNoteInfoToInputs(this);
            ((Button) findViewById(R.id.btnSave)).setOnClickListener(this.handleSaveUpdatedNote(this));
        }
        if (action.equals("CREATE")) {
            ((Button) findViewById(R.id.btnSave)).setOnClickListener(this.handleSaveCreatedNote(this));
        }
    }

    private void setNoteInfoToInputs(Context ctx) {
        EditText name = findViewById(R.id.nameUpdateInput);
        name.setText(noteToUpdate.getName());

        EditText description = findViewById(R.id.descriptionUpdateInput);
        description.setText(noteToUpdate.getDescription());

        Spinner priority = Utils.getPriorityDropdown((Spinner)findViewById(R.id.priorityUpdateInput), ctx);

        String priorityValue = noteToUpdate.getPriority();

        switch (priorityValue) {
            case "HIGH":
                priority.setSelection(0);
                break;
            case "MEDIUM":
                priority.setSelection(1);
                break;
            case "LOW":
                priority.setSelection(2);
                break;
        }

        EditText date = findViewById(R.id.dateUpdateInput);
        date.setText(noteToUpdate.getDate());
    }

    public View.OnClickListener handleSaveCreatedNote(final Context ctx) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameInput = findViewById(R.id.nameUpdateInput);
                final String name = nameInput.getText().toString();

                EditText descriptionInput = findViewById(R.id.descriptionUpdateInput);
                final String description = descriptionInput.getText().toString();

                final String priority = priorityDropdown.getSelectedItem().toString();

                EditText dateInput = findViewById(R.id.dateUpdateInput);
                final String date = dateInput.getText().toString();

                Note newNote = new Note(name, description, priority, date, "/path/to/image");

                notesAdapter.addNote(newNote);

                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        };
    }

    public View.OnClickListener handleSaveUpdatedNote(final Context ctx) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameInput = findViewById(R.id.nameUpdateInput);
                final String name = nameInput.getText().toString();

                EditText descriptionInput = findViewById(R.id.descriptionUpdateInput);
                final String description = descriptionInput.getText().toString();

                final String priority = priorityDropdown.getSelectedItem().toString();

                EditText dateInput = findViewById(R.id.dateUpdateInput);
                final String date = dateInput.getText().toString();

                HashMap<String, String> paramsToUpdate = new HashMap<String, String>(){{
                    if (!noteToUpdate.getName().equals(name)) {
                        put("name", name);
                    }
                    if (!noteToUpdate.getDescription().equals(description)) {
                        put("description", description);
                    }
                    if (!noteToUpdate.getPriority().equals(priority)) {
                        put("level", priority);
                    }
                    if (!noteToUpdate.getDate().equals(date)) {
                        put("date", date);
                    }
                }};

                notesAdapter.updateNote(noteToUpdate.getName(), paramsToUpdate);

                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        };
    }


}
