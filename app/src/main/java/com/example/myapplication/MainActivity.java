package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.larswerkman.holocolorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private ImageButton btnBrush;
    private ImageButton btnNew;
    private ImageButton btnReturn;
    private SeekBar borderBar;
    private ColorPicker borderPicker;
    private ColorPicker fillPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.drawingView = (DrawingView) findViewById(R.id.drawingView);
        this.setUpToolbarEvent();
        this.setUpDefaults();
        this.setUpListeners();
    }

    private void setUpToolbarEvent() {
        this.btnBrush = findViewById(R.id.btnBrush);
        this.btnNew = findViewById(R.id.btnNew);
        this.btnReturn = findViewById(R.id.btnReturn);
        this.borderBar = findViewById(R.id.borderBar);
        this.borderPicker = findViewById(R.id.borderPicker);
        this.fillPicker = findViewById(R.id.fillPicker);
    };

    private void setUpDefaults(){
        this.borderPicker.setColor(Color.BLACK);
        this.borderPicker.setOldCenterColor(Color.BLACK);
        this.fillPicker.setColor(Color.BLACK);
        this.fillPicker.setOldCenterColor(Color.BLACK);
    }

    private void setUpListeners(){
        this.btnBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout VerticalLayout = (LinearLayout) findViewById(R.id.verticalLayout);
                int value = (VerticalLayout.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE;
                VerticalLayout.setVisibility(value);
            }
        });

        this.btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TouchCoord.clearDrawings();
                drawingView.invalidate();
            }
        });

        this.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TouchCoord.removeLastTouch();
                drawingView.invalidate();
            }
        });

        this.borderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { drawingView.border = i + 1; }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Start border"); }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Stop border"); }
        });

        this.borderPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                borderPicker.setOldCenterColor(drawingView.colorBorder);
                drawingView.colorBorder = color;
            }
        });

        this.fillPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                fillPicker.setOldCenterColor(drawingView.color);
                drawingView.color = color;
            }
        });
    };
}