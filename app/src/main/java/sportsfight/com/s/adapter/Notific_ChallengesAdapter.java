package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.model.TransactionHistoryModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 20-02-2018.
 */

public class Notific_ChallengesAdapter  extends BaseAdapter {
    ArrayList<ReminderModel> items;
    Activity act;
    LayoutInflater inflater;

    public Notific_ChallengesAdapter(Activity act, ArrayList<ReminderModel> items) {
        this.act = act;
        this.items = items;
        inflater = act.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ReminderModel model=items.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.notificationrow, null, true);
            holder.gameName = (TextView) view.findViewById(R.id.gameName);
            holder.message = (TextView) view.findViewById(R.id.message);
            holder.status = (TextView) view.findViewById(R.id.status);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.icon.setImageDrawable(Util.getIcon(model.getGameName(), act));
        holder.gameName.setText(model.getGameName().toUpperCase());
        holder.message.setText(model.getMessage() );
        holder.date.setText(model.getMatchDatetime());
        if(model.getStatus().equalsIgnoreCase("Declined"))
        {
            holder.status.setTextColor(act.getResources().getColor(R.color.red));
        }else{
            holder.status.setTextColor(act.getResources().getColor(R.color.greencolor));
        }
        holder.status.setText(model.getStatus());
        view.setTag(holder);


        return view;
    }

    public class ViewHolder {
        ImageView icon;
        TextView date;
        TextView gameName;
        TextView status;
        TextView message;

    }


}