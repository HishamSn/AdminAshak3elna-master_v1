package admin.ashak3lena.adminashak3elna.utils;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;


public class StartUp extends Application {

    public static StartUp application;
    private SharedPreferences preferences;
    String LangApp="ar";


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onCreate() {
        super.onCreate();
        application = this;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(LangApp);

    }

    public static StartUp getInstance() {
        return application;
    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changeLanguage(String appLanguage) {
        appLanguage=LangApp;
        Resources res = application.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(appLanguage.toLowerCase().trim());
        conf.setLayoutDirection(new Locale(appLanguage.toLowerCase().trim()));
        res.updateConfiguration(conf, dm);
    }

    @Override
    public String getPackageName() {
        return super.getPackageName();
    }
}