package com.pitt.mpg.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pitt.mpg.MainActivity;
import com.pitt.mpg.OutputDetails;
import com.pitt.mpg.R;
import com.pitt.mpg.RequestDetails;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/*
* Here the Request Details object is partly set with the items that exist in this algorithm fragment
* For implementing the database functionality, I check if the object's data is already set, if not set, it will not use any text, but if set, it will use the previous value
* As soon as this fragment is clicked, a server connection is established prior to user selecting any algorithms, this is done in background to improve performance
* The app cannot progress further if the server is down (crashes)
* The query is build using different arrays and then a json string is sent and received back from the server when RUN button is clicked
* On clicking the Run button, all the object parameters for Import Fragment are set. Before clicking the button nothing is saved
* Immediately after successful reply from the server, the map fragment is automatically loaded with the results
*/

public class AlgorithmFragments extends Fragment implements View.OnClickListener, OnMapReadyCallback {
    View view;
    RequestDetails rd;
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

    Button runAlgos;
    Button selectAll;
    Button resetAll;

    JSONObject ret;
    String modelId = "";
    char models_arr[] = new char[12];


    float[] b = new float[11];


    String serverName = "71.199.97.2";
    int port = 8888;

    SupportMapFragment sMapFragment;
    public static ArrayList<String> al = new ArrayList<String>();
    public static HashMap<String, ArrayList<String>> hMapOUTPUT = MainActivity.hMapOUTPUT;
    public static OutputDetails od = new OutputDetails(hMapOUTPUT);
    public HashMap<String, Integer> colorHMap = new HashMap<String, Integer>();
    String[][] resultArr = MainActivity.getResultArray();

