package sportsfight.com.s.mainmodule;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.common.LocationSearch;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.model.InterestedGameModel;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 19-09-2018.
 */

public class NewProfile extends Activity implements View.OnClickListener,WebApiResponseCallback {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.edit_profile)
    Button edit_profile;
    AppController controller;
    Dialog dialog;
    @BindView(R.id.circleImageView4)
    ImageView profilePic ;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.emailId)
    TextView emailId;
    @BindView(R.id.winning_percentage)
    TextView winning_percentage;
    @BindView(R.id.club)
    TextView club;
    @BindView(R.id.totalGames)
    TextView  totalGames;
    @BindView(R.id.locationName)
    TextView  locationName;
    @BindView(R.id. mobileNumber)
    TextView  mobileNumber;
    @BindView(R.id.changelocation)
    TextView changeLocation;
    @BindView(R.id.addgames)
    View addgames;
    UserProfile profile=null;
    int apiCall=-1;
    int getProfileCall=1,updateProfile=2,updateprofilePic=3,updateLocation=4,enableDisableGame=5;
    Dialog dialogg=null;
    String tempName;
    int tempgenderId;
boolean isProfileUpdated=false;
boolean isGameEdited=false;
    final public static int SELECT_FILE = 02;
    public final int permissionReadCamera = 1;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    AlertDialog alertDialog = null;
    boolean isImageCaptured = false;
    int gameId;
    int gameLevelId;
    boolean isDeleted = false;
    boolean GameIsActive = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_profile);
        ButterKnife.bind(this);
        isGameEdited=false;
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        addgames.setOnClickListener(this);
        changeLocation.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = ContextCompat.checkSelfPermission(NewProfile.this,
                    Manifest.permission.CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewProfile.this,
                        new String[]{Manifest.permission.CAMERA},
                        permissionReadCamera);
            }
        }
        if(Util.isNetworkAvailable(NewProfile.this))
        {   dialog=Util.showPogress(NewProfile.this);
            apiCall=getProfileCall;
            controller.getApiCall().getData(Common.getGetProfile_UrlNew(Integer.toString(controller.getProfile().getUserId())),controller.getPrefManager().getUserToken(),this);
        }
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
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.edit_profile:
                updateProfilePopup();
                break;
            case R.id.changelocation:
                startActivityForResult(new Intent(NewProfile.this, LocationSearch.class),3);
                break;
            case R.id.addgames:
                Util.showToast(NewProfile.this,"Will be available in sprint 2");
                break;
        }

    }

    public void updateProfilePopup()
    {
         dialogg = new Dialog(this);
        LottieAnimationView animationView;
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.update_profile_popup);
        final Window window = dialogg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView profile_pic=(ImageView) dialogg.findViewById(R.id.profile_pic);
        final EditText name=(EditText)dialogg.findViewById(R.id.name);
        EditText email=(EditText)dialogg.findViewById(R.id.email);
        EditText mobile=(EditText)dialogg.findViewById(R.id.mobile);
        final Spinner gender=(Spinner)dialogg.findViewById(R.id.gender);
        ImageView drop_down=(ImageView)dialogg.findViewById(R.id.drop_down);
        Button update_profilepic=(Button) dialogg.findViewById(R.id.update_profilepic);
        Button cancel = (Button)dialogg.findViewById(R.id.cancel);
        Button save = (Button) dialogg.findViewById(R.id.save);
        if (controller.getProfile().getProfilePic().length() > 0) {
            Picasso.with(NewProfile.this).load(controller.getProfile().getProfilePic()).resize(60, 60).placeholder(R.drawable.user_icon).into(profile_pic);
        } else {
            Picasso.with(NewProfile.this).load(Common.noImageUrl).placeholder(R.drawable.user_icon).resize(60, 60).into(profile_pic);
        }
        name.setText(controller.getProfile().getUserName());
        email.setText(controller.getProfile().getEmail());
        mobile.setText(controller.getProfile().getMobile());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.cancel();
            }
        });
        if (controller.getProfile().getGender().equalsIgnoreCase("Male")) {
            gender.setSelection(0);
        } else {
            gender.setSelection(1);
        }
        update_profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.cancel();
                Util.selectImageDialog(NewProfile.this);
            }
        });
        drop_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.performClick();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()>0)
                {
                    if(Util.isNetworkAvailable(NewProfile.this))
                    {   apiCall=updateProfile;
                        int genderId=gender.getSelectedItemPosition()+1;
                        dialog=Util.showPogress(NewProfile.this);
                        tempName=name.getText().toString();
                        tempgenderId=genderId;
                        controller.getApiCall().postData(Common.updateProfileInfo,getUpdateProfileJSON(name.getText().toString(),genderId).toString(),controller.getPrefManager().getUserToken(),NewProfile.this);
                        isProfileUpdated=true;
                    }
                }else{
                    Util.showToast(NewProfile.this,"Please enter name");
                }

            }
        });
        dialogg.show();
    }
    public void showDialog(Bitmap Bitmap ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewProfile.this)
                .setTitle("Update Profile Pic");

        final FrameLayout frameView = new FrameLayout(NewProfile.this);
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
      //  Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
        image.setImageBitmap(Bitmap );
