package sportsfight.com.s.model;

/**
 * Created by Ashish.Kumar on 11-05-2018.
 */

public interface TransferPointsCallback {
    public void onTransferPointsClick(int senderId,int receiverId,String points,int otpType);
    public void onTransferAmountClick(BeneficieryModel model);

}
