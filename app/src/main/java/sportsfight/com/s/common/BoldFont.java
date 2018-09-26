package sportsfight.com.s.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Ashish.Kumar on 29-01-2018.
 */

public class BoldFont extends android.support.v7.widget.AppCompatTextView {

    public BoldFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public BoldFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoldFont(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "vs_font.ttf");
        setTypeface(tf);
    }
}