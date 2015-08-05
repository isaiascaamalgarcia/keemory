package com.example.bitware.keemory;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static int[] imgSourcesOne={R.drawable.one_silla, R.drawable.one_mesa, R.drawable.one_sofa};
    public static int[] imgSourcesTwo={ R.drawable.two_tablero, R.drawable.two_temporizador,R.drawable.two_reloj};
    public static int[] imgSourcesThree={R.drawable.three_cafe, R.drawable.three_tasa, R.drawable.three_vaso};
    public static int[] imgSourcesFour={R.drawable.four_lluvia, R.drawable.four_nube, R.drawable.four_sol};
    public static int[] imgSourcesFive={R.drawable.five_aretes, R.drawable.five_oreja, R.drawable.five_nariz};
    public static int[] imgSourcesSix={R.drawable.six_esfinge, R.drawable.six_piramide, R.drawable.six_estatua };
    public static int[] answerOne = {0,1,3};
    public static int nextTest = 0;

    /////////////Variables//////////////////////
    public static int res, n=6, m=3;
    public static int k = n;
    public static int pos = m;
    public static int[] numeros = new int[n];
    public static int[] resultado = new int[n];
    public static int[] numeros2 = new int[m];
    public static int[] respuetas = new int[m];
    public static Random rand = new Random();
    /////////////---------//////////////////////


    private ImageButton btn1, btn2, btn3;
    private boolean answer = false;
    private boolean firstCheck = false, secondCheck = false;
    public static int positionAnswerOne = 1, positionAnswerTwo = 0;
    public boolean correctOne = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btn1 = (ImageButton) findViewById(R.id.imgbtn1);
        btn2 = (ImageButton) findViewById(R.id.imgbtn2);
        btn3 = (ImageButton) findViewById(R.id.imgbtn3);

        theRandom();
        loadImages();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setEnabled(false); //El usuario no puede volver a presionar este boton
                //Verificamos si en este boton esta la respuesta correcta

                //verificamos si ya hay una respuesta correcta y complementamos la segunda
                if (firstCheck) {
                    if (positionAnswerOne == 0 || positionAnswerTwo == 0) {
                        secondCheck = true;
                    } else {
                        secondCheck = false;
                    }

                    //si ambas respuesta son correctas
                    if (correctOne && secondCheck) {
                        answer = true;
                    } else {
                        //con esto verificamos que el usuario se equivo y restablecemos los valores
                        resetGame();
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();

                    }
                    //Enviamos un mensaja si contesto bien o no
                    if (answer) {
                        Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                        resetGame();
                        loadImages();
                    } else {
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }

                } else {
                    if (positionAnswerOne == 0 || positionAnswerTwo == 0) {
                        correctOne = true;
                    }
                    firstCheck = true;
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setEnabled(false); //El usuario no puede volver a presionar este boton
                //Verificamos si ya se ha selecionado un primer elemento
                //Verificamos si en este boton esta la respuesta correcta

                //verificamos si ya hay una respuesta correcta y complementamos la segunda
                if (firstCheck) {
                    if (positionAnswerOne == 1 || positionAnswerTwo == 1) {
                        secondCheck = true;
                    } else {
                        secondCheck = false;
                    }

                    //si ambas respuesta son correctas
                    if (correctOne && secondCheck) {
                        answer = true;
                    } else {
                        //con esto verificamos que el usuario se equivo y restablecemos los valores
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }
                    //Enviamos un mensaja si contesto bien o no
                    if (answer) {
                        Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                        resetGame();
                        loadImages();
                    } else {
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }

                } else {
                    if (positionAnswerOne == 1 || positionAnswerTwo == 1) {
                        correctOne = true;
                    }
                    firstCheck =  true;
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setEnabled(false); //El usuario no puede volver a presionar este boton
                //Verificamos si en este boton esta la respuesta correcta

                //verificamos si ya hay una respuesta correcta y complementamos la segunda
                if (firstCheck) {
                    if (positionAnswerOne == 2 || positionAnswerTwo == 2) {
                        secondCheck = true;
                    } else {
                        secondCheck = false;
                    }

                    //si ambas respuesta son correctas
                    if (correctOne && secondCheck) {
                        answer = true;
                    } else {
                        //con esto verificamos que el usuario se equivo y restablecemos los valores
                        resetGame();
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();
                    }
                    //Enviamos un mensaja si contesto bien o no
                    if (answer) {
                        Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                        resetGame();
                        loadImages();
                    } else {
                        Toast.makeText(MainActivity.this, "Erroneas", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }

                } else {
                    if (positionAnswerOne == 2 || positionAnswerTwo == 2) {
                        correctOne = true;
                    }
                    firstCheck = true;
                }
            }
        });
        ////////Temporal
        Intent i = new Intent(this.getBaseContext(), colors.class);
        this.startActivity(i);
        //////////
    }
    private void theRandom() {
        numeros = new int[n];
        resultado = new int[n];
        k=n;
        rand = new Random();



        for(int i=0; i<n; i++){
            numeros[i] = i+1;
        }
        for(int i=0; i<n; i++){
            res = rand.nextInt(k);
            resultado[i] = numeros[res];
            numeros[res] = numeros[k-1];
            k--;
        }

    }
    private void loadImages() {
        //int res, n=6, m=3;
        //int k = n;
        int pos = m;
        numeros2 = new int[m];
        respuetas = new int[m];
        rand = new Random();
        for(int i=0; i<m; i++) {
            numeros2[i] = i;
        }
        for(int i=0; i<m;i++){
            res = rand.nextInt(pos);
            respuetas[i] = numeros2[res];
            numeros2[res] = numeros2[pos-1];
            pos--;
        }
        for(int i = 0; i<3;i++){
            Log.i("Valor", "" + respuetas[i]);
        }
        if(respuetas[0] == 0){
            positionAnswerOne = 0;
        }
        if(respuetas[1] == 0){
            positionAnswerOne = 1;
        }
        if(respuetas[2] == 0){
            positionAnswerOne = 2;
        }

        if(respuetas[0] == 1){
            positionAnswerTwo = 0;
        }
        if(respuetas[1] == 1){
            positionAnswerTwo = 1;
        }
        if(respuetas[2] == 1){
            positionAnswerTwo = 2;
        }

        Log.i("Respuestas", "1: " + positionAnswerOne + ":" + positionAnswerTwo);

        if(nextTest<numeros.length) {
            if (resultado[nextTest] == 1) {
                btn1.setImageResource(imgSourcesOne[respuetas[0]]);
                btn2.setImageResource(imgSourcesOne[respuetas[1]]);
                btn3.setImageResource(imgSourcesOne[respuetas[2]]);
            }
            if (resultado[nextTest] == 2) {
                btn1.setImageResource(imgSourcesTwo[respuetas[0]]);
                btn2.setImageResource(imgSourcesTwo[respuetas[1]]);
                btn3.setImageResource(imgSourcesTwo[respuetas[2]]);
            }
            if (resultado[nextTest] == 3) {
                btn1.setImageResource(imgSourcesThree[respuetas[0]]);
                btn2.setImageResource(imgSourcesThree[respuetas[1]]);
                btn3.setImageResource(imgSourcesThree[respuetas[2]]);
            }
            if (resultado[nextTest] == 4) {
                btn1.setImageResource(imgSourcesFour[respuetas[0]]);
                btn2.setImageResource(imgSourcesFour[respuetas[1]]);
                btn3.setImageResource(imgSourcesFour[respuetas[2]]);
            }
            if (resultado[nextTest] == 5) {
                btn1.setImageResource(imgSourcesFive[respuetas[0]]);
                btn2.setImageResource(imgSourcesFive[respuetas[1]]);
                btn3.setImageResource(imgSourcesFive[respuetas[2]]);
            }
            if (resultado[nextTest] == 6) {
                btn1.setImageResource(imgSourcesSix[respuetas[0]]);
                btn2.setImageResource(imgSourcesSix[respuetas[1]]);
                btn3.setImageResource(imgSourcesSix[respuetas[2]]);
            }
            nextTest++;
        } else {
            Toast.makeText(this, "Terminaste", Toast.LENGTH_SHORT).show();
            btn1.setImageResource(R.drawable.compose);
            btn2.setImageResource(R.drawable.compose);
            btn3.setImageResource(R.drawable.compose);
            nextTest=0;
            resetGame();
            Intent i = new Intent(this.getBaseContext(), remember.class);
            this.startActivity(i);
        }
    }
    private void resetGame() {
        firstCheck = false;
        secondCheck = false;
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        answer = false;
        correctOne = false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
