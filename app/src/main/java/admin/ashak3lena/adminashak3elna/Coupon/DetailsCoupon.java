package admin.ashak3lena.adminashak3elna.Coupon;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.util.*;

import admin.ashak3lena.adminashak3elna.*;
import admin.ashak3lena.adminashak3elna.R;
import admin.ashak3lena.adminashak3elna.utils.*;
import daboubi.khalid.faisalawe.com.sweetdialog.*;

public class DetailsCoupon extends AppCompatActivity {
    Bundle bundle;
    String id, delaerID, barcode, startdate, enddate, points, msg, msg2, scanStartDate, scanEndDate, used;
    TextView coupon_used, txtStartDate, txtEndDate, txtPoints, txtNumCoupons, txtStatus, txtDay, txtHour, txtMinute, txtSecond;
    Button btnReActive;
    SharedPreferences sharedPreferences;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_coupon);
        StartUp.getInstance().changeLanguage("ar");
        (findViewById(R.id.details_coupon)).setVisibility(View.INVISIBLE);

        init();
        if (util.haveNetworkConnection(DetailsCoupon.this)) {
            getData();
        } else {
            util.dialogErrorInternet(DetailsCoupon.this);
        }

//        getResources().getString(R.string.mystring)
    }


    public void init() {
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        bundle = getIntent().getExtras();
        barcode = bundle.getString("barcode");

//        barcode = "959426386104";

        viewPager = (ViewPager) findViewById(R.id.ViewPagerScanCoupon);


        txtStartDate = (TextView) findViewById(R.id.date_created);
        coupon_used = (TextView) findViewById(R.id.coupon_used);
        txtEndDate = (TextView) findViewById(R.id.date_end);
        txtNumCoupons = (TextView) findViewById(R.id.number_coupon);
        txtPoints = (TextView) findViewById(R.id.points);
        txtStatus = (TextView) findViewById(R.id.coupon_status);
        btnReActive = (Button) findViewById(R.id.btnReActive);

        btnReActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (util.haveNetworkConnection(DetailsCoupon.this)) {
                    usedCoupon();
                } else {
                    util.dialogErrorInternet(DetailsCoupon.this);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Home.class);
        startActivity(i);
//        finish();
    }

    public void getData() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constatns.MAIN_API + Constatns.Check_SCAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("daboubi",response);
                            JSONObject obj1Json = new JSONObject(response);
                            msg = obj1Json.getString("msg");
                            String b = obj1Json.getString("barcode");
                            if (b.equals(barcode)) {
                                String d = obj1Json.getString("currentdate") + " " + obj1Json.getString("currenttime");
                                String f = obj1Json.getString("scanenddate") + " " + obj1Json.getString("scanendtime");
                                scanStartDate = d;
                                scanEndDate = f;
                                
                                id = obj1Json.getString("id");
                                enddate = obj1Json.getString("enddate");
                                points = obj1Json.getString("points");
                                delaerID = obj1Json.getString("did");
                                startdate = obj1Json.getString("scanenddate");
                                msg2 = obj1Json.getString("msg2");

                                txtStartDate.setText("تاريخ الاضافة : " + startdate);
                                txtEndDate.setText("تاريخ الانتهاء : " + enddate);
                                txtNumCoupons.setText("الباركود : " + barcode);
                                txtPoints.setText("عدد النقاط : " + points);
                                used = obj1Json.getString("used");

                                if (used.equals("1")) {
                                    btnReActive.setVisibility(View.GONE);
//                                    btnReActive.setEnabled(false);
//                                    btnReActive.setBackground(getResources().getDrawable(R.drawable.button_gray));
//                                    coupon_used.setText("الكوبون مستخدم");
                                    txtStatus.setText("الكوبون مستخدم");
                                    coupon_used.setText("الكوبون مستخدم");
                                } else {
                                    btnReActive.setVisibility(View.VISIBLE);
//                                    btnReActive.setEnabled(true);
//                                    btnReActive.setBackground(getResources().getDrawable(R.drawable.button_orange));
//                                    coupon_used.setText("الكوبون غير مستخدم");
                                    txtStatus.setText("الكوبون غير مستخدم");
                                    coupon_used.setText("الكوبون غير مستخدم");
                                }
                                (findViewById(R.id.details_coupon)).setVisibility(View.VISIBLE);

                                pDialog.dismiss();
                            }
                            else
                            {
                                pDialog.dismiss();
                                dialogUsedCoupon(msg,false);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("uid", sharedPreferences.getString("uid", "0"));
                params.put("barcode", barcode);
                params.put("token", sharedPreferences.getString("token", "0"));
                return params;
            }
        };
        MySingleton.getInstance(DetailsCoupon.this).addToRequestQueue(stringRequest);
    }


    public void usedCoupon() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constatns.MAIN_API + Constatns.USED_COUPON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj1Json = new JSONObject(response);
                            JSONObject aaData = obj1Json.getJSONObject("aaData");
                            String check_f = aaData.getString("f");
                            String Flag = aaData.getString("flag");

                           if (check_f.equals("1"))
                           {
                               dialogUsedCoupon(Flag,true);
                           }

                            getData();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("uid", sharedPreferences.getString("uid", "0"));
                params.put("barcode", barcode);
                params.put("token", sharedPreferences.getString("token", "0"));
                return params;
            }
        };
        MySingleton.getInstance(DetailsCoupon.this).addToRequestQueue(stringRequest);
    }


    public void dialogUsedCoupon(String Msg, final boolean back) {
        final SweetAlertDialog dialog = new SweetAlertDialog(DetailsCoupon.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setCustomImage(R.drawable.logo_menu);
        dialog.setTitleText(Msg);
        dialog.setConfirmText("موافق");
        dialog.setColorTitleText(Color.parseColor("#000000"));
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
                if (!back)
                {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }
            }
        });
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
    }

}


