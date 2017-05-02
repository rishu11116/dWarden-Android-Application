package com.project.rishabhsingh.dWarden.AccountsManagementSystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.project.rishabhsingh.dWarden.AppDataPreferences;
import com.project.rishabhsingh.dWarden.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static String URL = AppDataPreferences.URL+"register";
    private EditText userName,email,password,confirmPassword;
    private boolean updateRequired=false;
    private Button nextButton;
    private ProgressDialog progressDialog;
    private String created;
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

        else if (TextUtils.isEmpty(editUserEmail) || (!editUserEmail.contains("@"))) {
            cancel=true;
            email.setError("Please enter a valid Email");
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

    private void registerTheStudent(final String username,final String useremail,final String userpassword) {

        progressDialog = new ProgressDialog(RegistrationActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating account...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    created = jsonObject.getString("created");
                    token=jsonObject.getString("token");
                    if(created.equals("false")) {
                        Toast.makeText(RegistrationActivity.this, "Registration failed!!",Toast.LENGTH_SHORT).show();
                        progressDialog.setMessage("Registration failed!");
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Please update your details.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrationActivity.this,SignUpActivity.class);
                        intent.putExtra("Email",useremail);
                        startActivity(intent);
                        finish();
                        progressDialog.setMessage("Account created!");
                        progressDialog.dismiss();
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
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("username",username);
                params.put("emailid",useremail);
                params.put("password",userpassword);
                params.put("updaterequired",Boolean.toString(updateRequired));
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
