package com.project.rishabhsingh.ludus;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.ArrayMap;
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
import android.widget.EditText;
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

public class RegistrationActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    String URL = "http://192.168.2.13:8090/api/register";
    private EditText userName,email,password,confirmPassword;
    private boolean updateRequired=false;
    Button nextButton;
    String result;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = (EditText) findViewById(R.id.editUserName);
        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(confirmPassword.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        nextButton=(Button)findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration() {

        boolean cancel=false;
        View focusView = null;
        String editUserName = userName.getText().toString();
        String editUserEmail = email.getText().toString();
        String editUserPassword = password.getText().toString();
        String editUserConfirmPassword = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(editUserName)) {
            cancel=true;
            userName.setError("Please enter your UserName");
            focusView = userName;
        }

        else if (TextUtils.isEmpty(editUserEmail)) {
            cancel=true;
            email.setError("Please enter your Email");
            focusView = email;
        }

        else if(editUserPassword.length()<6) {
            cancel=true;
            password.setError("Atleast 6 characters are required");
            focusView=password;
        }

        else if(editUserConfirmPassword.length()<6) {
            cancel=true;
            confirmPassword.setError("Atleast 6 characters are required");
            focusView=confirmPassword;
        }

        else if(!editUserPassword.equals(editUserConfirmPassword)) {
            cancel = true;
            confirmPassword.setError("Password Mismatch");
            focusView = confirmPassword;
        }

        if(cancel){
            if (focusView!=null)
                focusView.requestFocus();
        }
        else {
            registerTheStudent(editUserName,editUserEmail,editUserPassword);
        }
    }

    private void registerTheStudent(String username, String useremail, String userpassword) {

        requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("username",username);
//        param.put("emailid",useremail);
//        param.put("password",userpassword);
//        param.put("updaterequired",Boolean.toString(updateRequired));
        String address = URL+"?username="+username+"&emailid="+useremail+"&password="+userpassword+"&updaterequired="+Boolean.toString(updateRequired);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,address, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    result = jsonObject.getString("created");
                    token=jsonObject.getString("token");
                    if(result.equals("false")) {
                        Toast.makeText(RegistrationActivity.this, "Registration failed!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Please update your details.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this,SignUpActivity.class));
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        requestQueue.add(jor);
    }
}
