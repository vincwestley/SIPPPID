package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.DaoKomponen;
import smkn4.bdg.sipppid.DAO.ProvinsiDao;
import smkn4.bdg.sipppid.DAO.SkpdProvDao;
import smkn4.bdg.sipppid.DAO.sendDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.ServiceHandler;
import smkn4.bdg.sipppid.other.Static;

public class ProvinsiPengajuanActivity extends AppCompatActivity {

    private Spinner mSpinnerKomponen;
    private Button button;
    private Spinner mSpinnerKomponen2;
    ArrayList<String> dataKomponen = new ArrayList<>();
    ArrayList<String> dataKomponen2 = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String activityTitles = "Permohonan Informasi";
    private Toolbar toolbar;
    private TextView txtJudul;
    private TextView txtDeskripsi;
    private TextView txtTujuan;
    Call<List<sendDao>> daoSend;
    sendDao sDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provinsi_pengajuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button = (Button) findViewById(R.id.button);
        txtJudul = (TextView) findViewById(R.id.et_judul);
        txtDeskripsi = (TextView) findViewById(R.id.et_kandungan);
        txtTujuan = (TextView) findViewById(R.id.et_tujuan);
        mSpinnerKomponen = (Spinner) findViewById(R.id.sp_komponen);
        mSpinnerKomponen2 = (Spinner) findViewById(R.id.sp_komponen1);

        button.setOnClickListener(new View.OnClickListener() {
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

                if (TextUtils.isEmpty(txtJudul.getText().toString())) {
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

                final ProgressDialog pDialog = ProgressDialog.show(ProvinsiPengajuanActivity.this,
                        "Sedang Diproses",
                        "Silahkan Tunggu...", true);

                daoSend = SIPPPApi.service(BaseUrl.BASE_URL).getRespon(memberid, txtJudul.getText().toString(), Static.skpdprov, txtDeskripsi.getText().toString(), txtTujuan.getText().toString(), "2", Static.idprov, "0");
                daoSend.enqueue(new Callback<List<sendDao>>() {
                    @Override
                    public void onResponse(Call<List<sendDao>> call, Response<List<sendDao>> response) {
                        System.out.println("hasil : success");
                        sDao = response.body().get(0);
                        Static.idlaporan = sDao.getReqId();

                        pDialog.dismiss();
                        startActivity(new Intent(ProvinsiPengajuanActivity.this, LaporanPermohonanActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<sendDao>> call, Throwable t) {
                        System.out.println("hasil : fail");
                        progressDialog.dismiss();
                        pDialog.dismiss();
                    }
                });

            }
        });
        new BackgroundTask().execute();
    }
        @Override
        public boolean onSupportNavigateUp () {
            finish();
            return true;
        }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProvinsiPengajuanActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://ppid.kemendagri.go.id/api/get-provinsi";
            Log.d("url", url);
            String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);

            if (reader == null) return null;
            ProvinsiDao[] login = new GsonBuilder().create().fromJson(reader, ProvinsiDao[].class);

            dataKomponen.clear();

            for (int i = 0; i < login.length; i++) {
                dataKomponen.add(login[i].getProvinsi());
            }

            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataKomponen != null) {
                ArrayAdapter<String> newAdapater = new ArrayAdapter<String>(ProvinsiPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen);
                mSpinnerKomponen.setAdapter(newAdapater);

                mSpinnerKomponen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Object item = parent.getItemAtPosition(position)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                        ((TextView) parent.getChildAt(0)).setTextSize(14);

                        Object item = parent.getItemIdAtPosition(position);
                        System.out.println("Hasil" + item);

                        int pos = Integer.parseInt(String.valueOf(item));
                        System.out.println("pos" + pos);

                        String url = "http://ppid.kemendagri.go.id/api/get-provinsi";
                        Log.d("url", url);
                        String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        ProvinsiDao[] login = new GsonBuilder().create().fromJson(reader, ProvinsiDao[].class);

                        Static.idprov = login[pos].getId();
                        progressDialog.dismiss();
                        new BackgroundTask2().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            progressDialog.hide();
        }
    }

    private class BackgroundTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProvinsiPengajuanActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://ppid.kemendagri.go.id/api/get-skpd-provinsi?idprov=" + Static.idprov;
            Log.d("url", url);
            String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);

            if (reader == null) return null;
            SkpdProvDao[] login = new GsonBuilder().create().fromJson(reader, SkpdProvDao[].class);

            dataKomponen2.clear();

            for (int i = 0; i < login.length; i++) {
                dataKomponen2.add(login[i].getInstitusi());
            }


            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataKomponen2 != null) {
                ArrayAdapter<String> newAdapater2 = new ArrayAdapter<String>(ProvinsiPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen2);
                mSpinnerKomponen2.setAdapter(newAdapater2);

                mSpinnerKomponen2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Object item = parent.getItemAtPosition(position)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                        ((TextView) parent.getChildAt(0)).setTextSize(14);

                        Object item = parent.getItemIdAtPosition(position);

                        int pos = Integer.parseInt(String.valueOf(item));

                        String url = "http://ppid.kemendagri.go.id/api/get-skpd-provinsi?idprov=" + Static.idprov;
                        Log.d("url", url);
                        String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        SkpdProvDao[] login = new GsonBuilder().create().fromJson(reader, SkpdProvDao[].class);

                        Static.skpdprov = login[pos].getKode();
                        System.out.println("SKPD" +Static.skpdprov);
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            progressDialog.hide();
        }
    }

}