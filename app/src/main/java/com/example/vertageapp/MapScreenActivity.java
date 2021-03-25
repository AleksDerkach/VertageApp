package com.example.vertageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MapScreenActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String REQUEST_URL = "https://2fjd9l3x1l.api.quickmocker.com/kyiv/places";

    private GoogleMap mMap;

    private String[] names = new String[3];

    private List<Places> listPlaces;

    private ListView listView;

    private MyTask mt;

    private URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        String txtLogin = getIntent().getStringExtra("login");

        setTitle(txtLogin);

        mt = new MyTask();
        mt.execute(REQUEST_URL);

        //Toast.makeText(this, txtLogin, Toast.LENGTH_SHORT).show();
    }
    class MyTask extends AsyncTask<String, Void, List<Places> > {

        @Override
        protected List<Places>  doInBackground(String... strings) {
            List<Places> places = QueryUtils.fetchEarthquakeData(strings[0]);
            return places;
        }

        @Override
        protected void onPostExecute(List<Places> resPlaces) {
            if (resPlaces == null) return;
            super.onPostExecute(resPlaces);
            listPlaces = resPlaces;
            for (int i = 0; i<resPlaces.size(); i++) {
                names[i] = resPlaces.get(i).getName();
            }

            listView = findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapScreenActivity.this,
                    android.R.layout.simple_list_item_1, names);

            listView.setAdapter(adapter);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync((OnMapReadyCallback) MapScreenActivity.this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for (int i = 0; i < listPlaces.size(); i++) {
            LatLng place = new LatLng(listPlaces.get(i).getLat(), listPlaces.get(i).getLng());
            mMap.addMarker(new MarkerOptions()
                .position(place)
                .title("Marker in " + listPlaces.get(i).getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        }
    }
}