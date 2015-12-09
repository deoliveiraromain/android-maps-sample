package i2.application.bisonfute.demo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.ActivityOptions;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mSlidingContent = (RelativeLayout) findViewById(R.id.sliding_content);
        mSlidingContentTextTitle = (TextView) findViewById(R.id.sliding_content_text_title);
        mSlidingContentTextSnippet = (TextView) findViewById(R.id.sliding_content_text_snippet);
        //mSlidingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mSlidingAppBar = (AppBarLayout) findViewById(R.id.appBar_info);
        mSlidingLayout.setAnchorPoint(0.5f);

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

        hideSlidingLayout(mSlidingContent);
        // mSlidingLayout.setEnabled(false);
        // mSlidingContent.setVisibility(View.GONE);
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
            // inside your activity (if you did not enable transitions in your theme)
            //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // set an exit transition
            //getWindow().setExitTransition(new Explode());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
                ActivityCompat.startActivity(MainActivity.this, i,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
            }

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
            drawEvents();
        } else {
            mMap.clear();
        }
        this.isEventsDisplayed = !this.isEventsDisplayed;

    }

    private void drawEvents() {
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
                .title("HDV")
                .snippet("Hote de ville de Bordeaux")
                .icon(icon)
        );
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(44.830013, -0.5963850000000548))
                .title("Stade Chaban Delmas")
                .snippet("Stade de Bordeaux")
                .icon(icon)
        );
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
                hideSlidingLayout(mSlidingContent);
                mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        };
    }

    private void hideSlidingLayout(View view) {
        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_out);
        anim.setTarget(view);
        anim.start();
    }

    private GoogleMap.OnMarkerClickListener onMarkerClickListener() {
        return new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i(this.getClass().getName(), "Marker Clicked : " + marker.getTitle());
                Log.i(this.getClass().getName(), "Camera Position : " + mMap.getCameraPosition().target.latitude + mMap.getCameraPosition().target.longitude);
//                if (mMap.getCameraPosition().target.equals(marker.getPosition())) {
//                    //c'est le deuxieme click sur le marker donc on etent le layout
//                    Log.i(this.getClass().getName(), "Second time Marker Clicked : " + marker.getTitle());
//                    mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
//                } else {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                showSlidingLayout(mSlidingContent);
                setSlidingContent(marker);
//                }
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

    private void showSlidingLayout(View view) {
        Animator anim = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.fade_in);
        anim.setTarget(view);
        anim.start();
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
