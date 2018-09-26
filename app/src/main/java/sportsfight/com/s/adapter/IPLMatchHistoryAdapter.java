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
import sportsfight.com.s.model.IPLResultModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 12-04-2018.
 */

public class IPLMatchHistoryAdapter extends BaseAdapter {
    ArrayList<IPLResultModel> matchList;
    Activity act;
    LayoutInflater inflater;

    public IPLMatchHistoryAdapter(ArrayList<IPLResultModel> matchList, Activity act) {
        this.matchList = matchList;
        this.act = act;
        inflater = act.getLayoutInflater();
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
        IPLResultModel model=matchList.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.ipl_history_row, null, true);


        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.date=(TextView) view.findViewById(R.id.date);
        holder.result=(TextView) view.findViewById(R.id.result);
        holder.losser=(sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.losser);
        holder.winner=(sportsfight.com.s.common.CircleImageView) view.findViewById(R.id.winner);
        holder.date.setText(Util.getDateinMMDDYY(model.getMatchDate())+" at "+model.getSlotTime());
        holder.result.setText(model.getResultText());
        Picasso.with(act).load(model.getWinnerImage()).resize(200, 200)
                .centerInside().placeholder(R.drawable.user_icon).into(holder.winner);
        Picasso.with(act).load(model.getLooserImage()).resize(200, 200)
                .centerInside().placeholder(R.drawable.user_icon).into( holder.losser);
        view.setTag(holder);
        return view;
    }

    public class ViewHolder {
        TextView result, date;
        sportsfight.com.s.common.CircleImageView winner;
        sportsfight.com.s.common.CircleImageView losser;

    }
}
