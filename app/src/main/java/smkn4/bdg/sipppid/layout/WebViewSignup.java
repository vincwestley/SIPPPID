package smkn4.bdg.sipppid.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import smkn4.bdg.sipppid.R;

public class WebViewSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_signup);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://ppid.kemendagri.go.id/member/signup");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient());
    }
}
