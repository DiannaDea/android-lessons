package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper implements NoteListHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesDB";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String NOTES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_LEVEL + " INTEGER,"
                + KEY_DATE + " TEXT," + KEY_IMAGE + " TEXT" +")";
        db.execSQL(NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);
    }

    @Override
    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, note.getName());
        values.put(KEY_DESCRIPTION, note.getDescription());
        values.put(KEY_LEVEL, note.getPriority());
        values.put(KEY_DATE, note.getDate());

        String imagePath = note.getImagePath();

        values.put(KEY_IMAGE, imagePath);

        db.insert(TABLE_NOTES, null, values);
        db.close();

        return true;
    }

    @Override
    public boolean deleteNote(String noteName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_NAME + " = ?", new String[] { noteName });
        db.close();

        return true;
    }

    @Override
    public boolean updateNote(String noteName, HashMap<String, String> paramsToUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (Map.Entry paramToUpdate : paramsToUpdate.entrySet()) {
            String key = paramToUpdate.getKey().toString();
            String val = paramToUpdate.getValue().toString();

            switch(key) {
                case "name":
                    values.put(KEY_NAME, val);
                    break;
                case "description":
                    values.put(KEY_DESCRIPTION, val);
                    break;
                case "date":
                    values.put(KEY_DATE, val);
                    break;
                case "level":
                    values.put(KEY_LEVEL, Integer.parseInt(val));
                    break;
                case "image":
                    values.put(KEY_IMAGE, val);
                    break;
            }
        }

        db.update(TABLE_NOTES, values, KEY_NAME + " = ?",
                new String[] { noteName });

        return true;
    }

    @Override
    public List<Note> getNotes() {
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return this.retrieveQueriedElements(cursor);
    }

    @Override
    public Note getNote(String noteName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_LEVEL, KEY_DATE, KEY_IMAGE }, KEY_NAME + "=?",
                new String[] { noteName }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        String name = cursor.getString(1);
        String description = cursor.getString(2);
        int priority = Integer.parseInt(cursor.getString(3));
        String date = cursor.getString(4);
        String image = cursor.getString(5);

        return new Note(name, description, priority, date, image);

    }

    @Override
    public List<Note> filterByPriority(int priority) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_LEVEL, KEY_DATE, KEY_IMAGE }, KEY_LEVEL + "=?",
                new String[] { String.valueOf(priority) }, null, null, null, null);

        return this.retrieveQueriedElements(cursor);
    }

    @Override
    public List<Note> searchByText(String text) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION, KEY_LEVEL, KEY_DATE, KEY_IMAGE }, KEY_NAME + " LIKE ?",
                new String[]  {"%"+ text+ "%" }, null, null, null, null);

        List<Note> res =  this.retrieveQueriedElements(cursor);
        return res;
    }

    public List<Note> retrieveQueriedElements(Cursor cursor ) {
        List<Note> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int priority = Integer.parseInt(cursor.getString(3));
                String date = cursor.getString(4);
                String image = cursor.getString(5);

                Note note = new Note(name, description, priority, date, image);

                res.add(note);
            } while (cursor.moveToNext());
        }
        return res;
    }
}
