package sportsfight.com.s.interfaces;

import sportsfight.com.s.model.ChallengeModel;
import sportsfight.com.s.model.ReminderModel;

/**
 * Created by Ashish.Kumar on 21-02-2018.
 */

public interface Accept_reject_callback {
    public void onAcceptClick(ReminderModel model);
    public void onRejectClick(ReminderModel model);
}
