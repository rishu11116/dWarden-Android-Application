package com.project.rishabhsingh.dWarden.AppInitiation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.rishabhsingh.dWarden.AccountsManagementSystem.LoginActivity;
import com.project.rishabhsingh.dWarden.AccountsManagementSystem.SignUpActivity;
import com.project.rishabhsingh.dWarden.AppDataPreferences;
import com.project.rishabhsingh.dWarden.HomePageActivity;
import com.project.rishabhsingh.dWarden.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(AppDataPreferences.isIntroNeeded(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this,IntroActivity.class));
                    finish();
                }
                else {
                    if(AppDataPreferences.isTokenSet(SplashActivity.this)) {
                        checkDetails();
                    }
                    else
                    {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            }
        },2000);
    }

    private void checkDetails() {

        String URL = AppDataPreferences.URL+"student?email="+AppDataPreferences.getEmail(SplashActivity.this);
        requestQueue = Volley.newRequestQueue(SplashActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.length()>2) {
                        response=response.replace('[',' ');
                        response=response.replace(']',' ');
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(SplashActivity.this,"Welcome "+jsonObject.getString("studentname"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this,HomePageActivity.class));
                        finish();
                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this,SignUpActivity.class);
                        intent.putExtra("Email",AppDataPreferences.getEmail(SplashActivity.this));
                        startActivity(intent);
                        finish();
                        Toast.makeText(SplashActivity.this,"Please update your details.",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", AppDataPreferences.getEmail(SplashActivity.this));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("authorization", "token ce3fe9a203703c7ea3da8727ff8fbafec8ddbf44");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showDialogOnExit();
    }

    private void showDialogOnExit() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setTitle("Do you want to exit ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
