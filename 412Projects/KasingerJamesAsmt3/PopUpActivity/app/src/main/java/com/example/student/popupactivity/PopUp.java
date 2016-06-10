package com.example.student.popupactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;

public class PopUp extends DialogFragment {

    public interface message{

    }
    private EditText mEditText;
    public PopUp() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pop_up, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        getDialog().setTitle("Hello");

        setmEditText(mEditText, getArguments().getInt("button"));
        return view;
    }

    public void setmEditText(EditText textView , int mId){
        if(mId == R.id.butA) {
            textView.setText("graph 0");

        }else if(mId == R.id.butB){
            textView.setText("graph 1");
        }else if(mId == R.id.butC){
            textView.setText("graph 2");
        }else if(mId == R.id.butD){
            textView.setText("graph 3");
        }else{
            textView.setText("not sure");
        }
    }
}
