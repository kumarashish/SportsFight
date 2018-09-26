package sportsfight.com.s.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 30-08-2018.
 */

public class AvailableGrounds {
    String GroundId;
    String GroundName;
    String Location;
    String Distance;
    String Price;
    ArrayList<String> Gallery=new ArrayList<>();

    public AvailableGrounds(JSONObject availableGrounds) {
        try {
            GroundId = availableGrounds.isNull("GroundId") ? "" : availableGrounds.getString("GroundId");
            GroundName= availableGrounds.isNull("GroundName") ? "" : availableGrounds.getString("GroundName");
           Location= availableGrounds.isNull("Location") ? "" : availableGrounds.getString("Location");
            Distance= availableGrounds.isNull("Distance") ? "" : availableGrounds.getString("Distance");
            Price= availableGrounds.isNull("Price") ? "" : availableGrounds.getString("Price");
            JSONArray jsonArray=availableGrounds.getJSONArray("Gallery");
            for(int i=0;i<jsonArray.length();i++)
            {
                Gallery.add(jsonArray.getString(i));
            }
            ArrayList Gallery;
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }

    public String getDistance() {
        return Distance;
    }

    public String getPrice() {
        return Price;
    }

    public String getLocation() {
        return Location;
    }

    public ArrayList<String> getGallery() {
        return Gallery;
    }

    public String getGroundId() {
        return GroundId;
    }

    public String getGroundName() {
        return GroundName;
    }
}
