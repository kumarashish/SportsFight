package sportsfight.com.s.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 27-02-2018.
 */

public class WonViewAllAdapter extends BaseAdapter {
    ArrayList<MatchesModel> list;
    Activity act;
    AppController controller;
    public WonViewAllAdapter(ArrayList<MatchesModel> list, Activity act) {
        this.list = list;
        this.act = act;
        controller = (AppController) act.getApplicationContext();
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
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        MatchesModel model = list.get(i);
      ViewHolder holder = null;
        if (itemView == null) {
            holder = new ViewHolder();
            itemView = LayoutInflater.from(act).inflate(R.layout.congratulationcard, null, true);
            holder.heading = (TextView) itemView.findViewById(R.id.heading);
            holder.viewAllResult = (TextView) itemView.findViewById(R.id.viewAllResult);
            holder.cardview = (LinearLayout) itemView.findViewById(R.id.cardview);
            holder.tticon = (ImageView) itemView.findViewById(R.id.tticon);
            holder.info_text = (TextView) itemView.findViewById(R.id.info_text);
            holder.circleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
            holder.player1Won = (ImageView) itemView.findViewById(R.id.player1Won);
            holder.player1Name = (TextView) itemView.findViewById(R.id.Player1Name);
            holder.player1Bid = (TextView) itemView.findViewById(R.id.Player1Bid);
            holder.result = (TextView) itemView.findViewById(R.id.result);
            holder.yourBid = (TextView) itemView.findViewById(R.id.yourBid);
            holder.congratulation = (TextView) itemView.findViewById(R.id.congratulation);
            holder.circleImageView2 = (ImageView) itemView.findViewById(R.id.circleImageView2);
            holder.player2Won = (ImageView) itemView.findViewById(R.id.player2Won);
            holder. Player2Name = (TextView) itemView.findViewById(R.id.Player2Name);
            holder.Player2Bid = (TextView) itemView.findViewById(R.id.Player2Bid);
            holder.congratulationView= (nl.dionsegijn.konfetti.KonfettiView)itemView.findViewById(R.id.viewKonfetti);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }
        int val = model.getPlayer1Bids() + model.getPlayer2Bids();

//        holder.bid_count.setText("Current no. of Bids : " + val + " pts");
//        holder.date.setText(Util.getMulticolorTextView("Date : " + Util.getDateinMMDDYY(model.getMatchDate()), new Integer[]{act.getResources().getColor(R.color.black_font), act.getResources().getColor(R.color.light_grey)}, new Integer[]{0, 5, 7, model.getMatchDate().length() + 7}));
//        holder.time.setText(Util.getMulticolorTextView("Time : " + model.getSlotTime(), new Integer[]{act.getResources().getColor(R.color.black_font), act.getResources().getColor(R.color.light_grey)}, new Integer[]{0, 5, 7, model.getSlotTime().length() + 7}));
        holder.heading.setText("");
        holder.cardview.setBackground(Util.getCardBg(model.getGameName(), act));
        holder.tticon.setImageDrawable(Util.getIcon(model.getGameName(), act));
        holder.info_text.setText(model.getGameName().toUpperCase());
        holder.info_text.setTextColor(Util.getTextColor(model.getGameName(), act));

        if (model.getPlayer1ImageUrl().length() > 0) {
            Picasso.with(act).load(model.getPlayer1ImageUrl()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into(holder.circleImageView);
        } else {
            holder.circleImageView.setImageResource(R.drawable.user_icon);
        }
        holder.result.setText(model.getMessage());
        holder.yourBid.setText("My Bid :"+Integer.toString(model.getMyBid()));
        holder.player1Name.setText(model.getPlayer1Name());
        holder.player1Bid.setText("Bids: " + Integer.toString(model.getPlayer1Bids()) + " pts");
        if (model.getPlayer2ImageUrl().length() > 0) {
            Picasso.with(act).load(model.getPlayer2ImageUrl()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into(holder.circleImageView2);
        } else {
            holder.circleImageView2.setImageResource(R.drawable.user_icon);
        }
        holder.Player2Name.setText(model.getPlayer2Name());
        holder.Player2Bid.setText("Bids: " + Integer.toString(model.getPlayer2Bids()) + " pts");
        holder.viewAllResult.setText("");
        if (model.getWinnerId() == model.getPlayer1Id()) {
            holder.player1Won.setVisibility(View.VISIBLE);
            holder.player2Won.setVisibility(View.GONE);

        } else {
            holder. player1Won.setVisibility(View.GONE);
            holder.player2Won.setVisibility(View.VISIBLE);
        }
        if(model.isWon())
        {
            holder.congratulation.setText("Congratulation");
        }else{
            holder.congratulation.setText("Sorry you loose");
        }

        itemView.setTag(holder);
        return itemView;
    }

    public class ViewHolder {
        TextView heading;
        TextView viewAllResult;
        LinearLayout cardview;
        ImageView tticon;
        TextView info_text;
        ImageView circleImageView;
        ImageView player1Won;
        TextView player1Name;
        TextView player1Bid;
        TextView result;
        TextView yourBid;
        TextView congratulation;
        ImageView circleImageView2;
        ImageView player2Won;
        TextView Player2Name;
        TextView Player2Bid;
        nl.dionsegijn.konfetti.KonfettiView congratulationView;
    }
}