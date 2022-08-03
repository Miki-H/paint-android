package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private ImageButton btnBrush;
    private ImageButton btnNew;
    private ImageButton btnReturn;
    private SeekBar epaisseurBar;
    private SeekBar borderBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.drawingView = (DrawingView) findViewById(R.id.drawingView);
        setUpToolbarEvent();
    }

    private void setUpToolbarEvent(){
        this.btnBrush = findViewById(R.id.btnBrush);
        this.btnNew = findViewById(R.id.btnNew);
        this.btnReturn = findViewById(R.id.btnReturn);
        this.epaisseurBar = findViewById(R.id.epaisseurBar);
        this.borderBar = findViewById(R.id.borderBar);

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

        this.epaisseurBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                System.out.println(i);
                drawingView.epaisseur = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { System.out.println("Start"); }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { System.out.println("Stop"); }
        });
    }
}