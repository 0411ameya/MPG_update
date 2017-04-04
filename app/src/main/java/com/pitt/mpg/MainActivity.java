package com.pitt.mpg;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pitt.mpg.Fragments.AlgorithmFragments;
import com.pitt.mpg.Fragments.ImportFragment;
import com.pitt.mpg.Fragments.ProfileFragment;
import com.pitt.mpg.Fragments.ResultsFragment;

import org.json.simple.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    public static JSONObject ret;
    public static SupportMapFragment sMapFragment;
    public static GoogleMap mGoogleMap;
    public static RequestDetails rd;
    public static android.support.v4.app.FragmentManager SFM;
    public static LatLng ll = new LatLng(37.7749, -122.4194); //37.7749, -122.4194
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ret = new JSONObject();
        rd = new RequestDetails();
        sMapFragment = SupportMapFragment.newInstance();
        SFM = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //FragmentManager fm = getFragmentManager();
        //fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();

        sMapFragment.getMapAsync(this);

        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.normalMap) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (id == R.id.satellite) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (id == R.id.terrain) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        if (id == R.id.hybrid) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();

        int id = item.getItemId();



        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        if (id == R.id.nav_input) {
            FrameLayout fl = (FrameLayout)findViewById(R.id.content_frame);
            fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
            //fm.beginTransaction().addToBackStack(null);

        } else if (id == R.id.nav_gallery) {

            if (!sMapFragment.isAdded())
                sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        } else if (id == R.id.nav_algorithms) {
            FrameLayout fl = (FrameLayout)findViewById(R.id.content_frame);
            fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new AlgorithmFragments()).commit();

        } else if (id == R.id.nav_profiles) {
            FrameLayout fl = (FrameLayout)findViewById(R.id.content_frame);
            fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();

        } else if (id == R.id.nav_result) {
            FrameLayout fl = (FrameLayout)findViewById(R.id.content_frame);
            fl.removeAllViews();
            fm.beginTransaction().replace(R.id.content_frame, new ResultsFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mGoogleMap.setMyLocationEnabled(true);

        googleMap.setBuildingsEnabled(true);
        //googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 12.0f));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.7749, -122.4194))
                .draggable(true)
                .title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                 ll =  marker.getPosition();
            }
        });
    }

    public static RequestDetails getRDObject() {
      return rd;
    }

    public static JSONObject getJSONObject() {
        return ret;
    }

    public static android.support.v4.app.FragmentManager getSFM(){
        return SFM;
    }

    public static SupportMapFragment getsMapFragment(){
        return sMapFragment;
    }

    public static GoogleMap getG_MapObject(){
        return mGoogleMap;
    }

    public static LatLng getMarkerPosition(){
        return ll;
    }

}
