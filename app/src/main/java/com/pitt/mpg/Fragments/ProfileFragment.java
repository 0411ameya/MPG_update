package com.pitt.mpg.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pitt.mpg.MainActivity;
import com.pitt.mpg.R;
import com.pitt.mpg.RequestDetails;

import org.json.simple.JSONObject;

/*
* Here the Request Details object is partly set with the items that exist in this profile fragment method
* For implementing the database functionality, I check if the object's data is already set, if not set, it will not use any text, but if set, it will use the previous value
* On clicking the Save Profile button, all the profile parameter for Profile Fragment is set. Before clicking the save profile button nothing is saved
*/

public class ProfileFragment extends Fragment implements View.OnClickListener {
    JSONObject ret;
    View view;
    RequestDetails rd;
    ToggleButton shoppingLover;
    ToggleButton outdoorsLover;
    ToggleButton artLover;
    ToggleButton foodLover;
    ToggleButton nightLifeLover;
    Button saveProfile;
    Button selectAll;
    Button resetAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ret = MainActivity.getJSONObject();
        rd = MainActivity.getRDObject();

        foodLover = (ToggleButton) view.findViewById(R.id.tbFoodLover);
        nightLifeLover = (ToggleButton) view.findViewById(R.id.tbNightLover);
        outdoorsLover = (ToggleButton) view.findViewById(R.id.tbOutdoorsLover);
        artLover = (ToggleButton) view.findViewById(R.id.tbArtLover);
        shoppingLover = (ToggleButton) view.findViewById(R.id.tbShoppingLover);

        saveProfile = (Button) view.findViewById(R.id.bSaveProfile);
        selectAll = (Button) view.findViewById(R.id.bSelectAll);
        resetAll = (Button) view.findViewById(R.id.bResetAll);

        foodLover.setChecked(rd.is_pr_foodLOVER());
        nightLifeLover.setChecked(rd.is_pr_nightLOVER());
        outdoorsLover.setChecked(rd.is_pr_outdoorsLOVER());
        artLover.setChecked(rd.is_pr_artLOVER());
        shoppingLover.setChecked(rd.is_pr_shoppingLOVER());

        saveProfile.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        resetAll.setOnClickListener(this);

        foodLover.setOnClickListener(this);
        nightLifeLover.setOnClickListener(this);
        outdoorsLover.setOnClickListener(this);
        artLover.setOnClickListener(this);
        shoppingLover.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bSaveProfile:
                Toast.makeText(getActivity(), " Your profiles have been saved!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tbArtLover:
                if (artLover.isChecked()) {
                    rd.set_pr_artLOVER(true);
                    ret.put("profile", "ArtLover");
                } else {
                    rd.set_pr_artLOVER(false);
                }
                break;
            case R.id.tbFoodLover:
                if (foodLover.isChecked()) {
                    rd.set_pr_foodLOVER(true);
                    ret.put("profile", "FoodLover");
                } else {
                    rd.set_pr_foodLOVER(false);
                }
                break;
            case R.id.tbNightLover:
                if (nightLifeLover.isChecked()) {
                    rd.set_pr_nightLOVER(true);
                    ret.put("profile", "NightLover");
                } else {
                    rd.set_pr_nightLOVER(false);
                }
                break;
            case R.id.tbOutdoorsLover:
                if (outdoorsLover.isChecked()) {
                    rd.set_pr_outdoorsLOVER(true);
                    ret.put("profile", "OutdoorsLover");
                } else {
                    rd.set_pr_outdoorsLOVER(false);
                }
                break;
            case R.id.tbShoppingLover:
                if (shoppingLover.isChecked()) {
                    rd.set_pr_shoppingLOVER(true);
                    ret.put("profile", "shoppingLover");
                } else {
                    rd.set_pr_shoppingLOVER(false);
                }
                break;
            case R.id.bSelectAll:
                rd.set_pr_artLOVER(true);
                rd.set_pr_foodLOVER(true);
                rd.set_pr_nightLOVER(true);
                rd.set_pr_outdoorsLOVER(true);
                rd.set_pr_shoppingLOVER(true);

                artLover.setChecked(true);
                foodLover.setChecked(true);
                nightLifeLover.setChecked(true);
                outdoorsLover.setChecked(true);
                shoppingLover.setChecked(true);
                break;
            case R.id.bResetAll:
                rd.set_pr_artLOVER(false);
                rd.set_pr_foodLOVER(false);
                rd.set_pr_nightLOVER(false);
                rd.set_pr_outdoorsLOVER(false);
                rd.set_pr_shoppingLOVER(false);

                artLover.setChecked(false);
                foodLover.setChecked(false);
                nightLifeLover.setChecked(false);
                outdoorsLover.setChecked(false);
                shoppingLover.setChecked(false);
                break;
        }
    }
}