package com.example.sergio.dssandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static int PETICION_PERMISO_LOCALIZACION = 101 ;
    private GoogleMap mMap;
    Location location;
    private LocationManager locManager;
    private double lat;
    private double lng;
    private Marker marcador;
    private String direccion = "";


    String mensaje1;

    private LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            ActualizarUbicacion(location);
            setLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            mensaje1 = "GPS activado";
            mensaje();
        }

        @Override
        public void onProviderDisabled(String provider) {
            mensaje1 = "GPS Desactivado";
            locationStart();
            mensaje();
        }
    };

    private void mensaje() {
        Toast toast = Toast.makeText(this, mensaje1, Toast.LENGTH_LONG);
        toast.show();
    }

    private void setLocation(Location location) {
        if(location.getLatitude() != 0 && location.getLongitude() != 0){
            try{
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if(!list.isEmpty()){
                    Address Dir = list.get(0);
                    direccion = (Dir.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void locationStart() {
        LocationManager mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!gpsEnabled){
            Intent settingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingIntent);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PETICION_PERMISO_LOCALIZACION);

            return;
        }
        else{
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ActualizarUbicacion(location);
            locManager.requestLocationUpdates(locManager.GPS_PROVIDER, 1200, 0, locListener);

        }
    }

    private void ActualizarUbicacion(Location location) {
        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
            AgregarMarcador(lat,lng);
        }

    }

    private void AgregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate MiUbicaion = CameraUpdateFactory.newLatLng(coordenadas);
        if(marcador != null)
            marcador.remove();

        marcador = mMap.addMarker(new MarkerOptions()
            .position(coordenadas)
            .title("Mi ubicación"));

        mMap.animateCamera(MiUbicaion);

    }
}
