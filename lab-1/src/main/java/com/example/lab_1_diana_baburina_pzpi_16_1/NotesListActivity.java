package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    LinearLayout notesListLayout;
    // NotesAdapter notesAdapter;
    DatabaseHandler notesAdapter;
    Spinner priorityDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        JsonManager.readJSON(this);

        // this.notesAdapter = new NotesAdapter(this);
        this.notesAdapter = new DatabaseHandler(this);

        this.notesListLayout = (LinearLayout) findViewById(R.id.notesListLayout);
        
        this.refreshNotesList(notesAdapter.getNotes());

        setManagementButtons();
        setPriorityFilter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.refreshNotesList(notesAdapter.getNotes());
    }
    
    private void setManagementButtons() {
        ((Button) findViewById(R.id.btnCreateNote)).setOnClickListener(this.createNote(this));
        ((Button) findViewById(R.id.btnSearch)).setOnClickListener(this.searchNotes(this));
    }
    
    private void setPriorityFilter() {
        final String[] priorities = new String[]{
                getResources().getString(R.string.allPriorities),
                getResources().getString(R.string.highPriority),
                getResources().getString(R.string.mediumPriority),
                getResources().getString(R.string.lowPriority)
        };

        priorityDropdown = Utils.getPriorityDropdown((Spinner)findViewById(R.id.filterDropdown), this, priorities);
        priorityDropdown.setSelection(0);

        priorityDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (priorities[position].equals(getResources().getString(R.string.allPriorities))) {
                    refreshNotesList(notesAdapter.getNotes());
                } else {
                    refreshNotesList(notesAdapter.filterByPriority(position - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void refreshNotesList(List<Note> notes) {
        this.notesListLayout.removeAllViews();

        for(Note note : notes) {
            this.addNoteDetailsContainer(note);
        }
    }

    public View.OnClickListener searchNotes(final Context ctx) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchFieldInput = findViewById(R.id.searchField);
                final String searchField = searchFieldInput.getText().toString();

                if (searchField.length() >= 1) {
                    refreshNotesList(notesAdapter.searchByText(searchField));
                } else {
                    refreshNotesList(notesAdapter.getNotes());
                }
            }
        };
    }

    private View.OnClickListener deleteNote(final Context ctx,final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder deleteModalBuilder = Utils.getDeleteModal(ctx, note);

                deleteModalBuilder.setPositiveButton(getResources().getString(R.string.btnOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesAdapter.deleteNote(note.getName());
                        refreshNotesList(notesAdapter.getNotes());
                    }
                });

                deleteModalBuilder.setNeutralButton(getResources().getString(R.string.btnCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog deleteModal = deleteModalBuilder.create();


                deleteModal.show();
            }
        };
    }

    private View.OnClickListener getNoteInformation(final Context ctx,final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder infoModalBuilder = Utils.getInfoModal(ctx, note);

                infoModalBuilder.setPositiveButton(getResources().getString(R.string.btnOk), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog infoModal = infoModalBuilder.create();
                infoModal.show();
            }
        };
    }

    private View.OnClickListener createNote(final Context ctx) {
        return new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(NotesListActivity.this, ManageNoteActivity.class);

                intent.putExtra("ACTION", "CREATE");

                startActivityForResult(intent, 200);
            }
        };
    }

    private View.OnClickListener updateNote(final Context ctx, final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(NotesListActivity.this, ManageNoteActivity.class);

                intent.putExtra("ACTION", "UPDATE");
                intent.putExtra("NOTE_TO_UPDATE", note.getName());

                startActivityForResult(intent, 200);
            }
        };
    }

    private void addNoteDetailsContainer(Note note) {
        ArrayList<View.OnClickListener> handlers = new ArrayList<View.OnClickListener>();

        handlers.add(this.getNoteInformation(this, note));
        handlers.add(this.updateNote(this, note));
        handlers.add(this.deleteNote(this, note));

        LinearLayout noteContainer = Utils.getNoteContainer(this);
        LinearLayout noteButtonsContainer = Utils.getNoteButtonsContainer(this, handlers);
        LinearLayout noteDetailsContainer = Utils.getNoteDetailsContainer(this, note);

        noteContainer.addView(noteDetailsContainer);
        noteContainer.addView(noteButtonsContainer);


        this.notesListLayout.addView(noteContainer);
    }
}
