package sportsfight.com.s.model;

import android.view.View;

/**
 * Created by ashish.kumar on 31-08-2018.
 */

public class ViewModel {
    View view;
    int pos;
    int buttonId;

    public ViewModel(View view, int pos, int buttonId) {
        this.view = view;
        this.buttonId = buttonId;
        this.pos = pos;
    }

    public int getButtonId() {
        return buttonId;
    }

    public int getPos() {
        return pos;
    }

    public View getView() {
        return view;
    }
}
