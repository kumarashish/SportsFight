package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 14-05-2018.
 */

public class BeneficieryModel {
    String beneficieryName, beneficieryAccountNumber, IFSC_Code, BankDetails;
    int Id;

    public BeneficieryModel(JSONObject jsonObject) {
        try {
            beneficieryName = jsonObject.isNull("BeneficieryName") ? "" : jsonObject.getString("BeneficieryName");
            beneficieryAccountNumber = jsonObject.isNull("BeneficieryAccountNumber") ? "" : jsonObject.getString("BeneficieryAccountNumber");
            IFSC_Code = jsonObject.isNull("IFSC_Code") ? "" : jsonObject.getString("IFSC_Code");
            BankDetails = jsonObject.isNull("BankDetails") ? "" : jsonObject.getString("BankDetails");
            Id = jsonObject.isNull("Id") ? -1 : jsonObject.getInt("Id");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public int getId() {
        return Id;
    }

    public String getBankDetails() {
        return BankDetails;
    }

    public String getBeneficieryAccountNumber() {
        return beneficieryAccountNumber;
    }

    public String getBeneficieryName() {
        return beneficieryName;
    }

    public String getIFSC_Code() {
        return IFSC_Code;
    }


}
