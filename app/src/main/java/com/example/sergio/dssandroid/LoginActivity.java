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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText user_in, pass_in;
    private TextView reg, user_checked;

    private String url ="http://www.mocky.io/v2/5c2cdc612e0000070ae877cf";
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

/*
                String user = String.valueOf(user_in.getText());
                String pass = String.valueOf(pass_in.getText());
                LoginTask loginTask = new LoginTask();
                loginTask.execute(user, pass);
  */
            }
        });

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Logging"); // Calls onProgressUpdate()
            try {
                int time = 3000;

                final JSONObject jsonObject = new JSONObject();

                jsonObject.put("correo", user_in.getText().toString());
                jsonObject.put("pass", pass_in.getText().toString());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        resp = "true";
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resp = error.getMessage();
                            //Log.d("Error", error.getMessage());
                        }
                });

/*
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                resp = "true";
                                Log.d("Respuesta", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resp = error.getMessage();
                        //Log.d("Error", error.getMessage());
                    }
                });
*/
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

            if(result.equals("true")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
            user_in.setText(text[0]);
        }
    }
}




