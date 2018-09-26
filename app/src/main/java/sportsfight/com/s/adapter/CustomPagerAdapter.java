package sportsfight.com.s.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.model.TransactionHistoryModel;

/**
 * Created by Ashish.Kumar on 08-02-2018.
 */

public class CustomPagerAdapter extends PagerAdapter {
    private Activity mContext;
    ArrayList<TransactionHistoryModel> all;
    ArrayList<TransactionHistoryModel> spend;
    ArrayList<TransactionHistoryModel> won;
    ArrayList<TransactionHistoryModel> added;
    RelativeLayout header;

    public CustomPagerAdapter(Activity context, ArrayList<TransactionHistoryModel> all, ArrayList<TransactionHistoryModel> won, ArrayList<TransactionHistoryModel> spend, ArrayList<TransactionHistoryModel> added,RelativeLayout header) {
        mContext = context;
        this.all = all;
        this.spend = spend;
        this.won = won;
        this.added = added;
        this.header=header;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.transaction_list, collection, false);
        final ListView list = (ListView) layout.findViewById(R.id.list);
        TextView noItem = (TextView) layout.findViewById(R.id.noItem);
//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int last_item;
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                if(totalItemCount>3) {
//                    if (last_item < firstVisibleItem + visibleItemCount - 1) {
//                        header.setVisibility(View.GONE);
//                    } else if (last_item > firstVisibleItem + visibleItemCount - 1) {
//                        header.setVisibility(View.VISIBLE);
//                    }
//                    last_item = firstVisibleItem + visibleItemCount - 1;
//                }
//            }
//        });

        switch (position) {
            case 0:
//                if (all.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    list.setAdapter(new TransactionListItemAdapter(mContext,all));

//                } else {
//                    list.setVisibility(View.GONE);
//                    noItem.setVisibility(View.VISIBLE);
//
//                }
                break;
            case 1:
//                if (won.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    list.setAdapter(new TransactionListItemAdapter(mContext,won));
//                } else {
//                    list.setVisibility(View.GONE);
//                    noItem.setVisibility(View.VISIBLE);
//
//                }
                break;
            case 2:
//                if (spend.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    list.setAdapter(new TransactionListItemAdapter(mContext,spend));
//                } else {
//                    list.setVisibility(View.GONE);
//                    noItem.setVisibility(View.VISIBLE);
//
//                }
                break;
            case 3:
//                if (added.size() > 0) {
                    list.setVisibility(View.VISIBLE);
                    noItem.setVisibility(View.GONE);
                    list.setAdapter(new TransactionListItemAdapter(mContext,added));
//                } else {
//                    list.setVisibility(View.GONE);
//                    noItem.setVisibility(View.VISIBLE);
//
//                }
                break;
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
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
