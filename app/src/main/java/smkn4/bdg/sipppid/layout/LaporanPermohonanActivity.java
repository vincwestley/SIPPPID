package smkn4.bdg.sipppid.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.Static;

public class LaporanPermohonanActivity extends AppCompatActivity {


    private String activityTitles = "Permohonan Informasi";
    private Toolbar toolbar;
    private TextView idlaporan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_permohonan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        idlaporan = (TextView)findViewById(R.id.idlaporan);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String hasil = Static.idlaporan;

        idlaporan.setText("Dengan Nomor Laporan " +hasil);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

}
