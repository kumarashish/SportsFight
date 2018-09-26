package sportsfight.com.s.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.mainmodule.Reminders;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.model.TransactionHistoryModel;

/**
 * Created by Ashish.Kumar on 20-02-2018.
 */

public class AlertsPagerAdapter  extends PagerAdapter {

    private Activity mContext;
    ArrayList<ReminderModel> challenges;
    ArrayList<ReminderModel>notifications;
    ChallengeAdapter c_adapter;
    Notific_ChallengesAdapter notific_challengesAdapter;
    ListView list1=null;
    ListView list2=null;
    TextView noItem1=null;
    TextView noItem2=null;
    public AlertsPagerAdapter(Activity context, ArrayList<ReminderModel> challenges,ArrayList<ReminderModel>notifications) {
        mContext = context;
        this.challenges = challenges;
        this.notifications=notifications;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (challenges.size() > 0) {
            c_adapter.notifyDataSetChanged();
            list1.setVisibility(View.VISIBLE);
            noItem1.setVisibility(View.GONE);
        } else {
            noItem1.setText("No Challenges");
            list1.setVisibility(View.GONE);
            noItem1.setVisibility(View.VISIBLE);
        }
        if (notifications.size() > 0) {
            notific_challengesAdapter.notifyDataSetChanged();
            list2.setVisibility(View.VISIBLE);
            noItem2.setVisibility(View.GONE);
        } else {
            noItem2.setText("No Notifications");
            list2.setVisibility(View.GONE);
            noItem2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.transaction_list, collection, false);


        switch (position) {
            case 0:
                list1 = (ListView) layout.findViewById(R.id.list);
                noItem1 = (TextView) layout.findViewById(R.id.noItem);
                if (challenges.size() > 0) {
                    list1.setVisibility(View.VISIBLE);
                    noItem1.setVisibility(View.GONE);
                    c_adapter = new ChallengeAdapter(mContext, challenges);
                    list1.setAdapter(c_adapter);
                } else {
                    noItem1.setText("No Challenges");
                    list1.setVisibility(View.GONE);
                    noItem1.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                list2 = (ListView) layout.findViewById(R.id.list);
                noItem2 = (TextView) layout.findViewById(R.id.noItem);
                if (notifications.size() > 0) {
                    list2.setVisibility(View.VISIBLE);
                    noItem2.setVisibility(View.GONE);
                    notific_challengesAdapter = new Notific_ChallengesAdapter(mContext, notifications);
                    list2.setAdapter(notific_challengesAdapter);
                } else {
                    noItem2.setText("No Notifications");
                    list2.setVisibility(View.GONE);
                    noItem2.setVisibility(View.VISIBLE);
                }
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
