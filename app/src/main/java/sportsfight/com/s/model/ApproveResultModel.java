package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 08-03-2018.
 */

public class ApproveResultModel {
    int ChallengeId;
    String ResultText;
    int WinnerId;
    String WinnerName;
    int LooserId;
    String LooserName;
    int Id;

    public ApproveResultModel(JSONObject job) {
        try {
            ChallengeId = job.isNull("ChallengeId") ? -1 : job.getInt("ChallengeId");
            ResultText = job.isNull("ResultText") ? "" : job.getString("ResultText");
            WinnerId = job.isNull("WinnerId") ? -1 : job.getInt("WinnerId");
            WinnerName = job.isNull("WinnerName") ? "" : job.getString("WinnerName");
            LooserId = job.isNull("LooserId") ? -1 : job.getInt("LooserId");
            LooserName = job.isNull("LooserName") ? "" : job.getString("LooserName");
            Id = job.isNull("Id") ? -1 : job.getInt("Id");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public int getLooserId() {
        return LooserId;
    }

    public int getWinnerId() {
        return WinnerId;
    }

    public int getId() {
        return Id;
    }

    public int getChallengeId() {
        return ChallengeId;
    }

    public String getLooserName() {
        return LooserName;
    }

    public String getResultText() {
        return ResultText;
    }

    public String getWinnerName() {
        return WinnerName;
    }
}
