package com.example.kasingj.criminalintent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm = getSupportFragmentManager();
        //ask for fragment with view ID
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        //fragment transactions are used to add remove attach detach or
        // replace fragments in the frgment list
        //if frag already in list then just return it, if not in list create
        //frag might be in list if crimactivity created->destroyed->created...
        //fm saves a list of fragments.
        if(fragment == null){
            fragment = new CrimeFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
