package admin.ashak3lena.adminashak3elna.utils;

import android.app.*;
import android.content.Context;
import android.content.res.*;
import android.graphics.Typeface;
import android.net.*;

import android.util.*;

import java.util.*;



/**
 * Created by aldaboubi on 10/9/2017.
 */

public class util {



    public static void setLocale(Context ctx, String lang) { //call this in onCreate()
        Locale myLocale = new Locale(lang);
        Resources res = ctx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        //Intent refresh = new Intent(this, AndroidLocalize.class);
        //startActivity(refresh);
        //finish();
    }

    public static Typeface changeFont(Activity activity){
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "HacenTunisia.ttf");
        return typeface;
    }



    public static boolean haveNetworkConnection(Activity activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }

        final boolean value = haveConnectedWifi || haveConnectedMobile;

        return value;
    }

    public static void dialogErrorInternet(final Activity activity){
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View v = inflater.inflate(R.layout.check_internet_connection, null);
//
//        GifImageView gifImageView = v.findViewById(R.id.gif_internet);
//        TextView txtInternet = (TextView) v.findViewById(R.id.txtinternet);
//        Button btnRetry = (Button) v.findViewById(R.id.btnRetry);
//
//        txtInternet.setTypeface(util.changeFont(activity));
//        btnRetry.setTypeface(util.changeFont(activity));
//
//        final Dialog dialog = new Dialog(activity, R.style.DialogThemeWhite);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        btnRetry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(util.haveNetworkConnection(activity)){
//                    dialog.dismiss();
//                }
//            }
//        });
//        dialog.setContentView(v);
//        dialog.show();
//        Window window_register = dialog.getWindow();
//        window_register.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
    }

    private static final String LANGUAGE_APP = "LANGUAGE_APP";

    public static String getLanguageApplication() {
        return StartUp.getInstance().getPreferences().getString(LANGUAGE_APP, "ar");
    }


}
