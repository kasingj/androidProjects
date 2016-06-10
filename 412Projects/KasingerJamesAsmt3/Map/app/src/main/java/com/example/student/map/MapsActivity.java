package com.example.student.map;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView location;
    private Marker m1;
    private List<Marker> list;
    private Button clearMap;
    private Button howTo;
    private CheckBox drawPaths;
    private ArrayList<locDetails> aList = new ArrayList<locDetails>();
    private ArrayList<LatLng> bList = new ArrayList<LatLng>();
    private LatLng prevLocation;
    private AlertDialog instructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Explore the top 20 historic museums in the United States!\n1. Use a long touch to create your own markers\n" +
                "2. Check the draw Path box and use long touches to draw paths.\n3. Use the reset button to clear paths and markers.");
        builder.setCancelable(false);
        builder.setPositiveButton(
                "Got it",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        instructions = builder.create();
        instructions.show();
        howTo = (Button)findViewById(R.id.howTo);
        howTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.show();
            }
        });

        location = (TextView)findViewById(R.id.location);
        drawPaths=(CheckBox)findViewById(R.id.drawPaths);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpSpinner();

        clearMap = (Button)findViewById(R.id.clearMap);
        clearMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkReady()) {
                    return;
                }
                mMap.clear();
            }
        });

    }

    public void setUpSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.museumArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parseMuseumFile();
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locDetails loc = aList.get(position);
                mMap.addMarker(new MarkerOptions().position( loc.getmLoc() ).title(loc.mName));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(loc.getCameraPostion()) , 2000, null );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    private void parseMuseumFile() {
        Double latitude;
        Double longitude;
        String name;
        String deck="museums.csv";
        aList.clear();

        BufferedReader reader=null;
        try {

            InputStreamReader is = new InputStreamReader(getAssets().open(deck));

            reader = new BufferedReader(is);
            String line;

            while ((line = reader.readLine()) != null) {
                String[] RowData = line.split(",");
                name = RowData[0];
                latitude = Double.parseDouble(RowData[1]);
                longitude = Double.parseDouble(RowData[2]);
                aList.add(new locDetails(name, new LatLng(latitude,longitude)));

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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        location.setText("" +latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (drawPaths.isChecked()){
            if(prevLocation == null){
               prevLocation=latLng;
            }else {
                mMap.addPolyline((new PolylineOptions())
                        .add(prevLocation, latLng));
                prevLocation=latLng;
            }
        }else{
            prevLocation = null;
        }
            location.setText("" + latLng);
            m1 = mMap.addMarker(new MarkerOptions().position(latLng));
    }

    //data structure to hold location details


    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, "Map not ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private  class locDetails{
        private String mName;
        private LatLng mLoc;
        private CameraPosition mPos;

        public locDetails(String name,LatLng loc) {
            mLoc = loc;
            mName = name;
            buildCamera();
        }

        public CameraPosition getCameraPostion(){
            return mPos;
        }

        private void buildCamera(){
            mPos = new CameraPosition.Builder().target(mLoc)
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();
        }
        public LatLng getmLoc() {
            return mLoc;
        }
    }
}