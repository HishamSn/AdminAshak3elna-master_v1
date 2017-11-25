package admin.ashak3lena.adminashak3elna.Coupon;

import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.*;

import org.json.*;

import java.util.*;
import admin.ashak3lena.adminashak3elna.utils.*;


public class ScanCoupon extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Bundle bundle;
    Context context=this;
    String uid,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "0");
        token = sharedPreferences.getString("token", "0");

        if (isPermissionCamera()) {
            getP();
        }
    }
    public void checkCouponScan(final String barcode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constatns.MAIN_API + Constatns.Check_SCAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj1Json = new JSONObject(response);

                            String msg = obj1Json.getString("msg");
                            String f = obj1Json.getString("f");

                            Dialogs dialogs = new Dialogs(ScanCoupon.this);
                            if(f.equals("0")){
                            //    dialogs.error_scan_dialog("عذرا !",msg,ScanCoupon.this);
                            }else {
                                Intent i = new Intent(ScanCoupon.this, DetailsCoupon.class);
                                i.putExtra("barcode", "164151624182");
                                startActivity(i);
                                finish();
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
                params.put("barcode", "164151624182");
                params.put("token", sharedPreferences.getString("token", "0"));
                return params;
            }
        };
        MySingleton.getInstance(ScanCoupon.this).addToRequestQueue(stringRequest);
    }

    private void getP() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(util.haveNetworkConnection(ScanCoupon.this)){
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    checkCouponScan(result.getContents().toString());
//                    checkCouponScan("319059962582");
                    Intent i = new Intent(ScanCoupon.this, DetailsCoupon.class);
                    i.putExtra("barcode", result.getContents().toString());
                    startActivity(i);
                    finish();


                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }else{
        }

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
//                    activity.finish();
//                }
//            }
//        });
//        dialog.setContentView(v);
//        dialog.show();
//        Window window_register = dialog.getWindow();
//        window_register.setLayout(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
    }

    public boolean isPermissionCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.v("kha", "Permission is granted");
                return true;
            } else {
                Log.v("kha", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("kha", "Permission is granted");
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                StartUp.getInstance().changeLanguage("ar");

                if (permission.equals(android.Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        getP();
                    }
                }
            }
        }

    }

}
