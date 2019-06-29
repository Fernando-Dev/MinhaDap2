package minhadap.fernando.br.minhadap2;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private String url = "http://smap14.mda.gov.br/extratodap/";
    private Document document;
    private Element barra;
    private Elements header;
    private Elements footer;
    private Elements mensagem;
    private Elements btnMain;
    private boolean duploClique = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carregaPagina();

    }

    private void carregaPagina() {
        if (!StatusInternet.getInstance(getApplicationContext()).isOnline()) {
            setContentView(R.layout.activity_main_sem_internet);
        } else {
            setContentView(R.layout.activity_main);

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
                btnMain = document.getElementsByClass("form-control btn btn-success");
                btnMain.attr("style", "margin-bottom:20px;");
                footer = document.getElementsByClass("footer");
                footer.remove();
            } catch (IOException e) {
                e.printStackTrace();
            }
            webView = (WebView) findViewById(R.id.webview);
            progressBar = (ProgressBar) findViewById(R.id.progessBar);
            webView.setVisibility(View.INVISIBLE);
            webView.setInitialScale(1);
            WebSettings webSettings = webView.getSettings();
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            webSettings.setJavaScriptEnabled(true);
            if (document.toString() == null) {
                setContentView(R.layout.sistema_fora_ar);
            } else {
                webView.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", null);
                webView.setWebViewClient(new MyWebViewClient(this) {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        progressBar.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        super.onPageFinished(view, url);
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atualizar:
                carregaPagina();
                return true;
            case R.id.ajuda:
                startActivity(new Intent(MainActivity.this, Ajuda.class));
                return true;
            case R.id.sobre:
                startActivity(new Intent(this, Sobre.class));
                return true;
            case R.id.sair:
                finish();
                return true;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (duploClique) {
            super.onBackPressed();
            return;
        }
        this.duploClique = true;
        Toast.makeText(getBaseContext(), "Pressione novamente para sair!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                duploClique = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
