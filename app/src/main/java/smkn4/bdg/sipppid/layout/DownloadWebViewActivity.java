package smkn4.bdg.sipppid.layout;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import smkn4.bdg.sipppid.R;
import smkn4.bdg.sipppid.other.Static;

import static android.R.id.progress;

public class DownloadWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().requestFeature(Window.FEATURE_PROGRESS);*/
        setContentView(R.layout.activity_download_web_view);

        /*getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);*/

        WebView myWebView = (WebView) findViewById(R.id.webview);
        /*myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                setTitle("Loading...");
                setProgress(progress * 100);

                if(progress == 100){
                    setTitle(R.string.app_name);
                }
            }
        });*/
        /*myWebView.setWebViewClient(new WebViewClient());*/
        /*myWebView.getSettings().setJavaScriptEnabled(true);*/
        String pdf = Static.Link;
        myWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" +pdf);
        System.out.println(Static.Link);
        myWebView.setWebChromeClient(new WebChromeClient());

        finish();

        /*WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);*/


        /*System.out.println("Link : " +Static.Link);*/



    }
}
