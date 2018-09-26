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
import sportsfight.com.s.interfaces.ApproveResultCallBack;
import sportsfight.com.s.model.ApproveResultModel;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.results.ApproveResult;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 08-03-2018.
 */

public class ApproveResultAdapter extends BaseAdapter {
    Activity act;
    ArrayList<ApproveResultModel> list;
    ApproveResultCallBack callBack;
    LayoutInflater inflater;
    AppController controller;

    public ApproveResultAdapter(  Activity act, ArrayList<ApproveResultModel> list)
    {
        this.act=act;
        this.list=list;
        callBack=(ApproveResultCallBack)act;
        controller=(AppController)act.getApplicationContext();
        inflater= act.getLayoutInflater();
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
        final ApproveResultModel model = list.get(i);
       ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.approve_result_row, null, true);
            holder.player1Name = (TextView) view.findViewById(R.id.player1Name);
            holder.player2Name = (TextView) view.findViewById(R.id.player2Name);
            holder.winner_info = (TextView) view.findViewById(R.id.winner_info);
            holder.approve = (Button) view.findViewById(R.id.approveResult);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.player1Name.setText(model.getWinnerName());
        holder.player2Name.setText(model.getLooserName());
        holder.winner_info.setText(model.getResultText());
        holder.approve.setTypeface(controller.getDetailsFont());

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnApproveResultClick(model);
            }

        });
        view.setTag(holder);
        return view;
    }

    public class ViewHolder {
        TextView player1Name,player2Name,winner_info;
        Button approve;

    }
}
