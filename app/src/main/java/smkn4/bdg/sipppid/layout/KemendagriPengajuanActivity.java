package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.VolumeProvider;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.DaoKomponen;
import smkn4.bdg.sipppid.DAO.sendDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CheckConnection;
import smkn4.bdg.sipppid.other.ServiceHandler;
import smkn4.bdg.sipppid.other.Static;

public class KemendagriPengajuanActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Spinner mSpinnerKomponen;
    private EditText txtJudul, txtDeskripsi, txtTujuan;
    private Button btnPost;

    Call<List<sendDao>> daoSend;
    sendDao sDao;

    ArrayList<String> dataKomponen = new ArrayList<>();

    private String activityTitles = "Ajukan Permohonan";
    private Toolbar toolbar;

    public KemendagriPengajuanActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kemendagri_pengajuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtJudul = (EditText)findViewById(R.id.et_judul);
        txtDeskripsi = (EditText)findViewById(R.id.et_kandungan);
        txtTujuan = (EditText)findViewById(R.id.et_tujuan);
        btnPost = (Button)findViewById(R.id.button);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtJudul.getText();
                txtDeskripsi.getText();
                txtTujuan.getText();

                SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
                final String memberid = (shared.getString("memberid", ""));

                /*System.out.println("Masuk OnCLICK");
                System.out.println(memberid);
                System.out.println("judul" +txtJudul.getText().toString());
                System.out.println("desk" +txtDeskripsi.getText().toString());
                System.out.println("tuj" +txtTujuan.getText().toString());
                System.out.println("static" +Static.kodepengajuan);*/

                if(TextUtils.isEmpty(txtJudul.getText().toString())){
                    txtJudul.setError("Masukan Judul Dokumen");
                    return;
                }

                if (TextUtils.isEmpty(txtDeskripsi.getText().toString())) {
                    txtDeskripsi.setError("Masukan Kandungan Informasi");
                    return;
                }

                if (TextUtils.isEmpty(txtTujuan.getText().toString())) {
                    txtTujuan.setError("Masukan Tujuan Penggunaan");
                    return;
                }

                progressDialog.dismiss();

                final ProgressDialog pDialog = ProgressDialog.show(KemendagriPengajuanActivity.this,
                        "Sedang Diproses",
                        "Silahkan Tunggu...", true);

                daoSend = SIPPPApi.service(BaseUrl.BASE_URL).getRespon(memberid, txtJudul.getText().toString(), Static.kodepengajuan, txtDeskripsi.getText().toString(), txtTujuan.getText().toString(), "1", "5", "0");
                daoSend.enqueue(new Callback<List<sendDao>>() {
                    @Override
                    public void onResponse(Call<List<sendDao>> call, Response<List<sendDao>> response) {
                        System.out.println("hasil : success");
                        sDao  = response.body().get(0);
                        Static.idlaporan = sDao.getReqId();

                        pDialog.dismiss();
                        startActivity(new Intent(KemendagriPengajuanActivity.this, LaporanPermohonanActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<sendDao>> call, Throwable t) {
                        System.out.println("hasil : fail");
                        progressDialog.dismiss();
                        pDialog.dismiss();
                    }
                });

                /*daoSend = SIPPPApi.service(BaseUrl.BASE_URL).getRespon(memberid, txtJudul.getText().toString(), Static.kodepengajuan, txtDeskripsi.getText().toString(), txtTujuan.getText().toString());
                startActivity(new Intent(KemendagriPengajuanActivity.this, LaporanPermohonanActivity.class));
                *//*daoSend.enqueue(new Callback<sendDao>() {
                    @Override
                    public void onResponse(Call<sendDao> call, Response<sendDao> response) {
                        startActivity(new Intent(KemendagriPengajuanActivity.this, LaporanPermohonanActivity.class));
                        sDao = response.body();
                    }

                    @Override
                    public void onFailure(Call<sendDao> call, Throwable t) {

                    }
                });*/

                /*daoSend.enqueue(new Callback<sendDao>() {
                    @Override
                    public void onResponse(Call<sendDao> call, Response<sendDao> response) {

                        System.out.println("respon " +response.body());

                        Log.d("Hasil dari web ", response.message());
                        Log.d("Hasil dari web ", response.raw().toString());
                        Log.d("Hasil dari web ", String.valueOf(response.body()));

                        if(response.body().getStatus() == 0){
                            *//*Static.idlaporan = 0;*//*
                            startActivity(new Intent(KemendagriPengajuanActivity.this, LaporanPermohonanActivity.class));
                            finish();
                        } else {
                            *//*Static.idlaporan = 1;*//*
                            System.out.println("Permohonan Diterima" +response.body().getKeterangan());
                            startActivity(new Intent(KemendagriPengajuanActivity.this, LaporanPermohonanActivity.class));
                            finish();
                        }


                    }

                    @Override
                    public void onFailure(Call<sendDao> call, Throwable t) {

                    }
                });*/
            }
        });

        mSpinnerKomponen = (Spinner) findViewById(R.id.sp_komponen);
        new BackgroundTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*final ProgressDialog checkConnection = ProgressDialog.show(KemendagriPengajuanActivity.this,
                "Koneksi Terputus",
                "Menghubungkan dengan server...", true);

        if(CheckConnection.isOnline() == false){
            checkConnection.dismiss();
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ",Toast.LENGTH_SHORT).show();
            finish();
        }

        checkConnection.dismiss();*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KemendagriPengajuanActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*String url = "http://ppid.kemendagri.go.id/api/get-component";*/
            String url = "http://ppid.kemendagri.go.id/api/get-skpd-kemendagri";
            Log.d("url", url);
            String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);

            if (reader == null) return null;
            DaoKomponen[] login = new GsonBuilder().create().fromJson(reader, DaoKomponen[].class);

            for (int i = 0; i < login.length; i++) {
                dataKomponen.add(login[i].getInstitusi());
            }

            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataKomponen != null) {
                ArrayAdapter<String> newAdapater = new ArrayAdapter<String>(KemendagriPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen);
                mSpinnerKomponen.setAdapter(newAdapater);

                mSpinnerKomponen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Object item = parent.getItemAtPosition(position);

                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                        ((TextView) parent.getChildAt(0)).setTextSize(14);

                        Object item = parent.getItemIdAtPosition(position);
                        System.out.println("Hasil" +item);

                        int pos = Integer.parseInt(String.valueOf(item));

                        String url = "http://ppid.kemendagri.go.id/api/get-skpd-kemendagri";
                        Log.d("url", url);
                        String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        DaoKomponen[] login = new GsonBuilder().create().fromJson(reader, DaoKomponen[].class);

                        Static.kodepengajuan = login[pos].getKode();
                        progressDialog .dismiss();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            progressDialog.hide();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

}