//        Picasso.with(Profile.this).load(new File(Common.tempPath))
//                .resize(300, 300).centerCrop().into(image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageCaptured == true) {
                    dialog = Util.showPogress(NewProfile.this);
                    apiCall = updateprofilePic;
                    isProfileUpdated=true;
                    controller.getApiCall().postData(Common.getUpdateProfilePic_Url, getUpdateProfilePicJSON().toString(),controller.getPrefManager().getUserToken(), NewProfile.this);

                }
            }
        });
        alertDialog.show();
    }

    public static Bitmap modifyOrientation(String image_absolute_path) {
        Bitmap bitmap = BitmapFactory.decodeFile(image_absolute_path);
        try {

            ExifInterface ei = new ExifInterface(image_absolute_path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotate(bitmap, 90);

                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotate(bitmap, 180);

                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotate(bitmap, 270);

                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    return flip(bitmap, true, false);

                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    return flip(bitmap, false, true);

                default:
                    return bitmap;
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return bitmap;
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Bitmap bmp= modifyOrientation(Common.imageUri.getPath());
                Common.tempPath = Common.imageUri.getPath();

                isImageCaptured = true;
                showDialog(bmp);
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
               Bitmap bmp= modifyOrientation( Common.tempPath );
                isImageCaptured = true;
                showDialog(bmp);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                // Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath, options));

            } else {
                Toast.makeText(this, " This Image cannot be stored .please try with some other Image. ", Toast.LENGTH_SHORT).show();
            }

        }else if((requestCode==3)&&(resultCode==RESULT_OK))
        {
            String address=  controller.getAddress();
            if(address.length()>0)
            {   apiCall=updateLocation;
                dialog=Util.showPogress(NewProfile.this);
                isProfileUpdated=true;
                controller.getApiCall().getData(Common.getupdateLocation_Url(Integer.toString(controller.getProfile().getUserId()),controller.getAddress(),controller.getCurrentLocation().latitude,controller.getCurrentLocation().longitude),controller.getPrefManager().getUserToken(),NewProfile.this);
            }
        }
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
    public String getImageBase64(String imageUrl) {

        // File imageFile = new File(imageUrl);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(Common.tempPath,options);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        System.out.print(temp);
//        byte[] byteArray = getImageByteArray(imageFile);
//        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return temp;
    }
    public JSONObject getUpdateProfileJSON(String name, int genderId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("RegistrationId", controller.getProfile().getUserId());
            jsonObject.put("Name", name);
            jsonObject.put("Email", controller.getProfile().getEmail());
            jsonObject.put("Mobile", controller.getProfile().getMobile());
            jsonObject.put("GenderId", genderId);
            jsonObject.put("CompanyId", "");

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }


    public boolean isMinimumDValidated() {
        int count = 0;
        for (int i = 0; i < profile.getInterestedGame().size(); i++) {
            if (profile.getInterestedGame().get(i).isGameIsActive()) {
                count++;
            }
        }
        if (count > 1) {
            return true;
        } else {
            return false;
        }
    }
    public void setInterestedGames() {
        LinearLayout horizontalView=(LinearLayout)findViewById(R.id.horizontalView);
        horizontalView.removeAllViews();
        for (int i = 0; i < profile.getInterestedGame().size(); i++) {
           final InterestedGameModel model=profile.getInterestedGame().get(i);
            View inflatedLayout = getLayoutInflater().inflate(R.layout.game_card, horizontalView, false);
            LinearLayout rightSidebox = (LinearLayout) inflatedLayout.findViewById(R.id.rightSidebox);
            LinearLayout enabledView = (LinearLayout)  inflatedLayout.findViewById(R.id.enabledView);
            LinearLayout disabledView = (LinearLayout) inflatedLayout. findViewById(R.id.disabledView);
            ImageView gameIcon=(ImageView) inflatedLayout. findViewById(R.id.gameIcon);
            TextView gameName= (TextView) inflatedLayout. findViewById(R.id.gameTitle);
            TextView levelTitle= (TextView) inflatedLayout. findViewById(R.id.levelTitle);
            TextView rankTitle= (TextView) inflatedLayout. findViewById(R.id.rankTitle);
            TextView levelValue= (TextView) inflatedLayout. findViewById(R.id.levelValue);
            TextView rankValue= (TextView) inflatedLayout. findViewById(R.id.rankValue);
            View enableButton=(View)inflatedLayout. findViewById(R.id.enableButton);
            View deleteButton=(View)inflatedLayout. findViewById(R.id.deleteButton);
            View disableButton=(View)inflatedLayout. findViewById(R.id.disableButton);
            if (model.isGameIsActive()==false) {
                Picasso.with(NewProfile.this).load(model.getDisabledIcon()).resize(40,40).placeholder(R.drawable.juspay_ic_reload).into(gameIcon);
                rightSidebox.setBackground(getResources().getDrawable(R.drawable.right_side_disabled_card));
                enabledView.setVisibility(View.GONE);
                disabledView.setVisibility(View.VISIBLE);
                gameName.setTextColor(getResources().getColor(R.color.disabled_color));
                levelTitle.setTextColor(getResources().getColor(R.color.disabled_color));
                rankTitle.setTextColor(getResources().getColor(R.color.disabled_color));
            } else {
                Picasso.with(NewProfile.this).load(model.getEnabledIcon()).resize(40,40).placeholder(R.drawable.juspay_ic_reload).into(gameIcon);
                rightSidebox.setBackground(getResources().getDrawable(R.drawable.right_side_card_enabled));
                enabledView.setVisibility(View.VISIBLE);
                disabledView.setVisibility(View.GONE);
                gameName.setTextColor(getResources().getColor(R.color.black_font));
                levelTitle.setTextColor(getResources().getColor(R.color.black_font));
                rankTitle.setTextColor(getResources().getColor(R.color.black_font));
            }
            enableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Util.isNetworkAvailable(NewProfile.this)) {
                        dialog = Util.showPogress(NewProfile.this);
                        apiCall = enableDisableGame;
                        controller.getApiCall().postData(Common.getUpdateGamePreferencesUrl(Integer.toString(controller.getProfile().getUserId())), getUpdateGamePrefs(model.getGameId(), 2, false, true).toString(), controller.getPrefManager().getUserToken(), NewProfile.this);
                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (profile.getInterestedGame().size() > 1) {
                        if (Util.isNetworkAvailable(NewProfile.this)) {
                            dialog = Util.showPogress(NewProfile.this);
                            apiCall = enableDisableGame;
                            controller.getApiCall().postData(Common.getUpdateGamePreferencesUrl(Integer.toString(controller.getProfile().getUserId())), getUpdateGamePrefs(model.getGameId(), 2, true, false).toString(), controller.getPrefManager().getUserToken(), NewProfile.this);

                        }
                    } else {
                        Util.showToast(NewProfile.this,"You can not delete all games, Atleast one game should be present in preferences.");
                    }
                }
            });
            disableButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isMinimumDValidated()) {
                        if (Util.isNetworkAvailable(NewProfile.this)) {

                            dialog = Util.showPogress(NewProfile.this);
                            apiCall = enableDisableGame;

                            controller.getApiCall().postData(Common.getUpdateGamePreferencesUrl(Integer.toString(controller.getProfile().getUserId())), getUpdateGamePrefs(model.getGameId(), 2, false, false).toString(), controller.getPrefManager().getUserToken(), NewProfile.this);
                        }
                    }else {
                        Util.showToast(NewProfile.this,"Minimum 1 game should be enabled.");
                    }
                }
            });
            gameName.setText(model.getGameName());
            levelValue.setText(model.getGameLevel());
            rankValue.setText(Integer.toString(model.getRank()));
            horizontalView.addView(inflatedLayout);
            View blankview = getLayoutInflater().inflate(R.layout.emptyview, null, false);
            horizontalView.addView(blankview);
        }
    }

    public JSONObject getUpdateGamePrefs(int gameId, int gameLevelId, boolean isDeleted, boolean isActive) {
       this.gameId=gameId;
        this.gameLevelId=gameLevelId;
        this.isDeleted=isDeleted;
        this.GameIsActive =isActive;
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("GameId", gameId);
            jsonObject.put("GameLevelIdgameId", gameLevelId);
            jsonObject.put("IsDeleted", isDeleted);
            jsonObject.put("GameIsActive", isActive);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value)) {
            if(apiCall==getProfileCall) {
                profile = new UserProfile(Util.getResultJson(value));
                controller.setUserProfile(Util.getResultJson(value));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfile(0);
                    }
                });
            } else if (apiCall == updateProfile) {
                profile.updateUserProfileName_gender(tempName, tempgenderId);
                controller.setUserProfile(profile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfile(1);
                    }
                });
                if(dialogg!=null)
                {
                    dialogg.cancel();

                }
            }else if(apiCall==updateprofilePic)
            {
                String imageUrl=Util.getProfilePicUrl(value);
                profile.setProfilePic(imageUrl);
                controller.setUserProfile(profile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfile(2);
                    }
                });
                if(alertDialog!=null)
                {
                    alertDialog.cancel();
                }
            } else if (apiCall == updateLocation) {
                profile.setLocationName(controller.getAddress());
                controller.setUserProfile( profile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfile(3);
                    }
                });
                if (dialog != null) {
                    dialog.cancel();
                }
            }else if(apiCall==enableDisableGame)
            {
                isGameEdited=true;
                isProfileUpdated=true;
            profile.updateGamePreferences(gameId,gameLevelId,isDeleted,GameIsActive);
                controller.setUserProfile( profile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateProfile(4);
                    }
                });
                if (dialog != null) {
                    dialog.cancel();
                }
            }

            //Util.showToast(NewProfile.this,Util.getMessage(value));
        }else {
            Util.showToast(NewProfile.this,Util.getMessage(value));
            if(dialog!=null)
            {
                dialog.cancel();
            }
        }


    }

    public void updateProfile(int id) {

        name.setText(profile.getUserName());
        gender.setText(profile.getGender());
        emailId.setText(profile.getEmail());
        mobileNumber.setText(profile.getMobile());
        switch (id) {
            case 0:
                if(controller.getProfile().getProfilePic().length()>0) {
                    Picasso.with(NewProfile.this).load(profile.getProfilePic()).placeholder(R.drawable.user_icon).resize(80, 80).into(profilePic);
                }else{
                    Picasso.with(NewProfile.this).load(Common.noImageUrl).placeholder(R.drawable.user_icon).resize(80, 80).into(profilePic);
                }
                winning_percentage.setText(Integer.toString(profile.getWinPercentage()) + "%");
                club.setText(profile.getClubName());
                totalGames.setText(Integer.toString(profile.getTotalGames()));
                if(profile.getLocationName().length()!=0) {
                    locationName.setText(profile.getLocationName());
                }else {
                    Util.showToast(NewProfile.this,"Please update your playing location");
                    startActivityForResult(new Intent(NewProfile.this, LocationSearch.class),3);
                }
                setInterestedGames();
                break;
            case 2:

                if(controller.getProfile().getProfilePic().length()>0) {
                    Picasso.with(NewProfile.this).load(profile.getProfilePic()).placeholder(R.drawable.user_icon).resize(80, 80).into(profilePic);
                }else{
                    Picasso.with(NewProfile.this).load(Common.noImageUrl).placeholder(R.drawable.user_icon).resize(80, 80).into(profilePic);
                }
                break;
            case 3:
                locationName.setText(controller.getProfile().getLocationName());
                break;
            case 4:
                setInterestedGames();
        }
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }
    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
           dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(NewProfile.this);
        }
        Util. showToast(NewProfile.this,Util.getMessage(value));
    }

    @Override
    public void onBackPressed() {
        if (isProfileUpdated) {
            Intent _result = new Intent();
            _result.putExtra("isGameEdited",isGameEdited);
            setResult(Activity.RESULT_OK, _result);
        }
            finish();
    }
}
