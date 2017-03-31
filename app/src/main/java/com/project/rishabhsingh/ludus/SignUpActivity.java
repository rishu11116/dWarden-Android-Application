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

    private ArrayList<String> hostelList;
    private static String URL = "https://bkjbhckjhkjgfdgd";
    private EditText rollNo, name, email, course, specialization,  percentage, password, confirmPassword;
    private CheckBox checkBoxBloodDonation;
    private Button registerButton;
    RequestQueue requestQueue;
    private boolean isSpinnerInitial = true;
    Spinner spinner;
    ArrayAdapter<CharSequence> staticAdapter;
    ArrayAdapter<String> hostelAdapter;
    public static String year = null, hostel = null, bloodGroup = null, canDonateBlood = null;
    int yearPosition=0, hostelPosition=0, bloodPosition=0;
    LinearLayout registrationLayout;
    private String error,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // loadHostels();

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
                    email.requestFocus();
                    return true;
                }
                return false;
            }
        });

        email = (EditText) findViewById(R.id.editEmail);
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    course.requestFocus();
                    return true;
                }
                return false;
            }
        });

        course = (EditText) findViewById(R.id.editCourse);
        course.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    password.requestFocus();
                    return true;
                }
                return false;
            }
        });

        password = (EditText) findViewById(R.id.editPassword);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    confirmPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });

        confirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        confirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

        registerButton = (Button)findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
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
                        Toast.makeText(getBaseContext(),year, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        spinner = (Spinner)findViewById(R.id.spinnerHostel);
//        hostelAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,hostelList);
//        hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(hostelAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                hostelPosition = position;
//                if (isSpinnerInitial) {
//                    hostel = parent.getItemAtPosition(position).toString();
//                    isSpinnerInitial = false;
//                } else {
//                    if (position == 0) ;
//                    else {
//                        hostel = parent.getItemAtPosition(position).toString();
//                        Toast.makeText(SignUpActivity.this,hostel,Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

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

    private void attemptRegistration() {
        boolean cancel=false;
        View focusView = null;
        String userRoll = rollNo.getText().toString();
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userCourse = course.getText().toString();
        String userSpecialization = specialization.getText().toString();
        String userPercentage = percentage.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPassword.getText().toString();

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

        else if(TextUtils.isEmpty(userEmail)) {
            cancel=true;
            email.setError("Please enter your Email");
            focusView = email;
        }

        else if(TextUtils.isEmpty(userCourse)) {
            cancel=true;
            course.setError("Please enter your course");
            focusView = course;
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

        else if(userPassword.length()<6) {
            cancel=true;
            password.setError("Atleast 6 characters are required");
            focusView=password;
        }

        else if(userConfirmPassword.length()<6) {
            cancel = true;
            confirmPassword.setError("Atleast 6 characters are required");
            focusView = confirmPassword;
        }

        else if(!userPassword.equals(userConfirmPassword)) {
            cancel = true;
            confirmPassword.setError("Password Mismatch");
            focusView = confirmPassword;
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
                registerTheStudent(userRoll,userName,userEmail,userCourse,userSpecialization,userPercentage,userPassword);
            }
    }

    private void registerTheStudent(String userRoll, String userName, String userEmail,
                                    String userCourse, String userSpecialization,
                                    String userPercentage, String userPassword) {

            requestQueue = Volley.newRequestQueue(SignUpActivity.this);
            Map<String, String> param = new HashMap<String, String>();
            param.put("rollno", userRoll);
            param.put("name", userName);
            param.put("email", userEmail);
            param.put("course", userCourse);
            param.put("specialization", userSpecialization);
            param.put("year", year);
            param.put("hostel", hostel);
            param.put("bloodgroup", bloodGroup);
            param.put("percentage", userPercentage);
            param.put("password", userPassword);
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

//    private void loadHostels() {
//
//        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
//            @Override
//            protected Void doInBackground(Integer... integers) {
//
//                requestQueue = Volley.newRequestQueue(SignUpActivity.this);
//                Map<String, String> param = new HashMap<>();
//                param.put("college",CollegeChooserActivity.college);
//                JsonObjectRequest object = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(param), new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray hostelsArray = response.getJSONArray("hostels");
//                            for (int i = 0; i < hostelsArray.length(); i++) {
//                                JSONObject hostelObject = hostelsArray.getJSONObject(i);
//                                String hostelName = hostelObject.getString("hostelName");
//                                hostelList.add(hostelName);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        hostelAdapter.notifyDataSetChanged();
//                    }
//                },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("Volley", "Error");
//                            }
//                        }
//                );
//                requestQueue.add(object);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//            }
//        };
//        task.execute();
//    }
}
