package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.UpcomingMatchesAdapter;
import sportsfight.com.s.adapter.WonViewAllAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.model.MatchesModel;

/**
 * Created by Ashish.Kumar on 27-02-2018.
 */

public class WonViewAll  extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    Button back;
    AppController controller;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.listView)
    ListView listView;
    public static ArrayList<MatchesModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        heading.setText("Results");
        back.setOnClickListener(this);
        listView.setAdapter(new WonViewAllAdapter(list, WonViewAll.this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
        }

    }
}