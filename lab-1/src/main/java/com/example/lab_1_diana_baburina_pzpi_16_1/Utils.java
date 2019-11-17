package com.example.lab_1_diana_baburina_pzpi_16_1;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Utils {
    static LinearLayout getNoteContainer(Context ctx) {
        LinearLayout noteContainer = new LinearLayout(ctx);
        LinearLayout.LayoutParams deviceItemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deviceItemParams.setMargins(0,20,0,0);
        noteContainer.setOrientation(LinearLayout.HORIZONTAL);
        noteContainer.setBackgroundColor(Color.parseColor("#eaeaea"));
        noteContainer.setLayoutParams(deviceItemParams);

        return noteContainer;
    }

    static LinearLayout getNoteButtonsContainer(Context ctx, ArrayList<View.OnClickListener> handlers) {
        LinearLayout buttonsContainer = new LinearLayout(ctx);
        LinearLayout.LayoutParams buttonsContainerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2f);
        buttonsContainerParams.setMargins(0,20,20,20);
        buttonsContainer.setOrientation(LinearLayout.VERTICAL);

        buttonsContainer.setLayoutParams(buttonsContainerParams);

        Button infoNoteBtn = Utils.createButton(ctx, "More", handlers.get(0));
        Button updateNoteBtn = Utils.createButton(ctx, "Update", handlers.get(1));
        Button deleteNoteBtn = Utils.createButton(ctx, "Delete", handlers.get(2));

        buttonsContainer.addView(infoNoteBtn);
        buttonsContainer.addView(updateNoteBtn);
        buttonsContainer.addView(deleteNoteBtn);

        return buttonsContainer;
    }

    static LinearLayout getNoteDetailsContainer(Context ctx, Note note) {
        LinearLayout noteDetailsContainer = new LinearLayout(ctx);
        LinearLayout.LayoutParams deviceTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        noteDetailsContainer.setPadding(50, 50, 50, 50);
        noteDetailsContainer.setOrientation(LinearLayout.VERTICAL);
        noteDetailsContainer.setBackgroundColor(Color.parseColor("#eaeaea"));
        noteDetailsContainer.setLayoutParams(deviceTextParams);

        TextView name = Utils.createTextView(ctx, note.getName(), "name");
        noteDetailsContainer.addView(name);

        TextView description = Utils.createTextView(ctx, note.getDescription(), "");
        noteDetailsContainer.addView(description);

        TextView priority = Utils.createTextView(ctx, note.getPriority(), "");
        noteDetailsContainer.addView(priority);

        TextView date = Utils.createTextView(ctx, note.getDate(), "");
        noteDetailsContainer.addView(date);
        
        return noteDetailsContainer;
    }

    static Button createButton(final Context context, String text, View.OnClickListener handler) {
        Button btn = new Button(context);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        btn.setText(text);
        btn.setLayoutParams(btnParams);

        btn.setOnClickListener(handler);

        return btn;
    }

    static TextView createTextView(Context context, String text, String type) {
        TextView tv = new TextView(context);
        tv.setText(text);

        switch (type){
            case "name":
                tv.setTypeface(null, Typeface.BOLD);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setTextColor(Color.rgb(0, 110, 137));
                break;
                default:
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,15);
        tv.setLayoutParams(params);
        return tv;
    }

    static AlertDialog.Builder getDeleteModal(Context ctx, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Are you sure?");

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View customLayout = inflater.inflate( R.layout.delete_modal, null );

        builder.setView(customLayout);

        TextView tv = (TextView) customLayout.findViewById(R.id.deleteModalText);

        tv.setText(String.format("Do you want to delete note: %s?", note.getName()));

        return builder;
    }

    static AlertDialog.Builder getInfoModal(Context ctx, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Note details");

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View customLayout = inflater.inflate( R.layout.info_modal, null );

        builder.setView(customLayout);

        TextView name = (TextView) customLayout.findViewById(R.id.noteNameValue);
        name.setText(note.getName());

        TextView description = (TextView) customLayout.findViewById(R.id.noteDescriptionValue);
        description.setText(note.getDescription());

        TextView priority = (TextView) customLayout.findViewById(R.id.notePriorityValue);
        priority.setText(note.getPriority());

        TextView date = (TextView) customLayout.findViewById(R.id.noteDateValue);
        date.setText(note.getDate());

        return builder;
    }

    static Spinner getPriorityDropdown(Spinner spinnerElement, Context ctx, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerElement.setAdapter(adapter);

        // spinnerElement.setSelection(0);

        return spinnerElement;
    }
}
