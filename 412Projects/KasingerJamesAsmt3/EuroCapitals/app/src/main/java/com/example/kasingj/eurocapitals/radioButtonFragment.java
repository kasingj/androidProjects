package com.example.kasingj.eurocapitals;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by kasingj on 4/22/16.
 */
public class radioButtonFragment extends Fragment implements View.OnClickListener{
public String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.radio_button_frag_view, container, false);
        setAllListeners(v);
        return v;
    }

private void setAllListeners(View v){
    setUpListener(v, R.id.europe);
    setUpListener(v, R.id.unitedStates);
    setUpListener(v, R.id.southAmerica);
    setUpListener(v, R.id.canada);
    setUpListener(v, R.id.africa);
}


    private void setUpListener(View v, int child){
        View childView = v.findViewById(child);
        childView.setOnClickListener(this);
    }

    int translateToIndex(int id){
        int index = 0;
        switch(id){
            case R.id.europe:
                index = 1;
                break;
            case R.id.unitedStates:
                index = 2;
                break;
            case R.id.southAmerica:
                index = 3;
                break;
            case R.id.canada:
                index = 4;
                break;
            case R.id.africa:
                index = 5;
                break;
        }
        return index;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        RadioButton rb = (RadioButton) v;
        //CharSequence index = rb.getText();
        int index = translateToIndex(id);

        Activity activity= getActivity();
        splitActivityInterface coordinator = (splitActivityInterface)activity;
        coordinator.changeDescription(index);

    }
}