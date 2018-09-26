package sportsfight.com.s.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.CustomPagerAdapter;
import sportsfight.com.s.model.TransactionHistoryModel;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by ashish.kumar on 29-08-2018.
 */

public class WalletFragment  extends Fragment implements View.OnClickListener {
    ArrayList<TransactionHistoryModel> allItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> spendItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> wonItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> addedItemsList=new ArrayList<>();
    ViewPager viewPager;
    TextView all;
    TextView spend;
    TextView won;
    TextView added;
    RelativeLayout header;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_wallet_screen,
                container, false);
        viewPager =(ViewPager)view.findViewById(R.id.view_pager);

        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());
        allItemsList.add(new TransactionHistoryModel());

        wonItemsList.add(new TransactionHistoryModel());
        wonItemsList.add(new TransactionHistoryModel());
        wonItemsList.add(new TransactionHistoryModel());

        spendItemsList.add(new TransactionHistoryModel());
        spendItemsList.add(new TransactionHistoryModel());
        spendItemsList.add(new TransactionHistoryModel());

        addedItemsList.add(new TransactionHistoryModel());
        addedItemsList.add(new TransactionHistoryModel());
        addedItemsList.add(new TransactionHistoryModel());
        addedItemsList.add(new TransactionHistoryModel());

        header=(RelativeLayout) view.findViewById(R.id.header);
        all = (TextView) view.findViewById(R.id.all);
        spend = (TextView) view.findViewById(R.id.spend);
        won = (TextView) view.findViewById(R.id.won);
        added = (TextView) view.findViewById(R.id.added);
        added.setOnClickListener(this);
        all.setOnClickListener(this);
        won.setOnClickListener(this);
        spend.setOnClickListener(this);
        viewPager.setAdapter(new CustomPagerAdapter(getActivity(), allItemsList, wonItemsList,spendItemsList,addedItemsList,header));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.won:
                viewPager.setCurrentItem(1);
                break;
            case R.id.spend:
                viewPager.setCurrentItem(2);
                break;
            case R.id.added:
                viewPager.setCurrentItem(3);
                break;
            case R.id.all:
                viewPager.setCurrentItem(0);
                break;
        }

    }
    public void handleTabs(int position)
    {
        switch (position)
        {
            case 0:
                all.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button));
                all.setTextColor(getResources().getColor(R.color.white));
                spend.setBackgroundColor(getResources().getColor(R.color.white));
                won.setBackgroundColor(getResources().getColor(R.color.white));
                added.setBackgroundColor(getResources().getColor(R.color.white));
                spend.setTextColor(getResources().getColor(R.color.black_font));
                won.setTextColor(getResources().getColor(R.color.black_font));
                added.setTextColor(getResources().getColor(R.color.black_font));
                break;
            case 1:
                all.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.black_font));

                won.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button));
                won.setTextColor(getResources().getColor(R.color.white));
                added.setBackgroundColor(getResources().getColor(R.color.white));
                added.setTextColor(getResources().getColor(R.color.black_font));
                spend.setBackgroundColor(getResources().getColor(R.color.white));
                spend.setTextColor(getResources().getColor(R.color.black_font));

                break;
            case 2:
                all.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.black_font));
                spend.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button));
                won.setBackgroundColor(getResources().getColor(R.color.white));
                added.setBackgroundColor(getResources().getColor(R.color.white));
                spend.setTextColor(getResources().getColor(R.color.white));
                won.setTextColor(getResources().getColor(R.color.black_font));
                added.setTextColor(getResources().getColor(R.color.black_font));
                break;
            case 3:
                all.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.black_font));
                spend.setBackgroundColor(getResources().getColor(R.color.white));
                won.setBackgroundColor(getResources().getColor(R.color.white));
                added.setBackground(getResources().getDrawable(R.drawable.rounded_corner_button));
                spend.setTextColor(getResources().getColor(R.color.black_font));
                won.setTextColor(getResources().getColor(R.color.black_font));
                added.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

}

