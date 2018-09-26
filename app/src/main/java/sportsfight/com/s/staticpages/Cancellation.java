package sportsfight.com.s.staticpages;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;

/**
 * Created by ashish.kumar on 24-08-2018.
 */

public class Cancellation extends Activity implements View.OnClickListener {
    @BindView(R.id.back)
    Button back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancellation_policy);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();

    }
}
