package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
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
     UserProfile profile=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_profile);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        if(Util.isNetworkAvailable(NewProfile.this))
        {   dialog=Util.showPogress(NewProfile.this);
            controller.getApiCall().getData(Common.getGetProfile_UrlNew(Integer.toString(controller.getProfile().getUserId())),controller.getPrefManager().getUserToken(),this);
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
        }

    }

    public void updateProfilePopup()
    {
        final Dialog dialog = new Dialog(this);
        LottieAnimationView animationView;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_profile_popup);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button save = (Button) dialog.findViewById(R.id.save);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.cancel();
            }
        });
        dialog.show();
    }

    public void setInterestedGames() {


        LinearLayout horizontalView=(LinearLayout)findViewById(R.id.horizontalView);

        for (int i = 1; i < profile.getInterestedGame().size(); i++) {
            InterestedGameModel model=profile.getInterestedGame().get(i);
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
            gameName.setText(model.getGameName());
            levelValue.setText(model.getGameLevel());
            rankValue.setText(Integer.toString(model.getRank()));
            horizontalView.addView(inflatedLayout);
            View blankview = getLayoutInflater().inflate(R.layout.emptyview, null, false);
            horizontalView.addView(blankview);
        }
    }

    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value)) {
            profile = new UserProfile(Util.getResultJson(value));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateProfile();
                }
            });
        }
        if(dialog!=null)
        {
            dialog.cancel();
        }
    }
public void updateProfile()
{
    Picasso.with(NewProfile.this).load(profile.getProfilePic()).resize(80, 80).placeholder(R.drawable.user_icon).into(profilePic);
    name.setText(profile.getUserName());
    gender.setText(profile.getGender());
    emailId.setText(profile.getEmail());
    winning_percentage.setText(Integer.toString(profile.getWinPercentage()) + "%");
    club.setText(profile.getClubName());
    totalGames.setText(Integer.toString(profile.getTotalGames()));
    locationName.setText(profile.getLocationName());
    mobileNumber.setText(profile.getMobile());
    setInterestedGames();
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

}
