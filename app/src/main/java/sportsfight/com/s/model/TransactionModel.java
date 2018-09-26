package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 07-09-2018.
 */

public class TransactionModel {
    String TXNID="";
    String BANKTXNID;
    String ORDERID;
    String TXNAMOUNT;
    String RESPCODE;
    String RESPMSG;
    String TXNDATE;

    public TransactionModel(String Json) {
        try {
            JSONObject jsonObject = new JSONObject(Json);
            this.TXNID = jsonObject.isNull("TXNID")?"":jsonObject.getString("TXNID");
            this.BANKTXNID = jsonObject.isNull("BANKTXNID")?"":jsonObject.getString("BANKTXNID");
            this.ORDERID = jsonObject.isNull("ORDERID")?"":jsonObject.getString("ORDERID");
            this.TXNAMOUNT = jsonObject.isNull("TXNAMOUNT")?"":jsonObject.getString("TXNAMOUNT");
            this.RESPCODE =jsonObject.isNull("RESPCODE")?"": jsonObject.getString("RESPCODE");
            this.RESPMSG = jsonObject.isNull("RESPMSG")?"":jsonObject.getString("RESPMSG");
            this.TXNDATE =jsonObject.isNull("TXNDATE")?"": jsonObject.getString("TXNDATE");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }
    public String getBANKTXNID() {
        return BANKTXNID;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public String getRESPCODE() {
        return RESPCODE;
    }

    public String getRESPMSG() {
        return RESPMSG;
    }

    public String getTXNAMOUNT() {
        return TXNAMOUNT;
    }

    public String getTXNDATE() {
        return TXNDATE;
    }

    public String getTXNID() {
        return TXNID;
    }
}
