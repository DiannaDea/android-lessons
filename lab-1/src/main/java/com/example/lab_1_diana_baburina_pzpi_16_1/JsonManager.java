package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonManager {
    private static final String FILE_NAME="notes-db";

    public static JSONObject readJSON(Context context) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        StringBuffer output = new StringBuffer();
        JSONObject notes = new JSONObject();

        if (!file.exists()) {
            try {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    try {
                        notes = new JSONObject();
                        JSONArray emptyNotes = new JSONArray();
                        notes.put("notes", emptyNotes);

                        bufferedWriter.write(notes.toString());
                        bufferedWriter.close();
                    } catch (JSONException e) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileReader fileReader = new FileReader(file.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader((fileReader));

                String line = "";

                while((line = bufferedReader.readLine()) != null) {
                    output.append(line + "\n");
                }

                String response = output.toString();
                notes = new JSONObject(response);

                bufferedReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return notes;
    }

    public static void writeToJSON(Context context, JSONArray arrayToRewrite) {
        try {
            // get current notes from file
            JSONObject notesObj = JsonManager.readJSON(context);

            File file = new File(context.getFilesDir(), FILE_NAME);

            notesObj.put("notes", arrayToRewrite);

            // write to file
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(notesObj.toString());
            bw.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
