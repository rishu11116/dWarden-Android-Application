package com.project.rishabhsingh.ludus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class SignUpActivity extends AppCompatActivity {

    private static String URL = "https://bkjbhckjhkjgfdgd";
    private EditText rollNo, name, specialization,  percentage;
    private CheckBox checkBoxBloodDonation;
    private Button updateButton;
    RequestQueue requestQueue;
    private boolean isSpinnerInitial = true;
    Spinner spinner;
    ArrayAdapter<CharSequence> staticAdapter;
    public static String year = null, hostel = null, bloodGroup = null, canDonateBlood = null;
    int yearPosition=0, hostelPosition=0, bloodPosition=0;
    LinearLayout registrationLayout;
    private String error,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registrationLayout=(LinearLayout)findViewById(R.id.registrationForm);

        rollNo = (EditText) findViewById(R.id.editRollNo);
        rollNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    name.requestFocus();
                    return true;
                }
                return false;
            }
        });

        name = (EditText) findViewById(R.id.editName);
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    specialization.requestFocus();
                    return true;
                }
                return false;
            }
        });

        specialization = (EditText) findViewById(R.id.editSpecialization);
        specialization.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(specialization.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        percentage = (EditText) findViewById(R.id.editPercentage);
        percentage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        checkBoxBloodDonation = (CheckBox)findViewById(R.id.checkBoxBloodDonation);
        if(checkBoxBloodDonation.isChecked())
            canDonateBlood = "yes";
        else
            canDonateBlood="no";

        updateButton = (Button)findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptUpdate();
            }
        });

        spinner = (Spinner)findViewById(R.id.spinnerYear);
        staticAdapter = ArrayAdapter.createFromResource(this, R.array.degree_year, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                yearPosition = position;
                if (isSpinnerInitial) {
                    year = parent.getItemAtPosition(position).toString();
                    isSpinnerInitial = false;
                } else {
                    if (position == 0) ;
                    else {
                        year = parent.getItemAtPosition(position).toString();
                        Toast.makeText(getBaseContext(),year+" Year", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner = (Spinner)findViewById(R.id.spinnerHostel);
        staticAdapter = ArrayAdapter.createFromResource(this, R.array.hostel, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                hostelPosition = position;
                if (isSpinnerInitial) {
                    hostel = parent.getItemAtPosition(position).toString();
                    isSpinnerInitial = false;
                } else {
                    if (position == 0) ;
                    else {
                        hostel = parent.getItemAtPosition(position).toString();
                        Toast.makeText(getBaseContext(),hostel, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner = (Spinner)findViewById(R.id.spinnerBlood);
        staticAdapter = ArrayAdapter.createFromResource(this, R.array.blood_group, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bloodPosition = position;
                if (isSpinnerInitial) {
                    bloodGroup = parent.getItemAtPosition(position).toString();
                    isSpinnerInitial = false;
                } else {
                    if (position == 0) ;
                    else {
                        bloodGroup = parent.getItemAtPosition(position).toString();
                        Toast.makeText(getBaseContext(),bloodGroup, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void attemptUpdate() {

        boolean cancel=false;
        View focusView = null;
        String userRoll = rollNo.getText().toString();
        String userName = name.getText().toString();
        String userSpecialization = specialization.getText().toString();
        String userPercentage = percentage.getText().toString();

        if (TextUtils.isEmpty(userRoll)) {
            cancel=true;
            rollNo.setError("Please enter your Roll Number");
            focusView = rollNo;
        }

        else if(TextUtils.isEmpty(userName)) {
            cancel=true;
            name.setError("Please enter your Name");
            focusView = name;
        }

        else if(TextUtils.isEmpty(userSpecialization)) {
            cancel=true;
            specialization.setError("Please enter your specialization");
            focusView = specialization;
        }

        else if(TextUtils.isEmpty(userPercentage)) {
            cancel=true;
            percentage.setError("Please enter your percentage");
            focusView = percentage;
        }

        else if(yearPosition <= 0){
            cancel=true;
            focusView=null;
            Toast.makeText(SignUpActivity.this, "Year is not selected.", Toast.LENGTH_SHORT).show();
        }

        else if(hostelPosition <= 0){
            cancel=true;
            focusView=null;
            Toast.makeText(SignUpActivity.this, "Hostel is not selected.", Toast.LENGTH_SHORT).show();
        }

        else if(bloodPosition <= 0){
            cancel=true;
            focusView=null;
            Toast.makeText(SignUpActivity.this, "Blood Group is not selected.", Toast.LENGTH_SHORT).show();
        }

            if(cancel){
                if (focusView!=null)
                    focusView.requestFocus();
            }
            else {
                updateTheStudent(userRoll,userName,userSpecialization,userPercentage);
            }
    }

    private void updateTheStudent(String userRoll, String userName,String userSpecialization,
                                    String userPercentage) {

            requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            Map<String, String> param = new HashMap<String, String>();
            param.put("rollno", userRoll);
            param.put("name", userName);
            param.put("specialization", userSpecialization);
            param.put("year", year);
            param.put("hostel", hostel);
            param.put("bloodgroup", bloodGroup);
            param.put("percentage", userPercentage);
            param.put("BloodDonation",canDonateBlood);
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(param), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        result= jsonObject.getString("result");
                        if(result.equals("fail")) {
                            error = jsonObject.getString("error");
                            Toast.makeText(SignUpActivity.this, "Registration failed!!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Congrats,You are now successfully registered with us.", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            requestQueue.add(jor);
        }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==  KeyEvent.KEYCODE_BACK) {
            finish();
            Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }

}
