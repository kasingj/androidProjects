package com.example.student.dialogpopup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends AppCompatActivity {



    final Context context = this;
    private Button button;
    private Button button2;

    public void switchButton(int id){
        if(id == R.id.buttonShowCustomDialog ){
            dialogA();
        }else if(id ==R.id.dialog2Button){
            dialogB();
        }
    }

    private void dialogB() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customb);
        dialog.setTitle("Dialog B");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text2);
        text.setText("Android custom dialog example B!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image2);
        image.setImageResource(R.drawable.ic_launcher_web);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK2);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void dialogA() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example A");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.car);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        button = (Button) findViewById(R.id.buttonShowCustomDialog);
        button2 = (Button) findViewById(R.id.dialog2Button);


        // add button listener
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                switchButton(arg0.getId());
                // custom dialog
            }
        }
    );

        button2.setOnClickListener(new OnClickListener() {

                                      @Override
                                      public void onClick(View arg0) {
                                          switchButton(arg0.getId());
                                          // custom dialog
                                      }
                                  }
        );

    }
}


