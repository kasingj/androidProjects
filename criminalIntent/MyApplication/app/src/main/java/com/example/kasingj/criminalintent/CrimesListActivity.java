package com.example.kasingj.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by kasingj on 3/25/16.
 */
public class CrimesListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
