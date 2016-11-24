package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.LoginDao;
import smkn4.bdg.sipppid.R;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private TextView tvSignup;

    Call<List<LoginDao>> daoCall;
    LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvSignup = (TextView)findViewById(R.id.tvSignUp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences("PREF_SHARED", Context.MODE_PRIVATE);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, WebViewSignup.class));
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(txtUsername.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Masukan Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(txtPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Masukan Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                        "Sedang menyambungkan",
                        "Silahkan Tunggu ...", true);

                daoCall = SIPPPApi.service(BaseUrl.BASE_URL).getLogin(txtUsername.getText().toString(), txtPassword.getText().toString());
                daoCall.enqueue(new Callback<List<LoginDao>>() {
                    @Override
                    public void onResponse(Call<List<LoginDao>> call, Response<List<LoginDao>> response) {
                        Log.d("Hasil dari web ", response.message());
                        Log.d("Hasil dari web ", response.raw().toString());
                        if(response.body() != null){
                            loginDao = response.body().get(0);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("nama", loginDao.getNama());
                            editor.putString("memberid", loginDao.getMemberId());
                            editor.commit();

                            //SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
                            //txtUsername.setText("HASIL : " +shared.getString("nama", ""));

                            pDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MenuMainActivity.class));
                            finish();
                        }else {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Username atau Password Salah !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<LoginDao>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Username atau Password Salah !", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                });
            }//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = txtUsername.getText().toString();
//                String password = txtPassword.getText().toString();
//
//                LoginParser jParser = new LoginParser();
//                JSONObject json = jParser.getJSONFromUrl(url);
//
//                try {
//                    //JSONObject jsono = new JSONObject(data);
//                    //JSONArray jarray = jsono.getJSONArray("actors");
//
//                    //String data = EntityUtils.toString(entity);
//                    //JSONArray jarray = new JSONArray(data);
//
//                    // Getting JSON Array
//                    user = json.getJSONArray(String.valueOf(json));
//                    JSONObject c = user.getJSONObject(0);
//
//                    //JSONObject jsono = new JSONObject(data);
//                    //JSONArray jarray = jsono.getJSONArray("actors");
//
//                    JSONArray jarray = new JSONArray(data);
//
//                    // Storing  JSON item in a Variable
//                    String nama = c.getString(TAG_NAMA);
//                    String memberid = c.getString(TAG_MEMBER_ID);
//
//                    //Importing TextView
//
//                    //Set JSON Data in TextView
//                    txtUsername.setText(nama + "" + memberid);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                //HttpClient client = new DefaultHttpClient();
//
//                //HttpGet httpGet = new HttpGet("ppid.kemendagri.go.id/api/login?username=" +username+ "&password=" +password);
//
//                //HttpResponse response;
//                //try {
//                //  response = client.execute(httpGet);
//
//                //Log.d("Response of GET request", response.toString());
//                //} catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//                //e.printStackTrace();
//                //} catch (IOException e) {
//                // TODO Auto-generated catch block
//                //e.printStackTrace();
//                //}
//
//
//            }
//        });
        });
    }
}


//        private void makeGetRequest() {
//
//            HttpClient client = new DefaultHttpClient();
//            HttpGet request = new HttpGet("http://ppid.kemendagri.go.id/api/login?username=rizkyalfauji&password=gultom");
//            // replace with your url
//
//            HttpResponse response;
//            try {
//                response = client.execute(request);
//
//                String respon = response.toString();
//
//                // /Log.d("Response of GET request", response.toString());
//                System.out.println(respon);
//            } catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }

//}
