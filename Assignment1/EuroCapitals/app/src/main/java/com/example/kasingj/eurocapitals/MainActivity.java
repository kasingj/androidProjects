package com.example.kasingj.eurocapitals;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private static CountryCapitals currentCountry = null;
    private static HashMap<String,String> map = new HashMap<>();
    private static ArrayList<CountryCapitals> aList = new ArrayList<CountryCapitals>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseFile();
        //set view at beggining
        TextView tv = (TextView)findViewById(R.id.countryView);
        currentCountry=getRandomCountry();
        tv.setText(currentCountry.getCountry());
        Button bttn = (Button)findViewById(R.id.nextButton);


        bttn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView tv = (TextView)findViewById(R.id.countryView);
                                        currentCountry = getRandomCountry();
                                        tv.setText(currentCountry.getCountry());
                                        TextView tvShowCapital = (TextView)findViewById(R.id.capitalView);
                                        tvShowCapital.setText("");
                                    }
                                }
        );

        Button showButton = (Button)findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvShowCapital = (TextView)findViewById(R.id.capitalView);
                tvShowCapital.setText(currentCountry.getCapital());
            }
        });
    }


    private CountryCapitals getRandomCountry() {
        //get random country from list
        Random r = new Random();
        CountryCapitals CC = aList.get( r.nextInt(aList.size())    );
        return CC;
    }






    void parseFile(){
    String country;
    String capital;

    BufferedReader reader=null;
    try {
        InputStreamReader is = new InputStreamReader(getAssets().open("EuroCountriesCapitals.csv"));
        reader = new BufferedReader(is);
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] RowData = line.split(",");
            country = RowData[0];
            capital = RowData[1];
            //CountryCapitals CC = new CountryCapitals(country, capital);
            aList.add(new CountryCapitals(country, capital));

            //map.put(country,capital);
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

    private class CountryCapitals{
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

        public void setCapital(String capital) {
            this.capital = capital;
        }

    }

}
