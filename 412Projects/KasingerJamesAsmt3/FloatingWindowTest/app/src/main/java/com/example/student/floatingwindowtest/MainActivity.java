package com.example.student.floatingwindowtest;

import android.os.Bundle;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PopupWindow pwA;
    private LinearLayout layout;
    TextView tv;
    private LayoutParams params;
    LinearLayout mainLayout;
    Button butA,butB,butC,butD;
    boolean click = true;


    private void windowA(){
        pwA = new PopupWindow(this);
        layout =  new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);
        pwA.showAtLocation(mainLayout, Gravity.TOP,10,10);
        pwA.update(100,100,100,100);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("sample text view.");
        layout.addView(tv,params);
        pwA.setContentView(layout);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtons();
    }

    private void setButtons(){



        butA = (Button)findViewById(R.id.windowA);
        butA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowA();

            }
        });


        butB = (Button)findViewById(R.id.windowA);
        butB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowA();
            }
        });


        butC = (Button)findViewById(R.id.windowA);
        butC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowA();
            }
        });


        butD = (Button)findViewById(R.id.windowA);
        butD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowA();
            }
        });


    }
}
