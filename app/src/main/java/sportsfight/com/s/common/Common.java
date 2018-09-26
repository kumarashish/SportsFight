package sportsfight.com.s.common;

import android.net.Uri;

import java.util.SimpleTimeZone;

/**
 * Created by Ashish.Kumar on 23-01-2018.
 */

public class Common {
 public static int AddBeneficiery=3;
 public static int TransferPoint=1;
 public static int TransferMoney=2;

    public static String BaseUrl = "http://192.168.100.92:9191/api/";
    //public static String BaseUrl = "http://www.dmss.co.in/sportsfight/api/";
    //public static String LoginUrl = BaseUrl + "login/userlogin";
    public static String LoginUrl = BaseUrl +"login/userloginWithAppVersion";
    public static String SignUpUrl = BaseUrl + "login/Register";
    public static String GetListOfGames = BaseUrl + "Sports/GetAllGames";
    public static String OTPUrl = BaseUrl + "login/validate-mobile-registration?";
    public static String ResendOTPUrl = BaseUrl + "login/resend-otp-registration?";
    public static String forgetPasswordUrl = BaseUrl + "login/send-otp-username?";
    public static String resetPassword = BaseUrl + "login/reset-password?";
    public static  String getUpdateEmailUrl=BaseUrl + "login/send-email-update?";
    public static  String getUpdateMobileUrl=BaseUrl + "login/send-otp-update?";
    public static  String getValidateMobileUrl=BaseUrl + "user/update-mobile?";
    public static  String getProfile_Url=BaseUrl + "user/getprofile?";
    public static String getUpdateProfilePic_Url=BaseUrl + "user/update-profileimage";
    public static String getGender_Url=BaseUrl + "user/update-personalinfo?";
    public static String getUpdateGame_Url=BaseUrl + "user/update-usergames?";
    public static String getWalletPoints_Url=BaseUrl + "wallet/getpoints?";
    public static String getTransactionHistory_Url=BaseUrl + "wallet/get-transactions-bydate?";
    public static String getAddPoints_Url=BaseUrl + "wallet/addamount";
    public static String getPlayerList_Url=BaseUrl + "challenge/getusers-game?";
    public static String getFreeSlotsForGame=BaseUrl + "challenge/getslots-game?";
    public static String getAddChallengeUrl=BaseUrl + "challenge/addchallenge";
    public static String getReminderUrl=BaseUrl + "user/reminders?";
    public static String getAlertUrl=BaseUrl + "user/alerts?";
    public static String getAccept_RejectUrl=BaseUrl + "challenge/accept-reject-challenge";
    public static String getDashBoardrl=BaseUrl + "user/dashboard?";
    public static String getPlaceBidUrl=BaseUrl + "bid/addBid";
    public static String getUpdateBidUrl=BaseUrl + "bid/updateBid";
    public static String getMatchesForResultDeclarationUrl=BaseUrl + "referee/getallmatches_referee?id=8";
    public static String getUrlForResultDeclarationUrl=BaseUrl + "referee/declareresult_referee";
    public static String getMatchesListForAdminApprovalUrl=BaseUrl + "admin/get-match-results";
    public static String getApproveResultUrl=BaseUrl + "admin/approve-result?";
    public static String getChallengesListForAdminApprovalUrl=BaseUrl + "admin/get-challenges-approval";
    public static String getIPLMatchResultHistoryUrl=BaseUrl + "events/geteventresult?";
    public static String getIplMatchesUrl=BaseUrl + "events/geteventmatches?";
    public static String getApprove_Reject_ChallengesUrl=BaseUrl + "admin/approve-reject-match";
    public static String getMyBidUrl=BaseUrl + "user/getuserbiddetails?";
    public static String getProfileFromNumberUrl=BaseUrl + "user/getuser-details?";
    public static String gettransferPointsUrl=BaseUrl + "wallet/transffer-points";
    public static String getValidateOTPURL=BaseUrl + "wallet/validate-otp?";
    public static String getValidateBeneficieryOTPURL=BaseUrl + "wallet/ValidateBenificieryOTP?";
    public static String getValidateBankTransferOTPURL=BaseUrl + "wallet/validateBankTransferOTP?";
    public static String addBeneficiery=BaseUrl + "wallet/addBeneficiary";
    public static String getBeneficieryList=BaseUrl + "wallet/GetBeneficieryList?";
    public static String getTransferMoneyUrl=BaseUrl + "wallet/transferMoneyToBank";
    public static String isSlotAvailable=BaseUrl + "challenge/IsGameSlotValidate";
    public static String isUserAlreadyRegistered=BaseUrl+"login/useralreadyregistered?mobile=";
    public static String validateTransactionUrl="https://securegw.paytm.in/merchant-status/getTxnStatus";
    /*****************************************paymentgateway***************************************/
    public static String paymentGatewayUrlLive="https://api.instamojo.com/oauth2/token/";
    public static String paymentVerificationUrlLive="https://api.instamojo.com/v2/payments/";
    public static String liveClientId="serfYHfvRfUKxQGlbbOiWY7KLjqNkCj2roDIXO7Z";
    public static String liveClientSecret="XiIZjAWOzEXrQue3RC8mJuknh6FIJvaPF1dLjZYFrSo42nIUfuUYtmxu7HxiEyrfcscf6XoR9N7m2McA6GzfqnmpAuqyXQBqzxda6YE80itIaEaRANeUJghR413qVymd";
    /**********************************************************************************************/
    public static String getBankDetailFromIFSC="https://ifsc.razorpay.com/";
    public static String nameKey = "Name";
    public static String passwordKey = "Password";
    public static String emailKey = "Email";
    public static String mobileKey = "Mobile";
    public static String deviceIdKey = "DeviceId";
    public static String fCMIdKey = "FCMId";
    public static String userInterestedGamesDTOKey = "UserInterestedGamesDTO";
    public static Uri imageUri = null;
    public static String sdCardPath;
    public static String folderName;
    public static String tempPath;
    public static String FCMToken = "";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static int all = 1;
    public static int spend = 2;
    public static int won = 3;
    public static int added = 4;
    public static final String chess = "Chess";
    public static final String pool = "pool or snooker";
    public static final String minibasketball = "Mini Basketball";
    public static final String airhockey = "Air Hockey";
    public static final String fussball = "Fussball";
    public static final String tt = "Table Tennis";
    public static final String carrom = "Carrom";
    public static final String golf = "Mini Golf";
    public static String SessionExpired="Session Expired";
    public static String getLoginUrl( String deviceID,String fCMId,String Appversion) {
        return LoginUrl + "?deviceId=" + deviceID + "&fcmId=" + fCMId+"&Appversion="+Appversion;
    }

