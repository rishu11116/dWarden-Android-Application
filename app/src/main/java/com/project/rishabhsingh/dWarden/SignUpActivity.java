package com.project.rishabhsingh.dWarden;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static String URL = AppDataPreferences.URL+"student";
    private EditText rollNo, name, specialization,  percentage;
    private CheckBox checkBoxBloodDonation;
    private Button updateButton;
    private RequestQueue requestQueue;
    private boolean isSpinnerInitial = true;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> staticAdapter;
    private static String year = null, hostel = null, bloodGroup = null, canDonateBlood = null;
    private int yearPosition=0, hostelPosition=0, bloodPosition=0;
    private LinearLayout registrationLayout;
    private String result,emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailId=getIntent().getStringExtra("Email");

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

    private void updateTheStudent(final String userRoll,final String userName,final String userSpecialization,final String userPercentage) {

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Updating details...");
        progressDialog.show();

            requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(SignUpActivity.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();
                        result= jsonObject.getString("result");
//                        if(result.equals("fail")) {
//                            Toast.makeText(SignUpActivity.this, "Updation of your details failed!!",Toast.LENGTH_SHORT).show();
//                            progressDialog.setMessage("Updation failed!");
//                            progressDialog.dismiss();
//                        }
//                        else {
//                            Toast.makeText(SignUpActivity.this, "Congrats,You are now successfully registered with us.", Toast.LENGTH_LONG).show();
//                            Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
//                            startActivity(i);
//                            finish();
//                            progressDialog.setMessage("Details updated!");
//                            progressDialog.dismiss();
//                        }
                        finish();
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
                    params.put("statename","Uttar Pradesh");
                    params.put("collegename","BIET Jhansi");
                    params.put("hostelname", hostel);
                    params.put("branchname", userSpecialization);
                    params.put("studentname", userName);
                    params.put("studentrollno", userRoll);
                    params.put("studentemailid", emailId);
                    params.put("studentpercentage", userPercentage);
                    params.put("studentbloodgp", bloodGroup);
                    params.put("studentyear", year);
                    params.put("studentroomno", "Not Allocated");
                    params.put("candonateblood", canDonateBlood);
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
