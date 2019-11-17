package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    LinearLayout notesListLayout;
    NotesAdapter notesAdapter;
    Spinner priorityDropdown;
    String[] priorities = new String[]{"ALL", "HIGH", "MEDIUM", "LOW"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        JsonManager.readJSON(this);

        this.notesAdapter = new NotesAdapter(this);

        this.notesListLayout = (LinearLayout) findViewById(R.id.notesListLayout);
        ((Button) findViewById(R.id.btnCreateNote)).setOnClickListener(this.getCreateButtonHandler(this));

        this.refreshNotesList(notesAdapter.getNotes());
        priorityDropdown = Utils.getPriorityDropdown((Spinner)findViewById(R.id.filterDropdown), this, priorities);
        priorityDropdown.setSelection(0);

        priorityDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (priorities[position].equals("ALL")) {
                    refreshNotesList(notesAdapter.getNotes());
                } else {
                    refreshNotesList(notesAdapter.filterByPriority(priorities[position]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.refreshNotesList(notesAdapter.getNotes());
    }

    private void refreshNotesList(List<Note> notes) {
        this.notesListLayout.removeAllViews();

        for(Note note : notes) {
            this.addNoteDetailsContainer(note);
        }
    }

    private View.OnClickListener getDeleteButtonHandler(final Context ctx,final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder deleteModalBuilder = Utils.getDeleteModal(ctx, note);

                deleteModalBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notesAdapter.deleteNote(note.getName());
                        refreshNotesList(notesAdapter.getNotes());
                    }
                });

                deleteModalBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog deleteModal = deleteModalBuilder.create();


                deleteModal.show();
            }
        };
    }

    private View.OnClickListener getInfoButtonHandler(final Context ctx,final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder infoModalBuilder = Utils.getInfoModal(ctx, note);

                infoModalBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog infoModal = infoModalBuilder.create();
                infoModal.show();
            }
        };
    }

    private View.OnClickListener getCreateButtonHandler(final Context ctx) {
        return new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(NotesListActivity.this, ManageNoteActivity.class);

                intent.putExtra("ACTION", "CREATE");

                startActivityForResult(intent, 200);
            }
        };
    }

    private View.OnClickListener getUpdateButtonHandler(final Context ctx, final Note note) {
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

        handlers.add(this.getInfoButtonHandler(this, note));
        handlers.add(this.getUpdateButtonHandler(this, note));
        handlers.add(this.getDeleteButtonHandler(this, note));

        LinearLayout noteContainer = Utils.getNoteContainer(this);
        LinearLayout noteButtonsContainer = Utils.getNoteButtonsContainer(this, handlers);
        LinearLayout noteDetailsContainer = Utils.getNoteDetailsContainer(this, note);

        noteContainer.addView(noteDetailsContainer);
        noteContainer.addView(noteButtonsContainer);


        this.notesListLayout.addView(noteContainer);
    }
}
