package admin.ashak3lena.adminashak3elna.Coupon;

import android.content.*;
import android.graphics.Color;
import android.support.v4.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.*;
import admin.ashak3lena.adminashak3elna.*;
import admin.ashak3lena.adminashak3elna.utils.*;
import daboubi.khalid.faisalawe.com.sweetdialog.SweetAlertDialog;

public class DetailsCoupon extends AppCompatActivity {
    Bundle bundle;
    String id,delaerID, barcode, startdate, enddate, points, msg, msg2, scanStartDate, scanEndDate, used;
    TextView coupon_used,txtStartDate, txtEndDate, txtPoints, txtNumCoupons, txtStatus, txtDay, txtHour, txtMinute, txtSecond;
    Button btnReActive;
    SharedPreferences sharedPreferences;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_coupon);
        init();
//        getResources().getString(R.string.mystring)
    }


    public void init(){

        bundle = getIntent().getExtras();
      //  barcode =bundle.getString("barcode");
        barcode ="495733320892";

        viewPager = (ViewPager) findViewById(R.id.ViewPagerScanCoupon);

        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

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
                if (util.haveNetworkConnection(DetailsCoupon.this)){
                    getData();
                }else{
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constatns.MAIN_API + Constatns.USED_COUPON + "?uid=" + sharedPreferences.getString("userid", "0")  +"&barcode=" +barcode+"&token=" +sharedPreferences.getString("token", "0"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("aaData");
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject js =jsonArray.getJSONObject(i);
                                String b = js.getString("barcode");
                                if(b.equals(barcode)){
                                    String d = js.getString("currentdate") + " " + js.getString("currenttime");
                                    String f = js.getString("scanenddate") + " " + js.getString("scanendtime");
                                    scanStartDate = d;
                                    scanEndDate = f;

                                    id = js.getString("id");
                                    enddate = js.getString("enddate");
                                    points = js.getString("points");
                                    msg = js.getString("msg");
                                    delaerID =js.getString("did");
                                    startdate= js.getString("scanenddate");
                                    msg2 = js.getString("msg2");

                                    txtStartDate.setText("تاريخ الاضافة : "+startdate);
                                    txtEndDate.setText("تاريخ الانتهاء : "+enddate);
                                    txtNumCoupons.setText("الباركود : "+barcode);
                                    txtPoints.setText("عدد النقاط : "+points);
                                    used = js.getString("used");


                                    if(used.equals("1")){
                                        btnReActive.setEnabled(false);
                                        coupon_used.setText("الكوبون مستخدم");
                                        txtStatus.setText(msg2);
                                    }else{
                                        btnReActive.setEnabled(true);
                                        coupon_used.setText("الكوبون غير مستخدم");
                                        txtStatus.setText(msg2);
                                    }
                                    dialogUsedCoupon();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void dialogUsedCoupon()
    {
        final SweetAlertDialog dialog = new SweetAlertDialog(DetailsCoupon.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setCustomImage(R.drawable.logo_menu);
        dialog.setContentText("اصبح الان الكوبون مستخدما");
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
