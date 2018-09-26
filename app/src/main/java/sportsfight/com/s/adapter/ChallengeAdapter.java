package sportsfight.com.s.adapter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.interfaces.Accept_reject_callback;
import sportsfight.com.s.mainmodule.Reminders;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 20-02-2018.
 */

public class ChallengeAdapter extends BaseAdapter {
    ArrayList<ReminderModel> items;
    Activity act;
    LayoutInflater inflater;
Accept_reject_callback callback;
    public ChallengeAdapter(Activity act, ArrayList<ReminderModel> items) {
        this.act = act;
        this.items = items;
        inflater = act.getLayoutInflater();
        callback=(Accept_reject_callback)act;
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
        final ReminderModel model = items.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.challenges_row, null, true);
            holder.challengedby = (TextView) view.findViewById(R.id.challengedBy);
            holder.gamePoint = (TextView) view.findViewById(R.id.cahllengePoints);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.gameName = (TextView) view.findViewById(R.id.game_name);
            holder.accept = (View) view.findViewById(R.id.accept);
            holder.reject = (View) view.findViewById(R.id.reject);
            holder.gameIcon = (ImageView) view.findViewById(R.id.gameIcon);


        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.gameIcon.setImageDrawable(Util.getIcon(model.getGameName(), act));
        holder.gameName.setText(model.getGameName().toUpperCase());
        holder.challengedby.setText("Challenged by :" + model.getOpponentName());
        holder.date.setText("for match on :" + Util.getDateinMMDDYY(model.getMatchDatetime()) + " at " + model.getSlotTime());
        holder.gamePoint.setText("Challenged Points :"+model.getChallengedPoints());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onAcceptClick(model);

            }

        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onRejectClick(model);

            }
        });
        view.setTag(holder);

        return view;
    }

    public class ViewHolder {
        View accept;
        View reject;
        ImageView gameIcon;
        TextView gameName;
        TextView challengedby, gamePoint, date;

    }
}