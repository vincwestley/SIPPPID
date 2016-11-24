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
import android.widget.EditText;
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
import smkn4.bdg.sipppid.DAO.KabProvDao;
import smkn4.bdg.sipppid.DAO.ProvinsiDao;
import smkn4.bdg.sipppid.DAO.SkpdKabDao;
import smkn4.bdg.sipppid.DAO.sendDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.ServiceHandler;
import smkn4.bdg.sipppid.other.Static;

public class KabkotPengajuanActivity extends AppCompatActivity {


    private String activityTitles = "Permohonan Informasi";
    private Toolbar toolbar;
    private Spinner mSpinnerKomponen;
    private Spinner mSpinnerKomponen2;
    private Spinner mSpinnerKomponen3;
    private TextView txtJudul;
    private TextView txtDeskripsi;
    private TextView txtTujuan;
    private Button button;
    private ProgressDialog progressDialog;
    Call<List<sendDao>> daoSend;
    sendDao sDao;
    ArrayList<String> dataKomponen = new ArrayList<>();
    ArrayList<String> dataKomponen2 = new ArrayList<>();
    ArrayList<String> dataKomponen3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabkot_pengajuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbarTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtJudul = (EditText)findViewById(R.id.et_judul);
        txtDeskripsi = (EditText)findViewById(R.id.et_kandungan);
        txtTujuan = (EditText)findViewById(R.id.et_tujuan);
        Button button = (Button) findViewById(R.id.button);
        mSpinnerKomponen = (Spinner) findViewById(R.id.sp_komponen);
        mSpinnerKomponen2 = (Spinner) findViewById(R.id.sp_komponen1);
        mSpinnerKomponen3 = (Spinner) findViewById(R.id.sp_komponen2);

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

                final ProgressDialog pDialog = ProgressDialog.show(KabkotPengajuanActivity.this,
                        "Sedang Diproses",
                        "Silahkan Tunggu...", true);

                daoSend = SIPPPApi.service(BaseUrl.BASE_URL).getRespon(memberid, txtJudul.getText().toString(), Static.skpdkab, txtDeskripsi.getText().toString(), txtTujuan.getText().toString(), "3", Static.idprov, Static.idkab);
                daoSend.enqueue(new Callback<List<sendDao>>() {
                    @Override
                    public void onResponse(Call<List<sendDao>> call, Response<List<sendDao>> response) {
                        System.out.println("hasil : success");
                        sDao = response.body().get(0);
                        Static.idlaporan = sDao.getReqId();

                        pDialog.dismiss();
                        startActivity(new Intent(KabkotPengajuanActivity.this, LaporanPermohonanActivity.class));
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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setToolbarTitle() {getSupportActionBar().setTitle(activityTitles);}

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KabkotPengajuanActivity.this);
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
                ArrayAdapter<String> newAdapater = new ArrayAdapter<String>(KabkotPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen);
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
                        System.out.println("pos" +pos);

                        String url = "http://ppid.kemendagri.go.id/api/get-provinsi";
                        Log.d("url", url);
                        String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        ProvinsiDao[] login = new GsonBuilder().create().fromJson(reader, ProvinsiDao[].class);

                        Static.idprov= login[pos].getId();
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
            progressDialog = new ProgressDialog(KabkotPengajuanActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://ppid.kemendagri.go.id/api/get-kab-provinsi?idprov=" +Static.idprov;
            Log.d("url", url);
            String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);

            if (reader == null) return null;
            KabProvDao[] login = new GsonBuilder().create().fromJson(reader, KabProvDao[].class);

            dataKomponen2.clear();

            for (int i = 0; i < login.length; i++) {
                dataKomponen2.add(login[i].getKabupaten());
            }

            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataKomponen2 != null) {
                ArrayAdapter<String> newAdapater2 = new ArrayAdapter<String>(KabkotPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen2);
                mSpinnerKomponen2.setAdapter(newAdapater2);

                mSpinnerKomponen2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Object item = parent.getItemAtPosition(position)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                        ((TextView) parent.getChildAt(0)).setTextSize(14);

                        Object item = parent.getItemIdAtPosition(position);
                        System.out.println("Hasil" + item);

                        int pos = Integer.parseInt(String.valueOf(item));
                        System.out.println("pos2" +pos);

                        String url = "http://ppid.kemendagri.go.id/api/get-kab-provinsi?idprov=" +Static.idprov;
                        Log.d("url", url);
                        String reader2 = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        KabProvDao[] login2 = new GsonBuilder().create().fromJson(reader2, KabProvDao[].class);

                        Static.idkab = login2[pos].getId();
                        progressDialog.dismiss();
                        new BackgroundTask3().execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            progressDialog.hide();
        }
    }

    private class BackgroundTask3 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(KabkotPengajuanActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://ppid.kemendagri.go.id/api/get-skpd-kabupaten?idkab=" +Static.idkab;
            Log.d("url", url);
            String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);

            if (reader == null) return null;
            SkpdKabDao[] login = new GsonBuilder().create().fromJson(reader, SkpdKabDao[].class);

            dataKomponen3.clear();

            for (int i = 0; i < login.length; i++) {
                dataKomponen3.add(login[i].getInstitusi());
            }

            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            if (dataKomponen3 != null) {
                ArrayAdapter<String> newAdapater = new ArrayAdapter<String>(KabkotPengajuanActivity.this, android.R.layout.simple_spinner_item, dataKomponen3);
                mSpinnerKomponen3.setAdapter(newAdapater);

                mSpinnerKomponen3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Object item = parent.getItemAtPosition(position)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                        ((TextView) parent.getChildAt(0)).setTextSize(14);

                        Object item = parent.getItemIdAtPosition(position);
                        System.out.println("Hasil" + item);

                        int pos = Integer.parseInt(String.valueOf(item));
                        System.out.println("pos" +pos);

                        String url = "http://ppid.kemendagri.go.id/api/get-skpd-kabupaten?idkab=" +Static.idkab;
                        Log.d("url", url);
                        String reader = new ServiceHandler().makeServiceCall(url, ServiceHandler.GET);
                        SkpdKabDao[] login = new GsonBuilder().create().fromJson(reader, SkpdKabDao[].class);

                        Static.skpdkab = login[pos].getKode();
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
