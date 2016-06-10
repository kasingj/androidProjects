package com.example.kasingj.eurocapitals;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kasingj on 4/22/16.
 */


public class descriptionFragment extends Fragment{



    TextView descriptionView;
    String defaultDescription="Select the deck you wish to study.";
    String[] sArrayResource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sArrayResource = getResources().getStringArray(R.array.radio_options);

        View fragmentView = inflater.inflate(R.layout.description_frag_view, container, false);
        descriptionView = (TextView)fragmentView.findViewById(R.id.description) ;
        descriptionView.setText(defaultDescription);

        return fragmentView;
    }

    public void setdescriptionView(int index){
        descriptionView.setText(sArrayResource[index]);
    }

}
