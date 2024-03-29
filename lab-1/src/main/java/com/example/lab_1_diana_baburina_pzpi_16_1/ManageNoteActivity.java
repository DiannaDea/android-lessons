package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.HashMap;

public class ManageNoteActivity extends AppCompatActivity {
    Note noteToUpdate;
    NotesAdapter notesAdapter;
    Spinner priorityDropdown;
    String[] priorities;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);

        this.notesAdapter = new NotesAdapter(new DatabaseHandler(this));

        priorities = new String[]{
                getResources().getString(R.string.highPriority),
                getResources().getString(R.string.mediumPriority),
                getResources().getString(R.string.lowPriority)
        };

        String action = getIntent().getStringExtra("ACTION");

        priorityDropdown = Utils.getPriorityDropdown((Spinner)findViewById(R.id.priorityUpdateInput), this, priorities);

        ((Button) findViewById(R.id.btnChoseImage)).setOnClickListener(this.chooseImage(this));

        if (action.equals("UPDATE")) {
            String noteName = getIntent().getStringExtra("NOTE_TO_UPDATE");
            this.noteToUpdate = notesAdapter.getNote(noteName);
            this.setNoteInfoToInputs(this);
            ((Button) findViewById(R.id.btnSave)).setOnClickListener(this.updateNote());
        }
        if (action.equals("CREATE")) {
            ((Button) findViewById(R.id.btnSave)).setOnClickListener(this.saveNote());
        }
    }

    private void setNoteInfoToInputs(Context ctx) {
        EditText name = findViewById(R.id.nameUpdateInput);
        name.setText(noteToUpdate.getName());

        EditText description = findViewById(R.id.descriptionUpdateInput);
        description.setText(noteToUpdate.getDescription());

        Spinner priority = Utils.getPriorityDropdown((Spinner)findViewById(R.id.priorityUpdateInput), ctx, priorities);

        int priorityValue = noteToUpdate.getPriority();
        priority.setSelection(priorityValue);

        EditText date = findViewById(R.id.dateUpdateInput);
        date.setText(noteToUpdate.getDate());

        TextView imagePath = findViewById(R.id.imagePathUpdateInput);
        imagePath.setText(noteToUpdate.getImagePath());


        File imgFile = new  File(noteToUpdate.getImagePath());
        ImageView imgView = (ImageView) findViewById(R.id.imgView);

        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgView.setImageBitmap(myBitmap);
        } else {
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.note_default));
        }
    }

    public View.OnClickListener saveNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameInput = findViewById(R.id.nameUpdateInput);
                final String name = nameInput.getText().toString();

                EditText descriptionInput = findViewById(R.id.descriptionUpdateInput);
                final String description = descriptionInput.getText().toString();

                final int priority = priorityDropdown.getSelectedItemPosition();

                EditText dateInput = findViewById(R.id.dateUpdateInput);
                final String date = dateInput.getText().toString();

                TextView imagePathInput = findViewById(R.id.imagePathUpdateInput);
                final String imagePath = imagePathInput.getText().toString();

                Note newNote = new Note(name, description, priority, date, imagePath);

                notesAdapter.addNote(newNote);

                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        };
    }

    public View.OnClickListener updateNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameInput = findViewById(R.id.nameUpdateInput);
                final String name = nameInput.getText().toString();

                EditText descriptionInput = findViewById(R.id.descriptionUpdateInput);
                final String description = descriptionInput.getText().toString();

                final int priority = priorityDropdown.getSelectedItemPosition();

                EditText dateInput = findViewById(R.id.dateUpdateInput);
                final String date = dateInput.getText().toString();

                TextView imagePathInput = findViewById(R.id.imagePathUpdateInput);
                final String imagePath = imagePathInput.getText().toString();

                HashMap<String, String> paramsToUpdate = new HashMap<String, String>(){{
                    if (!noteToUpdate.getName().equals(name)) {
                        put("name", name);
                    }
                    if (!noteToUpdate.getDescription().equals(description)) {
                        put("description", description);
                    }
                    if (priority != noteToUpdate.getPriority()) {
                        put("level", Integer.toString(priority));
                    }
                    if (!noteToUpdate.getDate().equals(date)) {
                        put("date", date);
                    }
                    if (!noteToUpdate.getImagePath().equals(imagePath)) {
                        put("image", imagePath);
                    }
                }};

                notesAdapter.updateNote(noteToUpdate.getName(), paramsToUpdate);

                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        };
    }


    public View.OnClickListener chooseImage(final Context ctx) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            TextView imagePath = findViewById(R.id.imagePathUpdateInput);
            imagePath.setText(picturePath);

            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
