package com.pitt.mpg.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.pitt.mpg.MainActivity;
import com.pitt.mpg.R;
import com.pitt.mpg.RequestDetails;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class ImportFragment extends Fragment implements View.OnClickListener{
    View view;
    RequestDetails rd;
    JSONObject ret;
    EditText lat;
    EditText lng;
    EditText radius;
    EditText venues;
    Button saveDetails;
    Button selectAll;
    Button resetAll;
    LatLng ll;
    ToggleButton artANDentertainment;
    ToggleButton clgANDuniversity;
    ToggleButton event;
    ToggleButton food;
    ToggleButton nightLife;
    ToggleButton outdoors;
    ToggleButton professionalANDothers;
    ToggleButton residence;
    ToggleButton shopANDservice;
    ToggleButton travelANDtransport;
    GoogleMap mGoogleMap;
    String groupId = "";
    char groups_arr[] = new char[12];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rd = MainActivity.getRDObject();
        ret = MainActivity.getJSONObject();
        ll = MainActivity.getMarkerPosition();
        view = inflater.inflate(R.layout.fragment_import,container,false);
        lng = (EditText) view.findViewById(R.id.etLang);
        lat = (EditText) view.findViewById(R.id.etLong);
        mGoogleMap = MainActivity.mGoogleMap;
        lat.setText(ll.latitude + "");
        lng.setText(ll.longitude + "");
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rgCity);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if (checkedId == R.id.rbSF) {
                    rd.set_city("San Fransisco");
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 12.0f));
                }
                else {
                    rd.set_city("New York");
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.770039, -73.826566), 12.0f));
                }
            }
        });
        radius = (EditText) view.findViewById(R.id.etRadius);
        venues = (EditText) view.findViewById(R.id.etVenues);
        saveDetails = (Button) view.findViewById(R.id.bSaveDetails);
        artANDentertainment = (ToggleButton) view.findViewById(R.id.arts);
        clgANDuniversity = (ToggleButton) view.findViewById(R.id.clg);
        event = (ToggleButton) view.findViewById(R.id.event);
        food = (ToggleButton) view.findViewById(R.id.Food);
        nightLife = (ToggleButton) view.findViewById(R.id.tbNightLife);
        outdoors = (ToggleButton) view.findViewById(R.id.tbOutdoors);
        professionalANDothers = (ToggleButton) view.findViewById(R.id.tbProfessional);
        residence = (ToggleButton) view.findViewById(R.id.tbResidence);
        shopANDservice = (ToggleButton) view.findViewById(R.id.tbShopService);
        travelANDtransport = (ToggleButton) view.findViewById(R.id.tbTravelTransport);

        selectAll = (Button) view.findViewById(R.id.bSelectAll);
        resetAll = (Button) view.findViewById(R.id.bResetAll);

        saveDetails.setOnClickListener(this);
        artANDentertainment.setOnClickListener(this);
        clgANDuniversity.setOnClickListener(this);
        event.setOnClickListener(this);
        food.setOnClickListener(this);
        nightLife.setOnClickListener(this);
        outdoors.setOnClickListener(this);
        professionalANDothers.setOnClickListener(this);
        shopANDservice.setOnClickListener(this);
        travelANDtransport.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        resetAll.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch(id){
            case R.id.bSaveDetails:
                rd.set_lat(lat.getText().toString());
                rd.set_lng(lng.getText().toString());
                rd.set_radius(radius.getText().toString());
                rd.set_venues(venues.getText().toString());
                Toast.makeText(getActivity(), "Details Saved!", Toast.LENGTH_SHORT).show();
                rd.set_city(rd.get_city());
                groupId = generateGID(groups_arr);
                ret.put("UserLocation", rd.get_lat() + "; " + rd.get_lng());
                ret.put("topK", rd.get_venues());
                ret.put("radius", rd.get_radius());
                ret.put("groupIds", groupId);


                break;
            case R.id.arts:
                if (artANDentertainment.isChecked())
                {
                    rd.set_ip_artANDentertainment(true);
                    groups_arr[1] = 1;
                }
                else
                {
                    rd.set_ip_artANDentertainment(false);
                    groups_arr[1] = 0;
                }
                break;
            case R.id.clg:
                if (clgANDuniversity.isChecked())
                {
                    rd.set_ip_collegeANDuniversity(true);
                    groups_arr[2] = 1;
                }
                else
                {
                    rd.set_ip_collegeANDuniversity(false);
                    groups_arr[2] = 0;
                }
                break;
            case R.id.event:
                if (event.isChecked())
                {
                    rd.set_ip_event(true);
                    groups_arr[3] = 1;
                }
                else
                {
                    rd.set_ip_event(false);
                    groups_arr[3] = 0;
                }
                break;
            case R.id.Food:
                if (food.isChecked())
                {
                    rd.set_ip_food(true);
                    groups_arr[4] = 1;
                }
                else
                {
                    rd.set_ip_food(false);
                    groups_arr[0] = 0;
                }
                break;
            case R.id.tbNightLife:
                if (nightLife.isChecked())
                {
                    rd.set_ip_nightlifespot(true);
                    groups_arr[5] = 1;
                }
                else
                {
                    rd.set_ip_nightlifespot(false);
                    groups_arr[5] = 0;
                }
                break;
            case R.id.tbOutdoors:
                if (outdoors.isChecked())
                {
                    rd.set_ip_outdoorsANDrecreation(true);
                    groups_arr[6] = 1;
                }
                else
                {
                    rd.set_ip_outdoorsANDrecreation(false);
                    groups_arr[6] = 0;
                }
                break;
            case R.id.tbResidence:
                if (residence.isChecked())
                {
                    rd.set_ip_residence(true);
                    groups_arr[8] = 1;
                }
                else
                {
                    rd.set_ip_residence(false);
                    groups_arr[8] = 0;
                }
                break;
            case R.id.tbProfessional:
                if (professionalANDothers.isChecked())
                {
                    rd.set_ip_professionalANDothers(true);
                    groups_arr[7] = 1;
                }
                else
                {
                    rd.set_ip_professionalANDothers(false);
                    groups_arr[7]= 0;
                }
                break;
            case R.id.tbShopService:
                if (shopANDservice.isChecked())
                {
                    rd.set_ip_shopANDservice(true);
                    groups_arr[9] = 1;
                }
                else
                {
                    rd.set_ip_shopANDservice(false);
                    groups_arr[9] = 0;
                }
                break;
            case R.id.tbTravelTransport:
                if (travelANDtransport.isChecked())
                {
                    rd.set_ip_travelANDtransport(true);
                    groups_arr[10] = 1;
                }
                else
                {
                    rd.set_ip_travelANDtransport(false);
                    groups_arr[10] = 0;
                }
                break;
            case R.id.bSelectAll:
                for (int i = 1; i <= 10; i++)
                    groups_arr[i] = 1;
                rd.set_ip_artANDentertainment(true);
                rd.set_ip_collegeANDuniversity(true);
                rd.set_ip_event(true);
                rd.set_ip_food(true);
                rd.set_ip_nightlifespot(true);
                rd.set_ip_outdoorsANDrecreation(true);
                rd.set_ip_professionalANDothers(true);
                rd.set_ip_residence(true);
                rd.set_ip_shopANDservice(true);
                rd.set_ip_travelANDtransport(true);
                artANDentertainment.setChecked(true);
                clgANDuniversity.setChecked(true);
                event.setChecked(true);
                food.setChecked(true);
                nightLife.setChecked(true);
                outdoors.setChecked(true);
                professionalANDothers.setChecked(true);
                residence.setChecked(true);
                shopANDservice.setChecked(true);
                travelANDtransport.setChecked(true);
                break;
            case R.id.bResetAll:
                for (int i = 1; i <= 10; i++)
                    groups_arr[i] = 0;
                rd.set_ip_artANDentertainment(false);
                rd.set_ip_collegeANDuniversity(false);
                rd.set_ip_event(false);
                rd.set_ip_food(false);
                rd.set_ip_nightlifespot(false);
                rd.set_ip_outdoorsANDrecreation(false);
                rd.set_ip_professionalANDothers(false);
                rd.set_ip_residence(false);
                rd.set_ip_shopANDservice(false);
                rd.set_ip_travelANDtransport(false);
                artANDentertainment.setChecked(false);
                clgANDuniversity.setChecked(false);
                event.setChecked(false);
                food.setChecked(false);
                nightLife.setChecked(false);
                outdoors.setChecked(false);
                professionalANDothers.setChecked(false);
                residence.setChecked(false);
                shopANDservice.setChecked(false);
                travelANDtransport.setChecked(false);
                break;

        }


    }

    private String generateGID (char[] groups_arr) {
        String list = "";

        for (int i = 1; i < 11; i++) {
            if(groups_arr[i]==1){
                if (i != 10)
                        list += i + ",";
                else
                        list += i;
            }
        }
        return list;
    }


}
