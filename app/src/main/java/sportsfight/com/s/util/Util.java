package sportsfight.com.s.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.model.Calenderr;
import sportsfight.com.s.model.GameModel;

/**
 * Created by Ashish.Kumar on 18-01-2018.
 */

public class Util {
public static String[] months={"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
    public static boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            Toast.makeText(act, "Internet Unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static String getCompleteAddressString(final Activity act, final double LATITUDE, final double LONGITUDE) {
        List<Address> list;
        String strAdd=null;
        Geocoder geocoder = new Geocoder(act, Locale.getDefault());
        try {
            list = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            for (int n = 0; n <= list.get(0).getMaxAddressLineIndex()-2; n++) {
                strAdd +=   list.get(0).getAddressLine(n) + ", ";
            }
            strAdd = list.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strAdd;

    }
    public static int getCardBgInt(String gameName,Activity act)
    {int d=-1;
        switch (gameName)

        {case Common.carrom:
            d=R.drawable.red_card;
            break;
            case Common.pool:
                d=R.drawable.black_card;
                break;
            case Common.tt:
                d=R.drawable.orange_card;
                break;
            case Common.fussball:
                d=R.drawable.green_card;
                break;
            case Common.minibasketball:
                d=R.drawable.light_brown_card;
                break;
            case Common.golf:
                d=R.drawable.light_black;
                break;
            case Common.airhockey:
                d=R.drawable.yellow_card;
                break;
            case Common.chess:
                d=R.drawable.dark_brown_card;
                break;
           default:
                d=R.drawable.dark_brown_card;
                break;


        }
        return d;
    }
    public static Drawable getCardBg(String gameName,Activity act)
    {Drawable d=null;
        switch (gameName)

        {case Common.carrom:
            d=act.getDrawable(R.drawable.red_card);
            break;
            case Common.pool:
                d=act.getDrawable(R.drawable.black_card);
                break;
            case Common.tt:
                d=act.getDrawable(R.drawable.orange_card);
                break;
            case Common.fussball:
                d=act.getDrawable(R.drawable.green_card);
                break;
            case Common.minibasketball:
                d=act.getDrawable(R.drawable.light_brown_card);
                break;
            case Common.golf:
                d=act.getDrawable(R.drawable.light_black);
                break;
            case Common.airhockey:
                d=act.getDrawable(R.drawable.yellow_card);
                break;
            case Common.chess:
                d=act.getDrawable(R.drawable.dark_brown_card);
                break;
            default:
                d=act.getDrawable(R.drawable.dark_brown_card);;
                break;
        }
        return d;
    }
    public static Drawable getIcon(String gameName,Activity act)
    {Drawable d=null;
        switch (gameName)

        {case Common.carrom:
            d=act.getDrawable(R.drawable.carrom_orange);
            break;
            case Common.pool:
                d=act.getDrawable(R.drawable.pool_orange);
                break;
            case Common.tt:
                d=act.getDrawable(R.drawable.tt_orange);
                break;
            case Common.fussball:
                d=act.getDrawable(R.drawable.foos_ball_orange);
                break;
            case Common.minibasketball:
                d=act.getDrawable(R.drawable.bb_ornage_icon);
                break;
            case Common.golf:
                d=act.getDrawable(R.drawable.golf_orange);
                break;
            case Common.airhockey:
                d=act.getDrawable(R.drawable.air_hockey_orange);
                break;
            case Common.chess:
                d=act.getDrawable(R.drawable.chess_orange);
                break;
            default:
                d=act.getDrawable(R.drawable.user_icon);
                break;


        }
        return d;
    }
    public static int getTextColor(String gameName,Activity act)
    {int d=-1;
        switch (gameName)

        {case Common.carrom:
            d=act.getResources().getColor(R.color.red_Carrom);
            break;
            case Common.pool:
                d=act.getResources().getColor(R.color.black_font);
                break;
            case Common.tt:
                d=act.getResources().getColor(R.color.orangecolor);
                break;
            case Common.fussball:
                d=act.getResources().getColor(R.color.greencolor);
                break;
            case Common.minibasketball:
                d=act.getResources().getColor(R.color.lightbrown);
                break;
            case Common.golf:
                d=act.getResources().getColor(R.color.light_grey);
                break;
            case Common.airhockey:
                d=act.getResources().getColor(R.color.yellow);
                break;
            case Common.chess:
                d=act.getResources().getColor(R.color.browncolor);
                break;
            default:
                d=act.getResources().getColor(R.color.browncolor);
                break;


        }
        return d;
    }
public static String getDefaultStartDate()
{
    Calendar c = Calendar.getInstance();
int month=c.get(Calendar.MONTH)+1;

    String sDate = month + "/"
            + "01"
            + "/" + c.get(Calendar.YEAR);
    return sDate;
}

    public static String getMatchDate(String date) {
       String []datee=date.split("/");
        return datee[1];
    }

    public static String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return months[month];

    }

    public static ArrayList<Calenderr> getNext15Days() {
        ArrayList<Calenderr> list = new ArrayList<>();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);
/*
* Get Date in 15 days
*/
        for (int i = 0; i < 14; i++) {
            Calenderr callend = new Calenderr(format.format(date.getTime()), dayFormat.format(date.getTime()));
            date.add(Calendar.DATE, 1);
            list.add(callend);
        }
        return list;
    }
    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return Integer.toString(year);
    }
    public static String getCurrentDate()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String s = formatter.format(date);
        return s;
    }
    public static String getCurrentDateWithoutHHMMSS()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(date);
        return s;
    }
    public static String getYesterdayDate()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
    public static String getTommorowDate()
    {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
    public static String getCurrentDateWithoutTime()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String s = formatter.format(date);
        return s;
    }

    public static Spannable getMulticolorTextView(String string, Integer[] color, Integer[] value) {
        Spannable wordtoSpan = new SpannableString(string);
        wordtoSpan.setSpan(new ForegroundColorSpan(color[0]), value[0], value[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(color[1]), value[2], value[3], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return wordtoSpan;
    }
    public static String getDateinMMDDYY(String Date) {
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyy");
        try {
            Date date = formatter.parse(Date);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String s = format.format(date);
            return s;
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String[] getDateFromString(String datee)
    {       DateFormat formatter=new SimpleDateFormat("MM/dd/yyy hh:mm:ss");
        try {
            Date date = formatter.parse(datee);
            DateFormat formatt=new SimpleDateFormat("dd/MM/yyyy");
            DateFormat time=new SimpleDateFormat("h:mm aa");
            return new String[]{formatt.format(date),time.format(date)};
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return null;
    }
    public static void makeFolder(String path, String folder) {
        File directory = new File(path, folder);
        if (directory.exists() == false) {
            directory.mkdirs();
        }
    }

    public static Dialog showPogress(Activity act) {
        final Dialog dialog = new Dialog(act);
        LottieAnimationView animationView;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loader);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        return dialog;
    }

    public static void showToast(final Activity act, final String message) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void cancelDialog(final Activity act, final Dialog dialog) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        });
    }

    public static void showDialog(final Activity act, final Dialog dialog) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    public static void startActivityCommon(final Activity act, final Intent in) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.startActivity(in);
            }
        });

    }
    public static void startActivityForResultCommon(final Activity act, final Intent in) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                act.startActivityForResult(in,1);
            }
        });

    }

    public static String getDeviceID(Activity act) {
        String deviceId = "";
        deviceId = Settings.Secure.getString(act.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static boolean getStatus(String data) {
        boolean status = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            status = jsonObject.getBoolean("Status");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }

    public static String getProfilePicUrl(Activity act, String value) {
        String url = "";
        try {
            AppController controller = (AppController) act.getApplicationContext();
            JSONObject job = new JSONObject(value);
            url = job.getString("ImageUrl");
            controller.updateProfilePic(url);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return url;
    }

    public static boolean isNextScreenReady(String data, Activity act) {
        boolean status = false;
        AppController controller = (AppController) act.getApplicationContext();
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getBoolean("Status") == true) {
                if (!jsonObject.isNull("UserId")) {
                    controller.setTempId(jsonObject.getString("UserId"));
                }
                if (!jsonObject.isNull("Result")) {
                    controller.setUserProfile(jsonObject.getJSONObject("Result"));
                }
                status = true;
            } else {
                Util.showToast(act, jsonObject.getString("Message"));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }

    public static boolean isNextScreenNeedTobeCalled(String data, Activity act) {
        boolean status = false;
        AppController controller = (AppController) act.getApplicationContext();
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getBoolean("Status") == true) {
                if (!jsonObject.isNull("UserId")) {

                    controller.setTempId(jsonObject.getString("UserId"));
                }
                if (!jsonObject.isNull("Result")) {
                    if (controller.getPrefManager().isUserLoggedIn() == true) {
                        controller.setUserProfile(jsonObject.getJSONObject("Result"));
                    }
                }
                status = true;
            } else {

                Util.showToast(act, jsonObject.getString("Message"));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }

    public static String getMessage(String value) {
        String message = "";
        try {
            JSONObject jsonObject = new JSONObject(value);
            message = jsonObject.getString("Message");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return message;
    }

    public static boolean isMobileVerified(String value, Activity act) {
        boolean status = true;
        try {
            JSONObject jsonObject = new JSONObject(value);
            status = jsonObject.isNull("IsMobileVerified") ? true : jsonObject.getBoolean("IsMobileVerified");
            if (status == false) {
                if (!jsonObject.isNull("UserId")) {
                    AppController controller = (AppController) act.getApplicationContext();
                    controller.setTempId(jsonObject.getString("UserId"));
                }
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return status;
    }


    /* * camera module popup
    *************************************/
    public static void selectImageDialog(final Activity act) {
        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle("Profile Pic");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (isDeviceSupportCamera(act)) {
                        captureImage(act);
                    } else {
                        Toast.makeText(act, "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
                    }
                } else if (items[item].equals("Choose from gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    act.startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            Profile.SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Checking device has camera hardware or not
     */
    private static boolean isDeviceSupportCamera(Activity act) {
        if (act.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static void captureImage(Activity act) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Common.imageUri = getOutputMediaFileUri(Common.MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Common.imageUri);

        // start the image capture Intent
        try {
            act.startActivityForResult(intent, Profile.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public static Uri getOutputMediaFileUri(int type) {
        File tempFile = getOutputMediaFile(type);
        Uri uri = Uri.fromFile(tempFile);
        return uri;
    }

    private static File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Common.sdCardPath);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Common.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        File files = mediaFile;
        return mediaFile;
    }

    public static byte[] getImageByteArray(File inputFile) {
        try {
            FileInputStream input = new FileInputStream(inputFile);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[65536];
            int l;

            while ((l = input.read(buffer)) > 0)
                output.write(buffer, 0, l);

            input.close();
            output.close();

            return output.toByteArray();

        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    public static int getPoints(String data) {
        int points = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getBoolean("Status") == true) {

                if (!jsonObject.isNull("Points")) {

                    points = jsonObject.getInt("Points");
                }
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return points;
    }

    public static  int getGameId(String gameName, ArrayList<GameModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGameName().equalsIgnoreCase(gameName)) {
                return list.get(i).getGameId();
            }
        }
        return 0;
    }
    public static int[] getMin_maxPoints(String gameName,ArrayList<GameModel> list)
    {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGameName().equalsIgnoreCase(gameName)) {
                return new int[]{list.get(i).getMinPoints(),list.get(i).getMaxPoints()};
            }
        }
        return null;
    }


    public static String getGameName(int id, ArrayList<GameModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getGameId() == id) {
                return list.get(i).getGameName();
            }
        }
        return "";
    }

    public static String getAppVersion(Activity activity) {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static JSONObject getResultJson(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getJSONObject("Result");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static String getCamelCase(String value) {
        return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1, value.length());
    }
    public static JSONArray getResultJsonArray(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            return jsonObject.getJSONArray("Result");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return null;
    }

    public static boolean isSessionExpired(String value) {
        boolean status = false;
        if (Util.getMessage(value).equalsIgnoreCase(Common.SessionExpired)) {
            status = true;
        }
        return status;
    }

    public static void Logout(final Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(act, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(intent);
            }

        });

    }
}
