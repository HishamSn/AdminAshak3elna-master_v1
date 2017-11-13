package admin.ashak3lena.adminashak3elna.Auth;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.util.*;

import admin.ashak3lena.adminashak3elna.Home;
import admin.ashak3lena.adminashak3elna.R;
import admin.ashak3lena.adminashak3elna.utils.*;
import daboubi.khalid.faisalawe.com.sweetdialog.SweetAlertDialog;


public class login extends AppCompatActivity {

    EditText etUsername, etPass;
    Button btnLogin;
    public boolean loggedin = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loggedin = sharedPreferences.getBoolean("loggedin", false);

        if(loggedin){
            Intent i = new Intent(this, Home.class);
            startActivity(i);
            finish();
        }
    }

    public void init(){
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPass = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void login () {

        final String username = etUsername.getText().toString();
        final String password = etPass.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(username.equals("") || username.startsWith(" ") || password.equals("") || password.startsWith(" ")){
            progressDialog.dismiss();
            final SweetAlertDialog dialog = new SweetAlertDialog(login.this, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("عذرا !");
            dialog.setContentText("يرجى ادخال اسم المستخدم وكلمة المرور");
            dialog.setConfirmText("موافق");
            dialog.setColorTitleText(Color.parseColor("#ea0f07"));
            dialog.show();
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    dialog.dismiss();
                }
            });
        }else{
            final StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, Constatns.MAIN_API + Constatns.LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                Log.e("messi", response.toString());
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject jsonObject1 = jsonObject.getJSONObject("aaData");
                                String flag = jsonObject1.getString("flag");

                                int f = jsonObject1.getInt("f");
//                                Toast.makeText(login.this, String.valueOf(f), Toast.LENGTH_SHORT).show();
                                Log.e("dfgd", String.valueOf(f));

                                if(f == 1){
                                    final String token = jsonObject1.getString("token");
                                    progressDialog.dismiss();
                                    final SweetAlertDialog dialog = new SweetAlertDialog(login.this, SweetAlertDialog.SUCCESS_TYPE);
                                    dialog.setTitleText("شكرا لك");
                                    dialog.setContentText(flag);
                                    dialog.setConfirmText("موافق");
                                    dialog.setColorTitleText(Color.parseColor("#babd16"));
                                    dialog.show();
                                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            dialog.dismiss();
                                            Intent i = new Intent(login.this, Home.class);
                                            startActivity(i);
                                            editor.putBoolean("loggedin", true);
                                            editor.putString("token", token);
                                            editor.putString("uid", etUsername.getText().toString());
                                            editor.commit();
                                            finish();
                                        }
                                    });

                                }else if(f == 0){

                                    progressDialog.dismiss();
                                    final SweetAlertDialog dialog = new SweetAlertDialog(login.this, SweetAlertDialog.ERROR_TYPE);
                                    dialog.setTitleText("عذرا !");
                                    dialog.setContentText(flag);
                                    dialog.setConfirmText("موافق");
                                    dialog.setColorTitleText(Color.parseColor("#ea0f07"));
                                    dialog.show();
                                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            dialog.dismiss();
                                        }
                                    });
                                }else{
                                    progressDialog.dismiss();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();
                    params.put("uid", username);
                    params.put("password", password);
                    Log.e("uid", username);
                    Log.e("password", password);
                    return params;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }
}
