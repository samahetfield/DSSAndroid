package com.example.sergio.dssandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText user_in, pass_in;
    private TextView reg, user_checked;

    private String url ="http://10.0.2.2:8080/DSSJava/rest/usuario";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.iniciarsesion_btn);
        user_in = findViewById(R.id.usuario_input);
        pass_in = findViewById(R.id.contrase_input);
        reg = findViewById(R.id.registrarse);
        user_checked = findViewById(R.id.user_check);

        user_checked.setAlpha(0.0f);


        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = user_in.getText().toString();
                runner.execute(sleepTime);

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        private String user;
        ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String... params) {
            try {
                int time = 3000;

                JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++){
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                if(jo.getString("nombreUsuario").equals(user_in.getText().toString()) && jo.getString("contrasena").equals(pass_in.getText().toString())){
                                    resp = "true";
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                queue.add(jsonObjectRequest);

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
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", user_in.getText().toString());
                    startActivity(intent);
                }
                else {
                    progressDialog.dismiss();
                    pass_in.setError(getString(R.string.error_incorrect_password));
                    pass_in.requestFocus();
                }
            }
            else{
                progressDialog.dismiss();
                pass_in.setError(getString(R.string.error_incorrect_password));
                pass_in.requestFocus();
            }

        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "ProgressDialog",
                    "Comprobando usuario");
        }


        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}




