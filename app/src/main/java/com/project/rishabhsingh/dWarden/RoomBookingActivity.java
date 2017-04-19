package com.project.rishabhsingh.dWarden;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RoomBookingActivity extends AppCompatActivity {

    private static String URLTrying = AppDataPreferences.URL + "hostelallot";
    private static String URLAutomated = AppDataPreferences.URL + "";
    private EditText choice1Room, choice2Room, choice3Room, choice4Room, choice5Room, choice6Room, choice7Room, choice8Room, choice9Room, choice10Room;
    private String choice1Floor, choice2Floor, choice3Floor, choice4Floor, choice5Floor, choice6Floor, choice7Floor, choice8Floor, choice9Floor, choice10Floor;
    private String choice1, choice2, choice3, choice4, choice5, choice6, choice7, choice8, choice9, choice10;
    private RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10;
    private Button checkAvailabilityButton;
    private String status,duplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);

        radioGroup1 = (RadioGroup)findViewById(R.id.radioGroupChoice1);
        radioGroup2 = (RadioGroup)findViewById(R.id.radioGroupChoice2);
        radioGroup3 = (RadioGroup)findViewById(R.id.radioGroupChoice3);
        radioGroup4 = (RadioGroup)findViewById(R.id.radioGroupChoice4);
        radioGroup5 = (RadioGroup)findViewById(R.id.radioGroupChoice5);
        radioGroup6 = (RadioGroup)findViewById(R.id.radioGroupChoice6);
        radioGroup7 = (RadioGroup)findViewById(R.id.radioGroupChoice7);
        radioGroup8 = (RadioGroup)findViewById(R.id.radioGroupChoice8);
        radioGroup9 = (RadioGroup)findViewById(R.id.radioGroupChoice9);
        radioGroup10 = (RadioGroup)findViewById(R.id.radioGroupChoice10);

        checkAvailabilityButton = (Button) findViewById(R.id.check_availability_button);
        checkAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((choice1Floor == null) || (choice2Floor == null)
                        || (choice3Floor == null) || (choice4Floor == null)
                        || (choice5Floor == null) || (choice6Floor == null)
                        || (choice7Floor == null) || (choice8Floor == null)
                        || (choice9Floor == null) || (choice10Floor == null)
                        || (TextUtils.isEmpty(choice1Room.getText().toString()))
                        || (TextUtils.isEmpty(choice2Room.getText().toString()))
                        || (TextUtils.isEmpty(choice3Room.getText().toString()))
                        || (TextUtils.isEmpty(choice4Room.getText().toString()))
                        || (TextUtils.isEmpty(choice5Room.getText().toString()))
                        || (TextUtils.isEmpty(choice6Room.getText().toString()))
                        || (TextUtils.isEmpty(choice7Room.getText().toString()))
                        || (TextUtils.isEmpty(choice8Room.getText().toString()))
                        || (TextUtils.isEmpty(choice9Room.getText().toString()))
                        || (TextUtils.isEmpty(choice10Room.getText().toString()))) {
                    Toast.makeText(RoomBookingActivity.this, "One or more preferences are left incomplete !!", Toast.LENGTH_SHORT).show();
                } else {
                    choice1 = choice1Floor + choice1Room.getText().toString();
                    choice2 = choice2Floor + choice2Room.getText().toString();
                    choice3 = choice3Floor + choice3Room.getText().toString();
                    choice4 = choice4Floor + choice4Room.getText().toString();
                    choice5 = choice5Floor + choice5Room.getText().toString();
                    choice6 = choice6Floor + choice6Room.getText().toString();
                    choice7 = choice7Floor + choice7Room.getText().toString();
                    choice8 = choice8Floor + choice8Room.getText().toString();
                    choice9 = choice9Floor + choice9Room.getText().toString();
                    choice10 = choice10Floor + choice10Room.getText().toString();
                    findAvailableRooms(choice1, choice2, choice3, choice4, choice5, choice6, choice7, choice8, choice9, choice10);
                }

            }
        });

        choice1Room = (EditText) findViewById(R.id.editTextChoice1);
        choice1Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice2Room.requestFocus();
                    return true;
                }
                return false;
            }
        });

        choice2Room = (EditText) findViewById(R.id.editTextChoice2);
        choice2Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice3Room.requestFocus();
                    return true;
                }
                return false;
            }
        });

        choice3Room = (EditText) findViewById(R.id.editTextChoice3);
        choice3Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice4Room.requestFocus();
                }
                return false;
            }
        });

        choice4Room = (EditText) findViewById(R.id.editTextChoice4);
        choice4Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice5Room.requestFocus();
                }
                return false;
            }
        });

        choice5Room = (EditText) findViewById(R.id.editTextChoice5);
        choice5Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice6Room.requestFocus();
                }
                return false;
            }
        });

        choice6Room = (EditText) findViewById(R.id.editTextChoice6);
        choice6Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice7Room.requestFocus();
                }
                return false;
            }
        });

        choice7Room = (EditText) findViewById(R.id.editTextChoice7);
        choice7Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice8Room.requestFocus();
                }
                return false;
            }
        });

        choice8Room = (EditText) findViewById(R.id.editTextChoice8);
        choice8Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice9Room.requestFocus();
                }
                return false;
            }
        });

        choice9Room = (EditText) findViewById(R.id.editTextChoice9);
        choice9Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    choice10Room.requestFocus();
                }
                return false;
            }
        });

        choice10Room = (EditText) findViewById(R.id.editTextChoice10);
        choice10Room.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }

    public void onRadioGroup1Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround1:
                if (checked) {
                    choice1Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst1:
                if (checked) {
                    choice1Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond1:
                if (checked) {
                    choice1Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup2Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround2:
                if (checked) {
                    choice2Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst2:
                if (checked) {
                    choice2Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond2:
                if (checked) {
                    choice2Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup3Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround3:
                if (checked) {
                    choice3Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst3:
                if (checked) {
                    choice3Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond3:
                if (checked) {
                    choice3Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup4Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround4:
                if (checked) {
                    choice4Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst4:
                if (checked) {
                    choice4Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond4:
                if (checked) {
                    choice4Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup5Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround5:
                if (checked) {
                    choice5Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst5:
                if (checked) {
                    choice5Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond5:
                if (checked) {
                    choice5Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup6Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround6:
                if (checked) {
                    choice6Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst6:
                if (checked) {
                    choice6Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond6:
                if (checked) {
                    choice6Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup7Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround7:
                if (checked) {
                    choice7Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst7:
                if (checked) {
                    choice7Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond7:
                if (checked) {
                    choice7Floor = "S-";
                }
        }
    }

    public void onRadioGroup8Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround8:
                if (checked) {
                    choice8Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst8:
                if (checked) {
                    choice8Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond8:
                if (checked) {
                    choice8Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup9Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround9:
                if (checked) {
                    choice9Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst9:
                if (checked) {
                    choice9Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond9:
                if (checked) {
                    choice9Floor = "S-";
                    break;
                }
        }
    }

    public void onRadioGroup10Clicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButtonChoiceGround10:
                if (checked) {
                    choice10Floor = "G-";
                    break;
                }
            case R.id.radioButtonChoiceFirst10:
                if (checked) {
                    choice10Floor = "F-";
                    break;
                }
            case R.id.radioButtonChoiceSecond10:
                if (checked) {
                    choice10Floor = "S-";
                    break;
                }
        }
    }

    private void findAvailableRooms(final String choice1, final String choice2, final String choice3, final String choice4,
                                    final String choice5, final String choice6, final String choice7, final String choice8,
                                    final String choice9, final String choice10) {
        final RequestQueue requestQueue = Volley.newRequestQueue(RoomBookingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLTrying, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status=jsonObject.getString("status");
                    duplicate=jsonObject.getString("duplicate");
                    if(status.equals("null")) {
                        if(duplicate.equals("yes")) {
                            final AlertDialog alertDialog = new AlertDialog.Builder(RoomBookingActivity.this).create();
                            alertDialog.setTitle("Warning!!!");
                            alertDialog.setMessage("Sorry!!You have been already allotted a room in your hostel.Now,you cannot book another room for yourself.");
                            alertDialog.setIcon(R.drawable.wrong_warning);
                            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                    startActivity(new Intent(RoomBookingActivity.this, HomePageActivity.class));
                                }
                            });
                        } else {
                            final AlertDialog alertDialog = new AlertDialog.Builder(RoomBookingActivity.this).create();
                            alertDialog.setTitle("Warning!!!");
                            alertDialog.setIcon(R.drawable.wrong_warning);
                            alertDialog.setMessage("Oops!!There seems to be a problem with your room allotment.None of your preferred rooms is available.Should I search a room available for you or are you gonna try once more to fill your preferences ?");
                            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Search", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                    clearPreferences();
                                    automatedSearch();
                                }
                            });
                            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Let me try", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                    clearPreferences();
                                }
                            });
                            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                    else {
                        clearPreferences();
                        final AlertDialog alertDialog = new AlertDialog.Builder(RoomBookingActivity.this).create();
                        alertDialog.setTitle("Congratulations!!!");
                        alertDialog.setMessage("Room "+status+" is successfully allotted to you.You can now enjoy your hostel life in this new room.");
                        alertDialog.setIcon(R.drawable.right_tick);
                        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                startActivity(new Intent(RoomBookingActivity.this,HomePageActivity.class));
                            }
                        });
                        alertDialog.show();
                    }
                } catch(JSONException e){
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
                params.put("studentrollno", AppDataPreferences.studentRollNo);
                params.put("choice1", choice1);
                params.put("choice2", choice2);
                params.put("choice3", choice3);
                params.put("choice4", choice4);
                params.put("choice5", choice5);
                params.put("choice6", choice6);
                params.put("choice7", choice7);
                params.put("choice8", choice8);
                params.put("choice9", choice9);
                params.put("choice10", choice10);
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

    private void clearPreferences() {
        choice1Room.setText("");
        choice2Room.setText("");
        choice3Room.setText("");
        choice4Room.setText("");
        choice5Room.setText("");
        choice6Room.setText("");
        choice7Room.setText("");
        choice8Room.setText("");
        choice9Room.setText("");
        choice10Room.setText("");
        choice1Room.requestFocus();
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        radioGroup4.clearCheck();
        radioGroup5.clearCheck();
        radioGroup6.clearCheck();
        radioGroup7.clearCheck();
        radioGroup8.clearCheck();
        radioGroup9.clearCheck();
        radioGroup10.clearCheck();
    }

    private void automatedSearch() {
        final RequestQueue requestQueue = Volley.newRequestQueue(RoomBookingActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLAutomated, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    status = jsonObject.getString("status");
                    final AlertDialog alertDialog = new AlertDialog.Builder(RoomBookingActivity.this).create();
                    alertDialog.setTitle("Congratulations!!!");
                    alertDialog.setMessage("Room "+status+" is successfully allotted to you.You can now enjoy your hostel life in this new room.");
                    alertDialog.setIcon(R.drawable.right_tick);
                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                            startActivity(new Intent(RoomBookingActivity.this,HomePageActivity.class));
                        }
                    });
                    alertDialog.show();
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
                params.put("studentrollno", AppDataPreferences.studentRollNo);
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
}
