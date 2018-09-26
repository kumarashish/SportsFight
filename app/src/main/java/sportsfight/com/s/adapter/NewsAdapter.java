package sportsfight.com.s.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.model.MatchesModel;

/**
 * Created by Ashish.Kumar on 24-04-2018.
 */

public class NewsAdapter extends BaseAdapter {
    ArrayList<Article> newsList; Activity act;
    public NewsAdapter(ArrayList<Article> newsList, Activity act)
    {
        this.newsList=newsList;
        this.act=act;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        TextView newsCount;
        TextView viewAllResult;
        TextView heading;
        ImageView image;
        TextView details;
        RelativeLayout header;
        Article model=newsList.get(i);
        itemView = LayoutInflater.from(act).inflate(R.layout.newscard, null, true);
        newsCount = (TextView) itemView.findViewById(R.id.newsCount);
        heading = (TextView) itemView.findViewById(R.id.newsHeading);
        image = (ImageView) itemView.findViewById(R.id.image);
        details = (TextView) itemView.findViewById(R.id.details);
        viewAllResult = (TextView) itemView.findViewById(R.id.viewAllNews);
        header=(RelativeLayout) itemView.findViewById(R.id.header);
        header.setVisibility(View.GONE);

        String[] desc = model.getDescription().split(" />");
        String imagee = desc[0].replace("<img src='", "");
        imagee=imagee.replaceAll("' width='90' height='62'", "");
        desc[1]=desc[1].replaceAll("&quot;","");
        heading.setText(model.getTitle());
        details.setText(desc[1]);
        if(imagee!=null) {
            Picasso.with(act).load(imagee).resize(200, 200)
                    .centerInside().placeholder(R.drawable.user_icon).into(image);
        }

        return itemView;
    }
    public class ViewHolder{

    }
}
