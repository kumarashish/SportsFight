package sportsfight.com.s.mainmodule;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.ipl.MyBid;
import sportsfight.com.s.launchingmodule.Splash;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.model.GameModel;
import sportsfight.com.s.model.InterestedGameModel;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 30-01-2018.
 */

public class Profile extends Activity implements View.OnClickListener,WebApiResponseCallback {
    @BindView(R.id.profilePic)
    de.hdodenhof.circleimageview.CircleImageView profilePic;
    @BindView(R.id.editProfilePic)
    ImageView editProfilePic;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.interestedGame)
    LinearLayout interestedGame;
    @BindView(R.id.no_gameInterest)
    LinearLayout noInterest;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.genderGroup)
    RadioGroup radioGroup;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.editEmail)
    ImageView editEmail;
    @BindView(R.id.editMobile)
    ImageView editMobile;
    @BindView(R.id.saveGender)
    Button editGender;
    @BindView(R.id.editGamePrefs)
    ImageView editGameBtn;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.appversion)
    TextView appVersion;
    boolean isChessSelected = false;
    boolean isPoolSelected = false;
    boolean isBasketBallSelected = false;
    boolean isAirHockeySelected = false;
    boolean isFoosBallSelected = false;
    boolean isTTSelected = false;
    boolean isCarromSlected = false;
    boolean isMiniGolfSelected = false;

    final public static int SELECT_FILE = 02;
    public final int permissionReadCamera = 1;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    AppController controller;
    AlertDialog alertDialog = null;
    AlertDialog updateMobile_Email = null;
    Dialog otpAlertDialog = null;
    Dialog gamePrefs = null;
    boolean isImageCaptured = false;
    int apiCall = 0;
    final int getProfile = 1, updateProfilePic = 2, updateEmail = 3, updateMobile = 4, updateGender = 5, verifyOtp = 6, resendOtp = 7, getGameList = 8, updatePreffedGame = 9;
    Dialog dialog;
    int genderid = 1;
    String tempMobileNumber = null;
    ArrayList<GameModel> list = new ArrayList<>();
    ArrayList<Integer> interestedGameList = new ArrayList<>();
    ArrayList<Integer> tempInterestedGame = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        initializeAll();
        setValues();
        int permissionCheck = ContextCompat.checkSelfPermission(Profile.this,
                Manifest.permission.CAMERA);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Profile.this,
                        new String[]{Manifest.permission.CAMERA},
                        permissionReadCamera);
            }
            if (Util.isNetworkAvailable(Profile.this)) {
                dialog = Util.showPogress(Profile.this);
                apiCall = getGameList;
                controller.getApiCall().getData(Common.GetListOfGames,controller.getPrefManager().getUserToken(), this);
            }
        }
        appVersion.setText("Version : "+Util.getAppVersion(Profile.this));
    }
    public void addInterestedGames(UserProfile profile) {
        interestedGameList.clear();
        tempInterestedGame.clear();
        interestedGame.removeAllViews();
        for (int i = 0; i < profile.getInterestedGame().size(); i++) {
            int gameIcon = getGameIcon(profile.getInterestedGame().get(i).getGameName());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140, 140);
            layoutParams.setMargins(5, 5, 5, 5);
            ImageButton icon = new ImageButton(this);
            icon.setLayoutParams(layoutParams);
            icon.setId(profile.getInterestedGame().get(i).getGameId());
            icon.setImageResource(gameIcon);
            icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            icon.setPadding(10, 10, 10, 10);
            icon.setBackgroundResource(R.drawable.selector);
            interestedGame.addView(icon);
            interestedGameList.add(profile.getInterestedGame().get(i).getGameId());
            tempInterestedGame.add(profile.getInterestedGame().get(i).getGameId());
        }
    }

    public void initializeAll() {
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        editGender.setTypeface(controller.getDetailsFont());
        editEmail.setOnClickListener(this);
        editGender.setOnClickListener(this);
        editMobile.setOnClickListener(this);
        editProfilePic.setOnClickListener(this);
        editGameBtn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == male.getId()) {
                    genderid = 1;
                } else {
                    genderid = 2;
                }
            }
        });
    }

    public void setValues() {
        UserProfile profile = controller.getProfile();
        if ((profile.getProfilePic() != null) && (profile.getProfilePic().length() > 2)) {
            Picasso.with(Profile.this).load(profile.getProfilePic()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into(profilePic);
        } else {
            profilePic.setImageResource(R.drawable.user_icon);
        }
        name.setText(profile.getUserName());
        emailId.setText(profile.getEmail());
        mobile.setText(profile.getMobile());
        male.setTypeface(controller.getDetailsFont());
        female.setTypeface(controller.getDetailsFont());
        emailId.setTypeface(controller.getDetailsFont());
        mobile.setTypeface(controller.getDetailsFont());
        emailId.setEnabled(false);
        mobile.setEnabled(false);
        emailId.setFocusable(false);
        mobile.setFocusable(false);
        back.setOnClickListener(this);
        if (profile.getInterestedGame().size() > 0) {

            noInterest.setVisibility(View.GONE);

            addInterestedGames(profile);
        } else {
            noInterest.setVisibility(View.VISIBLE);
        }
//        switch (profile.getGender()) {
//            case 1:
//                radioGroup.check(male.getId());
//                break;
//            case 2:
//                radioGroup.check(female.getId());
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.editProfilePic:
                Util.selectImageDialog(Profile.this);
                break;
            case R.id.saveGender:
                if (Util.isNetworkAvailable(Profile.this)) {
                    apiCall = updateGender;
                    dialog = Util.showPogress(Profile.this);
                    controller.getApiCall().getData(Common.getUpdateGenderUrl(controller.getProfile().getUserId(), genderid),controller.getPrefManager().getUserToken(), this);
                }
                break;
            case R.id.editEmail:
                showUpdateDialog(1);
                break;
            case R.id.editMobile:
                showUpdateDialog(2);
                break;
            case R.id.editGamePrefs:
                showGamePrefsAlert();
                break;
        }
    }

    public int getGameIcon(String gameName) {
        int gameIcon = 0;
        switch (gameName) {
            case Common.chess:
                gameIcon = R.drawable.chess;
                break;
            case Common.pool:
                gameIcon = R.drawable.snooker;
                break;
            case Common.minibasketball:
                gameIcon = R.drawable.mini_basketball;
                break;
            case Common.airhockey:
                gameIcon = R.drawable.air_hockey;
                break;
            case Common.fussball:
                gameIcon = R.drawable.football;
                break;
            case Common.tt:
                gameIcon = R.drawable.tt;
                break;
            case Common.carrom:
                gameIcon = R.drawable.carroms;
                break;
            case Common.golf:
                gameIcon = R.drawable.glolf;
                break;
        }
        return gameIcon;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permissionReadCamera: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    Toast.makeText(this, "Please provide read permission for using camera.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this)
                .setTitle("Update Profile Pic");

        final FrameLayout frameView = new FrameLayout(Profile.this);
        builder.setView(frameView);

        alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.uploadprofilepic, frameView);
        ImageView image = (ImageView) dialoglayout.findViewById(R.id.pic);
        Button upload = (Button) dialoglayout.findViewById(R.id.upload);
        upload.setTypeface(controller.getDetailsFont());
        // Uri uri = Uri.fromFile();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
        image.setImageBitmap(myBitmap);
//        Picasso.with(Profile.this).load(new File(Common.tempPath))
//                .resize(300, 300).centerCrop().into(image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageCaptured == true) {
                    dialog = Util.showPogress(Profile.this);
                    apiCall = updateProfilePic;
                    controller.getApiCall().postData(Common.getUpdateProfilePic_Url, getUpdateProfilePicJSON().toString(),controller.getPrefManager().getUserToken(), Profile.this);

                }
            }
        });
        alertDialog.show();
    }


    public void showUpdateDialog(final int type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        final FrameLayout frameView = new FrameLayout(Profile.this);
        builder.setView(frameView);

        updateMobile_Email = builder.create();
        LayoutInflater inflater = updateMobile_Email.getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.update_email_alert, frameView);
        TextView heading = (TextView) dialoglayout.findViewById(R.id.heading);
        ImageView icon = (ImageView) dialoglayout.findViewById(R.id.icon);
        final EditText mobile_email = (EditText) dialoglayout.findViewById(R.id.email_mobile);
        Button submit = (Button) dialoglayout.findViewById(R.id.submit);
        mobile_email.setTypeface(controller.getDetailsFont());
        submit.setTypeface(controller.getDetailsFont());
        // Uri uri = Uri.fromFile();
        if (type == 1) {
            heading.setText("Update Email Id");
            icon.setImageResource(R.drawable.email);
            mobile_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            mobile_email.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
            mobile_email.setHint("Enter email id");
        } else {
            heading.setText("Update Mobile Number");
            icon.setImageResource(R.drawable.mobile);
            mobile_email.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
            mobile_email.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            mobile_email.setHint("Enter mobile number");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile_email.getText().length() > 0) {
                    switch (type) {
                        case 1:
                            if (controller.getValidation().isEmailIdValid(mobile_email)) {
                                apiCall = updateEmail;
                                dialog = Util.showPogress(Profile.this);
                                controller.getApiCall().getData(Common.getUpdateEmailUrl(controller.getProfile().getUserId(), mobile_email.getText().toString()),controller.getPrefManager().getUserToken(), Profile.this);
                            }
                            break;
                        case 2:
                            if (controller.getValidation().isPhoneNumberValid(mobile_email)) {
                                apiCall = updateMobile;
                                tempMobileNumber = mobile_email.getText().toString();
                                dialog = Util.showPogress(Profile.this);
                                controller.getApiCall().getData(Common.getUpdateMobileUrl(controller.getProfile().getUserId(), mobile_email.getText().toString()),controller.getPrefManager().getUserToken(), Profile.this);
                            }
                            break;
                    }
                } else {
                    String message = "";
                    switch (type) {
                        case 1:
                            message = "Please enter email id in above field";
                            break;
                        case 2:
                            message = "Please enter mobile number in above field";
                            break;
                    }
                    Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateMobile_Email.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Common.tempPath = Common.imageUri.getPath();
                isImageCaptured = true;
                showDialog();
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                Common.tempPath = c.getString(columnIndex);
                c.close();
                isImageCaptured = true;
                showDialog();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, options));

            } else {
                Toast.makeText(this, " This Image cannot be stored .please try with some other Image. ", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onSucess(String value) {
        if (dialog != null) {
            dialog.cancel();
            Util.showToast(Profile.this, Util.getMessage(value));
        }
        switch (apiCall) {
            case updateGender:
                if (Util.getStatus(value) == true) {
                   // controller.updateGender(genderid);
                }
                break;
            case updateProfilePic:
                if (Util.getStatus(value) == true) {

//                   // final String url = Util.getProfilePicUrl(Profile.this, value);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Picasso.with(Profile.this).load(url).resize(200, 200)
//                                    .centerInside().placeholder(R.drawable.user_icon).into(profilePic);
//
//                        }
//                    });
                    if (alertDialog != null) {
                        Util.cancelDialog(Profile.this, alertDialog);
                    }
                }
                break;
            case updateMobile:
                if (Util.getStatus(value) == true) {
                    if (updateMobile_Email != null) {
                        Util.cancelDialog(Profile.this, updateMobile_Email);
                        showOTPAlert();
                    }
                }
                break;
            case updateEmail:
                if (Util.getStatus(value) == true) {
                    if (updateMobile_Email != null) {
                        Util.cancelDialog(Profile.this, updateMobile_Email);

                    }
                }
                break;
            case verifyOtp:
                if (Util.getStatus(value) == true) {
                    if (otpAlertDialog != null) {
                        Util.cancelDialog(Profile.this, otpAlertDialog);

                    }
                    controller.updateMobileNumber(tempMobileNumber);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mobile.setText(tempMobileNumber);
                        }
                    });
                }
                break;
            case getGameList:
                if (Util.getStatus(value) == true) {
                    jsonParsing(value);
                }

                break;
            case updatePreffedGame:
                if (Util.getStatus(value) == true) {
                    if (gamePrefs != null) {
                        gamePrefs.cancel();
                    }
                    controller.updateGamePreferences(getList());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addInterestedGames(controller.getProfile());
                        }
                    });

                }

                break;

        }


    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();

        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Profile.this);
        }
        Util.showToast(Profile.this, Util.getMessage(value));
    }

    public JSONObject getUpdateProfilePicJSON() {
        JSONObject jsonObject = new JSONObject();
        File f = new File(Common.tempPath);
        try {
            jsonObject.put("RegistrationId", controller.getProfile().getUserId());
            jsonObject.put("Name", f.getName());
            jsonObject.put("ImageChars", getImageBase64(Common.tempPath));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

//    public static byte[] getImageByteArray(File inputFile) {
//        try {
//            FileInputStream input = new FileInputStream(inputFile);
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
//            byte[] buffer = new byte[65536];
//            int l;
//
//            while ((l = input.read(buffer)) > 0)
//                output.write(buffer, 0, l);
//
//            input.close();
//            output.close();
//
//            return output.toByteArray();
//
//        } catch (IOException e) {
//            System.err.println(e);
//            return null;
//        }
//    }

//    public String getImageBase64(String imageUrl) {
//
//        File imageFile = new File(imageUrl);
//        byte[] byteArray = getImageByteArray(imageFile);
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//        return encodedImage;
//    }
    public String getImageBase64(String imageUrl) {

       // File imageFile = new File(imageUrl);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
//        byte[] byteArray = getImageByteArray(imageFile);
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }
    public void showOTPAlert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                final FrameLayout frameView = new FrameLayout(Profile.this);
                builder.setView(frameView);

                otpAlertDialog = builder.create();
                LayoutInflater inflater = otpAlertDialog.getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.validate_otp_inside, frameView);
                Button submit = (Button) dialoglayout.findViewById(R.id.submit);
                Button cancel = (Button) dialoglayout.findViewById(R.id.cancel);
                TextView resendOtp = (TextView) dialoglayout.findViewById(R.id.resendOTP);
                final EditText otp = (EditText) dialoglayout.findViewById(R.id.otp);
                otp.setTypeface(controller.getDetailsFont());
                cancel.setTypeface(controller.getDetailsFont());
                submit.setTypeface(controller.getDetailsFont());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (otp.getText().length() == 6) {
                            if (Util.isNetworkAvailable(Profile.this)) {
                                apiCall = verifyOtp;
                                dialog = Util.showPogress(Profile.this);
                                controller.getApiCall().getData(Common.getValidateMobileUrl(controller.getProfile().getUserId(), tempMobileNumber, otp.getText().toString()),controller.getPrefManager().getUserToken(), Profile.this);
                            }
                        } else {
                            Toast.makeText(Profile.this, "Please enter otp.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                resendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resendOtp();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        otpAlertDialog.cancel();
                        otpAlertDialog = null;
                    }
                });

                otpAlertDialog.show();
            }
        });

    }

    public void showGamePrefsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        final FrameLayout frameView = new FrameLayout(Profile.this);
        builder.setView(frameView);
        gamePrefs = builder.create();
        LayoutInflater inflater = gamePrefs.getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.game_prefs_popup, frameView);
        final ImageButton chess = (ImageButton) dialoglayout.findViewById(R.id.chess);
        final ImageButton pool = (ImageButton) dialoglayout.findViewById(R.id.pool);
        final ImageButton basketball = (ImageButton) dialoglayout.findViewById(R.id.basketball);
        final ImageButton airhockey = (ImageButton) dialoglayout.findViewById(R.id.airhockey);
        final ImageButton foosball = (ImageButton) dialoglayout.findViewById(R.id.foosball);
        final ImageButton tt = (ImageButton) dialoglayout.findViewById(R.id.tt);
        final ImageButton carrom = (ImageButton) dialoglayout.findViewById(R.id.carrom);
        final ImageButton mini_golf = (ImageButton) dialoglayout.findViewById(R.id.mini_golf);
        if (interestedGameList.contains(Util.getGameId(Common.chess,list))) {
            isChessSelected = true;
            chess.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.pool,list))) {
            isPoolSelected = true;
            pool.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.minibasketball,list))) {
            isBasketBallSelected = true;
            basketball.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.airhockey,list))) {
            isAirHockeySelected = true;
            airhockey.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.fussball,list))) {
            isFoosBallSelected = true;
            foosball.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.tt,list))) {
            isTTSelected = true;
            tt.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.carrom,list))) {
            isCarromSlected = true;
            carrom.setBackgroundResource(R.drawable.selected_button);
        }
        if (interestedGameList.contains(Util.getGameId(Common.golf,list))) {
            isMiniGolfSelected = true;
            mini_golf.setBackgroundResource(R.drawable.selected_button);
        }
        Button submit = (Button) dialoglayout.findViewById(R.id.submit);
        submit.setTypeface(controller.getDetailsFont());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempInterestedGame.size() > 0) {
                    if (Util.isNetworkAvailable(Profile.this)) {
                        dialog = Util.showPogress(Profile.this);
                        apiCall = updatePreffedGame;
                        controller.getApiCall().postData(Common.getUpdateGame_Url(controller.getProfile().getUserId()), gamePreferenceJSON().toString(),controller.getPrefManager().getUserToken(), Profile.this);
                    }
                } else {
                    Toast.makeText(Profile.this, "Please select preffered game", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(chess, isChessSelected, 1, Common.chess);
            }
        });
        pool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(pool, isPoolSelected, 2, Common.pool);
            }
        });
        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(basketball, isBasketBallSelected, 3, Common.minibasketball);
            }
        });
        airhockey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(airhockey, isAirHockeySelected, 4, Common.airhockey);

            }
        });
        foosball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(foosball, isFoosBallSelected, 5, Common.fussball);

            }
        });
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(tt, isTTSelected, 6, Common.tt);

            }
        });
        carrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(carrom, isCarromSlected, 7, Common.carrom);
            }
        });
        mini_golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleButtonClick(mini_golf, isMiniGolfSelected, 8, Common.golf);

            }
        });
        gamePrefs.setCancelable(false);
        gamePrefs.show();
    }

    public void resendOtp() {
        apiCall = resendOtp;
        dialog = Util.showPogress(Profile.this);
        controller.getApiCall().getData(Common.getUpdateMobileUrl(controller.getProfile().getUserId(), tempMobileNumber),controller.getPrefManager().getUserToken(), this);

    }

    public JSONArray gamePreferenceJSON() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < tempInterestedGame.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("GameId", tempInterestedGame.get(i));
                jsonArray.put(i, jsonObject);
            }
        } catch (Exception ex) {

        }
        return jsonArray;
    }

    public void jsonParsing(String response) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject game = jsonArray.getJSONObject(i);
                GameModel model = new GameModel(game);
                list.add(model);
            }
        } catch (Exception ex) {

        }
        dialog.cancel();
    }


    public void handleButtonClick(ImageButton btn, boolean isSelected, int pos, String gameName) {
        if (isSelected == true) {
            btn.setBackgroundResource(R.drawable.whitecorner);
            tempInterestedGame.remove(tempInterestedGame.indexOf(Util.getGameId(gameName,list)));
        } else {
            btn.setBackgroundResource(R.drawable.selected_button);
            tempInterestedGame.add(Util.getGameId(gameName,list));
        }
        handleSelectionValue(pos);
    }

    public void handleSelectionValue(int pos) {
        switch (pos) {
            case 1:
                if (isChessSelected) {
                    isChessSelected = false;
                } else {
                    isChessSelected = true;
                }
                break;
            case 2:
                if (isPoolSelected) {
                    isPoolSelected = false;
                } else {
                    isPoolSelected = true;
                }
                break;
            case 3:
                if (isBasketBallSelected) {
                    isBasketBallSelected = false;
                } else {
                    isBasketBallSelected = true;
                }
                break;
            case 4:
                if (isAirHockeySelected) {
                    isAirHockeySelected = false;
                } else {
                    isAirHockeySelected = true;
                }
                break;
            case 5:
                if (isFoosBallSelected) {
                    isFoosBallSelected = false;
                } else {
                    isFoosBallSelected = true;
                }
                break;
            case 6:
                if (isTTSelected) {
                    isTTSelected = false;
                } else {
                    isTTSelected = true;
                }

                break;
            case 7:
                if (isCarromSlected) {
                    isCarromSlected = false;
                } else {
                    isCarromSlected = true;
                }
                break;
            case 8:
                if (isMiniGolfSelected) {
                    isMiniGolfSelected = false;
                } else {
                    isMiniGolfSelected = true;
                }
                break;
        }

    }

    public ArrayList<InterestedGameModel> getList() {
        ArrayList<InterestedGameModel> listt = new ArrayList<>();
        for (int i = 0; i < tempInterestedGame.size(); i++) {
            int id = tempInterestedGame.get(i);
            InterestedGameModel model = new InterestedGameModel(Util.getGameName(id,list), Integer.toString(id));
            listt.add(model);
        }
        return listt;
    }

}
