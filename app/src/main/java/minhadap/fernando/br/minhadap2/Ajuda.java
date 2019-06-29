package minhadap.fernando.br.minhadap2;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ajuda extends AppCompatActivity {

    private List<Map<String, Object>> ajudas;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_ajuda);


        listView = (ListView) findViewById(R.id.listView);

        String[] de = {"imagem", "texto"};
        int[] para = {R.id.image, R.id.texto};

        SimpleAdapter adapter = new SimpleAdapter(Ajuda.this, listarAjuda(),
                R.layout.dados_ajuda, de, para);
        listView.setAdapter(adapter);
    }


    private List<Map<String, Object>> listarAjuda() {
        ajudas = new ArrayList<Map<String, Object>>();

        Map<String, Object> item = new HashMap<String, Object>();

        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.ic_edit_help);
        item.put("texto","Este ícone é responsável pelo renomeio do arquivo pdf. Esta operação deve ser feita antes da geração do pdf");
        ajudas.add(item);

        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.ic_pdf_help);
        item.put("texto", "Este ícone é responsável pela impressão do extrato de DAP em fomato .pdf, mas para que o processo ocorra bem, é necessário manter a tela com zoom reduzido no maximo, para que as informações apareçam corretamente.");
        ajudas.add(item);

        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.ic_info);
        item.put("texto", "O Extrato de Dap fica armazenado na pasta Documents com nome ExtratoDeDap.pdf, renomeie o arquivo ou copie para outra pasta, pois caso seja realizado uma nova consulta o arquivo será atualizado com os dados da nova consulta. ");
        ajudas.add(item);

        item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.ic_info);
        item.put("texto","O app Minha Dap necessita de conexão com internet e sua rapidez na consulta dependerá da velocidade da conexão. Se o sistema de consulta do MDA estiver fora do ar o App também não funcionará devido utilizar a mesma base de consulta. ");
        ajudas.add(item);

        return ajudas;

    }
}
