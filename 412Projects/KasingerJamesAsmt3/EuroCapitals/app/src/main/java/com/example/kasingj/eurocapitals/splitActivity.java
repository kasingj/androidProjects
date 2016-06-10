package com.example.kasingj.eurocapitals;

/**
 * Created by kasingj on 4/22/16.
 */
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class
splitActivity extends AppCompatActivity implements splitActivityInterface{
    int mIndex=0;
    Button bttn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_activity);
        FragmentManager fm = getSupportFragmentManager();
        setStudyButton(mIndex);
    }


    public void setStudyButton(final int index){

        bttn = (Button)findViewById(R.id.studyButton);
        bttn.setOnClickListener(null);
        final Intent intent = new Intent(splitActivity.this, MainActivity.class);
        intent.putExtra("index", index);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIndex != 0) {
                    splitActivity.this.startActivity(intent);

                } else {
                    Toast.makeText(splitActivity.this, "You didn't select a deck!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void changeDescription(int index) {
        FragmentManager fm = getSupportFragmentManager();
        descriptionFragment dFrag = (descriptionFragment) fm.findFragmentById(R.id.descriptionFragment);
        mIndex = index;
        dFrag.setdescriptionView(index);
        setStudyButton(index);
    }
}
