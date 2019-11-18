package com.example.lab_1_diana_baburina_pzpi_16_1;

import java.util.HashMap;
import java.util.List;

public interface NoteListHandler {
    public List<Note> getNotes();
    public boolean addNote(Note note);
    public Note getNote(String noteName);
    public boolean updateNote(String noteName, HashMap<String, String> paramsToUpdate);
    public boolean deleteNote(String noteName);
    public List<Note> filterByPriority(int priority);
    public List<Note> searchByText(String text);
    public List<Note> searchAndFilter(String text, int priority);
}
