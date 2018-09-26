package sportsfight.com.s.adapter;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.mainmodule.Reminders;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 20-02-2018.
 */

public class ReminderAdapter extends BaseAdapter {
    Activity reminders; ArrayList<ReminderModel> list;
    LayoutInflater inflater;
    public ReminderAdapter(Activity reminders, ArrayList<ReminderModel> list) {
        this.reminders=reminders;
        this.list=list;
        inflater =reminders.getLayoutInflater();
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
        ReminderModel model=list.get(i);
        ViewHolder holder;
        if(view==null)
        {holder=new ViewHolder();
            view=inflater.inflate(R.layout.reminder_row, null, true);
            holder.icon=(ImageView) view.findViewById(R.id.imageIcon);
            holder.background=(LinearLayout) view.findViewById(R.id.cardBg);
            holder.game=(TextView)view.findViewById(R.id.gameName);
            holder.opponent=(TextView)view.findViewById(R.id.playerName);
            holder.date=(TextView)view.findViewById(R.id.date);
            holder.slot=(TextView)view.findViewById(R.id.time);

        }else{
            holder=(ViewHolder)view.getTag();
        }
        view.setTag(holder);
        holder.game.setText(model.getGameName().toUpperCase());
        holder.icon.setImageDrawable(Util.getIcon(model.getGameName(),reminders));
        holder.background.setBackground(Util.getCardBg(model.getGameName(),reminders));
        holder.date.setText(Util.getDateinMMDDYY(model.getMatchDatetime()));
        holder.slot.setText(model.getSlotTime());
        holder.opponent.setText(" Vs "+model.getOpponentName());
        return view;
    }
    public class ViewHolder{
        ImageView icon;
        LinearLayout background;
        TextView opponent,game;
        TextView date;
        TextView slot;

    }
}
