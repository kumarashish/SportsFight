package sportsfight.com.s.launchingmodule;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;

import sportsfight.com.s.common.PrefManager;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.loginmodule.Registration;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.NewDashBoard;
import sportsfight.com.s.util.AESHelper;


public class Splash extends Activity {
    AppController controller;
    private static int SPLASH_TIME_OUT = 3000;
    final int permissionReadExternalStorage=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        controller=(AppController)getApplicationContext();
        int permissionCheck = ContextCompat.checkSelfPermission(Splash.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Splash.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        permissionReadExternalStorage);
            } else {
                runThread();
            }
        } else {
            runThread();
        }
        try {
            String encryptedData = AESHelper.getKey("JaffaDon'ttrytodebugmycode");
            String encrypt = AESHelper.encrypt(encryptedData, "hello welcome to sportsfight app");
            String decrypt = AESHelper.decrypt("8B7CFBDE10757F86FE945B1110A3EDF4", "DD50C697CDF83B448F96C060F6F4B922616F21BA8B45F8B6CE3587B3D4A81BB3F4F76B4E48CB10D6DFFBA6DDB4AC2753");
            System.out.print(encryptedData);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }


    public void runThread()
    {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                launchHomeScreen();
            }
        }, SPLASH_TIME_OUT);
    }

    public void launchHomeScreen() {
        if (controller.getPrefManager().isFirstTimeLaunch()) {
            controller.getPrefManager().setFirstTimeLaunch(false);
            Intent i = new Intent(Splash.this,Registration.class);
            Registration.isCalledFromSplash=true;
            startActivity(i);
        } else {
            if (controller.getPrefManager().isUserLoggedIn()) {
                Intent i = new Intent(Splash.this, NewDashBoard.class);
                startActivity(i);
            } else {
                Intent i = new Intent(Splash.this, Login.class);
                startActivity(i);
            }


        }
        // close this activity
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permissionReadExternalStorage: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   runThread();
                } else {

                    Toast.makeText(Splash.this,"Please provide read external storage permission.",Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }
}
