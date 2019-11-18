package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler implements NoteListHandler {
    private Context context;

    public JsonHandler(Context context) {
        this.context = context;
    }

    @Override
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
                        (int)noteJSON.get("level"),
                        noteJSON.get("date").toString(),
                        noteJSON.get("image").toString()
                );

                 notes.add(note);
            }
        } catch (JSONException e) {}
        return notes;
    }

    @Override
    public boolean addNote(Note noteToAdd) {
        List<Note> notes = this.getNotes();
        notes.add(noteToAdd);

        JSONArray jsonArray = this.updateArray(notes);

        JsonManager.writeToJSON(this.context, jsonArray);
        return true;
    }

    @Override
    public Note getNote(String noteName) {
        List<Note> notes = this.getNotes();
        int index = this.findIndex(notes, noteName);

        if (index == -1) return null;

        return notes.get(index);
    }

    @Override
    public boolean updateNote(String noteName, HashMap<String, String> paramsToUpdate) {
        List<Note> notes = this.getNotes();
        int index = this.findIndex(notes, noteName);

        if (index == -1) return false;

        Note noteToUpdate = notes.get(index);

        if (noteToUpdate != null) {
            for (Map.Entry paramToUpdate : paramsToUpdate.entrySet()) {
                String key = paramToUpdate.getKey().toString();
                String val = paramToUpdate.getValue().toString();

                switch(key) {
                    case "name":
                        noteToUpdate.setName(val);
                        break;
                    case "description":
                        noteToUpdate.setDescription(val);
                        break;
                    case "date":
                        noteToUpdate.setDate(val);
                        break;
                    case "level":
                        noteToUpdate.setLevel(Integer.parseInt(val));
                        break;
                    case "image":
                        noteToUpdate.setImage(val);
                        break;
                }
            }
        }

        notes.set(index, noteToUpdate);

        JSONArray jsonArray = this.updateArray(notes);
        JsonManager.writeToJSON(this.context, jsonArray);

        return true;
    }

    @Override
    public boolean deleteNote(String noteName) {
        List<Note> notes = this.getNotes();

        int index = this.findIndex(notes, noteName);
        if (index == -1) return false;

        notes.remove(index);

        JSONArray jsonArray = this.updateArray(notes);
        JsonManager.writeToJSON(this.context, jsonArray);

        return true;
    }

    @Override
    public List<Note> filterByPriority(int priority) {
        List<Note> notes = this.getNotes();
        List<Note> res = new ArrayList<Note>();

        for (Note note : notes) {
            if (note.getPriority() == priority) {
                res.add(note);
            }
        }
        return res;
    }

    @Override
    public List<Note> searchByText(String text) {
        List<Note> notes = this.getNotes();
        List<Note> res = new ArrayList<Note>();

        for (Note note : notes) {
            if (note.getName().matches(String.format("(?i:.*%s.*)", text))) {
                res.add(note);
            }
        }
        return res;
    }

    private JSONArray updateArray(List<Note> notes){
        JSONArray jsonArray = new JSONArray();

        try {
            for (Note note : notes) {
                jsonArray.put(new JSONObject(note.toJSONString()));
            }
        } catch (JSONException e) {}

        return jsonArray;
    }

    private int findIndex(List<Note> notes, String noteName) {
        int index = -1;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getName().equals(noteName)) {
                index = i;
            }
        }

        return index;
    }
}
