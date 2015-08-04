package com.example.bitware.keemorycuidador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by Izzy-Izumi on 16/07/2015.
 */
public class CountDown extends CountDownTimer {

    private Activity nextActivity;
    private Class nextClass;
    private String getPreferencia;
    private int getPreferencia2;

    public CountDown(long millisInFuture, long countDownInterval, Activity act, Class cls) {
        super(millisInFuture, countDownInterval);
        nextActivity=act;
        nextClass=cls;
    }
    @Override
    public void onTick(long l) {
    }

    @Override
    public void onFinish() {
        nextActivity.startActivity(new Intent(nextActivity,nextClass));
        nextActivity.finish();
    }
}
