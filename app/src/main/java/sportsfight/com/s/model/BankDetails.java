package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 07-05-2018.
 */

public class BankDetails {
    String BRANCH;
    String ADDRESS;
    String CONTACT;
    String CITY;
    String DISTRICT;
    String STATE;
    String BANK;
    String BANKCODE;
    String IFSC;

    public BankDetails(JSONObject jsonObject) {
        try {
            BRANCH = jsonObject.isNull("BRANCH") ? "" : jsonObject.getString("BRANCH");
            ADDRESS = jsonObject.isNull("ADDRESS") ? "" : jsonObject.getString("ADDRESS");
            CONTACT = jsonObject.isNull("CONTACT") ? "" : jsonObject.getString("CONTACT");
            CITY = jsonObject.isNull("CITY") ? "" : jsonObject.getString("CITY");
            DISTRICT = jsonObject.isNull("DISTRICT") ? "" : jsonObject.getString("DISTRICT");
            STATE = jsonObject.isNull("STATE") ? "" : jsonObject.getString("STATE");
            BANK = jsonObject.isNull("BANK") ? "" : jsonObject.getString("BANK");
            BANKCODE = jsonObject.isNull("BANKCODE") ? "" : jsonObject.getString("BANKCODE");
            IFSC = jsonObject.isNull("IFSC") ? "" : jsonObject.getString("IFSC");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getBANK() {
        return BANK;
    }

    public String getBANKCODE() {
        return BANKCODE;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public String getCITY() {
        return CITY;
    }

    public String getCONTACT() {
        return CONTACT;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public String getIFSC() {
        return IFSC;
    }

    public String getSTATE() {
        return STATE;
    }

}
