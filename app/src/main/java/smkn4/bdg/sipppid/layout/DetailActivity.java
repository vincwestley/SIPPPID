package smkn4.bdg.sipppid.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.other.DocumentAdapter;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.Static;

public class DetailActivity extends AppCompatActivity {
    ArrayList<Documents> documentsList;

    DocumentAdapter adapter;
    TextView tvJudul;
    TextView tvKode;
    TextView tvDeskripsi;
    TextView tvPublisher;
    TextView tvPublishDate;
    TextView tvRequested;
    TextView tvIdKat;
    TextView tvIdJenis;
    private String activityTitles = "Dokumen";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvKode = (TextView)findViewById(R.id.tvKode);
        tvJudul = (TextView)findViewById(R.id.tvJudul);
        tvDeskripsi = (TextView)findViewById(R.id.tvDeskripsi);
        tvPublisher = (TextView)findViewById(R.id.tvPublisher);
        tvPublishDate = (TextView)findViewById(R.id.tvPublishDate);
        tvRequested = (TextView)findViewById(R.id.tvRequested);
        tvIdKat= (TextView)findViewById(R.id.tvIdKat);
        tvIdJenis= (TextView)findViewById(R.id.tvIdJenis);

        tvKode.setText(Static.Kode);
        tvDeskripsi.setText(Static.Deskripsi);
        tvJudul.setText(Static.Judul + "  ");
        tvPublishDate.setText(Static.PublishDate);
        tvPublisher.setText(Static.Publisher);
        tvRequested.setText(Static.Requested);
        tvIdJenis.setText(Static.Jenis);
        tvIdKat.setText(Static.Kategori);
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
