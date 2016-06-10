package com.example.student.artistoranimator;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Animate extends AppCompatActivity implements Animation.AnimationListener {

    ImageView car;
    Button rotate;
    Button enlarge;
    private Animation rotateAnimation;
    private Animation scaleAnimation;
    MediaPlayer mp;
    MediaPlayer mp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
        car = (ImageView)findViewById(R.id.imageView);
        mp = MediaPlayer.create(this,R.raw.carhorn);
        mp2 = MediaPlayer.create(this,R.raw.train);
        enlarge = (Button)findViewById(R.id.enlargeButton);
        rotate = (Button)findViewById(R.id.rotateButton);
        enlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnimation(v);
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateAnimation(v);
            }
        });

    }

    private void scaleAnimation(View v) {
        scaleAnimation = new ScaleAnimation(
                1.0f,1.5f,
                1.0f,1.5f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
    scaleAnimation.setDuration(1000);
        scaleAnimation.setAnimationListener(this);
        car.startAnimation(scaleAnimation);
        mp.start();
    }

    private void rotateAnimation(View v) {
        rotateAnimation = new RotateAnimation(
                            0.0f, 360f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
    rotateAnimation.setDuration(1000);
    rotateAnimation.setAnimationListener(this);
    car.startAnimation(rotateAnimation) ;
        mp2.start();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
