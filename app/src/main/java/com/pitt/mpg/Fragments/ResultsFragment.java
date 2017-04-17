package com.pitt.mpg.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pitt.mpg.MainActivity;
import com.pitt.mpg.OutputDetails;
import com.pitt.mpg.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ResultsFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    View view;
    ToggleButton pdComposite;
    ToggleButton pdDistPref;
    ToggleButton pagerank;
    ToggleButton pdPopDist;
    ToggleButton pdPref;
    ToggleButton pdCompositePagerank;
    ToggleButton pdDistPrefPageRank;
    ToggleButton kMedoids;
    ToggleButton disC;
    ToggleButton randomSelection;

    ArrayList<Marker> pdComposite_AL = new ArrayList<>();
    ArrayList<Marker> pdDistPref_AL = new ArrayList<>();
    ArrayList<Marker> pagerank_AL = new ArrayList<>();
    ArrayList<Marker> pdPopDist_AL = new ArrayList<>();
    ArrayList<Marker> pdPref_AL = new ArrayList<>();
    ArrayList<Marker> pdCompositePagerank_AL = new ArrayList<>();
    ArrayList<Marker> pdDistPrefPageRank_AL = new ArrayList<>();
    ArrayList<Marker> kMedoids_AL = new ArrayList<>();
    ArrayList<Marker> disC_AL = new ArrayList<>();
    ArrayList<Marker> randomSelection_AL = new ArrayList<>();

    SupportMapFragment sMapFragment;
    GoogleMap mG = MainActivity.mGoogleMap;

    public static OutputDetails od;

    public static ArrayList<String> al_OD = new ArrayList<String>();
    public HashMap<String, ArrayList<String>> hMap_OD = new HashMap<String, ArrayList<String>>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_results, container, false);

        od = AlgorithmFragments.getOD();
        hMap_OD = od.getOut();

        for (String key : hMap_OD.keySet()) {
            Log.d("HASHMAP in RESULTSFRAG", key + " :: " + hMap_OD.get(key));
        }

        sMapFragment = MainActivity.getsMapFragment();
        pdComposite = (ToggleButton) view.findViewById(R.id.tbPDComposite1);
        pdDistPref = (ToggleButton) view.findViewById(R.id.tbPDDistPref1);
        pagerank = (ToggleButton) view.findViewById(R.id.tbPageRank1);
        pdPopDist = (ToggleButton) view.findViewById(R.id.tbPopDist1);
        pdPref = (ToggleButton) view.findViewById(R.id.tbPref1);
        pdCompositePagerank = (ToggleButton) view.findViewById(R.id.tbPDCompositePageRank1);
        pdDistPrefPageRank = (ToggleButton) view.findViewById(R.id.tbPDDistPrefPageRank1);
        kMedoids = (ToggleButton) view.findViewById(R.id.tbK_Medoids1);
        disC = (ToggleButton) view.findViewById(R.id.tbDisC1);
        randomSelection = (ToggleButton) view.findViewById(R.id.tbRandomSelection1);



        initializeVISIBILITY();
        sMapFragment.getMapAsync(this);
        pdComposite.setOnClickListener(this);
        pdDistPref.setOnClickListener(this);
        pagerank.setOnClickListener(this);
        pdPopDist.setOnClickListener(this);
        pdPref.setOnClickListener(this);
        pdCompositePagerank.setOnClickListener(this);
        pdDistPrefPageRank.setOnClickListener(this);
        kMedoids.setOnClickListener(this);
        disC.setOnClickListener(this);
        randomSelection.setOnClickListener(this);

        return view;
    }


    private void initializeVISIBILITY() {

        if (hMap_OD.containsKey("PD(composite)")) {
            pdComposite.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(composite)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0]));
                m.setSnippet(arr[3]);
                pdComposite_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PD(dist+pref)")) {
            pdDistPref.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(dist+pref)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                m.setSnippet(arr[3]);
                pdDistPref_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PageRank")) {
            pagerank.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PageRank"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                m.setSnippet(arr[3]);
                pagerank_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PD(pop+dist)")) {
            pdPopDist.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(pop+dist)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                m.setSnippet(arr[3]);
                pdPopDist_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PD(pref)")) {
            pdPref.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(pref)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                m.setSnippet(arr[3]);
                pdPref_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PD(composite+PageRank)")) {
            pdCompositePagerank.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(composite+PageRank)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                m.setSnippet(arr[3]);
                pdCompositePagerank_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("PD(pref+dist+pr)")) {
            pdDistPrefPageRank.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("PD(pref+dist+pr)"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                m.setSnippet(arr[3]);
                pdDistPrefPageRank_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("K-medoids")) {
            kMedoids.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("K-medoids"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                m.setSnippet(arr[3]);
                kMedoids_AL.add(m);
            }
        }
        if (hMap_OD.containsKey("DisC")) {
            disC.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("DisC"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                m.setSnippet(arr[3]);
                disC_AL.add(m);
            }

        }
        if (hMap_OD.containsKey("Random")) {
            randomSelection.setEnabled(true);
            al_OD = new ArrayList<String>(hMap_OD.get("Random"));
            for (int i = 0; i < al_OD.size(); i++) {
                String[] arr = al_OD.get(i).split(";");
                Marker m = mG.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                m.setSnippet(arr[3]);
                randomSelection_AL.add(m);
            }
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tbPDComposite1:
                Log.d("OnClickTest", "I came in");
                if (pdComposite.isChecked()) {
                    Log.d("OnClickTest2", "I came in checked");
                    for (int i = 0; i < pdComposite_AL.size(); i++) {
                        pdComposite_AL.get(i).setVisible(true);
                        pdComposite_AL.get(i).setSnippet("PD(composite)");
                    }

                } else {
                    Log.d("OnClickTest3", "I came in UNchecked");
                    for (int i = 0; i < pdComposite_AL.size(); i++)
                        pdComposite_AL.get(i).setVisible(false);
                }

                break;
            case R.id.tbPDDistPref1:
                if (pdDistPref.isChecked()) {
                    for (int i = 0; i < pdDistPref_AL.size(); i++) {
                        pdDistPref_AL.get(i).setVisible(true);
                        pdDistPref_AL.get(i).setSnippet("PD(Dist+Pref)");
                    }

                } else {
                    for (int i = 0; i < pdDistPref_AL.size(); i++)
                        pdDistPref_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbPageRank1:
                if (pagerank.isChecked()) {
                    for (int i = 0; i < pagerank_AL.size(); i++) {
                        pagerank_AL.get(i).setVisible(true);
                        pagerank_AL.get(i).setSnippet("PageRank");
                    }

                } else {
                    for (int i = 0; i < pagerank_AL.size(); i++)
                        pagerank_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbPopDist1:
                if (pdPopDist.isChecked()) {
                    for (int i = 0; i < pdPopDist_AL.size(); i++) {
                        pdPopDist_AL.get(i).setVisible(true);
                        pdPopDist_AL.get(i).setSnippet("PD(Pop+Dist)");
                    }

                } else {
                    for (int i = 0; i < pdPopDist_AL.size(); i++)
                        pdPopDist_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbPref1:
                if (pdPref.isChecked()) {
                    for (int i = 0; i < pdPref_AL.size(); i++) {
                        pdPref_AL.get(i).setVisible(true);
                        pdPref_AL.get(i).setSnippet("PD(Pref)");
                    }

                } else {
                    for (int i = 0; i < pagerank_AL.size(); i++)
                        pdPref_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbPDCompositePageRank1:
                if (pdCompositePagerank.isChecked()) {
                    for (int i = 0; i < pdCompositePagerank_AL.size(); i++) {
                        pdCompositePagerank_AL.get(i).setVisible(true);
                        pdCompositePagerank_AL.get(i).setSnippet("PD(Composite+PageRank)");
                    }

                } else {
                    for (int i = 0; i < pdCompositePagerank_AL.size(); i++)
                        pdCompositePagerank_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbPDDistPrefPageRank1:
                if (pdDistPrefPageRank.isChecked()) {
                    for (int i = 0; i < pdDistPrefPageRank_AL.size(); i++) {
                        pdDistPrefPageRank_AL.get(i).setVisible(true);
                        pdDistPrefPageRank_AL.get(i).setSnippet("PD(Dist+Pref+PageRank)");
                    }

                } else {
                    for (int i = 0; i < pdDistPrefPageRank_AL.size(); i++)
                        pdDistPrefPageRank_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbK_Medoids1:
                if (kMedoids.isChecked()) {
                    for (int i = 0; i < kMedoids_AL.size(); i++) {
                        kMedoids_AL.get(i).setVisible(true);
                        kMedoids_AL.get(i).setSnippet("K-Medoids");
                    }

                } else {
                    for (int i = 0; i < kMedoids_AL.size(); i++)
                        kMedoids_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbDisC1:
                if (disC.isChecked()) {
                    for (int i = 0; i < disC_AL.size(); i++) {
                        disC_AL.get(i).setVisible(true);
                        disC_AL.get(i).setSnippet("DisC");
                    }

                } else {
                    for (int i = 0; i < disC_AL.size(); i++)
                        disC_AL.get(i).setVisible(false);
                }
                break;
            case R.id.tbRandomSelection1:
                if (randomSelection.isChecked()) {
                    for (int i = 0; i < randomSelection_AL.size(); i++) {
                        randomSelection_AL.get(i).setVisible(true);
                        randomSelection_AL.get(i).setSnippet("Random Selection");
                    }

                } else {
                    for (int i = 0; i < randomSelection_AL.size(); i++)
                        randomSelection_AL.get(i).setVisible(false);
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
