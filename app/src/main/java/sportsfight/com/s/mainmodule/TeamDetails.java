package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.challenge.Challenge1;
import sportsfight.com.s.model.AvailablePlayersModel;
import sportsfight.com.s.model.AvailableTeamModel;
import sportsfight.com.s.model.TeamPlayers;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 19-09-2018.
 */

public class TeamDetails  extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.challenge_btn)
    Button challenge_btn;
    @BindView(R.id.chat_btn)
    Button chat_btn;
    @BindView(R.id.playerList)
    LinearLayout team_playerlist;
    @BindView(R.id.totalGamesplayed)
    TextView totalGamesplayed;
    @BindView(R.id.winning_percent)
    TextView winning_percent;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.playing_location)
    TextView playing_location;
    @BindView(R.id.playerList_llt)
    LinearLayout playerList_llt;
    @BindView(R.id.profile_pic)
    ImageView profile_pic;
    public static AvailableTeamModel teamModel=null;
    public static AvailablePlayersModel model=null;
    boolean isTeamCalled;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_team);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        challenge_btn.setOnClickListener(this);
        chat_btn.setOnClickListener(this);
        isTeamCalled=getIntent().getBooleanExtra("isTeamCalled",false);
        updateUi();
    }

    public void updateUi() {
        if(isTeamCalled) {
            totalGamesplayed.setText("N/A");
            winning_percent.setText(teamModel.getWinningPercentage() + "%");
            name.setText(teamModel.getTeamName());
            playing_location.setText(teamModel.getPlayerLocation().trim()+"( "+teamModel.getDistance()+" km )");
            playerList_llt.setVisibility(View.VISIBLE);
            addTeamPlayers();
            Picasso.with(TeamDetails.this).load(teamModel.getTeamImage()).resize(80, 80).placeholder(R.drawable.user_icon).into(profile_pic);
        }else {
            totalGamesplayed.setText("N/A");
            winning_percent.setText(model.getWinningPercentage() + "%");
            name.setText(model.getPlayerName());
            playing_location.setText(model.getPlayerLocation());
            playerList_llt.setVisibility(View.GONE);
            Picasso.with(TeamDetails.this).load(model.getPlayerImage()).resize(80, 80).placeholder(R.drawable.user_icon).into(profile_pic);
        }
    }


    public void addTeamPlayers()
    {
        for(int i=0;i<teamModel.getPlayerList().size();i++)
        {TeamPlayers model=teamModel.getPlayerList().get(i);
            View playerRow = getLayoutInflater().inflate(R.layout.team_row, null, false);
            ImageView profilePic=(ImageView)playerRow.findViewById(R.id.profilePic);
            TextView name=(TextView)playerRow.findViewById(R.id.name);
            TextView location=(TextView)playerRow.findViewById(R.id.location);
            TextView captain=(TextView)playerRow.findViewById(R.id.captain);
            name.setText(model.getName());
            location.setText(model.getLocation());
            if(model.isCaptain()==false)
            {
                captain.setText("");
            }
            Picasso.with(TeamDetails.this).load(model.getPlayerImage()).resize(80,80).placeholder(R.drawable.user_icon).into(profilePic);
            team_playerlist.addView(playerRow);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id. challenge_btn:
                startActivity(new Intent(TeamDetails.this, Challenge1.class));
                break;
            case R.id. chat_btn:
                Util.showToast(TeamDetails.this,"Comming in future sprint");
                break;
        }

    }
}