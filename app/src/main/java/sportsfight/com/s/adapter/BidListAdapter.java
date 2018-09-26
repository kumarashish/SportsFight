package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.model.BidModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 23-04-2018.
 */

public class BidListAdapter extends BaseAdapter {
    Activity activity; ArrayList<BidModel> list;
    LayoutInflater inflater;
    public BidListAdapter(Activity activity, ArrayList<BidModel> list)
    {
        this.activity=activity;
        this.list=list;
        inflater = activity.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        BidModel model=list.get(i);
        TextView game_name;
        sportsfight.com.s.common.CircleImageView circleImageView;
        TextView date;
        TextView congrats;
        sportsfight.com.s.common.CircleImageView circleImageView2;
        TextView player1Name;
        TextView player2Name;
        TextView myBid;
        TextView result;

            view = inflater.inflate(R.layout.bid_row, null, true);
           game_name=(TextView)view.findViewById(R.id.game_name);
          circleImageView=(sportsfight.com.s.common.CircleImageView )view.findViewById(R.id.circleImageView);
           date=(TextView)view.findViewById(R.id.date);;
           congrats=(TextView)view.findViewById(R.id.congrats);;
          circleImageView2=(sportsfight.com.s.common.CircleImageView )view.findViewById(R.id.circleImageView2);
           player1Name=(TextView)view.findViewById(R.id.player1Name);;
         player2Name=(TextView)view.findViewById(R.id.player2Name);;
         myBid=(TextView)view.findViewById(R.id.myBid);;
           result=(TextView)view.findViewById(R.id.result);;

        if(model.getGameName().equalsIgnoreCase("CricketIPL"))
        {
          game_name.setText("Indian Premier League(IPL)");
        }else {
          game_name.setText(model.getGameName());
        }
       date.setText(Util.getDateinMMDDYY(model.getMatchDate()));
        if (model.getResultAmount() > 0) {
            congrats.setText("Congratulation");
          congrats.setTextColor(activity.getResources().getColor(R.color.greencolor));
            int resultvalue=model.getResultAmount()*10;
           result.setText("You won "+resultvalue+" pts.Your player "+model.getPlayer1Name()+"  won.");
        } else if(model.getResultAmount() ==-1){
            congrats.setText("Result Not Declared");
           congrats.setTextColor(activity.getResources().getColor(R.color.greencolor));
       result.setText("Waiting for admin to declare result..");
        }
        else {
        congrats.setText("Sorry! Better Luck Next Time");
          congrats.setTextColor(activity.getResources().getColor(R.color.red));
        result.setText("You lost "+model.getBidPoints()+"pts. "+model.getPlayer1Name()+" looses.");
        }
       player1Name.setText(model.getPlayer1Name());
        player2Name.setText(model.getPlayer2Name());

        myBid.setText("My Bid was on "+model.getPlayer1Name() +" for "+model.getBidPoints()+" pts.");
        if ((model.getPlayer2Image().length() > 1)) {
            if ((!model.getPlayer1Image().contains("http://www.dmss.co.in/sportsfight/docs/images/profile/")) && (!model.getPlayer1Image().contains("http"))) {
                Picasso.with(activity).load("http://www.dmss.co.in/sportsfight/docs/images/profile/" + model.getPlayer1Image()).resize(200, 200)
                        .centerInside().placeholder(R.drawable.user_icon).into(circleImageView);
            } else {
                Picasso.with(activity).load(model.getPlayer1Image()).resize(200, 200)
                        .centerInside().placeholder(R.drawable.user_icon).into(circleImageView);
            }
        }
        if ((model.getPlayer2Image().length() > 1)) {
            if ((!model.getPlayer2Image().contains("http://www.dmss.co.in/sportsfight/docs/images/profile/")) && (!model.getPlayer2Image().contains("http"))) {
                Picasso.with(activity).load("http://www.dmss.co.in/sportsfight/docs/images/profile/" + model.getPlayer2Image()).resize(200, 200)
                        .centerInside().placeholder(R.drawable.user_icon).into(circleImageView2);
            } else {
                Picasso.with(activity).load(model.getPlayer2Image()).resize(200, 200)
                        .centerInside().placeholder(R.drawable.user_icon).into(circleImageView2);
            }
        }

        return view;
    }


}
