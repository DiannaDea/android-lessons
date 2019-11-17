package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    LinearLayout notesListLayout;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        JsonManager.readJSON(this);

        this.notesAdapter = new NotesAdapter(this);

        this.notesListLayout = (LinearLayout) findViewById(R.id.notesListLayout);

        this.refreshNotesList();
//
//        List<Note> notes = notesAdapter.getNotes();
//
//       Note note = new Note("note1_22", "description", "HIGH", "12.12.2019 12:00", "/path/to/image");
//       Note note2 = new Note("note2_23", "description", "1", "12.12.2019 12:00", "/path/to/image");
//       Note note3 = new Note("note3", "description", "1", "12.12.2019 12:00", "/path/to/image");
//
//       notesAdapter.addNote(note);
//        notesAdapter.addNote(note2);
//        notesAdapter.addNote(note3);
//
//        List<Note> notes4 = notesAdapter.getNotes();
//
//       notesAdapter.deleteNote("note2");
//       List<Note> notes2 = notesAdapter.getNotes();
//
//        Note tes = notesAdapter.getNote("note1");
//
//        HashMap<String, String> paramsToUpdate = new HashMap<String, String>(){{
//            put("description", "DESCRIPTION_TEST");
//            put("name", "DIANA_TEST");
//            put("level", "3");
//            put("date", "12.12.2019 12:00");
//        }};
//
//        notesAdapter.updateNote("note1", paramsToUpdate);
//
//        Note tes1 = notesAdapter.getNote("DIANA_TEST");
        List<Note> notes24 = notesAdapter.getNotes();

        List<Note> searched = notesAdapter.searchByText("DIANA");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.refreshNotesList();
    }

    private void refreshNotesList() {
        this.notesListLayout.removeAllViews();
        List<Note> notes = notesAdapter.getNotes();

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
                        refreshNotesList();
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

    private View.OnClickListener getUpdateButtonHandler(final Context ctx, final Note note) {
        return new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(NotesListActivity.this, ManageNoteActivity.class);

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
