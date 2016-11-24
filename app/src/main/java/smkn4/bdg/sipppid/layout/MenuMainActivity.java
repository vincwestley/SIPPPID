package smkn4.bdg.sipppid.layout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import Network.BaseUrl;
import Network.SIPPPApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smkn4.bdg.sipppid.DAO.ProfileDao;

import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.fragment.HomeFragment;
import smkn4.bdg.sipppid.fragment.ProfilFragment;
import smkn4.bdg.sipppid.fragment.PermohonanFragment;
import smkn4.bdg.sipppid.fragment.MyPermohonan;
import smkn4.bdg.sipppid.fragment.KeberatanFragment;
import smkn4.bdg.sipppid.fragment.AjukanKeberatanFragment;
import smkn4.bdg.sipppid.other.CheckConnection;
import smkn4.bdg.sipppid.other.CircleTransform;

public class MenuMainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName;
    private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image

    private static final String urlNavHeaderBg = "http://3.bp.blogspot.com/-Uci0oObYam0/WAREwuz6ySI/AAAAAAAAENo/diTUwU_EpAUf-FbrrsdYa9_3U1awxphWACK4B/s1600/nav-menu-header-bg.jpg";
    private static String urlProfileImg;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFIL = "profile";
    private static final String TAG_APERMOHONAN = "apermohonan";
    private static final String TAG_PERMOHONAN = "permohonan";
    private static final String TAG_AKEBERATAN= "akeberatan";
    private static final String TAG_KEBERATAN= "keberatan";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;

    private Handler mHandler;
    Call<List<ProfileDao>> daoCall;
    ProfileDao profileDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        final String memberid = (shared.getString("memberid", ""));

        System.out.println("koneksi" +CheckConnection.isOnline());

        if(CheckConnection.isOnline() == false){
            Toast.makeText(this, "Tidak ada koneksi, cek koneksi anda kembali! ", Toast.LENGTH_SHORT).show();
        }

        daoCall = SIPPPApi.service(BaseUrl.BASE_URL).getProfile(memberid);
        daoCall.enqueue(new Callback<List<ProfileDao>>() {
            @Override
            public void onResponse(Call<List<ProfileDao>> call, Response<List<ProfileDao>> response) {
                if(response.body() != null){
                    profileDao = response.body().get(0);

                    SharedPreferences sharedPreferences = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("avatar", profileDao.getAvatar());
                    editor.putString("alamat", profileDao.getAlamat());
                    editor.putString("kota", profileDao.getKota());
                    editor.putString("provinsi", profileDao.getProvinsi());
                    editor.putString("email", profileDao.getEmail());

                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<List<ProfileDao>> call, Throwable t) {

            }
        });

        String avatar = (shared.getString("avatar", ""));

        if(avatar != null){
            urlProfileImg = "http://ppid.kemendagri.go.id/frontend/images/user/" +avatar;
        } else {
            urlProfileImg = "http://ppid.kemendagri.go.id/frontend/images/user/dummy.png";
        }


        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menux
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        SharedPreferences shared = getSharedPreferences("PREF_SHARED", MODE_PRIVATE);
        final String nama = (shared.getString("nama", ""));
        txtName.setText(nama);

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        /*navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);*/
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        navItemIndex = 0;

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // profile
                ProfilFragment profilFragment = new ProfilFragment();
                return profilFragment;
            case 2:
                // ajukan permohonan
                HomeFragment aPermohonan = new HomeFragment();
                return aPermohonan;
            case 3:
                //permohonan
                MyPermohonan permohonan = new MyPermohonan();
                return permohonan;
            case 4:
                // ajukan keberatan
                AjukanKeberatanFragment aKeberatan= new AjukanKeberatanFragment();
                return aKeberatan;

            case 5:
                //Keberatan
                KeberatanFragment keberatan = new KeberatanFragment();
                return keberatan;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        if(navItemIndex == 2){

        } else {
            navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_beranda:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(MenuMainActivity.this, ProfileActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_apermohonan:
                        /*navItemIndex = 2;
                        CURRENT_TAG = TAG_APERMOHONAN;
                        break;*/
                        /*startActivity(new Intent(MenuMainActivity.this, MenuPermohonanActivity.class));*/

                        final Dialog dialog = new Dialog(MenuMainActivity.this);
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
                                startActivity(new Intent(MenuMainActivity.this, KemendagriPengajuanActivity.class));
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_permohonan:
                        startActivity(new Intent(MenuMainActivity.this, PermohonanActivity.class));
                        drawer.closeDrawers();
                        return true;
                    /*case R.id.nav_akeberatan:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_AKEBERATAN;
                        break;
                        *//*navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        loadHomeFragment();
                        break;*/
                    case R.id.nav_keberatan:
                        /*navItemIndex = 5;
                        CURRENT_TAG = TAG_KEBERATAN;*/
                        startActivity(new Intent(MenuMainActivity.this, KeberatanActivity.class));
                        drawer.closeDrawers();
                        return true;
                        // launch new intent instead of loading fragment
                        /*startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;*/
                    case R.id.nav_logout:
                        // launch new intent instead of loading fragment
                        SharedPreferences sharedPreferences;
                        sharedPreferences = getSharedPreferences("PREF_SHARED", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("nama", "");
                        editor.putString("memberid", "");
                        editor.commit();

                        startActivity(new Intent(MenuMainActivity.this, LoginActivity.class));
                        finish();
                        //drawer.closeDrawers();

                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
               /* if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);*/

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        /*if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_faq) {
            startActivity(new Intent(MenuMainActivity.this, WebViewActivity.class));
        } else if (id == R.id.action_about_us){
            startActivity(new Intent(MenuMainActivity.this, AboutUsActivity.class));
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        /*if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }*/

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    /*private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/


}
