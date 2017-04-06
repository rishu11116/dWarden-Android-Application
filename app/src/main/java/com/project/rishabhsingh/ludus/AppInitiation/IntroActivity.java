package com.project.rishabhsingh.ludus.AppInitiation;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.project.rishabhsingh.ludus.AppDataPreferences;
import com.project.rishabhsingh.ludus.RegistrationActivity;
import com.project.rishabhsingh.ludus.LoginActivity;
import com.project.rishabhsingh.ludus.R;
import com.project.rishabhsingh.ludus.SampleSlide;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(SampleSlide.newInstance(R.layout.slide1));
        addSlide(SampleSlide.newInstance(R.layout.slide2));
        addSlide(SampleSlide.newInstance(R.layout.slide3));
        addSlide(SampleSlide.newInstance(R.layout.slide4));
        showSkipButton(false);
        setSlideOverAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        AppDataPreferences.setIntroNeed(IntroActivity.this, false);
        Intent i= new Intent(this,LoginActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
