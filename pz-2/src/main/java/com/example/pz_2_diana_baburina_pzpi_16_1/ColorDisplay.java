package com.example.pz_2_diana_baburina_pzpi_16_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;

public class ColorDisplay extends AppCompatActivity {
    private final ColorObject color = ColorObject.getInstance();
    final String TAG = "States";

    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;

    private TextView colorTextView;

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_display);

        this.initSeekBars();

        Log.d(TAG, "ColorDisplay: onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setColorText();
        Log.d(TAG, "ColorDisplay: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ColorDisplay: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ColorDisplay: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        // MAKE SURE COLORS ARE SAVED ON SCREEN ORIENTATION CHANGE
        color.setColor("red", seekBarRed.getProgress());
        color.setColor("green", seekBarGreen.getProgress());
        color.setColor("blue", seekBarBlue.getProgress());


        Log.d(TAG, "ColorDisplay: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // REMOVE LISTENERS FROM SEEKBARS
        seekBarRed.setOnSeekBarChangeListener(null);
        seekBarGreen.setOnSeekBarChangeListener(null);
        seekBarBlue.setOnSeekBarChangeListener(null);

        Log.d(TAG, "ColorDisplay: onDestroy()");
    }

    private void initSeekBars() {
        seekBarRed = findViewById(R.id.seekBarRed);
        seekBarGreen = findViewById(R.id.seekBarGreen);
        seekBarBlue = findViewById(R.id.seekBarBlue);

        seekBarRed.setMax(255);
        seekBarRed.setOnSeekBarChangeListener(this.getListeners("red", this));

        seekBarGreen.setMax(255);
        seekBarGreen.setOnSeekBarChangeListener(this.getListeners("green", this));

        seekBarBlue.setMax(255);
        seekBarBlue.setOnSeekBarChangeListener(this.getListeners("blue", this));
    }


    private SeekBar.OnSeekBarChangeListener getListeners (final String colorToChange, final ColorDisplay context) {
        return new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                color.setColor( colorToChange, progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                context.setColorText();
            }
        };
    }

    private void setColorText() {
        colorTextView = findViewById(R.id.colorText);
        int[] colorToShow = color.getColor();

        String result = String.format(
                "%s: %s (%d, %d, %d)",
                getResources().getString(R.string.colorLabel),
                getResources().getString(R.string.rgbLabel),
                colorToShow[0],
                colorToShow[1],
                colorToShow[2]
        );

        colorTextView.setText(result);
        colorTextView.setTextColor(Color.rgb(colorToShow[0], colorToShow[1], colorToShow[2]));

        layout = findViewById(R.id.SelectedColorContainer);

        layout.setBackgroundColor(Color.rgb(colorToShow[0], colorToShow[1], colorToShow[2]));
    }

}
