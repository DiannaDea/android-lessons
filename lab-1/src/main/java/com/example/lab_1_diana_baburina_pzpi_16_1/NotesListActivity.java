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

        //JsonManager.writeJSON(this, "{\"name\":\"Note 1\",\"description\":\"Description\",\"level\":\"1\",\"date\":\"12.12.2019 12:00\",\"image\":\"/path/to/image\"}");

        NotesAdapter notesAdapter = new NotesAdapter(this);
        List<Note> notes = notesAdapter.getNotes();

        int t = 1;
    }
}
