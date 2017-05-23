package layout;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pitt.mpg.MainActivity;
import com.pitt.mpg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    Button sanFran;
    Button newYork;
    SupportMapFragment sMapFragment;
    String selectedCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        sanFran = (Button) v.findViewById(R.id.btnSFmap);
        newYork = (Button) v.findViewById(R.id.btnNYmap);

        sanFran.setOnClickListener(this);
        newYork.setOnClickListener(this);
        sMapFragment = MainActivity.getsMapFragment();
        sMapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSFmap) {
            MainActivity.welcomeCity = "SF";
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();

            android.support.v4.app.FragmentManager SFM = MainActivity.getSFM();
            if (!sMapFragment.isAdded()) {
                SFM.beginTransaction().add(R.id.map, sMapFragment).commit();
            } else {
                SFM.beginTransaction().show(sMapFragment).commit();
            }

        } else {
            MainActivity.welcomeCity = "NY";
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();

            android.support.v4.app.FragmentManager SFM = MainActivity.getSFM();
            if (!sMapFragment.isAdded()) {
                SFM.beginTransaction().add(R.id.map, sMapFragment).commit();
            } else {
                SFM.beginTransaction().show(sMapFragment).commit();
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
