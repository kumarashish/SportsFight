package sportsfight.com.s.model;

import android.graphics.ColorSpace;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 14-02-2018.
 */

public class TransactionHistoryModel {
    String orderId;
    String transactionId;
    String amount;
    String paymentId;
    String transactionDate;
    String message;
int type;
public TransactionHistoryModel(){}
    public TransactionHistoryModel(JSONObject jsonObject,int type) {
        try {
            orderId = jsonObject.isNull("OrderId") ? "" : jsonObject.getString("OrderId");
            paymentId = jsonObject.isNull("PaymentId") ? "" : jsonObject.getString("PaymentId");
            transactionId = jsonObject.isNull("TransactionId") ? "" : jsonObject.getString("TransactionId");
            amount = jsonObject.isNull("Amount") ? "" : jsonObject.getString("Amount");
            transactionDate = jsonObject.isNull("TransactionTime") ? "" : jsonObject.getString("TransactionTime");
            message= jsonObject.isNull("TransactionMessage") ? "" : jsonObject.getString("TransactionMessage");
            this.type=type;
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public int getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
