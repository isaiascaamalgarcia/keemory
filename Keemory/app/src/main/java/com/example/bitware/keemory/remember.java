package com.example.bitware.keemory;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class remember extends AppCompatActivity {
    private static int[] form = {R.drawable.form_circle_blue, R.drawable.form_circle_orange, R.drawable.form_cruz_blue, R.drawable.form_cruz_orange,
                                 R.drawable.form_pentagon_blue, R.drawable.form_pentagon_orange, R.drawable.form_rhombus_blue, R.drawable.form_rhombus_orange,
                                 R.drawable.form_square_blue, R.drawable.form_square_orange, R.drawable.form_star_blue, R.drawable.form_star_orange};
    private static Random rand = new Random();
    private static int afterNow = 0;
    private ImageView imageView;
    private Button btnYes, btnNo;
    private TextView textView,tvLifes;
    private static boolean isAnswer;
    private static AlphaAnimation fadeIn = new AlphaAnimation(0.0f,1.0f);
    private static final int DURATION = 500;
    private static final int AFTER_TIME = 100;

    private static int points=0;
    private static int bonus =0;
    private static int lifes = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        imageView = (ImageView) findViewById(R.id.imgViewResult);
        btnYes = (Button) findViewById(R.id.buttonYes);
        btnNo  = (Button) findViewById(R.id.buttonNo);
        textView = (TextView) findViewById(R.id.textResult);
        tvLifes = (TextView) findViewById(R.id.txtViewLife);


        fadeIn.setDuration(DURATION);
        fadeIn.setStartOffset(AFTER_TIME);
        fadeIn.setFillAfter(true);

        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f,0.0f);
        fadeOut.setDuration(DURATION);
        fadeOut.setStartOffset(AFTER_TIME);
        fadeOut.setFillAfter(true);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        loadImage();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnswer){
                    points+= 10;
                    textView.setText("Puntaje: " + points);
                }else {
                    points-=20;
                    textView.setText("Puntaje: " + points);
                    lifes--;
                    if (lifes==0) {
                        btnNo.setEnabled(false);
                        btnYes.setEnabled(false);
                        GameAlertDialog myAlert = new GameAlertDialog();
                        myAlert.show(getFragmentManager(), "My alert");
                    }
                }
                tvLifes.setText("Vidas restantes" + lifes);
                loadImage();
                imageView.startAnimation(fadeIn);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAnswer){
                    points+= 10;
                    textView.setText("Puntaje: " + points);
                }else {
                    points-=20;
                    textView.setText("Puntaje: " + points);
                    lifes--;
                    if (lifes == 0){
                        btnNo.setEnabled(false);
                        btnYes.setEnabled(false);
                        GameAlertDialog myAlert = new GameAlertDialog();
                        myAlert.show(getFragmentManager(), "My alert");
                    }
                }
                tvLifes.setText("Vidas restantes" + lifes);
                loadImage();
                imageView.startAnimation(fadeIn);
            }
        });

    }
    private void loadImage() {
        int randVal = rand.nextInt(form.length);
        imageView.setImageResource(form[randVal]);
        imageView.startAnimation(fadeIn);
        Log.i("after:rand =", afterNow + ":" + randVal);
        switch (randVal){
            case 0:
                resolve(randVal,afterNow,true);
                break;
            case 1:
                resolve(randVal,afterNow,false);
                break;
            case 2:
                resolve(randVal,afterNow,true);
                break;
            case 3:
                resolve(randVal,afterNow,false);
                break;
            case 4:
                resolve(randVal,afterNow,true);
                break;
            case 5:
                resolve(randVal,afterNow,false);
                break;
            case 6:
                resolve(randVal,afterNow,true);
                break;
            case 7:
                resolve(randVal,afterNow,false);
                break;
            case 8:
                resolve(randVal,afterNow,true);
                break;
            case 9:
                resolve(randVal,afterNow,false);
                break;
            case 10:
                resolve(randVal,afterNow,true);
                break;
            case 11:
                resolve(randVal,afterNow,false);
                break;
        }
        afterNow = randVal;
        Log.i("after2:rand2 =", afterNow + ":" + randVal);
    }

    private void resolve(int rand, int after, boolean isPair) {
        if(isPair){
            if(rand == after || rand+1 == after){
                isAnswer=true;
            }else{
                isAnswer=false;
            }
        }else{
            if(rand==after || rand-1 == after){
                isAnswer=true;
            }else {
                isAnswer=false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remember, menu);
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