    public static String validateOtpUrl(String id , String otp) {
        return OTPUrl +"id=" +id + "&OTP="+otp;
    }

    public static String resendOtpUrl( String Id) {
        return ResendOTPUrl +"id="+Id;
    }

    public static String getForgetPasswordUrl(String userName)
    {
        return forgetPasswordUrl +"username=" + userName;
    }

    public static String resetPassword(String OTP,String password,String userId)
    {
        return resetPassword+"otp="+OTP+"&newpassword="+password+"&id="+userId;
    }


    public static String getUpdateGenderUrl(int userId,int genderId)
    {
        return getGender_Url+"id="+userId+"&gender="+genderId+"&modifiedname=";
    }


    public static String getProfile_Url(String userId)
    {
        return getProfile_Url+"id="+userId;
    }


    public static String getUpdateGame_Url(int userId)
    {
        return getUpdateGame_Url+"id="+userId;
    }


    public static String getUpdateMobileUrl(int userID, String mobileNumber) {
        return getUpdateMobileUrl + "id=" + userID + "&newmobile=" + mobileNumber;
    }


    public static String getUpdateEmailUrl(int userID, String emailId) {
        return getUpdateEmailUrl + "id=" + userID + "&newemail=" + emailId;
    }


    public static String getValidateMobileUrl(int userID,String mobileNumber,String otp) {
        return getValidateMobileUrl +"id=" + userID + "&newmobile=" + mobileNumber+"&OTP=" + otp;
    }


    public static String getWalletPointsUrl(int id)
    {
        return  getWalletPoints_Url+"id="+id;
    }


    public static String getTransactionHistoryUrl(int id,String startDate,String endDate)
    {
        return  getTransactionHistory_Url+"id="+id+"&startdate="+startDate+"&enddate="+endDate;
    }


    public static String getGamePlayersList(int gameId,int userId){
        return getPlayerList_Url+"gameid="+gameId+"&currentuserid="+userId;
    }


    public static String getGetFreeSlotsForGame(int gameId,String startDate,int days)
    {
        return getFreeSlotsForGame+"gameid="+gameId+"&startdate="+startDate+"&days="+days;
    }


    public static String getReminderUrl(int userId){
        return getReminderUrl+"id="+userId;
    }

    public static String getAlertsUrl(int userId){
        return getAlertUrl+"id="+userId;
    }

    public static String getGetDashBoardrl(int userId){
        return getDashBoardrl+"id="+userId;
    }

    public static String getPaymentStatusUrl(String paymentId) {
        return paymentVerificationUrlLive + "" + paymentId;
    }

    public static String getGetMatchesForResultDeclarationUrl(String date) {
        return getMatchesForResultDeclarationUrl + "&date=" + date;
    }

    public static String getApproveResultUrl(int challengeResultId, int adminId) {
        return getApproveResultUrl + "challengeResultId=" + challengeResultId + "&adminId=" + adminId;
    }

 public static String getIPLMatchesList(int id) {
  return getIplMatchesUrl + "id=" + id;
 }

 public static String getGetMyBidList(int id) {
  return getMyBidUrl + "id=" + id;
 }

 public static String getIplMatchHistoryUrl(String date) {
  return getIPLMatchResultHistoryUrl + "date=" + date;
 }

 public static String getGetBankDetailFromIFSC(String IFSCCode) {
  return getBankDetailFromIFSC + "" + IFSCCode;
 }

 public static String getProfileDetailsFromNumber(String mobilenumber) {
  return getProfileFromNumberUrl + "mobile=" + mobilenumber;
 }
    public static String getBeneficieryList(int userId) {
        return getBeneficieryList + "userId=" +userId;
    }
    public static String getValidateOtpUUrl(int id, String otp, int type) {
        if (type == Common.AddBeneficiery) {
            return getValidateBeneficieryOTPURL + "id=" + id + "&otp=" + otp + "&otpTypeId=" + type;
        } else if (type == Common.TransferMoney) {
            return getValidateBankTransferOTPURL + "id=" + id + "&otp=" + otp + "&otpTypeId=" + type;
        } else {
            return getValidateOTPURL + "id=" + id + "&otp=" + otp + "&otpTypeId=" + type;
        }
    }
    public static String isUserAlreadyExist(String mobileNumber,String email)
    {
      return isUserAlreadyRegistered+mobileNumber+"&email=" +email;
    }

}
