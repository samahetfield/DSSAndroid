package com.example.sergio.dssandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Registro extends AppCompatActivity {

    private EditText in_user, in_nombre, in_correo, in_pass;
    private Button btn_reg;
    private RequestQueue queue;
    private TextView serverResp;
    String param1="";
    private String url = "http://10.0.2.2:8080/DSSJava/rest/usuario?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        in_user = findViewById(R.id.user_input);
        in_nombre = findViewById(R.id.nombre_real);
        in_correo = findViewById(R.id.correo_user);
        in_pass = findViewById(R.id.pass_user);
        btn_reg = findViewById(R.id.btn_registrar);

        queue = Volley.newRequestQueue(this);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String para1 = "username="+in_user.getText().toString()+"&";
                para1 += "nombre="+in_nombre.getText().toString()+"&";
                para1 += "correo="+in_correo.getText().toString()+"&";
                para1 += "contrasena="+in_pass.getText().toString();

                url += para1;

                Log.d("URL_PUT", url);

                StringRequest stringRequest = new StringRequest(Request.Method.PUT,url,
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

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
