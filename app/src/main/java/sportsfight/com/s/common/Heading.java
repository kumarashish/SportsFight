package sportsfight.com.s.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class Heading extends android.support.v7.widget.AppCompatTextView {

    public Heading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Heading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Heading(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "headingfont.ttf");
        setTypeface(tf);
    }
}