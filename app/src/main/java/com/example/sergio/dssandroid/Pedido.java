package com.example.sergio.dssandroid;

import android.Manifest;
import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Pedido extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private Marker marcador;
    private GoogleMap mMap;
    private double lat, lng;
    private String user_connected;
    private RequestQueue queue;
    private String url = "http://10.0.2.2:8080/DSSJava/rest/producto";
    private ArrayList<CheckBox> arrayCheckBox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        user_connected = extras.getString("username");
        lat = extras.getDouble("latitud");
        lng = extras.getDouble("longitud");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_farma);
        mapFragment.getMapAsync(this);

        queue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LinearLayout main_ac = findViewById(R.id.layout_prod);

                for (int i = 0; i < response.length(); i++) {
                    LinearLayout parent = new LinearLayout(Pedido.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    params.setMargins(16,16,16,16);

                    parent.setLayoutParams(params);

                    CheckBox checkBox = new CheckBox(Pedido.this);
                    try {
                        checkBox.setId((int) response.getJSONObject(i).getDouble("productoID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    TextView tv_nombre = new TextView(Pedido.this);
                    tv_nombre.setLayoutParams(params);
                    try {
                        tv_nombre.setText(response.getJSONObject(i).getString("nombre"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tv_nombre.setGravity(Gravity.CENTER);
                    parent.addView(checkBox);
                    parent.addView(tv_nombre);
                    main_ac.addView(parent);
                    arrayCheckBox.add(checkBox);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(jsonArrayRequest);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            LatLng coordenadas = new LatLng(lat, lng);
            CameraUpdate MiUbicaion = CameraUpdateFactory.newLatLng(coordenadas);

            marcador = mMap.addMarker(new MarkerOptions()
                    .position(coordenadas)
                    .title("Mi ubicación"));

            mMap.animateCamera(MiUbicaion);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order) {
            for(int i=0; i<arrayCheckBox.size(); i++){
                CheckBox checkBox = arrayCheckBox.get(i);
                String url_put = "http://10.0.2.2:8080/DSSJava/rest/order?";

                if(checkBox.isChecked()){

                    url_put += "id=155&";
                    url_put += "productoID="+checkBox.getId()+"&";
                    url_put += "username="+user_connected;

                    Log.d("String request", url_put);
                    StringRequest stringRequest = new StringRequest(Request.Method.PUT,url_put,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Respuesta", response);
                                }
                            },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


                    queue.add(stringRequest);
                }
            }

            Toast.makeText(this,   "Pedido realizado correctamente", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("username", user_connected);
            startActivity(intent);

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exit) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if(id == R.id.nav_inicio){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("username", user_connected);
            startActivity(intent);
        } else if(id == R.id.nav_medicamentos){
            Intent intent = new Intent(getApplicationContext(), Medicamentos.class);
            intent.putExtra("username", user_connected);
            startActivity(intent);
        } else if(id == R.id.nav_farmacias){
            Intent intent = new Intent(getApplicationContext(), Farmacias.class);
            intent.putExtra("username", user_connected);
            startActivity(intent);
        }else if(id == R.id.nav_mapa){
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("username", user_connected);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
