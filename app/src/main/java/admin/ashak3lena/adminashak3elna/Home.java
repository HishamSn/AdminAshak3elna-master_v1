package admin.ashak3lena.adminashak3elna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import admin.ashak3lena.adminashak3elna.Auth.login;
import admin.ashak3lena.adminashak3elna.Coupon.*;
import admin.ashak3lena.adminashak3elna.utils.*;
import daboubi.khalid.faisalawe.com.sweetdialog.SweetAlertDialog;

public class Home extends AppCompatActivity {

    ImageButton btnLogout;
    ImageButton btnScan;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private final int TIME_OUT = 1800;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StartUp.getInstance().changeLanguage("ar");

        if (util.haveNetworkConnection(Home.this)) {

        } else {
            util.dialogErrorInternet(Home.this);
        }
        btnLogout = (ImageButton) findViewById(R.id.btnLogout);
        btnScan = (ImageButton) findViewById(R.id.btnScan);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (util.haveNetworkConnection(Home.this)) {
                    Intent i = new Intent(Home.this, ScanCoupon.class);
                    i.putExtra("from", "MyCoupons");
                    startActivity(i);
                } else {
                    util.dialogErrorInternet(Home.this);
                }
            }
        });

    }

    public void logout() {
        if (sharedPreferences.getBoolean("loggedin", false)) {

            final SweetAlertDialog dialog = new SweetAlertDialog(Home.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            dialog.setCustomImage(R.drawable.logo_menu);
            dialog.setTitleText("هل تريد تسجيل الخروج ؟");
            dialog.setColorTitleText(Color.parseColor("#000000"));
            dialog.setCancelText("إلغاء");
            dialog.setConfirmText("موافق");
            dialog.showCancelButton(true);
            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismiss();
                    dialog.dismiss();
                }
            })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                 @Override
                                                 public void onClick(SweetAlertDialog sDialog) {
                                                     sDialog = new SweetAlertDialog(Home.this, SweetAlertDialog.SUCCESS_TYPE);

                                                     SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                                     SharedPreferences.Editor editor = preferences.edit();
                                                     //Puting the value false for loggedin
                                                     editor.putBoolean("loggedin", false);
                                                     //Putting blank value to email
                                                     editor.putString("token", "");
                                                     editor.commit();
                                                     sDialog.setTitleText("تم تسجيل خروجك بنجاح");
                                                     sDialog.setContentText("سيتم تحويلك الى شاشة تسجيل الدخول");
                                                     sDialog.showCancelButton(false);
                                                     sDialog.showConfirmButton(false);
                                                     sDialog.setCancelClickListener(null);
                                                     sDialog.setConfirmClickListener(null);
                                                     sDialog.showConfirmButton(false);
                                                     sDialog.setColorTitleText(Color.parseColor("#babd16"));
                                                     sDialog.show();
                                                     sDialog.showConfirmButton(false);
                                                     final SweetAlertDialog finalSDialog = sDialog;
                                                     handler = new Handler();
                                                     runnable = new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             dialog.dismiss();
                                                             finalSDialog.dismiss();
                                                             Intent i = new Intent(Home.this, login.class);
                                                             startActivity(i);
                                                             finish();

                                                         }
                                                     };
                                                     handler.postDelayed(runnable, 2000);
                                                 }
                                             }
                    )
                    .show();


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StartUp.getInstance().changeLanguage("ar");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        StartUp.getInstance().changeLanguage("ar");
    }

    @Override
    protected void onStart() {
        super.onStart();
        StartUp.getInstance().changeLanguage("ar");
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler.removeCallbacksAndMessages(null);
        }
    }



}
