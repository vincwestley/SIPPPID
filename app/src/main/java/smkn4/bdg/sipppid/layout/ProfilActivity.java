
package smkn4.bdg.sipppid.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import smkn4.bdg.sipppid.R;

public class ProfilActivity extends AppCompatActivity {

    private String activityTitles = "Profil";
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

}
