package com.example.kasingj.eurocapitals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kasingj on 4/23/16.
 */
public class capitalFragment extends Fragment {
    TextView country;
    TextView capital;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.capital_fragment, container,false);
        country = (TextView)v.findViewById(R.id.countryView);
        capital = (TextView)v.findViewById(R.id.capitalView);
        return v;
    }

public void changeCountry(MainActivity.CountryCapitals CC){
    country.setText(CC.getCountry());
    capital.setVisibility(View.INVISIBLE);
    capital.setText(CC.getCapital());
}
    public void showCapital(){
        capital.setVisibility(View.VISIBLE);
    }
}
