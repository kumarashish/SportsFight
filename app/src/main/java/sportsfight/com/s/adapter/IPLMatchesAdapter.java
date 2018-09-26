package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.juspay.godel.ui.ACSOptionsFragment;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.interfaces.PlaceBidCallBack;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 12-04-2018.
 */

public class IPLMatchesAdapter extends BaseAdapter {
    ArrayList<MatchesModel> matchList;
    Activity act;
    LayoutInflater inflater;
    AppController controller;
    PlaceBidCallBack callBack;
    public IPLMatchesAdapter(ArrayList<MatchesModel> matchList, Activity act) {
        this.matchList = matchList;
        this.act = act;
        inflater = act.getLayoutInflater();
        controller=(AppController)act.getApplicationContext();
        callBack=(PlaceBidCallBack)act;
    }

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int i) {
        return matchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        final MatchesModel model = matchList.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.ipl_matches_row, null, true);
            holder.placeBid = (Button) view.findViewById(R.id.placeBid);
            holder.team1Icon = (sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.team1Icon);
            holder.team2Icon = (sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.team2Icon);
            holder.match_date = (TextView) view.findViewById(R.id.match_date);
            holder.venue = (TextView) view.findViewById(R.id.venue);
            holder.totalBid = (TextView) view.findViewById(R.id.totalBid);
            holder.player1Bid=(TextView) view.findViewById(R.id.player1Bid);
            holder.player2Bid=(TextView) view.findViewById(R.id.player2Bid);
            holder.myBid= (TextView) view.findViewById(R.id.myBid);
            holder.team1= (TextView) view.findViewById(R.id.team1);
            holder.team2= (TextView) view.findViewById(R.id.team2);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.placeBid.setTypeface(controller.getDetailsFont());
        holder.team1Icon = (sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.team1Icon);
        holder.team2Icon = (sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.team2Icon);
        Picasso.with(act).load(model.getPlayer1ImageUrl().replace("http://www.dmss.co.in/sportsfight/docs/images/profile/","")).resize(200, 200)
                .centerInside().placeholder(R.drawable.user_icon).into(holder.team1Icon);
        Picasso.with(act).load(model.getPlayer2ImageUrl().replace("http://www.dmss.co.in/sportsfight/docs/images/profile/","")).resize(200, 200)
                .centerInside().placeholder(R.drawable.user_icon).into(holder.team2Icon);
        holder.match_date.setText(Util. getDateinMMDDYY(model.getMatchDate()) +System.getProperty("line.separator")+" "+model.getSlotTime());
        holder.venue = (TextView) view.findViewById(R.id.venue);
        holder.venue.setText(model.getMatchPlace());
        holder.player1Bid.setText("Bid : "+model.getPlayer1Bids()+" pts.");
        holder.player2Bid.setText("Bid : "+model.getPlayer2Bids()+" pts.");
        holder.totalBid.setText("Total Bid : "+model.getTotalBids()+" pts.");
        holder.team1.setText(model.getPlayer1Name());
        holder.team2.setText(model.getPlayer2Name());
        if (model.getMyBid() > 0) {
            if (model.getMyBidToId() == model.getPlayer1Id()) {
                holder.myBid.setText("My Bid on " + model.getPlayer1Name() + " : " + model.getMyBid() + " pts.");
            } else {
                holder.myBid.setText("My Bid on " + model.getPlayer2Name() + " : " + model.getMyBid() + " pts.");
            }
        } else {
            holder.myBid.setText("My Bid : 0 pts.");
        }
        view.setTag(holder);
        holder.placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onPlaceBidClick(model);
            }
        });
        return view;
    }

    public class ViewHolder {
        sportsfight.com.s.common.CircleImageView team1Icon;
        sportsfight.com.s.common.CircleImageView team2Icon;
        TextView match_date, totalBid, venue,myBid,player1Bid,player2Bid,team1,team2;
        Button placeBid;
    }
}
