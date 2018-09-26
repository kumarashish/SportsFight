package sportsfight.com.s.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish.Kumar on 16-02-2018.
 */

public class Slots {
    ArrayList<Slotdetails> list = new ArrayList<>();
    String date;

    public Slots(JSONObject json) {
        try {
            this.date = json.getString("Date");
            JSONArray jsonArray = json.getJSONArray("Slots");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new Slotdetails(jsonArray.getJSONObject(i)));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public ArrayList<Slotdetails> getList() {
        return list;
    }

    public String getDate() {
        return date;
    }

    public class Slotdetails {
        String slotTime = "";
        String status = "";
        int id = -1;
boolean clicked=false;
        public Slotdetails(JSONObject slots) {
            try {
                this.slotTime = slots.getString("SlotTime");
                this.status = slots.getString("Status");
                this.id = slots.getInt("Id");
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }

        public int getId() {
            return id;
        }

        public String getSlotTime() {
            return slotTime;
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
        }

        public boolean isClicked() {
            return clicked;
        }

        public String getStatus() {
            return status;
        }
    }
}
