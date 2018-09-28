package sportsfight.com.s.mainmodule;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.ms_square.etsyblur.BlurSupport;
import com.prof.rssparser.Parser;
import org.w3c.dom.Text;
import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.fragment.BookingFragment;
import sportsfight.com.s.fragment.HomeFragment;
import sportsfight.com.s.fragment.MyGame;
import sportsfight.com.s.fragment.NotificationFragment;
import sportsfight.com.s.fragment.WalletFragment;
import sportsfight.com.s.interfaces.ViewAllCallBack;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.staticpages.ContactUs;
import sportsfight.com.s.util.Util;

public class NewDashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener,ViewAllCallBack,View.OnClickListener {
    AppController controller;
    TextView heading,yourLocation,distance;
    FrameLayout layout;
    int backPressedCount=0;
    int selectedFragment=-1;
    BottomNavigationView navigation;
    @BindView(R.id.logout)
    View logout;
    @BindView(R.id.contact_us)
    View contactUs;
    @BindView(R.id.profile)
    View profile;
    DrawerLayout drawer;
    Button add_button;
    RelativeLayout createTeam_bg;

    boolean isaddTeamClicked=false;
    LinearLayout expandedView;

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
        View  dashboardView=(View) findViewById(R.id.dashdoard);

        Button button=(Button)dashboardView.findViewById(R.id.menu);
        add_button=(Button) dashboardView.findViewById(R.id.add_team);
        createTeam_bg=(RelativeLayout) dashboardView.findViewById(R.id.createTeamBg);
        expandedView=(LinearLayout) dashboardView.findViewById(R.id.expadedView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        initializeAll(dashboardView);

    }

    public void initializeAll(View view)
    {   logout.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        profile.setOnClickListener(this);
        add_button.setOnClickListener(this);
        heading=(TextView)view.findViewById(R.id.yourLocation_tv);
        yourLocation=(TextView)view.findViewById(R.id.heading_tv);
        distance=(TextView)view.findViewById(R.id.distance_tv);
        layout=(FrameLayout)view.findViewById(R.id.frame);
        loadfragment(1);

    }


public void loadfragment(int value)
{
    Fragment fragmentA =null;
    if (fragmentA == null) {
        switch (value){
            case 1:
                fragmentA = new HomeFragment();
                add_button.setVisibility(View.VISIBLE);
                if(isaddTeamClicked)
                {
                    expandedView.setVisibility(View.VISIBLE);
                }
                selectedFragment=1;
                break;
            case 2:
                fragmentA = new WalletFragment();
                add_button.setVisibility(View.GONE);
                expandedView.setVisibility(View.GONE);
                backPressedCount=0;
                selectedFragment=2;
                break;
            case 3:
                fragmentA = new BookingFragment();
                add_button.setVisibility(View.GONE);
                expandedView.setVisibility(View.GONE);
                backPressedCount=0;
                selectedFragment=3;
                break;
            case 4:
                fragmentA = new MyGame();
                add_button.setVisibility(View.GONE);
                expandedView.setVisibility(View.GONE);
                backPressedCount=0;
                selectedFragment=4;
                break;
            case 5:
                fragmentA = new NotificationFragment();
                add_button.setVisibility(View.GONE);
                backPressedCount=0;
                selectedFragment=5;
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
        switch (view.getId())
        {
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
            case R.id.add_team:
                if(isaddTeamClicked)
                {  layout.setAlpha(1f);
                    isaddTeamClicked=false;
                    expandedView.setVisibility(View.GONE);

                }else {
                    layout.setAlpha(.1f);

                    //createTeam_bg.bringToFront();
                    isaddTeamClicked=true;
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
              int id=  navigation.getMenu().getItem(0).getItemId();
                navigation.setSelectedItemId(id);
                selectedFragment = 1;
                backPressedCount=0;
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
                Util.startActivityCommon(NewDashBoard.this,new Intent(NewDashBoard.this, Login.class));
                finish();
            }
        });
        dialog.show();
    }

}
