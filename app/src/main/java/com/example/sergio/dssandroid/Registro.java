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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sergio.dssandroid.servidor.Farmacia;
import com.example.sergio.dssandroid.servidor.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText in_user, in_nombre, in_correo, in_pass;
    private Button btn_reg;
    private RequestQueue queue;
    private TextView serverResp;
    String param1="";
    private String url = "http://10.0.2.2:8080/DSSJava/rest/producto?";

    public static final int MY_DEFAULT_TIMEOUT = 30000;


    private Usuario usuario;

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
                usuario = new Usuario(in_nombre.getText().toString(), in_user.getText().toString(), in_correo.getText().toString(), in_pass.getText().toString());

                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
                asyncTaskRunner.execute();
            }
        });

    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            try {
                int time = 3000;

                String para1 = in_user.getText().toString();
                para1.replaceAll(" ", "+");
                Log.d("parametro1", para1);
                param1 = "nombre="+para1;

                url += param1;

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


                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //progressDialog.dismiss();
            //reg.setText(result);

            if(result != null){
                if(result.equals("true")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    progressDialog.dismiss();
                }
            }
            else{
                progressDialog.dismiss();
            }

        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Registro.this,
                    "ProgressDialog",
                    "Comprobando usuario");
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}
