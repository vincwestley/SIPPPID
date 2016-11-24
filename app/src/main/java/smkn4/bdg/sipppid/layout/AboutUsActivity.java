package smkn4.bdg.sipppid.layout;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CircleTransform;

public class AboutUsActivity extends AppCompatActivity {

    private String activityTitles = "About Us";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        String avatar = (shared.getString("avatar", ""));

    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
