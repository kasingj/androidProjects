package com.example.student.artistoranimator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class painterOrAnimator extends AppCompatActivity {
    private Button paintButton;
    private Button animateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter_or_animator);
    setButtons();
    }
    public void setButtons(){
        paintButton = (Button)findViewById(R.id.paintButton);
        animateButton = (Button)findViewById(R.id.animateButton);

        paintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FingerPaint.class);
                startActivity(intent);
            }
        });
        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Animate.class);
                startActivity(intent);
            }
        });
    }
}
