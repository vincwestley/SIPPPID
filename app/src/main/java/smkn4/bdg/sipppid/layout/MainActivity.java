package smkn4.bdg.sipppid.layout;
//SALAH BRAD IEU DIP
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import com.paginate.Paginate;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.DAO.SearchDao;
import smkn4.bdg.sipppid.other.CheckConnection;
import smkn4.bdg.sipppid.other.DocumentAdapter;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.Static;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<Documents> documentsList;
    Call<List<Documents>> call;
    Documents documents;
    DocumentAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private String activityTitles = "Data Informasi Publik";
    private Toolbar toolbar;
    private LinearLayout maaf;
    private int var = 0;

    Call<List<SearchDao>> daoCall;
    SearchDao searchDao;

    private int noOfBtns;
    private Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btnAjukan = (Button)findViewById(R.id.btnAjukan);
        EditText inputSearch = (EditText)findViewById(R.id.action_search);
        maaf = (LinearLayout) findViewById(R.id.maaf);

        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_menu_permohonan);
                dialog.getWindow().setBackgroundDrawableResource(R.color.bgDialog);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());

                lp.width = WindowManager.LayoutParams.FILL_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                window.setAttributes(lp);

                Button kemendagri = (Button)dialog.findViewById(R.id.kemendagri);

                kemendagri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, KemendagriPengajuanActivity.class));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressDialog checkConnection = ProgressDialog.show(MainActivity.this,
                "Koneksi Terputus",
                "Menghubungkan dengan server...", true);

        if(CheckConnection.isOnline() == false){
            checkConnection.dismiss();
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
            finish();
        }

        checkConnection.dismiss();

        documentsList = new ArrayList<Documents>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        //new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");
        //new JSONAsyncTask().execute("http://www.sipppid.net/api");
        //final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        //progressDialog.setTitle("wtf");
        //progressDialog.show();

        final ProgressDialog pDialog = ProgressDialog.show(MainActivity.this,
                "Sedang Memuat",
                "Silahkan Tunggu ...", true);

        call = SIPPPApi.service(BaseUrl.BASE_URL).getDIP();
        call.enqueue(new Callback<List<Documents>>() {
            @Override
            public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                Log.d("Hasilnya ", String.valueOf(response.body()));
                documentsList.addAll(response.body());
                Log.d("HasilnyaSize ", String.valueOf(documentsList.size()));
                adapter.notifyDataSetChanged();
                pDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Execute "+ documentsList.get(0).getJudul(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Documents>> call, Throwable t) {
                pDialog.dismiss();
            }
        });


        final ListView listview = (ListView)findViewById(R.id.list);
        adapter = new DocumentAdapter(getApplicationContext(), R.layout.row, documentsList);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), actorsList.get(position).getName(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), documentsList.get(position).getId(), Toast.LENGTH_LONG).show();

                /*MenuDIPActivity dialog = new MenuDIPActivity();
                dialog.showDialog(MainActivity.this);*/
                /*startActivity(new Intent(MainActivity.this, MenuDIPActivity.class));*/
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_menu_dip);
                dialog.getWindow().setBackgroundDrawable(null);

                Button btnDetail = (Button)dialog.findViewById(R.id.btnDetail);
                Button btnBack = (Button)dialog.findViewById(R.id.btnBack);
                Button btnDownload = (Button)dialog.findViewById(R.id.btnDownload);

                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, DetailActivity.class));
                    }
                });

                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, DownloadWebViewActivity.class));
                    }
                });

                dialog.show();

                Static.Judul = documentsList.get(position).getJudul();
                Static.Kode = documentsList.get(position).getKode();
                Static.Publisher = documentsList.get(position).getPublisher();
                Static.PublishDate = documentsList.get(position).getPublishDate();
                Static.Requested = documentsList.get(position).getRequested();
                Static.Jenis = documentsList.get(position).getJenis();
                Static.Kategori = documentsList.get(position).getKategori();
                Static.Deskripsi = documentsList.get(position).getDeskripsi();
                Static.Link = documentsList.get(position).getLink();

                /*Intent i = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(i);*/
                //switch(position){
                //    case 0:
                //        Intent newActivity = new Intent(MainActivity.this, DetailActivity.class);
                //        startActivity(newActivity);
                //        break;
                }
            //}
            //@SuppressWarnings("unused")
            //public void onClick(View v){

            //};
        });



    }


    public void setView(){
        if(var == 0){
            finish();
        } else if(var == 1) {

            maaf.setVisibility(View.GONE);

            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            EditText inputSearch = (EditText)findViewById(R.id.action_search);
            maaf = (LinearLayout) findViewById(R.id.maaf);

            setSupportActionBar(toolbar);
            setToolbarTitle();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final ProgressDialog checkConnection = ProgressDialog.show(MainActivity.this,
                    "Koneksi Terputus",
                    "Menghubungkan dengan server...", true);

            if(CheckConnection.isOnline() == false){
                checkConnection.dismiss();
                Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
                finish();
            }

            checkConnection.dismiss();

            documentsList = new ArrayList<Documents>();
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
            //new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");
            //new JSONAsyncTask().execute("http://www.sipppid.net/api");
            //final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            //progressDialog.setTitle("wtf");
            //progressDialog.show();

            final ProgressDialog pDialog = ProgressDialog.show(MainActivity.this,
                    "Sedang Memuat",
                    "Silahkan Tunggu ...", true);

            call = SIPPPApi.service(BaseUrl.BASE_URL).getDIP();
            call.enqueue(new Callback<List<Documents>>() {
                @Override
                public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                    Log.d("Hasilnya ", String.valueOf(response.body()));
                    documentsList.addAll(response.body());
                    Log.d("HasilnyaSize ", String.valueOf(documentsList.size()));
                    adapter.notifyDataSetChanged();
                    pDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Execute "+ documentsList.get(0).getJudul(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<Documents>> call, Throwable t) {
                    pDialog.dismiss();
                }
            });


            final ListView listview = (ListView)findViewById(R.id.list);
            adapter = new DocumentAdapter(getApplicationContext(), R.layout.row, documentsList);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long id) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(getApplicationContext(), actorsList.get(position).getName(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), documentsList.get(position).getId(), Toast.LENGTH_LONG).show();
                    Static.Judul = documentsList.get(position).getJudul();
                    Static.Kode = documentsList.get(position).getKode();
                    Static.Publisher = documentsList.get(position).getPublisher();
                    Static.PublishDate = documentsList.get(position).getPublishDate();
                    Static.Requested = documentsList.get(position).getRequested();
                    Static.Jenis = documentsList.get(position).getJenis();
                    Static.Kategori = documentsList.get(position).getKategori();
                    Static.Deskripsi = documentsList.get(position).getDeskripsi();

                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(i);
                    //switch(position){
                    //    case 0:
                    //        Intent newActivity = new Intent(MainActivity.this, DetailActivity.class);
                    //        startActivity(newActivity);
                    //        break;
                }
                //}
                //@SuppressWarnings("unused")
                //public void onClick(View v){

                //};
            });

            var = 0;
        }
    }

    @Override
    public void onBackPressed() {
        setView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        setView();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call.clone().enqueue(new Callback<List<Documents>>() {
                    @Override
                    public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                        documentsList.clear();
                        documentsList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<List<Documents>> call, Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    //JSONObject jsono = new JSONObject(data);
                    //JSONArray jarray = jsono.getJSONArray("actors");

                    JSONArray jarray = new JSONArray(data);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Documents doc = new Documents();

                        //actor.setName(object.getString("name"));
                        //actor.setName(object.getString(""));
                        //actor.setDescription(object.getString("description"));
                        //actor.setDob(object.getString("dob"));
                        //actor.setCountry(object.getString("country"));
                        //actor.setHeight(object.getString("height"));
                        //actor.setSpouse(object.getString("spouse"));
                        //actor.setChildren(object.getString("children"));
                        //actor.setImage(object.getString("image"));

                        /*doc.setId(object.getString("Id"));
                        doc.setJudul(object.getString("Judul"));
                        doc.setPublisher(object.getString("Publisher"));
                        doc.setIdKat(object.getString("IdKat"));
                        doc.setIdJenis(object.getString("IdJenis"));
                        doc.setKode(object.getString("Kode"));
                        doc.setPublishDate(object.getString("PublishDate"));
                        doc.setRequested(object.getString("Requested"));
                        doc.setDeskripsi(object.getString("Deskripsi"));*/

                        documentsList.add(doc);
                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                maaf.setVisibility(View.GONE);
                System.out.println("input = " +searchView.getQuery());

                String text = String.valueOf(searchView.getQuery());

                call = SIPPPApi.service(BaseUrl.BASE_URL).getSearch(text);
                call.enqueue(new Callback<List<Documents>>() {
                    @Override
                    public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                        documentsList.clear();
                        Log.d("Hasilnya SEARCH", String.valueOf(response.body()));
                        Log.d("HasilnyaSize SEARCH", String.valueOf(documentsList.size()));
                        if(String.valueOf(response.body()) != null){
                            documentsList.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }

//                Toast.makeText(getApplicationContext(), "Execute "+ documentsList.get(0).getJudul(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<Documents>> call, Throwable t) {
                        var = 1;
                        documentsList.clear();
                        maaf.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                        System.out.println("masuk failure");
                    }
                });

                /*call = SIPPPApi.service(BaseUrl.BASE_URL).getSearch(text);
                daoCall.enqueue(new Callback<List<Documents>>() {
                    @Override
                    public void onResponse(Call<List<Documents>> call, Response<List<Documents>> response) {
                        Log.d("Hasil dari web ", response.message());
                        Log.d("Hasil dari web ", response.raw().toString());


                        Log.d("HASIL SEARCH", searchDao.getKode());
                    }

                    @Override
                    public void onFailure(Call<List<Documents> call, Throwable t) {

                    }
                });*/


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }


}
