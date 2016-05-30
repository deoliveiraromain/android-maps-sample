package i2.application.bisonfute.demo;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean isEventsDisplayed;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private SlidingUpPanelLayout mSlidingLayout;
    private RelativeLayout mSlidingContent;
    private TextView mSlidingContentTextTitle;
    private TextView mSlidingContentTextSnippet;
    //private CollapsingToolbarLayout mSlidingToolbar;
    private AppBarLayout mSlidingAppBar;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        setupDrawerLayout();

        setupNavigationView();

        setupSlidingLayout();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        setupMap();


    }

    private void setupSlidingLayout() {

        mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mSlidingContent = (RelativeLayout) findViewById(R.id.sliding_content);
        mSlidingContentTextTitle = (TextView) findViewById(R.id.sliding_content_text_title);
        mSlidingContentTextSnippet = (TextView) findViewById(R.id.sliding_content_text_snippet);
        //mSlidingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mSlidingAppBar = (AppBarLayout) findViewById(R.id.appBar_info);
        mSlidingLayout.setAnchorPoint(0.5f);

        mSlidingLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                // if (v == 0.5f) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_in);
                anim.setTarget(fab);
                anim.start();
                //  }
            }

            @Override
            public void onPanelCollapsed(View view) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_out);
                anim.setTarget(fab);
                anim.start();
            }

            @Override
            public void onPanelExpanded(View view) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_in);
                anim.setTarget(fab);
                anim.start();

            }

            @Override
            public void onPanelAnchored(View view) {

            }

            @Override
            public void onPanelHidden(View view) {

            }
        });

        hideSlidingLayout();
        // mSlidingLayout.setEnabled(false);
        // mSlidingContent.setVisibility(View.GONE);

    }

    private void hideSlidingLayout() {
        mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_out);
        anim.setTarget(mSlidingContent);
        anim.start();
    }

    private void showSlidingLayout() {
        mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_in);
        anim.setTarget(mSlidingContent);
        anim.start();
    }

    private void setupDrawerLayout() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    private void setupNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_traffic) {
            displayTraffic();

        } else if (id == R.id.nav_events) {
            displayEvents(!this.isEventsDisplayed);


        } else if (id == R.id.nav_conditions) {
            AnimUtils.startActivityWithAnimation(MainActivity.this, ConditionsActivity.class);

        } else if (id == R.id.nav_mentions) {
            AnimUtils.startActivityWithAnimation(MainActivity.this, MentionsLegalesActivity.class);

        } else if (id == R.id.nav_date) {

        } else if (id == R.id.nav_settings) {
            AnimUtils.startActivityWithAnimation(MainActivity.this, PreferenceActivity.class);

        }
        if (id == R.id.nav_traffic || id == R.id.nav_events) {
            menuItem.setChecked(!menuItem.isChecked());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    private void displayTraffic() {
        mMap.setTrafficEnabled(!mMap.isTrafficEnabled());
    }

    private void displayEvents(boolean isEventsDisplayed) {
        if (isEventsDisplayed) {
            drawEvents();
        } else {
            mMap.clear();
        }
        this.isEventsDisplayed = !this.isEventsDisplayed;

    }

    private void drawEvents() {
        List<MarkerOptions> liste = RandomPointsProvider.getRandomBordeauxPoints();
        for (MarkerOptions marker : liste) {
            mMap.addMarker(marker);
        }
    }

    private void setupMap() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);
        fragmentManager.beginTransaction().add(R.id.mainContent, mMapFragment).commit();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                // Add a marker in Sydney and move the camera
                setupMapUi();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            // Add a marker in Sydney and move the camera
            setupMapUi();
            mMap.setMyLocationEnabled(true);
        }
    }

    private void setupMapUi() {
        LatLng bordeaux = new LatLng(44.8404400, -0.5805000);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        //map.getUiSettings().setZoomControlsEnabled(true);
        //mMap.setOnMyLocationButtonClickListener(onMyLocationListener());
        //mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bordeaux, 13));
        //mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        mMap.setOnMarkerClickListener(onMarkerClickListener());
        mMap.setOnMapClickListener(onMapClickListener());
    }

    private GoogleMap.OnMapClickListener onMapClickListener() {
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideSlidingLayout();

            }
        };
    }


    private GoogleMap.OnMarkerClickListener onMarkerClickListener() {
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i(this.getClass().getName(), "Marker Clicked : " + marker.getTitle());
                Log.i(this.getClass().getName(), "Camera Position : " + mMap.getCameraPosition().target.latitude + mMap.getCameraPosition().target.longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                showSlidingLayout();
                setSlidingContent(marker);
                return true;
            }
        };
    }

    private void setSlidingContent(Marker marker) {
        mSlidingContentTextTitle.setText(marker.getTitle());
        mSlidingContentTextSnippet.setText(marker.getSnippet() + "  " + marker.getPosition().latitude + "  " + marker.getPosition().longitude);
        //mSlidingToolbar.setTitle(marker.getTitle());
        Toolbar toolbarInfo = (Toolbar) findViewById(R.id.toolbar_info);
        toolbarInfo.setTitle(marker.getTitle());
    }


    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                // .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(this.getClass().getName(), "Google Api Client Connected.");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, buildLocationRequest(), this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(this.getClass().getName(), "Google Api Client Suspended.");
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.i(this.getClass().getName(), "Location X : " + location.getLatitude());
            Log.i(this.getClass().getName(), "Location Y : " + location.getLongitude());
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AnimUtils.startActivityWithAnimation(MainActivity.this, PreferenceActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
