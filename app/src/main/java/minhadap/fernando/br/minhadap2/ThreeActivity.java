package minhadap.fernando.br.minhadap2;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;
import com.itextpdf.xmp.impl.Utils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.itextpdf.text.BaseColor.*;

public class ThreeActivity extends AppCompatActivity {

    private WebView webView3;
    private Document document;
    private Element barra, btnExportar, nome;
    private Elements header, footer, mensagem, botaoVoltar;
    private String url;
    private String nomeAgricultor = "ExtratoDeDap";
    private File docFolder, pdfFile;
    private static final int REQUEST_TAKE_FILE = 1;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private WebSettings webSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        carregaPagina();

        try {
            PdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    // carregar a pagina

    private void carregaPagina() {
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
            btnExportar = document.select("button").first();
            btnExportar.text("Extrato");
            botaoVoltar = document.getElementsByClass("btn btn-default");
            botaoVoltar.remove();
            footer = document.getElementsByClass("footer");
            footer.remove();
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView3 = (WebView) findViewById(R.id.webview3);
        webView3.setInitialScale(1);
        webSettings = webView3.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webView3.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", null);
        webView3.setWebViewClient(new MyWebViewClient(this));
    }

    // concedendo permissao ao armazenamento externo

    private void PdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_ASK_PERMISSIONS);
                    }
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        PdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permissão negada", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // iniciando nova thread para construcao do pdf
    public void chamaPdf() {
        ConstruindoPdf construindoPdf = new ConstruindoPdf();
        construindoPdf.execute();
    }


    private class ConstruindoPdf extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog p;

        @Override
        protected void onPreExecute() {
            p = new ProgressDialog(ThreeActivity.this);
            p.setMessage("Carregando... Por favor aguarde!");
            p.setIndeterminate(false);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setCancelable(false);
            p.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                criarPdf();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            p.dismiss();
            previaPdf();
            super.onPostExecute(aVoid);
        }
    }

    // caixa de dialogo para colocar o nome do agricultor
    public String dialogoNome(){

        final Dialog dialog = new Dialog(ThreeActivity.this, R.style.DialogoSemTitulo);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle(R.string.nome_arquivo);
        dialog.setCancelable(true);
        final EditText text =  dialog.findViewById(R.id.nomeAgricultor);
        Button btnSalvar = dialog.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText().toString().isEmpty()){
                    text.setError("Campo vazio!");
                }else{
                    nomeAgricultor = text.getText().toString();
                    dialog.dismiss();
                    Toast.makeText(ThreeActivity.this, "Arquivo renomeado com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

        return nomeAgricultor;
    }

    //transforma webview em bitmap

    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);
        view.draw(canvas);
        return bitmap;
    }

    // criar o arquivo pdf
    public void criarPdf() throws IOException, DocumentException {

        Bitmap bitmap = ThreeActivity.this.screenShot(webView3);

        docFolder = new File(Environment.getExternalStorageDirectory(),
                "/Documents");
        docFolder.mkdir();

        pdfFile = new File(docFolder.getAbsolutePath(), nomeAgricultor + ".pdf");

        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document extrato = new
                com.itextpdf.text.Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(extrato, fileOutputStream);
        PageOrientation event = new PageOrientation();
        writer.setPageEvent(event);
        extrato.open();

        // tabela do arquivo inteiro
        PdfPTable imageExtrato = new PdfPTable(1);
        imageExtrato.setSplitLate(false);
        imageExtrato.setWidthPercentage(100f);

        //tabela para imagem do extrato
        PdfPTable dap = new PdfPTable(1);
        dap.setWidthPercentage(100f);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Image image = Image.getInstance(stream.toByteArray());
        image.setAlignment(Image.MIDDLE);
        image.setWidthPercentage(100f);
        dap.addCell(image);

        // adicionando tabela  imagem extrato ao arquivo inteiro
        imageExtrato.addCell(new PdfPCell(dap));


        // construindo rodape do arquivo
        PdfPTable footer = new PdfPTable(1);
        footer.setWidthPercentage(100f);
        footer.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        footer.getDefaultCell().setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        footer.getDefaultCell().setPaddingTop(10);
        Font font = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
        Paragraph frase = new Paragraph("Criado pelo App Minha Dap", font);
        frase.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        footer.addCell(frase);

        // adicionando rodape ao arquivo inteiro
        imageExtrato.addCell(new PdfPCell(footer));

        // finalizando o documento
        event.setRotation(PdfPage.PORTRAIT);
        extrato.add(imageExtrato);
        extrato.addCreationDate();
        extrato.close();
    }

    // chamada para abertura do pdf
    private void previaPdf() {
        if (pdfFile.exists()) {
            new AlertDialog.Builder(ThreeActivity.this)
                    .setCancelable(true)
                    .setIcon(R.drawable.ic_check)
                    .setTitle("Extrato de Dap")
                    .setMessage("O extrato está salvo na pasta Documents. Você deseja visualizar o extrato?")
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents";
                            File newFile = new File(path, nomeAgricultor + ".pdf");
                            Uri fileUri = FileProvider.getUriForFile(ThreeActivity.this,
                                    BuildConfig.APPLICATION_ID + ".provider", newFile);
                            intent.setType("application/pdf");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            intent.setDataAndType(fileUri, "application/pdf");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("NÃO", null)
                    .show();
        } else {
            new AlertDialog.Builder(ThreeActivity.this)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_error)
                    .setTitle("Erro!")
                    .setMessage("Desculpe!, O Extrato de Dap não foi gerado. Por favor entre em contato conosco.")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ThreeActivity.this, Sobre.class));
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_three, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.imprimir:
                chamaPdf();
                return true;
            case R.id.editar:
                dialogoNome();
                return true;
            case R.id.ajuda:
                startActivity(new Intent(ThreeActivity.this, Ajuda.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
