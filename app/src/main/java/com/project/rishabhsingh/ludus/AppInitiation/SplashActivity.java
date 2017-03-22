package com.project.rishabhsingh.ludus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(AppDataPreferences.isIntroNeeded(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this,IntroActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this,CollegeChooserActivity.class));
                }
            }
        },2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showDialogOnExit();
    }

    private void showDialogOnExit() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setTitle("Do you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
