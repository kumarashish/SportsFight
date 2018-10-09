package sportsfight.com.s.mainmodule;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.ms_square.etsyblur.BlurSupport;
import com.prof.rssparser.Parser;
import org.w3c.dom.Text;
import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.PlaceArrayAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.LocationSearch;
import sportsfight.com.s.fragment.BookingFragment;
import sportsfight.com.s.fragment.HomeFragment;
import sportsfight.com.s.fragment.MyGame;
import sportsfight.com.s.fragment.NotificationFragment;
import sportsfight.com.s.fragment.WalletFragment;
import sportsfight.com.s.interfaces.ViewAllCallBack;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.staticpages.ContactUs;
import sportsfight.com.s.util.Util;

public class NewDashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener,ViewAllCallBack,View.OnClickListener, LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    AppController controller;
    TextView heading, yourLocation, distance;
    FrameLayout layout;
    int backPressedCount = 0;
    int selectedFragment = -1;
    BottomNavigationView navigation;
    @BindView(R.id.logout)
    View logout;
    @BindView(R.id.contact_us)
    View contactUs;
    @BindView(R.id.profile)
    View profile;
    DrawerLayout drawer;
    View add_team;
    RelativeLayout createTeam_bg;
    ImageView addIcon;

    boolean isaddTeamClicked = false;
    LinearLayout expandedView;
    View createTeam;
    View myTeam;
    RelativeLayout addView;
    AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    ProgressBar progressbar;
    private PlaceArrayAdapter mPlaceArrayAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dash_board);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View dashboardView = (View) findViewById(R.id.dashdoard);
        Button button = (Button) dashboardView.findViewById(R.id.menu);
        add_team = (View) dashboardView.findViewById(R.id.add_team);
        addIcon = (ImageView) dashboardView.findViewById(R.id.addIcon);
        createTeam = (View) dashboardView.findViewById(R.id.createTeam);
        myTeam = (View) dashboardView.findViewById(R.id.myTeam);
        addView = (RelativeLayout) dashboardView.findViewById(R.id.addView);
        createTeam_bg = (RelativeLayout) dashboardView.findViewById(R.id.createTeamBg);
        expandedView = (LinearLayout) dashboardView.findViewById(R.id.expadedView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        initializeAll(dashboardView);
        mGoogleApiClient = new GoogleApiClient.Builder(NewDashBoard.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .enableAutoManage(NewDashBoard.this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        createLocationRequest();
    }

    public void initializeAll(View view) {
        logout.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        profile.setOnClickListener(this);
        add_team.setOnClickListener(this);
        createTeam.setOnClickListener(this);
        myTeam.setOnClickListener(this);
        heading = (TextView) view.findViewById(R.id.yourLocation_tv);
        yourLocation = (TextView) view.findViewById(R.id.heading_tv);
        distance = (TextView) view.findViewById(R.id.distance_tv);
        layout = (FrameLayout) view.findViewById(R.id.frame);
        yourLocation.setOnClickListener(this);
        loadfragment(1);
    }


    public void loadfragment(int value) {
        Fragment fragmentA = null;
        if (fragmentA == null) {
            switch (value) {
                case 1:
                    fragmentA = new HomeFragment();
                    addView.setVisibility(View.VISIBLE);
                    if (isaddTeamClicked) {
                        expandedView.setVisibility(View.VISIBLE);
                    }
                    selectedFragment = 1;
                    break;
                case 2:
                    fragmentA = new WalletFragment();
                    addView.setVisibility(View.GONE);
                    expandedView.setVisibility(View.GONE);
                    backPressedCount = 0;
                    selectedFragment = 2;
                    break;
                case 3:
                    fragmentA = new BookingFragment();
                    addView.setVisibility(View.GONE);
                    expandedView.setVisibility(View.GONE);
                    backPressedCount = 0;
                    selectedFragment = 3;
                    break;
                case 4:
                    fragmentA = new MyGame();
                    addView.setVisibility(View.GONE);
                    expandedView.setVisibility(View.GONE);
                    backPressedCount = 0;
                    selectedFragment = 4;
                    break;
                case 5:
                    fragmentA = new NotificationFragment();
                    addView.setVisibility(View.GONE);
                    backPressedCount = 0;
                    selectedFragment = 5;
                    break;
            }
        }
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(layout.getId(), fragmentA);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                heading.setVisibility(View.VISIBLE);
                distance.setVisibility(View.VISIBLE);
                yourLocation.setText("Hitech City,Madhapur");
                loadfragment(1);
                return true;
            case R.id.navigation_wallet:
                heading.setVisibility(View.GONE);
                distance.setVisibility(View.GONE);
                yourLocation.setText("Wallet");
                loadfragment(2);
                return true;
            case R.id.navigation_booking:
                heading.setVisibility(View.GONE);
                distance.setVisibility(View.GONE);
                yourLocation.setText("Booking");
                loadfragment(3);
                return true;
            case R.id.navigation_mygame:
                heading.setVisibility(View.GONE);
                distance.setVisibility(View.GONE);
                yourLocation.setText("MyGame");
                loadfragment(4);
                return true;
            case R.id.navigation_notifications:
                heading.setVisibility(View.GONE);
                distance.setVisibility(View.GONE);
                yourLocation.setText("Notifications");
                loadfragment(5);
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                showAlert();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.contact_us:
                startActivity(new Intent(NewDashBoard.this, ContactUs.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile:
                startActivity(new Intent(NewDashBoard.this, NewProfile.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.createTeam:
                Toast.makeText(NewDashBoard.this, "Comming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.myTeam:
                Toast.makeText(NewDashBoard.this, "Comming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.heading_tv:
                showChangeLocationAlert();
                break;
            case R.id.add_team:
                if (isaddTeamClicked) {
                    addIcon.setImageDrawable(getApplicationContext().getDrawable(R.drawable.add));
                    layout.setAlpha(1f);
                    isaddTeamClicked = false;
                    expandedView.setVisibility(View.GONE);

                } else {
                    addIcon.setImageDrawable(getApplicationContext().getDrawable(R.drawable.minus));
                    layout.setAlpha(.1f);
                    //createTeam_bg.bringToFront();
                    isaddTeamClicked = true;
                    expandedView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void CongratulationViewALL() {

    }

    @Override
    public void MyMatchesViewALL() {

    }

    @Override
    public void UpComingViewALL() {

    }

    @Override
    public void TournamentsViewALL() {

    }

    @Override
    public void NewsViewALL() {

    }

    @Override
    public void onBackPressed() {
        if (backPressedCount == 0) {
            backPressedCount = 1;
            if (selectedFragment != 1) {
                int id = navigation.getMenu().getItem(0).getItemId();
                navigation.setSelectedItemId(id);
                selectedFragment = 1;
                backPressedCount = 0;
            }
            if (backPressedCount == 1) {
                Toast.makeText(NewDashBoard.this, "Press again to close app", Toast.LENGTH_SHORT).show();
            }
        } else {
            finish();
        }
    }

    public void showAlert() {
        final Dialog dialog = new Dialog(this);
        LottieAnimationView animationView;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button no = (Button) dialog.findViewById(R.id.no);
        Button yes = (Button) dialog.findViewById(R.id.yes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.logout();
                dialog.cancel();
                Util.startActivityCommon(NewDashBoard.this, new Intent(NewDashBoard.this, Login.class));
                finish();
            }
        });
        dialog.show();
    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            controller.setCurrentAddress(String.valueOf(item.description));
            getLocation(placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    public void getLocation(String placeId) {
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            controller.setLocation(queriedLocation);
                            Log.v("Latitude is", "" + queriedLocation.latitude);
                            Log.v("Longitude is", "" + queriedLocation.longitude);
                            mAutocompleteTextView.setText(controller.getAddress());
                        }
                        places.release();
                    }
                });
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            // mNameView.setText(Html.fromHtml(place.getAddress() + ""));


        }
    };
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                }
            };

    public void showChangeLocationAlert() {
        final Dialog dialog = new Dialog(this);
        LottieAnimationView animationView;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_location_popup);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        RadioGroup group = (RadioGroup) dialog.findViewById(R.id.radio_gp);
        View view=(View)dialog.findViewById(R.id.view);
        mAutocompleteTextView = (AutoCompleteTextView) dialog.findViewById(R.id.mAutocompleteTextView);
        mAutocompleteTextView.setThreshold(2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
        progressbar=(ProgressBar)dialog.findViewById(R.id.progressbar);

        if (HomeFragment.selectedId != -1) {
            group.check(HomeFragment.selectedId);
        } else {
            group.check(R.id.radio1);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(NewDashBoard.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewDashBoard.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (ActivityCompat.checkSelfPermission(NewDashBoard.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewDashBoard.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                progressbar.bringToFront();
                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, NewDashBoard.this);
                Log.d(TAG, "Location update started ..............: ");
            }
        });
        Button done = (Button) dialog.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                HomeFragment.selectedId = i;
                switch (i) {
                    case R.id.radio1:
                        HomeFragment.selectedRange = 5;
                        break;
                    case R.id.radio2:
                        HomeFragment.selectedRange = 10;
                        break;
                    case R.id.radio3:
                        HomeFragment.selectedRange = 15;
                        break;
                    case R.id.radio4:
                        HomeFragment.selectedRange = 20;
                        break;
                }
            }
        });


        dialog.show();
    }



    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            String address = Util.getCompleteAddressString(NewDashBoard.this, location.getLatitude(), location.getLongitude());
            if(   mAutocompleteTextView!=null) {
                mAutocompleteTextView.setText(address);
                progressbar.setVisibility(View.GONE);
            }
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            controller.setAddress(address, loc);



        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) NewDashBoard.this);
            Log.d(TAG, "Location update stopped .......................");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

    }
}
