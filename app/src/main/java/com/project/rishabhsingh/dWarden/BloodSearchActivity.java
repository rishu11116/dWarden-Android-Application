package com.project.rishabhsingh.dWarden;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BloodSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_search);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DonorSearchFragment donorSearchFragment = new DonorSearchFragment(this);
        DonorListFragment donorListFragment = new DonorListFragment();
//        fragmentTransaction.add(R.id.fragment_donorSearch,donorSearchFragment);
//        fragmentTransaction.add(R.id.fragment_donorList,donorListFragment);
        fragmentTransaction.commit();
    }
}
