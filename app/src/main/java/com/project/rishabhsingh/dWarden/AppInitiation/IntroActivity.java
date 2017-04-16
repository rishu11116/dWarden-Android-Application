package com.project.rishabhsingh.dWarden.AppInitiation;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.project.rishabhsingh.dWarden.AppDataPreferences;
import com.project.rishabhsingh.dWarden.HomePageActivity;
import com.project.rishabhsingh.dWarden.LoginActivity;
import com.project.rishabhsingh.dWarden.R;
import com.project.rishabhsingh.dWarden.SampleSlide;

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
        finish();
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
