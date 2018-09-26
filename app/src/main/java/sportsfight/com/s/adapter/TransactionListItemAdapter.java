package sportsfight.com.s.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.model.TransactionHistoryModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 14-02-2018.
 */

public class TransactionListItemAdapter extends BaseAdapter {
    ArrayList<TransactionHistoryModel> items;
    Activity act;
    LayoutInflater inflater;
    public TransactionListItemAdapter(Activity act, ArrayList<TransactionHistoryModel> items)
    {
        this.act=act;
        this.items=items;
       inflater = act.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      //  TransactionHistoryModel model=items.get(i);
  ViewHolder holder=null;
  if(view==null)
  {holder=new ViewHolder();
      view=inflater.inflate(R.layout.wallet_row, null, true);
//      holder.points=(TextView)view.findViewById(R.id.points);
//      holder.dateStamp=(TextView)view.findViewById(R.id.dateStamp);
//      holder.timeStamp=(TextView)view.findViewById(R.id.timeStamp);
//      holder.message=(TextView)view.findViewById(R.id.message);
//      holder.orderId=(TextView)view.findViewById(R.id.orderId);
//      holder.transactionId=(TextView)view.findViewById(R.id.transactionId);
  }else{
      holder=(ViewHolder)view.getTag();
  }
//        if ((model.getType() == Common.added) || (model.getType() == Common.won)) {
//            holder.points.setText(" + " + Integer.toString(Math.round(Float.parseFloat(model.getAmount())) * 10));
//        } else if (model.getType() == Common.spend) {
//            {
//                holder.points.setText(" - " + Integer.toString(Math.round(Float.parseFloat(model.getAmount())) * 10));
//                holder.points.setTextColor(act.getResources().getColor(R.color.red));
//            }
//
//        } else {
//            if (model.getMessage().contains("added")) {
//                holder.points.setText(" + " + Integer.toString(Math.round(Float.parseFloat(model.getAmount())) * 10));
//            } else if (model.getMessage().contains("deducted")) {
//                holder.points.setText(" - " + Integer.toString(Math.round(Float.parseFloat(model.getAmount())) * 10));
//                holder.points.setTextColor(act.getResources().getColor(R.color.red));
//            }
//        }
//String []values=Util.getDateFromString(model.getTransactionDate());
//  holder.dateStamp.setText(values[0]);
//  holder.timeStamp.setText(values[1]);
//  holder.message.setText(model.getMessage());
//  holder.orderId.setText("Order Id: "+model.getOrderId());
//  holder.transactionId.setText("Transaction Id:"+model.getTransactionId());
  view.setTag(holder);

  return view;
    }
    public class ViewHolder{
TextView points;
TextView timeStamp,dateStamp;
TextView message;
TextView orderId;
TextView transactionId;

    }
}

