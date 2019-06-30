package minhadap.fernando.br.minhadap2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

/**
 * Created by Flavia on 27/01/2019.
 */

public class MyWebViewClient extends WebViewClient {
    private Context context;

    public MyWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        // abre a segunda tela do app para fazer a pesquisa
        //"http://smap14.mda.gov.br/extratodap/PesquisarDAP"
        if (url.equals(Constantes.SITE_PESQUISAR_DAP)) {
            Intent intent = new Intent(context, SecondActivity.class);
            intent.putExtra("URL", url);
            context.startActivity(intent);
            return true;
            // abre a terceira tela do app para ver os dados da dap que esta sendo pesquisada

        } else if (!url.isEmpty()) {
            Intent intent = new Intent(context, ThreeActivity.class);
            intent.putExtra("URL", url);
            context.startActivity(intent);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}

