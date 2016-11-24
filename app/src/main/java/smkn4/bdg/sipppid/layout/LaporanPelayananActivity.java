package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.LaporanDao;
import smkn4.bdg.sipppid.DAO.LoginDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CheckConnection;

public class LaporanPelayananActivity extends AppCompatActivity {

    Call<List<LaporanDao>> daoCall;
    LaporanDao laporanDao;
    private String activityTitles = "Laporan Pelayanan";
    private Toolbar toolbar;
    private TextView tvJumlahDokumen, tvJumlahPermohonan, tvJumlahPengunjung, tvJumlahDokumenUnduh;

    //#273540

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pelayanan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvJumlahDokumen = (TextView)findViewById(R.id.tvJumlahDokumen);
        tvJumlahPermohonan = (TextView)findViewById(R.id.tvJumlahPermohonan);
        tvJumlahPengunjung = (TextView)findViewById(R.id.tvJumlahPengunjung);
        tvJumlahDokumenUnduh = (TextView)findViewById(R.id.tvJumlahDokumenUnduh);

        final ProgressDialog pDialog = ProgressDialog.show(LaporanPelayananActivity.this,
                "Sedang Memuat",
                "Silahkan Tunggu ...", true);

        daoCall = SIPPPApi.service(BaseUrl.BASE_URL).getLaporan();
        daoCall.enqueue(new Callback<List<LaporanDao>>() {
            @Override
            public void onResponse(Call<List<LaporanDao>> call, Response<List<LaporanDao>> response) {
                laporanDao = response.body().get(0);

                tvJumlahDokumen.setText(laporanDao.getDokumen());
                tvJumlahDokumen.setVisibility(View.VISIBLE);

                tvJumlahPermohonan.setText(laporanDao.getPermohonan());
                tvJumlahPermohonan.setVisibility(View.VISIBLE);

                tvJumlahPengunjung.setText(laporanDao.getPengunjung());
                tvJumlahPengunjung.setVisibility(View.VISIBLE);

                tvJumlahDokumenUnduh.setText(laporanDao.getDownload());
                tvJumlahDokumenUnduh.setVisibility(View.VISIBLE);

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<LaporanDao>> call, Throwable t) {
                pDialog.dismiss();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog checkConnection = ProgressDialog.show(LaporanPelayananActivity.this,
                "Koneksi Terputus",
                "Menghubungkan dengan server...", true);

        if(CheckConnection.isOnline() == false){
            checkConnection.dismiss();
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
        }

        checkConnection.dismiss();

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
