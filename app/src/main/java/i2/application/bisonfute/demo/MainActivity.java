package i2.application.bisonfute.demo;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean isEventsDisplayed;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawerLayout();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent();

        setupMap();
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    private void setupDrawerContent() {
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


        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            overridePendingTransition(R.animator.right_in, R.animator.left_out);
        }

        if (id != R.id.nav_settings) {
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
            BitmapDescriptor icon =
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_working);

            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.8417531, -0.5691530999999941))
                            .title("Miroir d'eau")
                            .snippet("Miroir d'eau de bordeaux")
                            .icon(icon)
            );
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.844469, -0.5737920000000258))
                            .title("Quinconces")
                            .snippet("Place des quinconces")
                            .icon(icon)
            );
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.8268502, -0.5560778999999911))
                            .title("Gare Saint Jean")
                            .snippet("Gare de Bordeaux")
                            .icon(icon)
            );

            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.83731700000001, -0.5771620000000439))
                            .title("Gare Saint Jean")
                            .snippet("Gare de Bordeaux")
                            .icon(icon)
            );
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.83731700000001, -0.5771620000000439))
                            .title("HDV")
                            .snippet("Hote de ville de Bordeaux")
                            .icon(icon)
            );
            Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(44.830013, -0.5963850000000548))
                            .title("Stade Chaban Delmas")
                            .snippet("Stade de Bordeaux")
                            .icon(icon)
            );
        } else {
            mMap.clear();
        }
        this.isEventsDisplayed = !this.isEventsDisplayed;

    }

    private void setupMap() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(onMapReady());
        fragmentManager.beginTransaction().add(R.id.mainContent, mMapFragment).commit();

    }


    private OnMapReadyCallback onMapReady() {
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                setupMapUi();
                buildGoogleApiClient();

            }
        };
    }

    private void setupMapUi() {
        LatLng bordeaux = new LatLng(44.8404400, -0.5805000);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.setOnMyLocationButtonClickListener(onMyLocationListener());
        //mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bordeaux));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        mMap.setOnMarkerClickListener(onMarkerClickListener());
    }

    private GoogleMap.OnMarkerClickListener onMarkerClickListener(){
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i(this.getClass().getName(), "Marker Clicked : " + marker.getTitle());
                return false;
            }
        };
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationListener() {
        return
                new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        Log.i(this.getClass().getName(), "LOCATION BUTTON CLICKED.");
                        return false;
                    }
                };
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, buildLocationRequest(), this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
