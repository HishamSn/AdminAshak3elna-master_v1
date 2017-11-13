package admin.ashak3lena.adminashak3elna.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;

import admin.ashak3lena.adminashak3elna.R;
import daboubi.khalid.faisalawe.com.sweetdialog.SweetAlertDialog;


/**
 * Created by aldaboubi on 10/13/2017.
 */

public class Dialogs {

    private int i = -1;

    SweetAlertDialog pDialog;
    Activity context;
    public Dialogs(Activity ctx){
        this.context = ctx;
        pDialog = new SweetAlertDialog(ctx);
    }

    public void ProgressDialog(){
         pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("يرجى الانتظار ...");
        pDialog.setColorTitleText(Color.parseColor("#000000"));
        pDialog.show();
        pDialog.setCancelable(false);
        new CountDownTimer(800 * 7, 800) {
            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                i++;
                switch (i){
                    case 0:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 1:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 3:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case 4:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        break;
                    case 5:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case 6:
                        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        break;
                }
            }

            public void onFinish() {
                i = -1;
                pDialog.dismiss();
            }
        }.start();
    }

    public void error_dialog(String title, String content){
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText("موافق")
                .setColorTitleText(Color.parseColor("#ea0f07"))
                .setCustomImage(R.drawable.logo_menu)
                .show();
    }

    public void error_scan_dialog(String title, String content, final Activity activity){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(content);
        sweetAlertDialog.setConfirmText("موافق");
        sweetAlertDialog.setColorTitleText(Color.parseColor("#ea0f07"));
        sweetAlertDialog.setCustomImage(R.drawable.logo_menu);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        activity.finish();
                        sweetAlertDialog.dismiss();
                    }
                });
        sweetAlertDialog.show();
    }




    public void cancelDialog(){
        pDialog.dismiss();
    }
}
