package com.example.student.quitmap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, OnMapClickListener, OnMapLongClickListener, updateMap, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private ArrayList<diaryEntry> diaryEntries = new ArrayList<diaryEntry>();
    private static int numEntries = 0;
    android.location.LocationManager mLocManager;
    LocationListener mLocListener;
    public String curLatLong = "";
    public Button craved;
    public Button resisted;
    public Button smoked;
    public Button howTo;
    public static final int PERMISSION_ACCESS_FINE_LOCATION =1;
    private GoogleApiClient googleApiClient;
    public static final int DEFAULT = 0;
    AlertDialog instructions;

    public static int getNewID() {
        return ++numEntries;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setup location google client
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        //Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you trying to quit smoking? Keep track the thoughts and feelings you have on your way through the immersive experience of journaling!\n1. Use a long touch to create a marker and log in your journal\n" +
                "2. Use the 3 actions at the bottom to log an experience at your current location.\n3. Be sure to click on the markers to see what you wrote at that time\n4. Green markers mean you resisted a craving, blue markers mean you had a craving, red markers mean you smoked.");
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

        //set buttons
        howTo = (Button)findViewById(R.id.instructions);
        howTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructions.show();
            }
        });

        craved = (Button)findViewById(R.id.craving);
        resisted = (Button)findViewById(R.id.resisted);
        smoked = (Button)findViewById(R.id.smoked);

        craved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markCurrentLoc(R.id.craving);
            }
        });

        resisted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markCurrentLoc(R.id.resisted);
            }
        });

        smoked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markCurrentLoc(R.id.smoked);
            }
        });

        //permission check
        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocListener = new MyLocationListener();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_FINE_LOCATION);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    private void populateMap() {
        DatabaseOperations dbop = new DatabaseOperations(this);
        Cursor cursor = DatabaseOperations.pullDiaryEntries(dbop);
        //get column indices
        int iLatLong = cursor.getColumnIndex(TableData.TableInfo.LATLONG);
        int iId = cursor.getColumnIndex(TableData.TableInfo.DIARY_ID);
        int iContent = cursor.getColumnIndex(TableData.TableInfo.DIARY_CONTENT);
        int iTime = cursor.getColumnIndex(TableData.TableInfo.TIME);
        int iType = cursor.getColumnIndex(TableData.TableInfo.CONTENT_TYPE);

        //add entries to list
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            numEntries++;
            //create diary entry
            diaryEntry entry = new diaryEntry(cursor.getInt(iId), cursor.getString(iLatLong), cursor.getString(iContent), cursor.getInt(iType), cursor.getString(iTime));

            //add to list.
            diaryEntries.add(entry);
        }
        cursor.close();
        applyEntriesToMap();
    }

    private void applyEntriesToMap() {
        //use the diary entry array add to map
        for (diaryEntry entry : diaryEntries) {
            //create marker, apply label, geo coordinates set camera position
            if (checkReady()) {
                addColoredMarker(entry);
            }
        }

    }

    public void addColoredMarker(diaryEntry entry){
        int contentType = entry.getContentType();
        Marker m1;
        LatLng ll = entry.getLatLong();
        switch ( contentType ){
            case PopUp.CRAVED_CONTENT_TYPE:
                m1 = mMap.addMarker(new MarkerOptions().position(ll).title("" + entry.getmDate()).snippet(entry.getDiaryEntry())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE ) ));
                break;
            case PopUp.SMOKED_CONTENT_TYPE:
                m1 = mMap.addMarker(new MarkerOptions().position(ll).title("" + entry.getmDate()).snippet(entry.getDiaryEntry())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED ) ));
                break;
            case PopUp.RESISTED_CONTENT_TYPE:
                m1 = mMap.addMarker(new MarkerOptions().position(ll).title("" + entry.getmDate()).snippet(entry.getDiaryEntry())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                break;
            default:
                m1 = mMap.addMarker(new MarkerOptions().position(ll).title("" + entry.getmDate()).snippet(entry.getDiaryEntry())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng wwu = new LatLng(48.732840, -122.485472);
        mMap.addMarker(new MarkerOptions().position(wwu).title("Bellingham"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wwu,15));
        populateMap();
    }


    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showPopUpWindow(LatLng latLng, int actionId) {
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("latlong", latLng.latitude + "," + latLng.longitude);
        bundle.putString("actionId",""+actionId);

        PopUp popup = new PopUp();
        //popup.setTargetFragment
        popup.setArguments(bundle);
        popup.show(fm, "fragment_edit_name");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //do nothing
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // pop up dialogue entry.
        showPopUpWindow(latLng, DEFAULT );
    }


    @Override
    public void updateMap(diaryEntry entry) {
        //interface method called from popUp fragment
        if (checkReady()) {
            LatLng loc = entry.getLatLong();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15));
            addColoredMarker(entry);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if(lastLocation != null){
            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
            curLatLong = lat+","+lon;
        }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}




    public LatLng getLatLong(String LatLong){
        String[] ll = LatLong.split(",");
        LatLng latlong;
        try {
             latlong = new LatLng( Double.parseDouble(ll[0]) , Double.parseDouble(ll[1]) );
        } catch (NumberFormatException e){
            latlong = new LatLng(48.732840, -122.485472);
        }

        return latlong;
    }

    public void markCurrentLoc(int viewId) {
        showPopUpWindow(  getLatLong(curLatLong) , viewId );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
     }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );

            curLatLong = location.getLatitude() + "," + location.getLongitude();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String arg0) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }
}

/*************************************************************************************/
