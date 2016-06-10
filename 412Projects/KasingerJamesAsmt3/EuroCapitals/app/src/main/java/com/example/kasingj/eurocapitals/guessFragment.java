package com.example.kasingj.eurocapitals;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kasingj on 4/23/16.
 */
public class guessFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.guess_fragment, container, false);
        setButtons(fragmentView);
        return fragmentView;
    }

    private void setButtons(View frag) {
        Button bttn1 = (Button)frag.findViewById(R.id.nextButton);

//next button
        bttn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Activity activity = getActivity();
                                        capitalsCoordinator coordinator = (capitalsCoordinator)activity;
                                        coordinator.changeCountry();
                                    }
                                }
        );


        Button bttn2 = (Button) frag.findViewById(R.id.showButton);

//show button
        bttn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Activity activity = getActivity();
                                        capitalsCoordinator coordinator = (capitalsCoordinator)activity;
                                        coordinator.displayCapital();
                                    }
                                }
        );


        Button bttn3 = (Button)frag.findViewById(R.id.deleteButton);

//delete button
        bttn3.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Activity activity = getActivity();
                                         capitalsCoordinator coordinator = (capitalsCoordinator)activity;
                                         coordinator.deleteCard();
                                     }
                                 }
        );

        Button bttn4 = (Button)frag.findViewById(R.id.resetButton);

//next button
        bttn4.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Activity activity = getActivity();
                                         capitalsCoordinator coordinator = (capitalsCoordinator)activity;
                                         coordinator.resetDeck();
                                     }
                                 }
        );

    }
}
