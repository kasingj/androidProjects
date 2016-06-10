package com.example.student.popupactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bttn = (Button)findViewById(R.id.butA);
        bttn.setOnClickListener(this);
        Button bttn2 = (Button)findViewById(R.id.butB);
        bttn2.setOnClickListener(this);
        Button bttn3 = (Button)findViewById(R.id.butC);
        bttn3.setOnClickListener(this);
        Button bttn4 = (Button)findViewById(R.id.butD);
        bttn4.setOnClickListener(this);



    }


    private void showEditDialog(int id) {
        Toast.makeText(MainActivity.this, "showEditDialog", Toast.LENGTH_SHORT).show();
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("button", id);
        PopUp popup = new PopUp();
        popup.setArguments(bundle);
        popup.show(fm, "fragment_edit_name");

    }

    @Override
    public void onClick(View v) {
        showEditDialog(v.getId());
    }
}