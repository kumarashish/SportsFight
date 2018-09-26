package sportsfight.com.s.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.interfaces.PlaceBidCallBack;
import sportsfight.com.s.interfaces.ViewAllCallBack;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.NewsViewAll;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.mainmodule.UpcomingMatchesViewAll;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 08-02-2018.
 */

public class DashboardItemAdapter extends RecyclerView.Adapter  {
    ArrayList<MatchesModel> myMatches=new ArrayList<>();
    ArrayList<MatchesModel> upComingMatches=new ArrayList<>();
    ArrayList<MatchesModel> bids=new ArrayList<>();
    ArrayList<MatchesModel> tournaments=new ArrayList<>();
    ArrayList<Article> newsList=new ArrayList<>();
    ArrayList<Integer> viewCount=new ArrayList<>();
    ArrayList<MatchesModel> addedModel=new ArrayList<>();
    Activity act;
    AppController controller;
    ViewAllCallBack callBack;

    MatchesModel model;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }

    public DashboardItemAdapter(Activity act, ArrayList<MatchesModel> bids, ArrayList<MatchesModel> myMatches, ArrayList<MatchesModel>upComingMatches, ArrayList<MatchesModel> tournaments, ArrayList<Article> news) {
       this.myMatches=myMatches;
       this.bids=bids;
       this.newsList=news;
       this.upComingMatches=upComingMatches;
       this.tournaments=tournaments;
       this.act=act;
       controller=(AppController)act.getApplicationContext();
        callBack=(ViewAllCallBack)act;
        //callback=(PlaceBidCallBack)act;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if ((!viewCount.contains(0)) && (bids.size() > 0)) {
            MatchesModel model=bids.get(0);
            viewCount.add(0);
            addedModel.add(model);
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.congratulationcard, parent, false);
            return new Congratulation(itemView);
        } else if ((!viewCount.contains(1)) && (myMatches.size() > 0)) {
            MatchesModel model=myMatches.get(0);
            viewCount.add(1);
            addedModel.add(model);
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mymatches, parent, false);
            return new MyMatches(itemView);
        } else if ((!viewCount.contains(2)) && (upComingMatches.size() > 0)) {
           final PlaceBidCallBack callback=(PlaceBidCallBack) act;
            final MatchesModel model=upComingMatches.get(0);
            viewCount.add(2);
            addedModel.add(model);
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upcomingmatches, parent, false);
            return new UpComingMatches(itemView);
        } else if ((!viewCount.contains(3)) && (tournaments.size() > 0)) {
            viewCount.add(3);
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_tournament, parent, false);
            return new MyViewHolder(itemView);
        }
        else if ((!viewCount.contains(4)) && (newsList.size() > 0)) {
            viewCount.add(4);
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.newscard, parent, false);
            return new News(itemView);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (viewCount.get(position))
        {
            case 0:
                model=addedModel.get(position);
               Congratulation c_holder=(Congratulation)holder;
                c_holder.heading.setText("Results(" + bids.size() + ")");
                ImageView img = new ImageView(act);
                Picasso.with(act).load(Util.getCardBgInt(model.getGameName(), act)).into(   c_holder.cardview);
                c_holder.tticon.setImageDrawable(Util.getIcon(model.getGameName(), act));
                c_holder.info_text.setText(model.getGameName().toUpperCase());


                if (model.getPlayer1ImageUrl().length() > 0) {

                    Picasso.with(act).load(model.getPlayer1ImageUrl()).resize(200, 200)
                            .centerInside().placeholder(R.drawable.user_icon).into(   c_holder.circleImageView);
                } else {
                    c_holder.circleImageView.setImageResource(R.drawable.user_icon);
                }
                c_holder.player1Name.setText(model.getPlayer1Name());
                c_holder.player1Bid.setText("Bids: "+Integer.toString(model.getPlayer1Bids())+" pts");
                if (model.getPlayer2ImageUrl().length() > 0) {
                    Picasso.with(act).load(model.getPlayer2ImageUrl()).placeholder(R.drawable.user_icon).into(   c_holder.circleImageView2);
                } else {
                    c_holder.circleImageView2.setImageResource(R.drawable.user_icon);
                }
                c_holder.Player2Name.setText(model.getPlayer2Name());
                c_holder.Player2Bid.setText("Bids : "+Integer.toString(model.getPlayer2Bids()));
                c_holder.yourBid.setText("My Bid :"+Integer.toString(model.getMyBid()));
               c_holder.result.setText(model.getMessage());
                if (model.getWinnerId() == model.getPlayer1Id()) {
                    c_holder.player1Won.setVisibility(View.VISIBLE);
                    c_holder.player2Won.setVisibility(View.GONE);

                } else {
                    c_holder. player1Won.setVisibility(View.GONE);
                    c_holder.player2Won.setVisibility(View.VISIBLE);
                }
                if(model.isWon())
                {
                    c_holder.congratulation.setText("Congratulation");
                  if  (Dashboard.isCongratulationShown==false) {
//                      Dashboard.isCongratulationShown=true;
//                      c_holder.congratulationView.build()
//                              .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
//                              .setDirection(0.0, 500.0)
//                              .setSpeed(1f, 5f)
//                              .setFadeOutEnabled(true)
//                              .setTimeToLive(2000L)
//                              .addShapes(Shape.RECT, Shape.CIRCLE)
//                              .addSizes(new Size(12, 5))
//                              .setPosition(-50f, c_holder.congratulationView.getWidth() + 50f, -50f, -50f)
//                              .stream(200, 5000L);
                  }
                }else{
                    c_holder.congratulation.setText("Sorry you loose");
                }
                c_holder.info_text.setTextColor(Util.getTextColor(model.getGameName(), act));
                c_holder.viewAllResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.CongratulationViewALL();
                    }
                });
                break;
            case 1:
               model=addedModel.get(position);
                MyMatches holderr=(MyMatches)holder;
                holderr.heading.setText("My Matches(" + myMatches.size() + ")");
                int val=model.getPlayer1Bids()+model.getPlayer2Bids();
                holderr.bid_count.setText("");
                Picasso.with(act).load(Util.getCardBgInt(model.getGameName(), act)).into(holderr.cardview);
                holderr.tticon.setImageDrawable(Util.getIcon(model.getGameName(), act));
                holderr.info_text.setText(model.getGameName().toUpperCase());
                holderr.info_text.setTextColor(Util.getTextColor(model.getGameName(),act));
                if (model.getPlayer1ImageUrl().length() > 0) {
                    Picasso.with(act).load(model.getPlayer1ImageUrl()).resize(200, 200).centerInside().placeholder(R.drawable.user_icon).into(holderr.circleImageView);
                } else {
                    holderr.circleImageView.setImageResource(R.drawable.user_icon);
                }

                holderr.player1Name.setText(model.getPlayer1Name());
                holderr.player1Bid.setText("");
                if (model.getPlayer2ImageUrl().length() > 0) {
                    Picasso.with(act).load(model.getPlayer2ImageUrl()).resize(200, 200)
                            .centerInside().placeholder(R.drawable.user_icon).into(holderr.circleImageView2);
                } else {
                    holderr. circleImageView2.setImageResource(R.drawable.user_icon);
                }
                holderr. Player2Name.setText(model.getPlayer2Name());
                holderr.Player2Bid.setText("");

                holderr. viewAllResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.MyMatchesViewALL();
                    }
                });
                holderr.date.setText(Util.getMulticolorTextView("Date : "+Util.getDateinMMDDYY(model.getMatchDate()),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getMatchDate().length()+7}));
                holderr.time.setText(Util.getMulticolorTextView("Time : "+model.getSlotTime(),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getSlotTime().length()+7}));

                break;
            case 2:
                 model=addedModel.get(position);
                UpComingMatches holderrr=(UpComingMatches)holder;
                int vall=model.getPlayer1Bids()+model.getPlayer2Bids();

                holderrr.bid_count.setText("");
                holderrr.date.setText(Util.getMulticolorTextView("Date : "+Util.getDateinMMDDYY(model.getMatchDate()),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getMatchDate().length()+7}));
                holderrr.time.setText(Util.getMulticolorTextView("Time : "+model.getSlotTime(),new Integer[]{act.getResources().getColor(R.color.black_font),act.getResources().getColor(R.color.light_grey)},new Integer[]{0,5,7,model.getSlotTime().length()+7}));
                holderrr.heading.setText("Upcoming Matches(" + upComingMatches.size() + ")");
                Picasso.with(act).load(Util.getCardBgInt(model.getGameName(), act)).into( holderrr.cardview);
                holderrr.tticon.setImageDrawable(Util.getIcon(model.getGameName(), act));
                holderrr.info_text.setText(model.getGameName().toUpperCase());
                holderrr.info_text.setTextColor(Util.getTextColor(model.getGameName(),act));

                if (model.getPlayer1ImageUrl().length() > 0) {
                    Picasso.with(act).load(model.getPlayer1ImageUrl()).resize(200, 200)
                            .centerInside().placeholder(R.drawable.user_icon).into( holderrr.circleImageView);
                } else {
                    holderrr.circleImageView.setImageResource(R.drawable.user_icon);
                }
                holderrr.player1Name.setText(model.getPlayer1Name());
                holderrr.player1Bid.setText("");
                if (model.getPlayer2ImageUrl().length() > 0) {
                    Picasso.with(act).load(model.getPlayer2ImageUrl()).resize(200, 200)
                            .centerInside().placeholder(R.drawable.user_icon).into( holderrr.circleImageView2);
                } else {
                    holderrr.circleImageView2.setImageResource(R.drawable.user_icon);
                }
                holderrr.Player2Name.setText(model.getPlayer2Name());
                holderrr.Player2Bid.setText("");
                holderrr.myBid.setText("");
                holderrr.placeBid.setTypeface(controller.getDetailsFont());
                holderrr.placeBid.setVisibility(View.GONE);
                holderrr.viewAllResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.UpComingViewALL();
                    }
                });
                holderrr.placeBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                    }
                });
                break;
               case 4:
                   Article model=newsList.get(0);
                   try {
                       News hold = (News) holder;
                       hold.newsCount.setText("News(" + newsList.size() + ")");
                       String[] desc = model.getDescription().split(" />");
                       String image = desc[0].replace("<img src='", "");
                       image=image.replaceAll("' width='90' height='62'", "");

                       Picasso.with(act).load(image.trim()).resize(200, 200)
                               .centerInside().placeholder(R.drawable.user_icon).into(hold.image);

                       hold.viewAllResult.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               callBack.NewsViewALL();
                           }
                       });
                       hold.heading.setText(model.getTitle());
                       desc[1]=desc[1].replaceAll("&quot;","");
                       hold.details.setText(desc[1]);
                   }catch (Exception ex)
                   {
                       ex.fillInStackTrace();
                   }
