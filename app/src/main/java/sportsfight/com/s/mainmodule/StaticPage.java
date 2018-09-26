package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.launchingmodule.Splash;

/**
 * Created by Ashish.Kumar on 26-02-2018.
 */

public class StaticPage extends Activity implements View.OnClickListener{
    public static Bitmap bmp;
    public static String  headingValue;
    public static String messageValue;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.messageView)
    RelativeLayout messageView;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.back)
    Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staticpage);
        ButterKnife.bind(this);
        if (bmp != null) {
            image.setImageBitmap(bmp);
            image.setVisibility(View.VISIBLE);
            messageView.setVisibility(View.GONE);
        } else {
            heading.setText(headingValue);
            description.setText(messageValue);
            image.setVisibility(View.GONE);
            messageView.setVisibility(View.VISIBLE);
        }
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==back.getId())
        {
            if(isAppAlreadyOpen())
            {
                finish();
            }else{
                Intent in =new Intent(StaticPage.this, Splash.class);
                startActivity(in);
                finish();
            }
        }
    }

    public boolean isAppAlreadyOpen() {
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if (taskList.get(0).numActivities >1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        image=null;
        headingValue=null;
        messageView=null;
        super.onDestroy();
    }
}
