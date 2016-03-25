package com.example.kasingj.testfeatures;

/**
 * Created by kasingj on 3/24/16.
 */
public class TrueFalse {

    private int mQuestion;
    private boolean mTrueQuestion;


    public TrueFalse(int question,boolean trueQuestion){
        mQuestion=question;
        mTrueQuestion=trueQuestion;

    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;

    }

    public int getQuestion() {
        return mQuestion;
    }
}
