package com.example.kasingj.eurocapitals;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements capitalsCoordinator{
    private static CountryCapitals currentCountry = null;
    private static HashMap<String,String> map = new HashMap<>();
    private static ArrayList<CountryCapitals> aList = new ArrayList<CountryCapitals>();
    capitalFragment displayFrag;
    private int deckIndex;
    private int currentCardIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_main_activity);
        Intent intent =  getIntent();
        deckIndex = 0;
        deckIndex = intent.getIntExtra("index", deckIndex) ;
        parseFile(deckIndex);
        changeCountry();
    }

    private CountryCapitals getRandomCountry() {
        //get random country from list
        Random r = new Random();
        currentCardIndex = r.nextInt(aList.size());
        CountryCapitals CC = aList.get(currentCardIndex);
        return CC;
    }

    void parseFile(int deckIndex){
    String country;
    String capital;
        String deck="";
        aList.clear();

    BufferedReader reader=null;
    try {
        switch (deckIndex){
            case 1:
                deck = "EuroCountriesCapitals.csv";
                break;
            case 2:
                deck = "USstateCapitals.csv";
                break;
            case 3:
                deck = "SAmerCountryCaps.csv";
                break;
            case 4:
                deck = "CanadianProvinceCaps.csv";
                break;
            case 5:
                deck = "AfricanCountryCaps.csv";
                break;
        }
        InputStreamReader is = new InputStreamReader(getAssets().open(deck));

        reader = new BufferedReader(is);
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] RowData = line.split(",");
            country = RowData[0];
            capital = RowData[1];
            aList.add(new CountryCapitals(country, capital));

        }
    }
    catch (IOException ex) {
        ex.printStackTrace();
    }
    finally {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // handle exception
                e.printStackTrace();
            }
        }
    }
}

    @Override
    public void changeCountry() {

        if(aList.size() ==1){
            Toast.makeText(this,"No more cards in the deck!",Toast.LENGTH_SHORT).show();
            return;
        }else {
            FragmentManager fg = getSupportFragmentManager();
            displayFrag = (capitalFragment) fg.findFragmentById(R.id.display);
            CountryCapitals CC = getRandomCountry();
            displayFrag.changeCountry(CC);
        }
        }

    @Override
    public void displayCapital() {
        displayFrag.showCapital();
    }

    @Override
    public void deleteCard() {
        if(aList.size() ==1){
            Toast.makeText(this,"No more cards in the deck!",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"Card deleted",Toast.LENGTH_SHORT).show();
        aList.remove(currentCardIndex);
        changeCountry();
    }

    @Override
    public void resetDeck() {
        parseFile(deckIndex);
        Toast.makeText(this,"The deck has been reset!",Toast.LENGTH_SHORT).show();
    }

    public class CountryCapitals{
        public String country="";
        public String capital="";
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }

        public CountryCapitals(String country, String capital) {
            this.country = country;
            this.capital = capital;
        }

        public String getCapital() {
            return capital;
        }
    }
}
