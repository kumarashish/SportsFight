package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.interfaces.DeclareResultCallBack;
import sportsfight.com.s.model.DeclareResultModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 26-03-2018.
 */

public class ApproveMatchAdapter  extends BaseAdapter {
    ArrayList<DeclareResultModel> list;
    Activity activity;
    LayoutInflater inflater;
    DeclareResultCallBack callback;
    AppController controller;
    ViewHolder holder = null;
    Boolean isApproveMatch;

    public ApproveMatchAdapter(ArrayList<DeclareResultModel> list, Activity act, Boolean isApproveMatch) {
        this.list = list;
        this.activity = act;
        inflater = act.getLayoutInflater();
        callback = (DeclareResultCallBack) act;
        controller = (AppController) activity.getApplicationContext();
        this.isApproveMatch = isApproveMatch;
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
        final DeclareResultModel model = list.get(i);
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.approve_match_row, null, true);
            holder.player1Name = (TextView) view.findViewById(R.id.player1Name);
            holder.player2Name = (TextView) view.findViewById(R.id.player2Name);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.info_text = (sportsfight.com.s.common.BoldFont) view.findViewById(R.id.info_text);
            holder.cardView = (sportsfight.com.s.common.CustomLayout) view.findViewById(R.id.cardview);
            holder.gameIcon = (ImageView) view.findViewById(R.id.tticon);
            holder.approveMatch = (Button) view.findViewById(R.id.approveMatch);
            holder.declineMatch= (Button) view.findViewById(R.id.declineMatch);
            holder.player1Selected = (ImageView) view.findViewById(R.id.player1_selected);
            holder.player2Selected = (ImageView) view.findViewById(R.id.player2_selected);
            holder.player1 = (ImageView) view.findViewById(R.id.circleImageView);
            holder.player2 = (ImageView) view.findViewById(R.id.circleImageView2);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.gameIcon.setImageDrawable(Util.getIcon(model.getChallengeResponse().getGameName(), activity));
        holder.info_text.setText(model.getChallengeResponse().getGameName().toUpperCase());
        holder.player1Name.setText(model.getChallengeResponse().getChallengedByName());
        holder.player2Name.setText(model.getChallengeResponse().getChallengedToName());
        holder.date.setText("Match on :" + Util.getDateinMMDDYY(model.getMatchDate()));
        holder.cardView.setBackground(Util.getCardBg(model.getChallengeResponse().getGameName(), activity));
        holder.info_text.setTextColor(Util.getTextColor(model.getChallengeResponse().getGameName(), activity));
        holder.approveMatch.setTypeface(controller.getDetailsFont());
        holder.declineMatch.setTypeface(controller.getDetailsFont());
        holder.approveMatch.setText("Approve Match");
        holder.approveMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    model.setApproved(true);
                    callback.onDeclareResultClick(model);
            }
        });
        holder.declineMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setApproved(false);
                callback.onDeclareResultClick(model);
            }
        });
        view.setTag(holder);
        return view;

    }

    public class ViewHolder {
        TextView player1Name;
        TextView player2Name;
        sportsfight.com.s.common.BoldFont info_text;
        sportsfight.com.s.common.CustomLayout cardView;
        ImageView player1, player2;
        ImageView gameIcon;
        TextView date;
        Button approveMatch, declineMatch;
        ImageView player2Selected;
        ImageView player1Selected;
    }
}