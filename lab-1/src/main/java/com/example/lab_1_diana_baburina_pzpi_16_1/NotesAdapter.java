package com.example.lab_1_diana_baburina_pzpi_16_1;

import java.util.HashMap;
import java.util.List;

public class NotesAdapter {
    private NoteListHandler handler;

    public NotesAdapter(NoteListHandler handler) {
        this.handler = handler;
    }

    public List<Note> getNotes(){
        return this.handler.getNotes();
    }

    public boolean addNote(Note note) {
        return this.handler.addNote(note);
    }

    public Note getNote(String noteName){
        return this.handler.getNote(noteName);
    }

    public boolean updateNote(String noteName, HashMap<String, String> paramsToUpdate) {
        return this.handler.updateNote(noteName, paramsToUpdate);
    }

    public boolean deleteNote(String noteName){
        return this.handler.deleteNote(noteName);
    }

    public List<Note> filterByPriority(int priority) {
        return this.handler.filterByPriority(priority);
    }

    public List<Note> searchByText(String text) {
        return this.handler.searchByText(text);
    }

    public List<Note> searchAndFilter(String text, int priority) {
        return this.handler.searchAndFilter(text, priority);
    }
}
