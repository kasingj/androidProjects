package com.example.student.quitmap;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JournalActivity extends AppCompatActivity {

    Button smokeEntries;
    Button resistEntries;
    Button cravedEntries;
    LinearLayout diaryContainer;
    ArrayList<diaryEntry> smokedList = new ArrayList<diaryEntry>();
    ArrayList<diaryEntry> cravedList = new ArrayList<diaryEntry>();
    ArrayList<diaryEntry> resistList = new ArrayList<diaryEntry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        diaryContainer = (LinearLayout) findViewById(R.id.diaryContainer);
        pullEntries();
        setButtons();
    }

    private void pullEntries() {
        DatabaseOperations dbop = new DatabaseOperations(this);
        Cursor cursor = DatabaseOperations.pullDiaryEntries(dbop);

        int iLatLong = cursor.getColumnIndex(TableData.TableInfo.LATLONG);
        int iId = cursor.getColumnIndex(TableData.TableInfo.DIARY_ID);
        int iContent = cursor.getColumnIndex(TableData.TableInfo.DIARY_CONTENT);
        int iTime = cursor.getColumnIndex(TableData.TableInfo.TIME);
        int iType = cursor.getColumnIndex(TableData.TableInfo.CONTENT_TYPE);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            //create diary entry
            diaryEntry entry = new diaryEntry(cursor.getInt(iId), cursor.getString(iLatLong), cursor.getString(iContent), cursor.getInt(iType), cursor.getString(iTime).toString() );
            int contentType = entry.getContentType();
            switch ( contentType ){
                case PopUp.CRAVED_CONTENT_TYPE:
                    cravedList.add(entry);
                    break;
                case PopUp.SMOKED_CONTENT_TYPE:
                    smokedList.add(entry);
                    break;
                case PopUp.RESISTED_CONTENT_TYPE:
                    resistList.add(entry);
                    break;
                default:
                    break;
            }
        }
        cursor.close();
        Log.d("JournalActivity", "made it through all entries");
    }

    private void setButtons() {
        smokeEntries = (Button) findViewById(R.id.smokedEntries);
        resistEntries = (Button)findViewById(R.id.resistEntries);
        cravedEntries = (Button)findViewById(R.id.cravedEntries);
        diaryContainer.removeAllViews();

        smokeEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToScrollView(smokedList);
            }
        });

        resistEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToScrollView(resistList);
            }
            }
        );

        cravedEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addToScrollView(cravedList);
        }
        });
    }


    public void addToScrollView(ArrayList<diaryEntry> list){
        if(!list.isEmpty()) {
            diaryContainer.removeAllViews();
            for (diaryEntry entries: list) {
                View child = getLayoutInflater().inflate(R.layout.post, null);
                TextView tv = (TextView)child.findViewById(R.id.postContent);
                //set diary entry content.
                tv.setText( entries.getDiaryEntry() ) ;
                TextView time = (TextView)child.findViewById(R.id.postTime);
                time.setText( entries.getmDate().toString() );
                diaryContainer.addView(child);
            }
            return;
        }
        Toast.makeText(JournalActivity.this, "No entries available", Toast.LENGTH_SHORT).show();
        return;
    }
}
