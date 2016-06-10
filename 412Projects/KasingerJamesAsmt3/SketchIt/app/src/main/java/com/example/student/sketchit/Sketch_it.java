package com.example.student.sketchit;

import android.graphics.Canvas;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class Sketch_it extends AppCompatActivity {
    Button snapPhotoBttn;
    Button drawOnCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sketch_it);
        //set canvas and buttons
        FragmentManager fm = getSupportFragmentManager();
        setButtons();

    }

    private void setButtons() {
        snapPhotoBttn = (Button)findViewById(R.id.cameraButton);
        drawOnCanvas = (Button)findViewById(R.id.drawButton);
        snapPhotoBttn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 takePhoto();
                                             }
                                         }
        );

        drawOnCanvas.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startDrawing();
                                             }
                                         }
        );
    }

    private void startDrawing() {
        FragmentManager fm = getSupportFragmentManager();
        image_view_fragment frag = (image_view_fragment)fm.findFragmentById(R.id.canvasFragment);

        //do drawing stuff using frag
        frag.takePhoto();


        Log.d("startDrawing","begining of startDrawing");
        //audio effect?
        Toast.makeText(Sketch_it.this, "start drawing!", Toast.LENGTH_SHORT).show();
    }

    private void takePhoto() {
        FragmentManager fm = getSupportFragmentManager();
        image_view_fragment frag = (image_view_fragment)fm.findFragmentById(R.id.canvasFragment);

        //do camera stuff using frag
        frag.setupCanvas();
        Toast.makeText(Sketch_it.this, "You snapped a photo!", Toast.LENGTH_SHORT).show();
        Log.d("takePhoto","begining of takePhoto");
    }
}
