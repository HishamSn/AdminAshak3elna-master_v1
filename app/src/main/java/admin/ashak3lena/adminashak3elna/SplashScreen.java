package admin.ashak3lena.adminashak3elna;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import admin.ashak3lena.adminashak3elna.Auth.login;

public class SplashScreen extends AppCompatActivity {

    private final int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, login.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
