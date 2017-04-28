package com.project.rishabhsingh.dWarden;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private String baseURL = AppDataPreferences.URL+"student?email=";
    RequestQueue requestQueue;
    private TextView userName,userRoll,userBranch,userYear,userEmail,userBlood,userRoom;
    private String name,roll,branch,year,blood,room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName=(TextView)findViewById(R.id.user_profile_name);
        userRoll=(TextView)findViewById(R.id.user_profile_id);
        userBranch=(TextView)findViewById(R.id.user_profile_branch);
        userYear=(TextView)findViewById(R.id.user_profile_batch);
        userEmail=(TextView)findViewById(R.id.user_profile_email);
        userBlood=(TextView)findViewById(R.id.user_profile_blood);
        userRoom=(TextView)findViewById(R.id.user_room_allotted);
        load_Profile();

    }

    private void load_Profile() {

        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching your details...");
        progressDialog.show();
        String URL = baseURL+AppDataPreferences.getEmail(ProfileActivity.this);
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response=response.replace('[',' ');
                    response=response.replace(']',' ');
                    JSONObject jsonObject = new JSONObject(response);

                    if (!jsonObject.getString("studentname").equals("")) {
                        name = jsonObject.getString("studentname");
                    }
                    if (!jsonObject.getString("studentrollno").equals("")) {
                        roll = jsonObject.getString("studentrollno");
                    }
                    if (!jsonObject.getString("branchname").equals("")) {
                        branch = jsonObject.getString("branchname");
                    }
                    if (!jsonObject.getString("studentyear").equals("")) {
                        year = jsonObject.getString("studentyear");
                    }
                    if (!jsonObject.getString("studentbloodgp").equals("")) {
                        blood = jsonObject.getString("studentbloodgp");
                    }
                    if (!jsonObject.getString("studentroomno").equals("")) {
                        room = jsonObject.getString("studentroomno");
                    }
                    progressDialog.setMessage("Your profile is loaded...");
                    progressDialog.dismiss();
                    populate_profile();
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
                params.put("email", AppDataPreferences.getEmail(ProfileActivity.this));
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

    private void populate_profile() {

        userName.setText(name+"\nBIET Jhansi Undergraduate\nUttar Pradesh");
        userRoll.append("\t\t\t"+roll);
        userBranch.append("\t\t\t"+branch);
        userYear.append("\t\t\t"+year);
        userEmail.append("\t\t\t"+AppDataPreferences.getEmail(ProfileActivity.this));
        userBlood.append("\t\t\t"+blood);
        userRoom.append("\t\t\t"+room);
    }

}
