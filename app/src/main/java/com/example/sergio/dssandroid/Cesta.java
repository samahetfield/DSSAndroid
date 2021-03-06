package com.example.sergio.dssandroid;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cesta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue queue;
    private String url="http://10.0.2.2:8080/DSSJava/rest/order";
    private String url_prod = "http://10.0.2.2:8080/DSSJava/rest/producto";
    private String user_connected="";
    private ArrayList<String> productos = new ArrayList<>();
    private ArrayList<Integer> id_productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        user_connected = extras.getString("username");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        queue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {
                final LinearLayout main_ac = findViewById(R.id.layout_cesta);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        if(response.getJSONObject(i).getJSONObject("usuario").getString("nombreUsuario").equals(user_connected)){
                            final int finalI1 = i;
                            JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(url_prod, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response1) {
                                    for(int j = 0; j< response1.length(); j++){

                                        try {
                                            if(response1.getJSONObject(j).getInt("productoID") == response.getJSONObject(finalI1).getJSONObject("producto").getInt("productoID")){
                                                LinearLayout parent = new LinearLayout(Cesta.this);
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                                params.setMargins(16,16,16,16);

                                                parent.setLayoutParams(params);

                                                ImageView iv = new ImageView(Cesta.this);
                                                iv.setImageResource(R.mipmap.medicamentos);

                                                TextView tv_nombre = new TextView(Cesta.this);
                                                tv_nombre.setLayoutParams(params);
                                                JSONObject jso = null;

                                                try {
                                                    jso = response1.getJSONObject(j);

                                                    tv_nombre.setText(jso.getString("nombre"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                tv_nombre.setGravity(Gravity.CENTER);
                                                tv_nombre.setTextSize(24);
                                                tv_nombre.setTypeface(tv_nombre.getTypeface(), Typeface.BOLD);
                                                parent.addView(iv);
                                                parent.addView(tv_nombre);
                                                main_ac.addView(parent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            queue.add(jsonArrayRequest1);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        Log.d("Tamaño", String.valueOf(id_productos.size()));

        queue.add(jsonArrayRequest);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
        } else if(id == R.id.nav_cesta){
            Intent intent = new Intent(getApplicationContext(), Cesta.class);
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
