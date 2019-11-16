package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.List;

public class NotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        JSONObject a = JsonManager.readJSON(this);

        NotesAdapter notesAdapter = new NotesAdapter(this);
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
}
