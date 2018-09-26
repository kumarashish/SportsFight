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
import sportsfight.com.s.ipl.IplMatches;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.model.IPLResultModel;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.model.TransactionHistoryModel;

/**
 * Created by Ashish.Kumar on 19-04-2018.
 */

public class IPLPagerAdapter extends PagerAdapter {

    private Activity mContext;
    ArrayList<IPLResultModel> resultHistory;
    ArrayList<MatchesModel> IplMatchesList;
    IPLMatchesAdapter adapter;
    IPLMatchHistoryAdapter historyAdapter;
    public IPLPagerAdapter(Activity context, ArrayList<MatchesModel> IplMatchesList, ArrayList<IPLResultModel> resultHistory) {
        mContext = context;
        this.resultHistory = resultHistory;
        this.IplMatchesList = IplMatchesList;

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.transaction_list, collection, false);
        ListView list = (ListView) layout.findViewById(R.id.list);
        TextView noItem = (TextView) layout.findViewById(R.id.noItem);
        switch (position) {
            case 0:
                if (IplMatchesList.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    adapter=new IPLMatchesAdapter(IplMatchesList, mContext);
                    list.setAdapter(adapter);
                } else {
                    list.setVisibility(View.GONE);
                    noItem.setVisibility(View.VISIBLE);

                }
                break;
            case 1:
                if (resultHistory.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    historyAdapter=new IPLMatchHistoryAdapter(resultHistory, mContext);
                    list.setAdapter(historyAdapter);
                } else {
                    list.setVisibility(View.GONE);
                    noItem.setVisibility(View.VISIBLE);

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
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        if(  historyAdapter!=null) {
            historyAdapter.notifyDataSetChanged();
        }
    }
}
