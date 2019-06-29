package minhadap.fernando.br.minhadap2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class FourActivity extends AppCompatActivity {

    private WebView webView4;
    private String url;
    private Document document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            url = extra.getString("URL");
        }

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView4 = (WebView) findViewById(R.id.webview4);
        webView4.setInitialScale(1);
        WebSettings webSettings = webView4.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setJavaScriptEnabled(true);
        webView4.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", null);
        webView4.setWebViewClient(new MyWebViewClient(this));
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