    Socket client;
    OutputStream outToServer;
    DataOutputStream out;
    InputStream inFromServer;
    DataInputStream in;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_algorithms, container, false);
        rd = MainActivity.getRDObject();
        ret = MainActivity.getJSONObject();

        initializeColorMaps();


        sMapFragment = MainActivity.getsMapFragment();

        pdComposite = (ToggleButton) view.findViewById(R.id.tbPDComposite);
        pdDistPref = (ToggleButton) view.findViewById(R.id.tbPDDistPref);
        pagerank = (ToggleButton) view.findViewById(R.id.tbPageRank);
        pdPopDist = (ToggleButton) view.findViewById(R.id.tbPopDist);
        pdPref = (ToggleButton) view.findViewById(R.id.tbPref);
        pdCompositePagerank = (ToggleButton) view.findViewById(R.id.tbPDCompositePageRank);
        pdDistPrefPageRank = (ToggleButton) view.findViewById(R.id.tbPDDistPrefPageRank);
        kMedoids = (ToggleButton) view.findViewById(R.id.tbK_Medoids);
        disC = (ToggleButton) view.findViewById(R.id.tbDisC);
        randomSelection = (ToggleButton) view.findViewById(R.id.tbRandomSelection);

        runAlgos = (Button) view.findViewById(R.id.bRunAlgo);
        selectAll = (Button) view.findViewById(R.id.bSelectAll);
        resetAll = (Button) view.findViewById(R.id.bResetAll);

        pdComposite.setChecked(rd.is_al_PDcomposite());
        pdDistPref.setChecked(rd.is_al_PDdistpref());
        pagerank.setChecked(rd.is_al_pagerank());
        pdPopDist.setChecked(rd.is_al_PDpopdist());
        pdPref.setChecked(rd.is_al_PDpref());
        pdCompositePagerank.setChecked(rd.is_al_PDcompositepagerank());
        pdDistPrefPageRank.setChecked(rd.is_al_PDprefdistpagerank());
        kMedoids.setChecked(rd.is_al_KMedoids());
        disC.setChecked(rd.is_al_DisC());
        randomSelection.setChecked(rd.is_al_RandomSelection());


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

        //Toast.makeText(getActivity(), "Inside Algorithms!", Toast.LENGTH_SHORT).show();

        runAlgos.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        resetAll.setOnClickListener(this);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Toast.makeText(getActivity(), "Checking if the Server is Online...", Toast.LENGTH_SHORT).show();

        try {
            client = new Socket(serverName, port);
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            Toast.makeText(getActivity(), "Server is Active... Select your algorithms to run!", Toast.LENGTH_SHORT).show();

        } catch (NoRouteToHostException e){
            Toast.makeText(getActivity(), "We are sorry, the server is currently offline. Please try after some time!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(getActivity(), "We are sorry, the server is currently offline. Please try after some time!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        return view;
    }


    /**
     * This method sets the hash map so that whenever a algorithms results needs to be plotted, the appropriate color is picked
     */
    private void initializeColorMaps() {
        colorHMap.put("PD(composite)", 0);
        colorHMap.put("PD(dist+pref)", 1);
        colorHMap.put("PageRank", 2);
        colorHMap.put("PD(pop+dist)", 3);
        colorHMap.put("PD(pref)", 4);
        colorHMap.put("PD(composite+PageRank)", 5);
        colorHMap.put("PD(pref+dist+pr)", 6);
        colorHMap.put("K-medoids", 7);
        colorHMap.put("DisC", 8);
        colorHMap.put("Random", 9);

        b[0] = BitmapDescriptorFactory.HUE_RED;
        b[1] = BitmapDescriptorFactory.HUE_AZURE;
        b[2] = BitmapDescriptorFactory.HUE_GREEN;
        b[3] = BitmapDescriptorFactory.HUE_ORANGE;
        b[4] = BitmapDescriptorFactory.HUE_RED;
        b[5] = BitmapDescriptorFactory.HUE_YELLOW;
        b[6] = BitmapDescriptorFactory.HUE_MAGENTA;
        b[7] = BitmapDescriptorFactory.HUE_ROSE;
        b[8] = BitmapDescriptorFactory.HUE_CYAN;
        b[9] = BitmapDescriptorFactory.HUE_BLUE;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.bRunAlgo:
                /**
                 * An array list for selected algorithm is create which is used as value for the model name as key
                 * Array list of one algorithm holds all the markers returned for that particular algorithm
                 * Once the hash map is filled, the hash map is then sent to the putTheMarkers method
                 */
                modelId = getMID(models_arr);
                ret.put("Models", modelId);
                Toast.makeText(getActivity(), "Running your input, fetching results!", Toast.LENGTH_SHORT).show();
                Log.d("DEBUGGING algoRun ", "came in run!");
                try {
                    String details = ret.toString();
                    Log.d("DEBUGGING details  ", details);
                    //String serverName = "71.199.97.2";
                    //int port = 8888;
                    //client = new Socket(serverName, port);
                    //outToServer = client.getOutputStream();
                    //out = new DataOutputStream(outToServer);
                    //System.out.println("ret.toString() "  + ret.toString());
                    out.writeUTF(details);
                    inFromServer = client.getInputStream();
                    in = new DataInputStream(inFromServer);
                    String received = in.readUTF();
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(received);
                    JSONArray jArr = (JSONArray) json.get("Results");
                    Log.d("JSON ARR STRING : ", "Received : " + jArr.toString());
                    //Toast.makeText(getActivity(), "Received : " + jArr.toString(), Toast.LENGTH_SHORT).show();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < jArr.size(); i++) {
                        JSONObject jsonobject = (JSONObject) jArr.get(i);
                        String radius = jsonobject.get("radiusOfGyration") + "";
                        String runTime = jsonobject.get("runtime") + "";
                        String relevency = jsonobject.get("Relevnecy") + "";
                        String diversity = jsonobject.get("Diversity") + "";
                        String modelName = jsonobject.get("Model") + "";
                        int color = colorHMap.get(modelName);
                        setResultsArray(modelName, diversity, relevency, radius, runTime);
                        sb.append("Model: " + modelName + "\n");
                        Log.d("Misc Details of " + modelName, "RunTime: " + runTime + " Radius: " + radius + " Relevency: " + relevency + " Diversity: " + diversity);
                        JSONArray resArr = (JSONArray) jsonobject.get("results");
                        if (resArr != null) {
                            for (int j = 0; j < resArr.size(); j++) {
                                jsonobject = (JSONObject) resArr.get(j);
                                String venueLocation = jsonobject.get("VenueLocation") + "";
                                String venueName = jsonobject.get("VenueName") + "";
                                String toPut = venueName + ";" + venueLocation + ";" + modelName + ";" + color;
                                Log.d("DEBUGGING toPut ", toPut + " for " + modelName);

                                al.add(toPut);
                            }
                        }
                        Log.d("DEBUGGING Model", "DONE FOR " + modelName);
                        hMapOUTPUT.put(modelName, new ArrayList<String>(al));
                        al.clear();
                    }
                    od.setOut(hMapOUTPUT);
                    getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                    sMapFragment.getMapAsync(this);
                    android.support.v4.app.FragmentManager SFM = MainActivity.getSFM();
                    if (!sMapFragment.isAdded()) {
                        SFM.beginTransaction().add(R.id.map, sMapFragment).commit();
                    } else {
                        SFM.beginTransaction().show(sMapFragment).commit();
                        putTheMarkers(MainActivity.mGoogleMap, hMapOUTPUT);
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException p) {
                    p.printStackTrace();
                }


                break;


            case R.id.tbPDComposite:
                if (pdComposite.isChecked()) {
                    rd.set_al_PDcomposite(true);
                    models_arr[0] = 1;
                } else {
                    rd.set_al_PDcomposite(false);
                    models_arr[0] = 0;
                }
                break;
            case R.id.tbPDDistPref:
                if (pdDistPref.isChecked()) {
                    rd.set_al_PDdistpref(true);
                    models_arr[1] = 1;
                } else {
                    rd.set_al_PDdistpref(false);
                    models_arr[1] = 0;
                }
                break;
            case R.id.tbPageRank:
                if (pagerank.isChecked()) {
                    rd.set_al_pagerank(true);
                    models_arr[2] = 1;
                } else {
                    rd.set_al_pagerank(false);
                    models_arr[2] = 1;
                }
                break;
            case R.id.tbPopDist:
                if (pdPopDist.isChecked()) {
                    rd.set_al_PDpopdist(true);
                    models_arr[3] = 1;
                } else {
                    rd.set_al_PDpopdist(false);
                    models_arr[3] = 0;
                }
                break;
            case R.id.tbPref:
                if (pdPref.isChecked()) {
                    rd.set_al_PDpref(true);
                    models_arr[4] = 1;
                } else {
                    rd.set_al_PDpref(false);
                    models_arr[4] = 0;
                }
                break;
            case R.id.tbPDCompositePageRank:
                if (pdCompositePagerank.isChecked()) {
                    rd.set_al_PDcompositepagerank(true);
                    models_arr[5] = 1;
                } else {
                    rd.set_al_PDcompositepagerank(false);
                    models_arr[5] = 0;
                }
                break;
            case R.id.tbPDDistPrefPageRank:
                if (pdDistPrefPageRank.isChecked()) {
                    rd.set_al_PDprefdistpagerank(true);
                    models_arr[6] = 1;
                } else {
                    rd.set_al_PDprefdistpagerank(false);
                    models_arr[6] = 0;
                }
                break;
            case R.id.tbK_Medoids:
                if (kMedoids.isChecked()) {
                    rd.set_al_KMedoids(true);
                    models_arr[7] = 1;
                } else {
                    rd.set_al_KMedoids(false);
                    models_arr[7] = 0;
                }
                break;
            case R.id.tbDisC:
                if (disC.isChecked()) {
                    rd.set_al_DisC(true);
                    models_arr[8] = 1;
                } else {
                    rd.set_al_DisC(false);
                    models_arr[8] = 0;
                }
                break;
            case R.id.tbRandomSelection:
                if (randomSelection.isChecked()) {
                    rd.set_al_RandomSelection(true);
                    models_arr[9] = 1;
                } else {
                    rd.set_al_RandomSelection(false);
                    models_arr[9] = 0;
                }
                break;
            case R.id.bSelectAll:
                for (int i = 0; i <= 9; i++)
                    models_arr[i] = 1;
                rd.set_al_PDcomposite(true);
                rd.set_al_PDdistpref(true);
                rd.set_al_pagerank(true);
                rd.set_al_PDpopdist(true);
                rd.set_al_PDpref(true);
                rd.set_al_PDcompositepagerank(true);
                rd.set_al_PDprefdistpagerank(true);
                rd.set_al_KMedoids(true);
                rd.set_al_DisC(true);
                rd.set_al_RandomSelection(true);
                pdComposite.setChecked(true);
                pdDistPref.setChecked(true);
                pagerank.setChecked(true);
                pdPopDist.setChecked(true);
                pdPref.setChecked(true);
                pdCompositePagerank.setChecked(true);
                pdDistPrefPageRank.setChecked(true);
                kMedoids.setChecked(true);
                disC.setChecked(true);
                randomSelection.setChecked(true);
                break;
            case R.id.bResetAll:
                for (int i = 0; i <= 9; i++)
                    models_arr[i] = 0;
                rd.set_al_PDcomposite(false);
                rd.set_al_PDdistpref(false);
                rd.set_al_pagerank(false);
                rd.set_al_PDpopdist(false);
                rd.set_al_PDpref(false);
                rd.set_al_PDcompositepagerank(false);
                rd.set_al_PDprefdistpagerank(false);
                rd.set_al_KMedoids(false);
                rd.set_al_DisC(false);
                rd.set_al_RandomSelection(false);
                pdComposite.setChecked(false);
                pdDistPref.setChecked(false);
                pagerank.setChecked(false);
                pdPopDist.setChecked(false);
                pdPref.setChecked(false);
                pdCompositePagerank.setChecked(false);
                pdDistPrefPageRank.setChecked(false);
                kMedoids.setChecked(false);
                disC.setChecked(false);
                randomSelection.setChecked(false);
                break;

        }
    }

    /**
     * These are the results retreived from the server and properly filled in the respective positions in result array which is then
     * used in the Compare Fragment in a table layout to display the results
     * @param modelName
     * @param diversity
     * @param relevency
     * @param radius
     * @param runTime
     */
    private void setResultsArray(String modelName, String diversity, String relevency, String radius, String runTime) {
        if (diversity.length() > 6)
            diversity = diversity.substring(0,6);
        if (relevency.length() > 6)
            relevency = relevency.substring(0,6);
        if (radius.length() > 6)
            radius = radius.substring(0,6);
        switch (modelName) {
            case "PageRank":
                resultArr[2][1] = diversity;
                resultArr[2][2] = relevency;
                resultArr[2][3] = radius;
                resultArr[2][4] = runTime;
                break;
            case "PD(dist+pref)":
                resultArr[1][1] = diversity;
                resultArr[1][2] = relevency;
                resultArr[1][3] = radius;
                resultArr[1][4] = runTime;
                break;
            case "PD(pop+dist)":
                resultArr[3][1] = diversity;
                resultArr[3][2] = relevency;
                resultArr[3][3] = radius;
                resultArr[3][4] = runTime;
                break;
            case "PD(pref)":
                resultArr[4][1] = diversity;
                resultArr[4][2] = relevency;
                resultArr[4][3] = radius;
                resultArr[4][4] = runTime;
                break;
            case "PD(composite+PageRank)":
                resultArr[5][1] = diversity;
                resultArr[5][2] = relevency;
                resultArr[5][3] = radius;
                resultArr[5][4] = runTime;
                break;
            case "PD(pref+dist+pr)":
                resultArr[6][1] = diversity;
                resultArr[6][2] = relevency;
                resultArr[6][3] = radius;
                resultArr[6][4] = runTime;
                break;
            case "K-medoids":
                resultArr[7][1] = diversity;
                resultArr[7][2] = relevency;
                resultArr[7][3] = radius;
                resultArr[7][4] = runTime;
                break;
            case "DisC":
                resultArr[8][1] = diversity;
                resultArr[8][2] = relevency;
                resultArr[8][3] = radius;
                resultArr[8][4] = runTime;
                break;
            case "Random":
                resultArr[9][1] = diversity;
                resultArr[9][2] = relevency;
                resultArr[9][3] = radius;
                resultArr[9][4] = runTime;
                break;
            default:
                resultArr[0][1] = diversity;
                resultArr[0][2] = relevency;
                resultArr[0][3] = radius;
                resultArr[0][4] = runTime;
                break;

        }
    }

    /**
     * The two arguments taken are
     * @param mGoogleMap this is the main activity singleton object of Google map
     * @param hMapOUTPUT this is the hash map that is set when server replies and the results are parsed and rendered
     */
    private void putTheMarkers(GoogleMap mGoogleMap, HashMap<String, ArrayList<String>> hMapOUTPUT) {
        for (String key : hMapOUTPUT.keySet()) {
            Log.d("HASHMAP in putMarkers", key + " :: " + hMapOUTPUT.get(key));
        }

        if (rd.get_city() == "San Fransisco") {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 12.0f));
        } else {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.770039, -73.826566), 12.0f));
        }

        for (String key : hMapOUTPUT.keySet()) {
            //System.out.println(key  +" :: "+ hMapOUTPUT.get(key));

            ArrayList<String> ven = hMapOUTPUT.get(key);
            for (int i = 0; i < ven.size(); i++) {
                String[] arr = ven.get(i).split(";");
                Marker m = mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arr[1]), Double.parseDouble(arr[2])))
                        .title(arr[0])
                        .icon(BitmapDescriptorFactory.defaultMarker(b[Integer.parseInt(arr[4])])));
                m.setSnippet(arr[3]);

                //title;Lat;Lng;color
            }
        }


    }

    /**
     *
     * @param models_arr takes model array that get sets at particular index every time the respective click event occurs
     * @return a string which is sent to server to indicate which algorithms are selected
     */
    private String getMID(char[] models_arr) {
        String list = "";

        for (int i = 0; i < 10; i++) {
            if (models_arr[i] == 1) {
                if (i != 9)
                    list += i + ",";
                else
                    list += i;
            }
        }
        return list;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    /**
     *
     * @return Output Details object to check if it is null when this fragment is opened. We cannot allow this object to crash the app if it is null
     */
    public static OutputDetails getOD() {
        return od;
    }

}
