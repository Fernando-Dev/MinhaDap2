package minhadap.fernando.br.minhadap2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private WebView webView2;
    private Document document;
    private Element barra, btnExportar,btnVisual1;
    private Elements header, footer, mensagem, botaoVoltar, btnVisual2;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            url = extra.getString("URL");
        }

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            document = Jsoup.connect(url).get();
            barra = document.getElementById("barra-brasil");
            barra.remove();
            mensagem = document.getElementsByClass("container-fluid bg-casa-civil");
            mensagem.remove();
            header = document.getElementsByClass("page-header");
            header.remove();
            btnExportar =document.getElementById("btnExport");
            btnExportar.remove();
            botaoVoltar = document.getElementsByClass("form-control btn btn-default");
            botaoVoltar.remove();
            footer = document.getElementsByClass("footer");
            footer.remove();
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView2 = (WebView) findViewById(R.id.webview2);
        webView2.setInitialScale(1);
        WebSettings webSettings = webView2.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setJavaScriptEnabled(true);
        webView2.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", null);
        webView2.setWebViewClient(new MyWebViewClient(this));
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
