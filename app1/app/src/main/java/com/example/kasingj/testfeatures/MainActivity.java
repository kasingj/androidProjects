package com.example.kasingj.testfeatures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_americas, true)
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button trueButton = (Button) findViewById(R.id.true_button);
        Button falseButton = (Button) findViewById(R.id.false_button);
        Button nextButton = (Button) findViewById(R.id.changeButton);
        Button mCheatButton = (Button) findViewById(R.id.cheat_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start cheat activity
                Intent i =new Intent(MainActivity.this, cheat.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(cheat.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater=false;
                updateQuestion();
            }
        });
        //if orientation change this retains current question between instances.
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        updateQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //nothing yet but soon
                checkAnswer(false);
                updateQuestion();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(cheat.EXTRA_ANSWER_SHOWN, false);
    }


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;
        if(mIsCheater){
            messageResId= R.string.judgment_toast;
        }else{
        if (answerIsTrue == userPressedTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        }
        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}