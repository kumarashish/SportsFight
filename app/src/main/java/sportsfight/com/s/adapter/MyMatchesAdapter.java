package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 26-02-2018.
 */

public class MyMatchesAdapter  extends BaseAdapter{
    ArrayList<MatchesModel> list; Activity act;
    AppController controller;
    public MyMatchesAdapter(ArrayList<MatchesModel> list, Activity act)
    {
        this.list=list;
        this.act=act;
        controller=(AppController)act.getApplicationContext();
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
        MatchesModel model=list.get(i);
        ViewHolder holder=null;
        if(itemView==null) {
            holder = new ViewHolder();
            itemView = LayoutInflater.from(act)
                    .inflate(R.layout.mymatches, null, true);
            holder.header = (RelativeLayout) itemView.findViewById(R.id.header);
            holder.date = (TextView) itemView.findViewById(R.id.date);
            holder.time = (TextView) itemView.findViewById(R.id.time);
            holder.heading = (TextView) itemView.findViewById(R.id.heading);
            holder.viewAllResult = (TextView) itemView.findViewById(R.id.viewAllResult);
            holder.cardview = (LinearLayout) itemView.findViewById(R.id.cardview);
            holder.tticon = (ImageView) itemView.findViewById(R.id.tticon);
            holder.info_text = (TextView) itemView.findViewById(R.id.info_text);
            holder.circleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
            holder.player1Name = (TextView) itemView.findViewById(R.id.Player1Name);
            holder.player1Bid = (TextView) itemView.findViewById(R.id.Player1Bid);
            holder.circleImageView2 = (ImageView) itemView.findViewById(R.id.circleImageView2);
            holder.Player2Name = (TextView) itemView.findViewById(R.id.Player2Name);
            holder.Player2Bid = (TextView) itemView.findViewById(R.id.Player2Bid);
            holder.bid_count = (TextView) itemView.findViewById(R.id.bid_count);
        }else{
            holder=(ViewHolder)itemView.getTag();
        }
        holder.date.setText(Util.getMulticolorTextView("Date : "+Util.getDateinMMDDYY(model.getMatchDate()),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getMatchDate().length()+7}));
        holder.time.setText(Util.getMulticolorTextView("Time : "+model.getSlotTime(),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getSlotTime().length()+7}));

        holder.heading.setText("");
        int val=model.getPlayer1Bids()+model.getPlayer2Bids();
        holder. bid_count.setText("Current no. of Bids : "+val+" pts");
        holder.cardview.setBackground(Util.getCardBg(model.getGameName(), act));
        holder.tticon.setImageDrawable(Util.getIcon(model.getGameName(), act));
        holder.info_text.setText(model.getGameName().toUpperCase());
        holder.info_text.setTextColor(Util.getTextColor(model.getGameName(),act));
        if (model.getPlayer1ImageUrl().length() > 0) {

            Picasso.with(act).load(model.getPlayer1ImageUrl()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into( holder.circleImageView);
        } else {
            holder.circleImageView.setImageResource(R.drawable.user_icon);
        }
        holder.player1Name.setText(model.getPlayer1Name());
        holder.player1Bid.setText("Bids: "+Integer.toString(model.getPlayer1Bids())+" pts");
        if (model.getPlayer2ImageUrl().length() > 0) {
            Picasso.with(act).load(model.getPlayer2ImageUrl()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into( holder.circleImageView2);
        } else {
            holder.circleImageView2.setImageResource(R.drawable.user_icon);
        }
        holder.Player2Name.setText(model.getPlayer2Name());
        holder.Player2Bid.setText("Bids: "+Integer.toString(model.getPlayer2Bids())+" pts");
        holder.header.setVisibility(View.GONE);
        itemView.setTag(holder);
        return itemView;
    }
    public class  ViewHolder{
        RelativeLayout header;
        TextView date;
        TextView time;
        TextView heading;
        TextView viewAllResult;
        LinearLayout cardview;
        ImageView tticon;
        TextView info_text;
        ImageView circleImageView;
        TextView player1Name;
        TextView player1Bid;
        ImageView circleImageView2;
        TextView Player2Name;
        TextView Player2Bid;
        TextView  bid_count;
    }
}
