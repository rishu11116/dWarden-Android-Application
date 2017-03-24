package com.project.rishabhsingh.ludus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollegeChooserActivity extends AppCompatActivity {

    private ArrayList<String> collegeList;
    RequestQueue requestQueue;
    String URL = "https://bkjbhckjhkjgfdgd";

    private boolean isSpinnerInitial = true;
    Spinner spinner;
    Button nextButton;
    ArrayAdapter<CharSequence> stateAdapter;
    ArrayAdapter<String> collegeAdapter;
    public static String state = null;
    public static String college = null;
    int statePosition,collegePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_chooser);

        collegeList = new ArrayList<>();
        nextButton=(Button)findViewById(R.id.next_button);
        spinner = (Spinner)findViewById(R.id.spinnerState);
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.state, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stateAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                statePosition = position;
                if (isSpinnerInitial) {
                    state = String.valueOf(position);
                    isSpinnerInitial = false;
                    Toast.makeText(CollegeChooserActivity.this,state,Toast.LENGTH_SHORT).show();
                } else {
                    if (position == 0) ;
                    else {
                        state = String.valueOf(position);
                        Toast.makeText(CollegeChooserActivity.this,state,Toast.LENGTH_SHORT).show();
                    }
                }
                if (state != null) {
                    loadColleges();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner = (Spinner)findViewById(R.id.spinnerCollege);
        collegeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,collegeList);
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(collegeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                collegePosition = position;
                if (isSpinnerInitial) {
                    college = String.valueOf(position);
                    isSpinnerInitial = false;
                    Toast.makeText(CollegeChooserActivity.this,college,Toast.LENGTH_SHORT).show();
                } else {
                    if (position == 0) ;
                    else {
                        college = String.valueOf(position);
                        Toast.makeText(CollegeChooserActivity.this,college,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CollegeChooserActivity.this,SignUpActivity.class));
            }
        });
    }

    private void loadColleges() {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                requestQueue = Volley.newRequestQueue(CollegeChooserActivity.this);
                Map<String, String> param = new HashMap<>();
                param.put("state", state);
                JsonObjectRequest object = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(param), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray collegesArray = response.getJSONArray("colleges");
                            for (int i = 0; i < collegesArray.length(); i++) {
                                JSONObject collegeObject = collegesArray.getJSONObject(i);
                                String collegeName = collegeObject.getString("collegeName");
                                collegeList.add(collegeName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // collegeAdapter.notifyDataSetChanged();
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        }
                );
                requestQueue.add(object);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        task.execute();
    }
}
