package com.project.rishabhsingh.dWarden.DonorSearchingSystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.rishabhsingh.dWarden.R;

public class DonorSearchFragment extends Fragment {

    private Spinner donorSearchSpinner;
    private ArrayAdapter<CharSequence> staticAdapter;
    private boolean isSpinnerInitial = true;
    public static String bloodGroup=null;
    private int bloodPosition=0;
    private ImageButton donorSearchButton;
    private Context context;

    public DonorSearchFragment() {

    }

    @SuppressLint("ValidFragment")
    public DonorSearchFragment(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_donor_search, container, false);

        donorSearchSpinner = (Spinner)v.findViewById(R.id.spinnerDonorSearch);
        staticAdapter = ArrayAdapter.createFromResource(context,R.array.blood_group,android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donorSearchSpinner.setAdapter(staticAdapter);
        donorSearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                        Toast.makeText(context,bloodGroup, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        donorSearchButton = (ImageButton)v.findViewById(R.id.buttonDonorSearch);
        donorSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bloodPosition<=0) {
                    Toast.makeText(context,"Please select a blood group to search donors.",Toast.LENGTH_SHORT).show();
                }
                else {
                    DonorListFragment.donorList.clear();
                    DonorListFragment.adapter.notifyDataSetChanged();
                    DonorListFragment.loadDonors(bloodGroup);
                }
            }
        });
        return v;
    }
}