//                   STring imagePath=
//                 image;

                break;

        }

    }

    @Override
    public int getItemViewType(int position) {
            return position;
    }
    @Override
    public int getItemCount() {
        return getCount();
    }
    public int getCount() {
        int size = 0;
        if ((bids.size() > 0)) {
            size = size + 1;
        }
        if ((myMatches.size() > 0)) {
            size = size + 1;
        }
        if ((upComingMatches.size() > 0)) {
            size = size + 1;
        }
        if ((tournaments.size() > 0)) {
            size = size + 1;
        }
        if ((newsList.size() > 0)) {
            size = size + 1;
        }
        return size;
    }

    public class Tournaments {

    }

    public class News extends RecyclerView.ViewHolder {
        TextView newsCount;
        TextView viewAllResult;
        TextView heading;
        ImageView image;
        TextView details;

        public News(View itemView) {
            super(itemView);
            newsCount = (TextView) itemView.findViewById(R.id.newsCount);
            heading = (TextView) itemView.findViewById(R.id.newsHeading);
            image = (ImageView) itemView.findViewById(R.id.image);
            details = (TextView) itemView.findViewById(R.id.details);
            viewAllResult = (TextView) itemView.findViewById(R.id.viewAllNews);


        }
    }




    public static class UpComingMatches extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        TextView heading;
        TextView viewAllResult;
        TextView  bid_count;
        ImageView tticon;
        TextView info_text;
        ImageView circleImageView;
        TextView player1Name;
        TextView player1Bid;
        ImageView circleImageView2;
        TextView Player2Name;
        TextView Player2Bid;
        TextView myBid;
        TextView bidCount;
        sportsfight.com.s.common.CustomLayout cardview;
        Button placeBid;

        public  UpComingMatches(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            bid_count = (TextView) itemView.findViewById(R.id.bid_count);
            heading = (TextView) itemView.findViewById(R.id.heading);
            viewAllResult = (TextView) itemView.findViewById(R.id.viewAllResult);
            cardview = (sportsfight.com.s.common.CustomLayout) itemView.findViewById(R.id.cardview);
            tticon = (ImageView) itemView.findViewById(R.id.tticon);
            info_text = (TextView) itemView.findViewById(R.id.info_text);
            circleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
            player1Name = (TextView) itemView.findViewById(R.id.player1Name);
            player1Bid = (TextView) itemView.findViewById(R.id.player1Bid);
            circleImageView2 = (ImageView) itemView.findViewById(R.id.circleImageView2);
            Player2Name = (TextView) itemView.findViewById(R.id.player2Name);
            Player2Bid = (TextView) itemView.findViewById(R.id.player2Bid);
            myBid = (TextView) itemView.findViewById(R.id.myBid);
            bidCount = (TextView) itemView.findViewById(R.id.bid_count);
            placeBid = (Button) itemView.findViewById(R.id.placeBid);

        }
    }

    public static class MyMatches extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        TextView heading;
        TextView viewAllResult;
        sportsfight.com.s.common.CustomLayout  cardview;
        ImageView tticon;
        TextView info_text;
        ImageView circleImageView;
        TextView player1Name;
        TextView player1Bid;
        ImageView circleImageView2;
        TextView Player2Name;
        TextView Player2Bid;
        TextView bid_count;
        public MyMatches(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            heading = (TextView) itemView.findViewById(R.id.heading);
            viewAllResult = (TextView) itemView.findViewById(R.id.viewAllResult);
            cardview = (sportsfight.com.s.common.CustomLayout) itemView.findViewById(R.id.cardview);
            tticon = (ImageView) itemView.findViewById(R.id.tticon);
            info_text = (TextView) itemView.findViewById(R.id.info_text);
            circleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
            player1Name = (TextView) itemView.findViewById(R.id.Player1Name);
            player1Bid = (TextView) itemView.findViewById(R.id.Player1Bid);
            circleImageView2 = (ImageView) itemView.findViewById(R.id.circleImageView2);
            Player2Name = (TextView) itemView.findViewById(R.id.Player2Name);
            Player2Bid = (TextView) itemView.findViewById(R.id.Player2Bid);
            bid_count = (TextView) itemView.findViewById(R.id.bid_count);

        }
    }

    public static class Congratulation extends RecyclerView.ViewHolder {
        TextView heading;
        TextView viewAllResult;
        sportsfight.com.s.common.CustomLayout cardview;
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
        public Congratulation(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            viewAllResult = (TextView) itemView.findViewById(R.id.viewAllResult);
            cardview = (sportsfight.com.s.common.CustomLayout) itemView.findViewById(R.id.cardview);
            tticon = (ImageView) itemView.findViewById(R.id.tticon);
            info_text = (TextView) itemView.findViewById(R.id.info_text);
            circleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
            player1Won = (ImageView) itemView.findViewById(R.id.player1Won);
            player1Name = (TextView) itemView.findViewById(R.id.Player1Name);
            player1Bid = (TextView) itemView.findViewById(R.id.Player1Bid);
            result = (TextView) itemView.findViewById(R.id.result);
            yourBid = (TextView) itemView.findViewById(R.id.yourBid);
            congratulation = (TextView) itemView.findViewById(R.id.congratulation);
            circleImageView2 = (ImageView) itemView.findViewById(R.id.circleImageView2);
            player2Won = (ImageView) itemView.findViewById(R.id.player2Won);
            Player2Name = (TextView) itemView.findViewById(R.id.Player2Name);
            Player2Bid = (TextView) itemView.findViewById(R.id.Player2Bid);
            congratulationView=(nl.dionsegijn.konfetti.KonfettiView)itemView.findViewById(R.id.viewKonfetti);
        }
    }

}
