package minhadap.fernando.br.minhadap2;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class Sobre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        //adsElement.setTitle("Anuncie conosco");

        View aboutPage = new AboutPage(this)
                .setDescription("O App Minha Dap foi desenvolvido para facilitar o acesso às informações referente à Declaração de Aptidão ao Pronaf (DAP), onde foi adaptado para Plataforma Android tomando como base o sistema web de consulta do Ministério do Desenvolvimento Agrário (MDA). Por meio deste se oferece mais um canal de consulta, tanto aos agricultores quanto aos profissionais que necessitam desta informação. Qualquer dúvida, reclamação, sugestão ou elogio referente ao uso do aplicativo, pode entrar em contato pelo os dados logo abaixo.")
                .isRTL(false)
                .setImage(R.drawable.farm)
                .addItem(new Element().setTitle("Versão 1.3"))
                .addItem(adsElement)
                .addGroup("Entre em contato")
                .addEmail("fernando_dev@outlook.com.br")
                .addWebsite("http://nandodev.tk")
                .addPlayStore("minhadap.fernando.br.minhadap2")
                .addGitHub("Fernando-Dev")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right),
                Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Sobre.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

}
