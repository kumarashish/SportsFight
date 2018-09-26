package sportsfight.com.s.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.interfaces.GameSlotCallback;
import sportsfight.com.s.model.Slots;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 16-02-2018.
 */

public class GridViewAdapter extends BaseAdapter {

int clickedPosition=-1;
    Activity act;
    ArrayList<Slots.Slotdetails> list = new ArrayList<>();
    private static LayoutInflater inflater = null;
    GameSlotCallback callback;
    String gameDate;

    public GridViewAdapter(Activity act, ArrayList<Slots.Slotdetails> list,String gameDate) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.act = act;
        callback=(GameSlotCallback)act;
        this.gameDate=gameDate;

        inflater = (LayoutInflater) act.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        Button slot;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.available_slots, null);
        holder.slot = (Button) rowView.findViewById(R.id.availableSlot);
        holder.slot.setText(list.get(position).getSlotTime());
        if(list.get(position).getStatus().equalsIgnoreCase("Available"))
        {
            holder.slot.setBackgroundResource(R.drawable.avialble_box);
            holder.slot.setTextColor(act.getResources().getColor(R.color.greencolor));
        }else if(list.get(position).getStatus().equalsIgnoreCase("Pending"))
        {
            holder.slot.setBackgroundResource(R.drawable.pendingbox);
            holder.slot.setTextColor(act.getResources().getColor(R.color.yellow));
        }
            if(list.get(position).isClicked())
            {
                holder.slot.setBackgroundDrawable(act.getResources().getDrawable(R.drawable.clicked_slot));
            }
        holder.slot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (list.get(position).getStatus().equalsIgnoreCase("Available")) {
                    if (clickedPosition != -1) {
                        list.get(clickedPosition).setClicked(false);
                    }
                    clickedPosition = position;
                    list.get(position).setClicked(true);
                    notifyDataSetChanged();
                    callback.onGameSlotSelected(gameDate,list.get(position).getId(),list.get(position).getSlotTime());
                } else {
                    Util.showToast(act, "You can canot select this slot as currently this slot in " + list.get(position).getStatus() + " state.");
                }

            }
        });

        return rowView;
    }
}