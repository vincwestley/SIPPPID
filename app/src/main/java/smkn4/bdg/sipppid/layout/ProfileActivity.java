package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.ProfileDao;
import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.CircleTransform;

public class ProfileActivity extends AppCompatActivity {

    Call<List<ProfileDao>> daoCall;
    ProfileDao profileDao;
    private String activityTitles = "Profil";
    private Toolbar toolbar;
    private TextView tv_name, tv_alamat, tv_email, tv_memberid, tv_kota;
    private ImageView img_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String url = "http://ppid.kemendagri.go.id/frontend/images/user/dummy.png";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_alamat = (TextView)findViewById(R.id.tv_alamat);
        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_memberid = (TextView)findViewById(R.id.tv_memberid);
        tv_kota = (TextView)findViewById(R.id.tv_kota);
        img_profile = (ImageView) findViewById(R.id.img_profile);
        final ImageView i = (ImageView) findViewById(R.id.img_profile);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        String nama = (shared.getString("nama", ""));
        String memberid = (shared.getString("memberid", ""));
        String avatar = (shared.getString("avatar", ""));
        String alamat = (shared.getString("alamat", ""));
        String kota = (shared.getString("kota", ""));
        String provinsi = (shared.getString("provinsi", ""));
        String email = (shared.getString("email", ""));

        tv_name.setText(nama);
        tv_alamat.setText(alamat);
        tv_email.setText(email);
        tv_memberid.setText(memberid);
        tv_kota.setText(kota+ ", " +provinsi);

        Glide.with(this).load("http://ppid.kemendagri.go.id/frontend/images/user/" +avatar)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_profile);

        /*final ProgressDialog pDialog = ProgressDialog.show(ProfileActivity.this,
                "Sedang Memuat",
                "Silahkan Tunggu ...", true);

        daoCall = SIPPPApi.service(BaseUrl.BASE_URL).getProfile(memberid);
        daoCall.enqueue(new Callback<List<ProfileDao>>() {
            @Override
            public void onResponse(Call<List<ProfileDao>> call, Response<List<ProfileDao>> response) {
                profileDao = response.body().get(0);
                tv_name.setText(profileDao.getNama());
                tv_alamat.setText(profileDao.getAlamat());
                tv_email.setText(profileDao.getEmail());
                tv_memberid.setText(profileDao.getMemberId());
                tv_kota.setText(profileDao.getKota()+ ", " +profileDao.getProvinsi());
                if (profileDao.getAvatar() != ""){
                    //Picasso.with(ProfileActivity.this).load("http://ppid.kemendagri.go.id/frontend/images/user/" +profileDao.getAvatar()).into(img_profile);
                    //i.setImageBitmap(DownloadFullFromUrl("http://ppid.kemendagri.go.id/frontend/images/user/" +profileDao.getAvatar()));
                    Glide.with(ProfileActivity.this).load("http://ppid.kemendagri.go.id/frontend/images/user/" +profileDao.getAvatar())
                            .crossFade()
                            .thumbnail(0.5f)
                            .bitmapTransform(new CircleTransform(ProfileActivity.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_profile);
                } else {
                    //Picasso.with(ProfileActivity.this).load("http://ppid.kemendagri.go.id/frontend/images/user/dummy.png").into(img_profile);
                    i.setImageBitmap(DownloadFullFromUrl("http://ppid.kemendagri.go.id/frontend/images/user/dummy.png"));
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ProfileDao>> call, Throwable t) {

            }
        });*/

    }

    public Bitmap DownloadFullFromUrl(String imageFullURL) {
        Bitmap bm = null;
        try {
            URL url = new URL(imageFullURL);
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            bm = BitmapFactory.decodeByteArray(baf.toByteArray(), 0,
                    baf.toByteArray().length);
        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
        }
        return bm;
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
