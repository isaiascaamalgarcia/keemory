package com.example.bitware.keemory;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class colors extends AppCompatActivity {

    private Button btnYes, btnNo;
    private TextView txtColor, txtWord, txtPoints;
    private static int points=0;
    ///////////////////////////////////blanco/////rojo//////aMARILLO///////VERDE////AZUL/////NEGRO
    private static String[] colorText={"#FFFFFF", "#FF0000", "#FFFF00", "#00FF00", "#0000FF", "#000000"};
    private static String[] colorWord={"Blanco", "Rojo", "Amarillo", "verde", "Azul", "Negro"};
    private static boolean isAnswer = false;
    private static int oportunities = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnYes = (Button) findViewById(R.id.bntYes);
        btnNo = (Button) findViewById(R.id.btnNo);
        txtColor = (TextView) findViewById(R.id.txtColor);
        txtWord = (TextView) findViewById(R.id.txtWord);
        txtPoints = (TextView) findViewById(R.id.textPoints);
        loadGame();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnswer){
                    points += 10;
                    Log.i("Correcto+","" +isAnswer);
                }else{
                    Log.i("Incorrecto+","" +isAnswer);
                    oportunities--;
                }
                txtPoints.setText("Puntaje: " + points);
                if(oportunities<=0){
                    btnNo.setEnabled(false);
                    btnYes.setEnabled(false);
                    GameAlertDialog gad = new GameAlertDialog();
                    gad.show(getFragmentManager(),"My alert");
                }
                loadGame();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnswer){
                    points += 10;
                    Log.i("Correcto+","" +isAnswer);
                }else {
                    Log.i("Incorrecto+","" +isAnswer);
                    oportunities--;
                }
                txtPoints.setText("Puntaje: " + points);
                if(oportunities<=0){
                    btnNo.setEnabled(false);
                    btnYes.setEnabled(false);
                    GameAlertDialog gad = new GameAlertDialog();
                    gad.show(getFragmentManager(),"My alert");
                }
                loadGame();
            }
        });

    }
    private void loadGame(){
        Random rand = new Random();
        int color = rand.nextInt(colorText.length);
        txtColor.setText(colorWord[color]);

        int mix = rand.nextInt(2);
        if(mix==1){
            txtWord.setTextColor(Color.parseColor(colorText[color]));
            isAnswer=true;
        }else {
            isAnswer=false;
            if(color == colorText.length-1) {
                txtWord.setTextColor(Color.parseColor(colorText[color - 1]));
            }else {
                txtWord.setTextColor(Color.parseColor(colorText[color + 1]));
            }
        }
        color = rand.nextInt(colorText.length);
        txtWord.setText(colorWord[color]);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_colors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
