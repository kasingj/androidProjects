package com.example.student.quitmap;

import com.google.android.gms.maps.model.LatLng;
/**
 * Created by student on 5/26/16.
 */
public class diaryEntry {
    private int mId;
    private String mLatLong;
    private String mDiaryEntry;
    private String mDate;
    private int mContentType;



    public diaryEntry(int id, String latlong, String diaryEntry, int contentType, String date) {

        this.mId = id;
        this.mLatLong = latlong;
        this.mDiaryEntry = diaryEntry;
        this.mContentType = contentType;
        //format timebnbnbbbbbnbn
        this.mDate = date;
    }

    public int getId() {
        return mId;
    }

    public String getmDate() {
        return mDate;
    }
    public int getContentType() {
        return mContentType;
    }
    public void setId(int id) {
        this.mId = id;
    }
    public String getLatLongString() {
        return mLatLong;
    }
    public LatLng getLatLong(){
        String newString = mLatLong.replace("lat/lng: (", "");
        newString = newString.replace(")","");
        String[] ll = newString.split(",");

        return new LatLng( Double.parseDouble(ll[0]) , Double.parseDouble(ll[1]) );
    }
    public String getDiaryEntry() {
        return mDiaryEntry;
    }

}
