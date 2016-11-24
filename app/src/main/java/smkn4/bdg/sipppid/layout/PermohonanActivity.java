package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.DAO.PermohonanDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CheckConnection;
import smkn4.bdg.sipppid.other.DocumentAdapter;
import smkn4.bdg.sipppid.other.PermohonanAdapter;

public class PermohonanActivity extends AppCompatActivity {

    private ArrayList<PermohonanDao> permohonanList;
    private Call<List<PermohonanDao>> call;
    private PermohonanDao permohonan;
    SwipeRefreshLayout swipeRefreshLayout;
    PermohonanAdapter adapter;
    private Toolbar toolbar;
    private String activityTitles = "Permohonan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permohonan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog checkConnection = ProgressDialog.show(PermohonanActivity.this,
                "Koneksi Terputus",
                "Menghubungkan dengan server...", true);

        if(CheckConnection.isOnline() == false){
            checkConnection.dismiss();
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
            finish();
        }

        checkConnection.dismiss();

        permohonanList  = new ArrayList<PermohonanDao>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        final String memberid = (shared.getString("memberid", ""));

        final ProgressDialog pDialog = ProgressDialog.show(PermohonanActivity.this,
                "Sedang Memuat",
                "Silahkan Tunggu ...", true);

        call =  SIPPPApi.service(BaseUrl.BASE_URL).getRequest(memberid);
        call.enqueue(new Callback<List<PermohonanDao>>() {
            @Override
            public void onResponse(Call<List<PermohonanDao>> call, Response<List<PermohonanDao>> response) {
                    Log.d("Hasilnya ", String.valueOf(response.body()));
                    permohonanList.addAll(response.body());
                    Log.d("HasilnyaSize ", String.valueOf(permohonanList.size()));
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PermohonanDao>> call, Throwable t) {
                pDialog.dismiss();
            }
        });

        pDialog.dismiss();

        final ListView listview = (ListView)findViewById(R.id.list);
        adapter = new PermohonanAdapter(getApplicationContext(), R.layout.row_permohonan, permohonanList);
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
        super.onStart();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call.clone().enqueue(new Callback<List<PermohonanDao>>() {
                    @Override
                    public void onResponse(Call<List<PermohonanDao>> call, Response<List<PermohonanDao>> response) {
                        permohonanList.clear();
                        permohonanList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<PermohonanDao>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

    }
}
