package minhadap.fernando.br.minhadap2;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Flavia on 27/01/2019.
 */

public class MyWebViewClient extends WebViewClient {
    private Context context;

    public MyWebViewClient(Context context){
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.equals("http://smap14.mda.gov.br/extratodap/PesquisarDAP")){
            Intent intent = new Intent(context,SecondActivity.class);
            intent.putExtra("URL", url);
            context.startActivity(intent);
            return true;
        }else if(url.equals("http://smap14.mda.gov.br/extratodap/PesquisarDAP/ExtratoDapPF?Token=Y3BmPTQzODEwNzQ4NDA0Jk51bWVyb0RBUD0mdGlwbz1GaXNpY2E=")){
            Intent intent = new Intent(context, ThreeActivity.class);
            intent.putExtra("URL",url);
            context.startActivity(intent);
            return true;
        }else if (url.equals("http://smap14.mda.gov.br/extratodap/PesquisarDAP/Visualizar?Token=Y3BmPW51bGwmbnVtZXJvREFQPW51bGwmdXN1YXJpbyZjaGF2ZT01NzY3MjY0MDE3MTc3NTU4JnRpcG89RmlzaWNh")){
            Intent intent = new Intent(context, FourActivity.class);
            intent.putExtra("URL", url);
            context.startActivity(intent);
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
