package com.example.feres.cityplus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.feres.cityplus.Adapters.SectionsPagerAdapter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Home extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private GoogleMap mMap;
    private GpsTracker gpsTracker;
    private Location mlocation;
    private double latitude;
    private double longitude;
    private ViewPager mViewPager;
    private LinearLayout cover_layout;
    private AppBarLayout appbar;
    private com.getbase.floatingactionbutton.FloatingActionsMenu fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("City+");
        setSupportActionBar(toolbar);


        gpsTracker = new GpsTracker(getApplicationContext());
        mlocation=gpsTracker.getLocation();
        latitude =mlocation.getLatitude();
        longitude=mlocation.getLongitude();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        cover_layout = (LinearLayout) findViewById(R.id.cover_layout);
        cover_layout.setOnClickListener(this);

        fab = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.fab);
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                if(cover_layout.getVisibility() == View.GONE) {
                    cover_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMenuCollapsed() {
                if(cover_layout.getVisibility() == View.VISIBLE) {
                    cover_layout.setVisibility(View.GONE);
                }
            }
        });


        CoordinatorLayout main_content = (CoordinatorLayout) findViewById(R.id.main_content);
        View bottomSheet = main_content.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.i("cjj", "newState--->" + newState);
//                ViewCompat.setScaleX(bottomSheet,1);
//                ViewCompat.setScaleY(bottomSheet,1);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("cjj", "slideOffset=====》" + slideOffset);
//                ViewCompat.setScaleX(bottomSheet,slideOffset);
//                ViewCompat.setScaleY(bottomSheet,slideOffset);
            }
        });

        findViewById(R.id.bottom_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng myPosition = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions()
                .position(myPosition)
                .title("je suis là :p")
                .snippet(myPosition.latitude+" - "+myPosition.longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(10.0f)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cover_layout){
            if(fab.isExpanded()){
                fab.collapse();
            }
        }
    }
}
