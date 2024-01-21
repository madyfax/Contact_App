package demo.contact.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import demo.contact.app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Myapp.GetVerify()) {
                    Intent i = new Intent(SplashActivity.this, MainContactActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 800);
    }
}
