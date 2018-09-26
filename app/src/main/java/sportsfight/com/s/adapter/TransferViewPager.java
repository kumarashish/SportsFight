package sportsfight.com.s.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.AddBeneficieryCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.BeneficieryModel;
import sportsfight.com.s.model.IPLResultModel;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.model.TransferPointsCallback;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Transfer;

/**
 * Created by Ashish.Kumar on 04-05-2018.
 */

public class TransferViewPager extends PagerAdapter  {
    public Activity mContext;
    AddBeneficieryCallBack callBack;
    AppController controller;
    WebApiResponseCallback webAPiCallback;
    Dialog dialog;
    UserProfile beneficieryProfile=null;
EditText userNumber;
   TextView beneficeryName;
 TextView beneficeryNumber;
   de.hdodenhof.circleimageview.CircleImageView profilePic;
      LinearLayout   beneficeryLayout;
      TransferPointsCallback transferPointsCallback;
      EditText points;
      ArrayList<BeneficieryModel> beneficieryList=null;
    BeneficieryListAdapter adapter=null;
    ListView listView;
    TextView     no_beneficiery;
    public TransferViewPager(Activity context,ArrayList<BeneficieryModel> beneficieryList) {
        mContext = context;
        callBack=(AddBeneficieryCallBack)context;
        controller=(AppController)context.getApplicationContext();
        webAPiCallback=(WebApiResponseCallback)context;
        transferPointsCallback=(TransferPointsCallback)context;
        this.beneficieryList=beneficieryList;
    }

    @Override
    public Object instantiateItem(final ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = null;
        switch (position) {
            case 0:
                layout = (ViewGroup) inflater.inflate(R.layout.transfer_sportsfight_user, collection, false);
                LinearLayout recetTransfersLayout = (LinearLayout) layout.findViewById(R.id.recetTransfersLayout);
                beneficeryLayout = (LinearLayout) layout.findViewById(R.id.beneficeryLayout);
               userNumber = (EditText) layout.findViewById(R.id.userNumber);
                Button search = (Button) layout.findViewById(R.id.search);
                Button transfer = (Button) layout.findViewById(R.id.transfer);
               beneficeryName = (TextView) layout.findViewById(R.id.beneficeryName);
                 beneficeryNumber = (TextView) layout.findViewById(R.id.beneficeryNumber);
                points = (EditText) layout.findViewById(R.id.points);
                 profilePic=(de.hdodenhof.circleimageview.CircleImageView)layout.findViewById(R.id.circularImageView);
                points.setTypeface(controller.getDetailsFont());
                transfer.setTypeface(controller.getDetailsFont());
                userNumber.setTypeface(controller.getDetailsFont());
                recetTransfersLayout.setVisibility(View.GONE);
                beneficeryLayout.setVisibility(View.GONE);
                userNumber.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(editable.length()!=0)
                        {
                            beneficeryName.setText("");
                            beneficeryNumber.setText("");
                            profilePic.setImageResource(R.drawable.user_icon);
                            beneficieryProfile=null;

                        }

                    }
                });
                userNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (userNumber.getText().length() != 10) {
                            beneficeryLayout.setVisibility(View.GONE);
                            if (userNumber.getText().length() == 0) {
                                Util.showToast(mContext, "Please enter mobile number");
                            } else {
                                Util.showToast(mContext, "Please enter valid mobile number");
                            }
                        }else {
                            handleSearch();
                        }
                        return true;
                    }
                    return false;
                }
            });
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleSearch();
                        }
                });
                transfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     if(points.getText().length()==0)
                     {
                         Util.showToast(mContext, "Please enter points to transfer");
                     }else{
                         if(Integer.parseInt(points.getText().toString())<500)
                         {
                             Util.showToast(mContext, "Points should be greater than 500");
                         }
                        else if(controller.getProfile().getUserId()==beneficieryProfile.getUserId())
                         {
                             Util.showToast(mContext, "You can not transfer points to youself");
                         }
                         else{
                             transferPointsCallback.onTransferPointsClick(controller.getProfile().getUserId(),beneficieryProfile.getUserId(),points.getText().toString(),Common.TransferPoint);
                         }
                     }
                    }
                });
                break;
            case 1:
                layout = (ViewGroup) inflater.inflate(R.layout.transfer_bank, collection, false);
                 no_beneficiery = (TextView) layout.findViewById(R.id.no_beneficiery);
                TextView     add_beneficiery = (TextView) layout.findViewById(R.id.add_beneficiery);
                listView = (ListView) layout.findViewById(R.id.listview);
                add_beneficiery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callBack.onAddBeneficieryClick();
                    }
                });
                if(beneficieryList.size()==0) {
                    listView.setVisibility(View.GONE);
                    no_beneficiery.setVisibility(View.VISIBLE);
                }else{
                    listView.setVisibility(View.VISIBLE);
                    no_beneficiery.setVisibility(View.GONE);
                    adapter=new BeneficieryListAdapter(beneficieryList,mContext);
                    listView.setAdapter(adapter);
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

    public void handleSearch() {
        if (userNumber.getText().length() == 10) {
            dialog = Util.showPogress(mContext);
            controller.getApiCall().getData(Common.getProfileDetailsFromNumber(userNumber.getText().toString()),controller.getPrefManager().getUserToken(), new WebApiResponseCallback() {
                @Override
                public void onSucess(String value) {
                    if (Util.getStatus(value) == true) {
                        beneficieryProfile = new UserProfile(Util.getResultJson(value));
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beneficeryName.setText(beneficieryProfile.getUserName());
                                beneficeryNumber.setText(beneficieryProfile.getMobile());
                                Picasso.with(mContext).load(beneficieryProfile.getProfilePic()).resize(200, 200).placeholder(mContext.getDrawable(R.drawable.user_icon)).into(profilePic);
                                beneficeryLayout.setVisibility(View.VISIBLE);
                                points.requestFocus();
                                points.setSelection(1);
                            }
                        });
                    } else {
                        Util.showToast(mContext, Util.getMessage(value));
                    }
                    if (dialog != null) {
                        dialog.cancel();
                    }
                }

                @Override
                public void onError(String value) {
                    if (dialog != null) {
                        dialog.cancel();
                    }
                    Util.showToast(mContext, Util.getMessage(value));
                }
            });
        } else {
            if (userNumber.getText().length() == 0) {
                Util.showToast(mContext, "Please enter mobile number");
            } else {
                Util.showToast(mContext, "Please enter valid mobile number");
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(adapter!=null)
        {
            adapter.notifyDataSetChanged();
        }else{
            listView.setVisibility(View.VISIBLE);
            no_beneficiery.setVisibility(View.GONE);
            adapter=new BeneficieryListAdapter(beneficieryList,mContext);
            listView.setAdapter(adapter);
        }
    }
}