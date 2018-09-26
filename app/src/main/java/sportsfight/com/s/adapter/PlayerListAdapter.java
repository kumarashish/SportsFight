package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import sportsfight.com.s.R;
import sportsfight.com.s.interfaces.ListClickListner;
import sportsfight.com.s.mainmodule.Challenge;
import sportsfight.com.s.model.UserProfile;

/**
 * Created by Ashish.Kumar on 15-02-2018.
 */

public class PlayerListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<UserProfile> playerList;

    private ArrayList<UserProfile> arraylist;
    LayoutInflater inflater;
    int selectedPosition=-1;
    ListClickListner listClickListner;
    public PlayerListAdapter(Activity activity, ArrayList<UserProfile>  playerList) {
        this.activity = activity;
        this.playerList = playerList;
        inflater = activity.getLayoutInflater();
        listClickListner=(ListClickListner)activity;
        this.arraylist = new ArrayList<UserProfile>();
        this.arraylist.addAll(playerList);
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int i) {
        return playerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        UserProfile profile = playerList.get(i);
        ViewHolder holder = null;
        holder = new ViewHolder();
        view = inflater.inflate(R.layout.player_row, null, true);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.selectrow = (View) view.findViewById(R.id.selectRow);
        holder.profilePic = (ImageView) view.findViewById(R.id.profilePic);
        holder.selectedIcon = (ImageView) view.findViewById(R.id.selected_icon);
        holder.name.setText(profile.getUserName());
        if ((profile.getProfilePic() != null) && (profile.getProfilePic().length() > 0)) {
            Picasso.with(activity).load(profile.getProfilePic()).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into(holder.profilePic);
        }
        if (profile.isSelected() == true) {
            holder.selectedIcon.setVisibility(View.VISIBLE);
        } else {
            holder.selectedIcon.setVisibility(View.INVISIBLE);
        }
        holder.selectrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != -1) {
                    playerList.get(selectedPosition).setSelected(false);
                }
                selectedPosition = i;
                playerList.get(selectedPosition).setSelected(true);
                listClickListner.onClick(selectedPosition);
                notifyDataSetChanged();
            }
        });
        view.setTag(holder);
        return view;
    }
    public class ViewHolder{
        TextView name;
        View selectrow;
        ImageView profilePic;
        ImageView selectedIcon;

    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        playerList.clear();
        if (charText.length() == 0) {
            playerList.addAll(arraylist);
        } else {
            for (UserProfile wp : arraylist) {
                if (wp.getUserName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    playerList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
