package pick.com.app.uitility.session;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import pick.com.app.varient.user.pojo.RegistrationModel;

import javax.inject.Inject;

public class SessionManager {

    private static String DeviceID = "DeviceID";
    private static String LoginModel = "LoginModel";
    private static String launguage = "launguage";

    @Inject
    SharedPreferences preferences;

    public SessionManager() {
    }


    public static SharedPreferences getPrefs(Context context) {

        return context.getSharedPreferences("UserNameAcrossApplication",
                context.MODE_PRIVATE);

    }

    public  String getDeviceID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(DeviceID, DeviceID);
    }

    public  void setDeviceID(String deviceID,Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(DeviceID, deviceID).commit();
    }


    public   String getLaunguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);

        return  sharedPreferences.getString(launguage, launguage);
    }

    public  void setLaunguage(String laungua,Context context) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(launguage, laungua);
        editor.apply();
    }



    public static pick.com.app.varient.user.pojo.RegistrationModel getLoginModel(Context context) {
         if(getPrefs(context).getString(LoginModel, LoginModel)==LoginModel)
             return new RegistrationModel();
             else
             return  new Gson().fromJson( getPrefs(context).getString(LoginModel, LoginModel), pick.com.app.varient.user.pojo.RegistrationModel.class);
    }

    public static   void setLoginModel(pick.com.app.varient.user.pojo.RegistrationModel loginModel,Context context) {

        getPrefs(context).edit().putString(LoginModel, new Gson().toJson(loginModel)).commit();
    }
}

