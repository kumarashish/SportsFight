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

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 19-09-2018.
 */

public class NewProfile extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.edit_profile)
    Button edit_profile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_profile);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        edit_profile.setOnClickListener(this);
        setProfile();
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

    public void setProfile() {


        LinearLayout horizontalView=(LinearLayout)findViewById(R.id.horizontalView);

        for (int i = 1; i < 11; i++) {
            View inflatedLayout = getLayoutInflater().inflate(R.layout.game_card, horizontalView, false);
            LinearLayout rightSidebox = (LinearLayout) inflatedLayout.findViewById(R.id.rightSidebox);
            LinearLayout enabledView = (LinearLayout)  inflatedLayout.findViewById(R.id.enabledView);
            LinearLayout disabledView = (LinearLayout) inflatedLayout. findViewById(R.id.disabledView);
            TextView gameName= (TextView) inflatedLayout. findViewById(R.id.gameTitle);
            TextView levelTitle= (TextView) inflatedLayout. findViewById(R.id.levelTitle);
            TextView rankTitle= (TextView) inflatedLayout. findViewById(R.id.rankTitle);
            if (i % 2 != 0) {
                rightSidebox.setBackground(getResources().getDrawable(R.drawable.right_side_disabled_card));
                enabledView.setVisibility(View.GONE);
                disabledView.setVisibility(View.VISIBLE);
                gameName.setTextColor(getResources().getColor(R.color.disabled_color));
               levelTitle.setTextColor(getResources().getColor(R.color.disabled_color));
                rankTitle.setTextColor(getResources().getColor(R.color.disabled_color));
            } else {
                rightSidebox.setBackground(getResources().getDrawable(R.drawable.right_side_card_enabled));
                enabledView.setVisibility(View.VISIBLE);
                disabledView.setVisibility(View.GONE);
                gameName.setTextColor(getResources().getColor(R.color.black_font));
                levelTitle.setTextColor(getResources().getColor(R.color.black_font));
                rankTitle.setTextColor(getResources().getColor(R.color.black_font));
            }
            horizontalView.addView(inflatedLayout);
            View blankview = getLayoutInflater().inflate(R.layout.emptyview, null, false);
            horizontalView.addView(blankview);
        }
    }
}
