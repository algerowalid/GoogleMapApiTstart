package com.example.admin.googlemapapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Constante.myList = new ArrayList<Positions>();

        Positions pos = new Positions(1, "CHERIFI","WALID","-", 34.919452, -1.294377);
        Positions pos1 = new Positions(1, "CHERIFI","WALID","-", 34.921009, -1.295225);
        Positions pos2= new Positions(1, "CHERIFI","WALID","-", 34.920754, -1.288883);

        Constante.myList.add(pos);
        Constante.myList.add(pos1);
        Constante.myList.add(pos2);

        // calcul distance en tre deux points
        //double met = distance_in_meter(pos.getX1(), pos.getX2(), pos2.getX1(),pos2.getX2());




        Intent dsl = new Intent(MainActivity.this, MapActivity.class);
        startActivity(dsl);

    }


    private static double distance_in_meter(final double lat1, final double lon1, final double lat2, final double lon2) {
        double R = 6371000f; // Radius of the earth in m
        double dLat = (lat1 - lat2) * Math.PI / 180f;
        double dLon = (lon1 - lon2) * Math.PI / 180f;

        LatLng latlong1 = new LatLng(lat1,lon1);
        LatLng latlong2 = new LatLng(lat2,lon2);


        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(latlong1.latitude * Math.PI / 180f) * Math.cos(latlong2.latitude * Math.PI / 180f) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d;
    }
}
