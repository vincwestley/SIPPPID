package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.KeberatanDao;
import smkn4.bdg.sipppid.DAO.PermohonanDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CheckConnection;
import smkn4.bdg.sipppid.other.KeberatanAdapter;
import smkn4.bdg.sipppid.other.PermohonanAdapter;

public class KeberatanActivity extends AppCompatActivity {

    private ArrayList<KeberatanDao> keberatanList;
    private Call<List<KeberatanDao>> call;
    private KeberatanDao permohonan;
    SwipeRefreshLayout swipeRefreshLayout;
    KeberatanAdapter adapter;
    private Toolbar toolbar;
    private String activityTitles = "Keberatan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keberatan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog checkConnection = ProgressDialog.show(KeberatanActivity.this,
                "Koneksi Terputus",
                "Menghubungkan dengan server...", true);

        if(CheckConnection.isOnline() == false){
            checkConnection.dismiss();
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
            finish();
        }

        checkConnection.dismiss();

        keberatanList = new ArrayList<KeberatanDao>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        final String memberid = (shared.getString("memberid", ""));



        final ProgressDialog pDialog = ProgressDialog.show(this,
                "Sedang Memuat",
                "Silahkan Tunggu...", true);

        call = SIPPPApi.service(BaseUrl.BASE_URL).getKeberatan(memberid);
        call.enqueue(new Callback<List<KeberatanDao>>() {
            @Override
            public void onResponse(Call<List<KeberatanDao>> call, Response<List<KeberatanDao>> response) {
                Log.d("Hasilnya ", String.valueOf(response.body()));
                keberatanList.addAll(response.body());
                Log.d("HasilnyaSize ", String.valueOf(keberatanList.size()));
                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<KeberatanDao>> call, Throwable t) {
                pDialog.dismiss();
            }
        });

        final ListView listview = (ListView)findViewById(R.id.list);
        adapter = new KeberatanAdapter(getApplicationContext(), R.layout.row_keberatan, keberatanList);
        listview.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

    @Override
    protected void onStart() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call.clone().enqueue(new Callback<List<KeberatanDao>>() {
                    @Override
                    public void onResponse(Call<List<KeberatanDao>> call, Response<List<KeberatanDao>> response) {
                        keberatanList.clear();
                        keberatanList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<KeberatanDao>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

        super.onStart();
    }
}
