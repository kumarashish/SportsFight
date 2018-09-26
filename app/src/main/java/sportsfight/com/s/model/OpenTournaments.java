package sportsfight.com.s.model;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 30-08-2018.
 */

public class OpenTournaments {
    String TournamentId;
    String TournamnetName;
    String EntryFees;
    String MinimumPlayer;
    String WinningAmount;
    public OpenTournaments(JSONObject openTournaments) {
        try {
            TournamentId = openTournaments.isNull("TournamentId") ? "" : openTournaments.getString("TournamentId");
            TournamnetName = openTournaments.isNull("TournamnetName") ? "" : openTournaments.getString("TournamnetName");
            EntryFees = openTournaments.isNull("EntryFees") ? "" : openTournaments.getString("EntryFees");
            MinimumPlayer = openTournaments.isNull("MinimumPlayer") ? "" : openTournaments.getString("MinimumPlayer");
            WinningAmount = openTournaments.isNull("WinningAmount") ? "" : openTournaments.getString("WinningAmount");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getEntryFees() {
        return EntryFees;
    }

    public String getMinimumPlayer() {
        return MinimumPlayer;
    }

    public String getTournamentId() {
        return TournamentId;
    }

    public String getTournamnetName() {
        return TournamnetName;
    }

    public String getWinningAmount() {
        return WinningAmount;
    }
}
