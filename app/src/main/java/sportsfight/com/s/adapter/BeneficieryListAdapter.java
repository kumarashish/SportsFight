package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.model.BeneficieryModel;
import sportsfight.com.s.model.TransferPointsCallback;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 14-05-2018.
 */

public class BeneficieryListAdapter extends BaseAdapter {
    ArrayList<BeneficieryModel> list;
    Activity activity;
    LayoutInflater inflater;
    TransferPointsCallback callback;
    public BeneficieryListAdapter( ArrayList<BeneficieryModel> list,Activity activity)
    {
        this.list=list;
        this.activity=activity;
        inflater = activity.getLayoutInflater();
        callback=(TransferPointsCallback)activity;
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
        final BeneficieryModel model=list.get(i);
        ViewHolder holder=null;
        if (view == null) {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.beneficiary_row, null, true);
            holder.AccountNumber=(TextView)view.findViewById(R.id.userAccount);
            holder.bankDetails=(TextView)view.findViewById(R.id.bankDetails);
            holder.Name=(TextView)view.findViewById(R.id.userName);
            holder.IFSC_code=(TextView)view.findViewById(R.id.ifsccode);
            holder.view=(View)view.findViewById(R.id.view);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.Name.setText("Name : "+ Util.getCamelCase(model.getBeneficieryName()));
        holder.bankDetails.setText("Bank : "+model.getBankDetails());
        holder.IFSC_code.setText("IFSC_code : "+model.getIFSC_Code());
        holder.AccountNumber.setText("Account Number : "+model.getBeneficieryAccountNumber());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onTransferAmountClick(model);
            }
        });
        view.setTag(holder);
        return view;
    }
    public class ViewHolder{
        //ifsccode,userName
        View view;
        TextView AccountNumber,Name,IFSC_code,bankDetails;


    }
}
