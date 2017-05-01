package com.project.rishabhsingh.dWarden;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView navEmailTextView,navNameTextView;
    private RequestQueue requestQueue;
    private static String hostel;
    private ProgressDialog progressDialog;
    private TextView textViewChiefWardenName,textViewWardenName,textViewChiefSupervisorName,textViewSupervisorName,
            textViewChiefElectricianName,textViewElectricianName,textViewAmbulance1Name,textViewAmbulance2Name,
            textViewAmbulance3Name;
    private ImageView imageViewChiefWarden,imageViewWarden,imageViewChiefSupervisor,imageViewSupervisor,imageViewChiefElectrician,
            imageViewElectrician,imageViewAmbulance1,imageViewAmbulance2,imageViewAmbulance3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        navEmailTextView=(TextView)header.findViewById(R.id.navEmailTextView);
        navNameTextView=(TextView)header.findViewById(R.id.navNameTextView);
        navEmailTextView.setText(AppDataPreferences.getEmail(HomePageActivity.this));

        loadNameandHostel();

        textViewChiefWardenName = (TextView)findViewById(R.id.textChiefWardenName);
        textViewWardenName = (TextView)findViewById(R.id.textWardenName);
        textViewChiefSupervisorName = (TextView)findViewById(R.id.textChiefSupervisorName);
        textViewSupervisorName = (TextView)findViewById(R.id.textSupervisorName);
        textViewChiefElectricianName = (TextView)findViewById(R.id.textChiefElectricianName);
        textViewElectricianName = (TextView)findViewById(R.id.textElectricianName);
        textViewAmbulance1Name = (TextView)findViewById(R.id.textAmbulance1Name);
        textViewAmbulance2Name = (TextView)findViewById(R.id.textAmbulance2Name);
        textViewAmbulance3Name = (TextView)findViewById(R.id.textAmbulance3Name);

        imageViewChiefWarden = (ImageView)findViewById(R.id.imageChiefWarden);
        imageViewWarden = (ImageView)findViewById(R.id.imageWarden);
        imageViewChiefSupervisor = (ImageView)findViewById(R.id.imageChiefSupervisor);
        imageViewSupervisor = (ImageView)findViewById(R.id.imageSupervisor);
        imageViewChiefElectrician = (ImageView)findViewById(R.id.imageChiefElectrician);
        imageViewElectrician = (ImageView)findViewById(R.id.imageElectrician);
        imageViewAmbulance1 = (ImageView)findViewById(R.id.imageAmbulance1);
        imageViewAmbulance2 = (ImageView)findViewById(R.id.imageAmbulance2);
        imageViewAmbulance3 = (ImageView)findViewById(R.id.imageAmbulance3);

        imageViewChiefWarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        imageViewWarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewChiefSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewChiefElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewAmbulance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewAmbulance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageViewAmbulance3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(HomePageActivity.this,ProfileActivity.class));
        }
        else if (id == R.id.nav_roomBooking) {
            startActivity(new Intent(HomePageActivity.this,RoomBookingActivity.class));
        }
        else if (id == R.id.nav_bloodNeed) {

        }
        else if (id == R.id.nav_settings) {

        }
        else if (id == R.id.nav_developers) {

        }
        else if (id == R.id.nav_logout) {
            AppDataPreferences.setToken(HomePageActivity.this,null);
            AppDataPreferences.setEmail(HomePageActivity.this,null);
            startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadNameandHostel() {

        progressDialog = new ProgressDialog(HomePageActivity.this,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching details...");
        progressDialog.show();

        String URL = AppDataPreferences.URL+"student?email="+AppDataPreferences.getEmail(HomePageActivity.this);
        requestQueue = Volley.newRequestQueue(HomePageActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response=response.replace('[',' ');
                    response=response.replace(']',' ');
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getString("studentname").equals("")) {
                        navNameTextView.setText(jsonObject.getString("studentname"));
                    }
                    if (!jsonObject.getString("hostelname").equals("")) {
                        hostel=jsonObject.getString("hostelname");
                        if(hostel.equals("Vrindawan Bhawan Bhawan")) {
                            setVrindawanNames();
                        }
                        else if(hostel.equals("Saket Bhawan")) {
                            setSaketNames();
                        }
                        else if(hostel.equals("Panchavati Bhawan")) {
                            setPanchavatiNames();
                        }
                        else if(hostel.equals("Jai Bharat Bhawan")) {
                            setJaiBharatNames();
                        }
                        else if(hostel.equals("Yashodhara Bhawan")) {
                            setYashodharaNames();
                        }
                        else {
                            setKalpanaNames();
                        }
                        progressDialog.dismiss();
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
                params.put("email", AppDataPreferences.getEmail(HomePageActivity.this));
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

    private void callPhone() {
        final AlertDialog alertDialog = new AlertDialog.Builder(HomePageActivity.this).create();
        alertDialog.setTitle("Permission !!!");
        alertDialog.setIcon(R.drawable.phone_permission);
        alertDialog.setMessage("Do you want to call your hostel Chief Warden ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8979965258"));
                if (ActivityCompat.checkSelfPermission(HomePageActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)  {
                    return;
                }
                startActivity(callIntent);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void setYashodharaNames() {

        textViewChiefWardenName.setText("Dr. Amitabh Srivastava");
        textViewWardenName.setText("Dr. Shahnaz Ayub");
        textViewChiefSupervisorName.setText("Suresh Chaudhary");
        textViewSupervisorName.setText("Annu Singh");
        textViewChiefElectricianName.setText("Mr. K.S.Yadav");
        textViewElectricianName.setText("Mr. Sanjeev Sahu");
        textViewAmbulance1Name.setText("Mr. Rakesh Raghav");
        textViewAmbulance2Name.setText("Mr. Arvind Rajak");
        textViewAmbulance3Name.setText("Mr. Vivek Saxena");
    }

    private void setKalpanaNames() {

        textViewChiefWardenName.setText("Dr. Sanjay Agarwal");
        textViewWardenName.setText("Dr. Ekta Pandey");
        textViewChiefSupervisorName.setText("Dr. S.Mandal");
        textViewSupervisorName.setText("Mrs. Annu Singh");
        textViewChiefElectricianName.setText("Mr. K.S.Yadav");
        textViewElectricianName.setText("Mr. Sanjeev Sahu");
        textViewAmbulance1Name.setText("Mr. Rakesh Raghav");
        textViewAmbulance2Name.setText("Mr. Arvind Rajak");
        textViewAmbulance3Name.setText("Mr. Vivek Saxena");
    }

    private void setVrindawanNames() {

        textViewChiefWardenName.setText("Dr. A.K.Solanki");
        textViewWardenName.setText("Dr. P.K.Srivastava");
        textViewChiefSupervisorName.setText("Mr. Pushpendra Singh");
        textViewSupervisorName.setText("Mr. S.N. Chaturvedi");
        textViewChiefElectricianName.setText("Mr. K.S.Yadav");
        textViewElectricianName.setText("Mr. Sanjeev Sahu");
        textViewAmbulance1Name.setText("Mr. Rakesh Raghav");
        textViewAmbulance2Name.setText("Mr. Arvind Rajak");
        textViewAmbulance3Name.setText("Mr. Vivek Saxena");
    }

    private void setSaketNames() {

        textViewChiefWardenName.setText("Steve Jobs");
        textViewWardenName.setText("Sundar Pichai");
        textViewChiefSupervisorName.setText("Satya Nadella");
        textViewSupervisorName.setText("Elon Musk");
        textViewChiefElectricianName.setText("Bill Gates");
        textViewElectricianName.setText("Steve Chen");
        textViewAmbulance1Name.setText("Jeff Bezos");
        textViewAmbulance2Name.setText("Jeff Weiner");
        textViewAmbulance3Name.setText("Tim Cook");
    }

    private void setPanchavatiNames() {

        textViewChiefWardenName.setText("Steve Jobs");
        textViewWardenName.setText("Sundar Pichai");
        textViewChiefSupervisorName.setText("Satya Nadella");
        textViewSupervisorName.setText("Elon Musk");
        textViewChiefElectricianName.setText("Bill Gates");
        textViewElectricianName.setText("Steve Chen");
        textViewAmbulance1Name.setText("Jeff Bezos");
        textViewAmbulance2Name.setText("Jeff Weiner");
        textViewAmbulance3Name.setText("Tim Cook");
    }

    private void setJaiBharatNames() {

        textViewChiefWardenName.setText("Steve Jobs");
        textViewWardenName.setText("Sundar Pichai");
        textViewChiefSupervisorName.setText("Satya Nadella");
        textViewSupervisorName.setText("Elon Musk");
        textViewChiefElectricianName.setText("Bill Gates");
        textViewElectricianName.setText("Steve Chen");
        textViewAmbulance1Name.setText("Jeff Bezos");
        textViewAmbulance2Name.setText("Jeff Weiner");
        textViewAmbulance3Name.setText("Tim Cook");
    }
}
