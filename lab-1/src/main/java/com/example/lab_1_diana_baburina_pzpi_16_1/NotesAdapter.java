package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter {
    private Context context;

    public NotesAdapter(Context context) {
        this.context = context;
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<Note>();
        JSONObject notesJSON = JsonManager.readJSON(this.context);
        try {
            JSONArray existingNotes = notesJSON.getJSONArray("notes");

            for (int i = 0; i < existingNotes.length(); i++) {
                JSONObject noteJSON = existingNotes.getJSONObject(i);

                Note note = new Note(
                        noteJSON.get("name").toString(),
                        noteJSON.get("description").toString(),
                        noteJSON.get("level").toString(),
                        noteJSON.get("date").toString(),
                        noteJSON.get("image").toString()
                );

                notes.add(note);
            }
        } catch (JSONException e) {}
        return notes;
    }

}
