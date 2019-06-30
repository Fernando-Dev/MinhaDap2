package minhadap.fernando.br.minhadap2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {


        //customizar o plano de fundo e sua duracao na animacao
        configSplash.setBackgroundColor(R.color.colorPrimary); //qualquer do arquivo colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //duracao em milissgundos para duracao da animacao
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //ordem de direcao para inicio da animacao
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //ordem de direcao para inicio da animacao

        //customizar o logo
        configSplash.setLogoSplash(R.drawable.farm); //qualquer arquivo de drawable ou mipmap
        configSplash.setAnimLogoSplashDuration(2000); //duracao em milissgundos para duracao da animacao
        configSplash.setAnimLogoSplashTechnique(Techniques.DropOut); //definir o tipo de animacao que sera executado

        /*
        //customizar o pacote
        configSplash.setPathSplash(Constantes.DROID_LOGO); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.accent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.Wheat); //path object filling color
        */

        //customizar o titulo
        configSplash.setTitleSplash("Minha Dap");// texto do titulo
        configSplash.setTitleTextColor(R.color.Wheat);//cor o titulo
        configSplash.setTitleTextSize(30f); //tamanho da fonte em float
        configSplash.setAnimTitleDuration(3000);//duracao da animacao
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);//efeito no titulo

    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
