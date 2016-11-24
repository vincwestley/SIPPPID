package smkn4.bdg.sipppid.layout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import smkn4.bdg.sipppid.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        final String nama = (shared.getString("nama", ""));


        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1500);
                } catch (Exception e) {

                } finally {
                    if(nama != ""){
                        Intent i = new Intent(SplashScreen.this, MenuMainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        };
        welcomeThread.start();

    }
}
