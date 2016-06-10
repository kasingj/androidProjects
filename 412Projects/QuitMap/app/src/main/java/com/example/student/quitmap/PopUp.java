package com.example.student.quitmap;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import java.util.Date;

public class PopUp extends DialogFragment {

    public static final int SMOKED_CONTENT_TYPE = 1;
    public static final int CRAVED_CONTENT_TYPE = 2;
    public static final int RESISTED_CONTENT_TYPE = 3;
    public interface message{

    }
    private Button saveBttn;
    public PopUp() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pop_up, container);
        getDialog().setTitle("Hello");

        int iRadioButton = Integer.parseInt( getArguments().get("actionId").toString() );
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.contentType);

        switch (iRadioButton){
            case R.id.craving:
                rg.check( R.id.icrave );
                break;
            case R.id.smoked:
                rg.check( R.id.ismoked );
                break;
            case R.id.resisted:
                rg.check( R.id.iresist );
                break;
            case MapsActivity.DEFAULT:
                break;
        }


        saveBttn = (Button) view.findViewById(R.id.saveDiary);
        saveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveDiaryEntry();
                    dismiss();
            }
        });
        return view;
    }



    private void saveDiaryEntry() {
        int id = MapsActivity.getNewID();
        DatabaseOperations dbop =new DatabaseOperations(getContext());

        String ll = getArguments().get("latlong").toString();

        EditText tv = (EditText) getView().findViewById(R.id.diaryContent);
        String diaryContent = tv.getText().toString();
        RadioGroup rg = (RadioGroup) getView().findViewById(R.id.contentType);


        int type = convertIdToType(rg.getCheckedRadioButtonId() );
        diaryEntry entry = new diaryEntry(id, ll, diaryContent, type, (new Date()).toString() );

        DatabaseOperations.saveDiaryEntry(dbop, entry);

        updateMap upMapInterface = (updateMap) getActivity();
        upMapInterface.updateMap(entry);
        //save all fields

    }

    private int convertIdToType(int contentId) {
        switch ( contentId ){
            case R.id.icrave:
                return CRAVED_CONTENT_TYPE;

            case R.id.ismoked:
                return SMOKED_CONTENT_TYPE;
            case R.id.iresist:
                return RESISTED_CONTENT_TYPE;
            default:
                return 0;
        }
    }


}
