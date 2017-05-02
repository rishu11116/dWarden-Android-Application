package com.project.rishabhsingh.dWarden;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SampleActivity extends AppCompatActivity {

    ArrayList<String> branchList;
    RequestQueue requestQueue;
    String URL = AppDataPreferences.URL+"branch";
    boolean isSpinnerInitial = true;
    Spinner spinner;
    ProgressDialog progressDialog;
    static String branch = null;
    int branchPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        branchList=new ArrayList<>();
        branchList.add("--Select--");
        loadBranches();

        spinner = (Spinner)findViewById(R.id.spinnerBranch);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                branchPosition = position;
                if (isSpinnerInitial) {
                    branch = parent.getItemAtPosition(position).toString();
                    isSpinnerInitial = false;
                } else {
                    if (position == 0) ;
                    else {
                        branch = parent.getItemAtPosition(position).toString();
                        Toast.makeText(getBaseContext(),branch, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadBranches() {

        progressDialog = new ProgressDialog(SampleActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(SampleActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject branchObject = jsonArray.getJSONObject(i);
                        String branchName = branchObject.getString("id");
                        branchList.add(branchName);
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(SampleActivity.this, android.R.layout.simple_spinner_dropdown_item,branchList));
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("authorization","token ce3fe9a203703c7ea3da8727ff8fbafec8ddbf44");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
