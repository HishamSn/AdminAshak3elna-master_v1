package admin.ashak3lena.adminashak3elna.Coupon;

import android.app.ProgressDialog;
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
        getData();
        setContentView(R.layout.activity_details_coupon);
        init();
//        getResources().getString(R.string.mystring)
    }


    public void init() {
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        bundle = getIntent().getExtras();
        barcode = bundle.getString("barcode");
        Toast.makeText(this, "" + barcode, Toast.LENGTH_LONG).show();

        barcode = "495733320892";

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
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constatns.MAIN_API + Constatns.Check_SCAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1Json = new JSONObject(response);

                            String b = obj1Json.getString("barcode");
                            if (b.equals(barcode)) {
                                String d = obj1Json.getString("currentdate") + " " + obj1Json.getString("currenttime");
                                String f = obj1Json.getString("scanenddate") + " " + obj1Json.getString("scanendtime");
                                scanStartDate = d;
                                scanEndDate = f;


                                id = obj1Json.getString("id");
                                enddate = obj1Json.getString("enddate");
                                points = obj1Json.getString("points");
                                msg = obj1Json.getString("msg");
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

                                pDialog.dismiss();
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

                params.put("uid", "3");
                params.put("barcode", "992559466347");
                params.put("token", "c9f0f895fb98ab9159f51fd0297e236d");
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
                               dialogUsedCoupon(Flag);
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

                params.put("uid", "3");
                params.put("barcode", "992559466347");
                params.put("token", "c9f0f895fb98ab9159f51fd0297e236d");
                return params;
            }
        };
        MySingleton.getInstance(DetailsCoupon.this).addToRequestQueue(stringRequest);
    }


    public void dialogUsedCoupon(String Msg) {
        final SweetAlertDialog dialog = new SweetAlertDialog(DetailsCoupon.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setCustomImage(R.drawable.logo_menu);
        dialog.setContentText(Msg);
        dialog.setConfirmText("موافق");
        dialog.setColorTitleText(Color.parseColor("#000000"));
        dialog.show();
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
    }

}
