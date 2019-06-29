package minhadap.fernando.br.minhadap2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        inicioApp();
    }
    private void inicioApp(){
        new Abertura().execute();
    }
    private class Abertura extends AsyncTask <String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "página carregada...";
        }

        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra("data", s);
            startActivity(intent);
            finish();
        }
    }
}
