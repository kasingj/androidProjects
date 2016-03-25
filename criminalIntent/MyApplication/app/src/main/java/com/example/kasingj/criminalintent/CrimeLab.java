package com.example.kasingj.criminalintent;
import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kasingj on 3/25/16.
 */

public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    //having a context allows the singleton to start activities access resources etc
    private CrimeLab(Context appContext) {
        mAppContext=appContext;
        mCrimes= new ArrayList<Crime>();
        //creates 100 crimes for the arraylist model layer
        for(int i=0;i<100;i++){
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i%2 == 0); //every other one
            mCrimes.add(c);
        }
    }


    public static CrimeLab getInstance(Context c) {
        if(sCrimeLab==null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
            //this context is global to your application, use for application wide singletons
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime c : mCrimes){
            if(c.getId()==id){return c;}
        }
    return null;
    }

}
