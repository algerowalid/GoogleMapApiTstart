package com.example.admin.googlemapapi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class
MapActivity extends FragmentActivity implements OnMapReadyCallback{


    private GoogleMap mMap;
    private LocationManager locationManager;

    Marker AncienMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        checkLocation();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng ab = null;

        //Ajouter des poisiton (Maylist est remplis sur le mylist )
        for (int i = 0; i<Constante.myList.size();i++){
            LatLng sydney = new LatLng(Constante.myList.get(i).getX1(),Constante.myList.get(i).getX2());
            mMap.addMarker(new MarkerOptions().position(sydney).title(Constante.myList.get(i).getNom()));

            ab = sydney;

        }

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Hennaya"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ab));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng point) {
                //TODO Handle your code.
                //Add marker to map for clarity
                Marker nowMarker = mMap.addMarker(new MarkerOptions().position(point).title("Ma Position"));
                if(AncienMarker!=null){
                    AncienMarker.remove();
                }
                AncienMarker = nowMarker;
            }
        });
    }



    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Activer votre localisation")
                .setMessage("Votre localisation est désactivé .\nVeuillez svp Activer votre localisation " +
                        "")
                .setPositiveButton("Paramétre", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Toast.makeText(MapActivity.this,"Localisation impossible",Toast.LENGTH_SHORT).show();
                    }
                });


        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
