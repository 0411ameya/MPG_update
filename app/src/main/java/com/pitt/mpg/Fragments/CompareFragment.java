package com.pitt.mpg.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.pitt.mpg.MainActivity;
import com.pitt.mpg.OutputDetails;
import com.pitt.mpg.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CompareFragment extends Fragment implements View.OnClickListener{

    TextView pdComposite_d;
    TextView pdComposite_r;
    TextView pdComposite_g;
    TextView pdComposite_runtime;

    TextView pdDistPref_d;
    TextView pdDistPref_r;
    TextView pdDistPref_g;
    TextView pdDistPref_runtime;

    TextView pageRank_d;
    TextView pageRank_r;
    TextView pageRank_g;
    TextView pageRank_runtime;

    TextView pdPopDist_d;
    TextView pdPopDist_r;
    TextView pdPopDist_g;
    TextView pdPopDist_runtime;

    TextView pdPref_d;
    TextView pdPref_r;
    TextView pdPref_g;
    TextView pdPref_runtime;

    TextView pdCompositePr_d;
    TextView pdCompositePr_r;
    TextView pdCompositePr_g;
    TextView pdCompositePr_runtime;

    TextView pdDistPrefPr_d;
    TextView pdDistPrefPr_r;
    TextView pdDistPrefPr_g;
    TextView pdDistPrefPr_runtime;

    TextView kMedoids_d;
    TextView kMedoids_r;
    TextView kMedoids_g;
    TextView kMedoids_runtime;

    TextView disC_d;
    TextView disC_r;
    TextView disC_g;
    TextView disC_runtime;

    TextView random_d;
    TextView random_r;
    TextView random_g;
    TextView random_runtime;

    String[][] resultArray = MainActivity.getResultArray();
    Button clearALL;

    public static OutputDetails od;



    GoogleMap mG = MainActivity.mGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_compare, container, false);

        pdComposite_d = (TextView) v.findViewById(R.id.row_d_PDCOMPOSITE);
        pdComposite_r = (TextView) v.findViewById(R.id.row_r_PDCOMPOSITE);
        pdComposite_g = (TextView) v.findViewById(R.id.row_g_PDCOMPOSITE);
        pdComposite_runtime = (TextView) v.findViewById(R.id.row_run_PDCOMPOSITE); //PD COMPOSITE 1

        pdDistPref_d = (TextView) v.findViewById(R.id.row_d_PDDISTPREF);
        pdDistPref_r = (TextView) v.findViewById(R.id.row_r_PDDISTPREF);
        pdDistPref_g = (TextView) v.findViewById(R.id.row_g_PDDISTPREF);
        pdDistPref_runtime = (TextView) v.findViewById(R.id.row_run_PDDISTPREF); // PD DIST PREF 2

        pageRank_d = (TextView) v.findViewById(R.id.row_d_PAGERANK);
        pageRank_r = (TextView) v.findViewById(R.id.row_r_PAGERANK);
        pageRank_g = (TextView) v.findViewById(R.id.row_g_PAGERANK);
        pageRank_runtime = (TextView) v.findViewById(R.id.row_run_PAGERANK); //PAGE RANK 3

        pdPopDist_d = (TextView) v.findViewById(R.id.row_d_PDPOPDIST);
        pdPopDist_r = (TextView) v.findViewById(R.id.row_r_PDPOPDIST);
        pdPopDist_g = (TextView) v.findViewById(R.id.row_g_PDPOPDIST);
        pdPopDist_runtime = (TextView) v.findViewById(R.id.row_run_PDPOPDIST); // PD POP DIST 4

        pdPref_d = (TextView) v.findViewById(R.id.row_d_PDPREF);
        pdPref_r = (TextView) v.findViewById(R.id.row_r_PDPREF);
        pdPref_g = (TextView) v.findViewById(R.id.row_g_PDPREF);
        pdPref_runtime = (TextView) v.findViewById(R.id.row_run_PDPREF); //PD PREF 5

        pdCompositePr_d = (TextView) v.findViewById(R.id.row_d_PDCOMPOSITEPR);
        pdCompositePr_r = (TextView) v.findViewById(R.id.row_r_PDCOMPOSITEPR);
        pdCompositePr_g = (TextView) v.findViewById(R.id.row_g_PDCOMPOSITEPR);
        pdCompositePr_runtime = (TextView) v.findViewById(R.id.row_run_PDCOMPOSITEPR); // PD POP DIST 6

        pdDistPrefPr_d = (TextView) v.findViewById(R.id.row_d_PDDISTPREFPR);
        pdDistPrefPr_r = (TextView) v.findViewById(R.id.row_r_PDDISTPREFPR);
        pdDistPrefPr_g = (TextView) v.findViewById(R.id.row_g_PDDISTPREFPR);
        pdDistPrefPr_runtime = (TextView) v.findViewById(R.id.row_run_PDDISTPREFPR); //PD DIST PREF 7

        kMedoids_d = (TextView) v.findViewById(R.id.row_d_KMEDOIDS);
        kMedoids_r = (TextView) v.findViewById(R.id.row_r_KMEDOIDS);
        kMedoids_g = (TextView) v.findViewById(R.id.row_g_KMEDOIDS);
        kMedoids_runtime = (TextView) v.findViewById(R.id.row_run_KMEDOIDS); // PD POP DIST 8

        disC_d = (TextView) v.findViewById(R.id.row_d_DISC);
        disC_r = (TextView) v.findViewById(R.id.row_r_DISC);
        disC_g = (TextView) v.findViewById(R.id.row_g_DISC);
        disC_runtime = (TextView) v.findViewById(R.id.row_run_DISC); //PD DIST PREF 9

        random_d = (TextView) v.findViewById(R.id.row_d_RANDOM);
        random_r = (TextView) v.findViewById(R.id.row_r_RANDOM);
        random_g = (TextView) v.findViewById(R.id.row_g_RANDOM);
        random_runtime = (TextView) v.findViewById(R.id.row_run_RANDOM); // PD POP DIST 10

        clearALL = (Button) v.findViewById(R.id.b_clearALL);

        clearALL.setOnClickListener(this);

        setAllTextsToTextViews();

        return v;
    }

    private void setAllTextsToTextViews() {
        pdComposite_d.setText(resultArray[0][1]);
        pdComposite_r.setText(resultArray[0][2]);
        pdComposite_g.setText(resultArray[0][3]);
        pdComposite_runtime.setText(resultArray[0][4]); //1

        pdDistPref_d.setText(resultArray[1][1]);
        pdDistPref_r.setText(resultArray[1][2]);
        pdDistPref_g.setText(resultArray[1][3]);
        pdDistPref_runtime.setText(resultArray[1][4]); //2

        pageRank_d.setText(resultArray[2][1]);
        pageRank_r.setText(resultArray[2][2]);
        pageRank_g.setText(resultArray[2][3]);
        pageRank_runtime.setText(resultArray[2][4]); //3

        pdPopDist_d.setText(resultArray[3][1]);
        pdPopDist_r.setText(resultArray[3][2]);
        pdPopDist_g.setText(resultArray[3][3]);
        pdPopDist_runtime.setText(resultArray[3][4]); //4

        pdPref_d.setText(resultArray[4][1]);
        pdPref_r.setText(resultArray[4][2]);
        pdPref_g.setText(resultArray[4][3]);
        pdPref_runtime.setText(resultArray[4][4]); //5

        pdCompositePr_d.setText(resultArray[5][1]);
        pdCompositePr_r.setText(resultArray[5][2]);
        pdCompositePr_g.setText(resultArray[5][3]);
        pdCompositePr_runtime.setText(resultArray[5][4]); //6

        pdDistPrefPr_d.setText(resultArray[6][1]);
        pdDistPrefPr_r.setText(resultArray[6][2]);
        pdDistPrefPr_g.setText(resultArray[6][3]);
        pdDistPrefPr_runtime.setText(resultArray[6][4]); //7

        kMedoids_d.setText(resultArray[7][1]);
        kMedoids_r.setText(resultArray[7][2]);
        kMedoids_g.setText(resultArray[7][3]);
        kMedoids_runtime.setText(resultArray[7][4]); //8

        disC_d.setText(resultArray[8][1]);
        disC_r.setText(resultArray[8][2]);
        disC_g.setText(resultArray[8][3]);
        disC_runtime.setText(resultArray[8][4]); //9

        random_d.setText(resultArray[9][1]);
        random_r.setText(resultArray[9][2]);
        random_g.setText(resultArray[9][3]);
        random_runtime.setText(resultArray[9][4]); //10

    }

    @Override
    public void onClick(View view) {
        mG.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                resultArray[i][j] = "-";
            }
        }
        setAllTextsToTextViews();
        od = AlgorithmFragments.getOD();
        od.setOut(new HashMap<String, ArrayList<String>>());
        MainActivity.resetRDObject();
    }
}
