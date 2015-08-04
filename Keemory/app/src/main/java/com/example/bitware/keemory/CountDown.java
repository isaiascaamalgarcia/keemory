package com.example.bitware.keemory;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Izzy-Izumi on 16/07/2015.
 */
public class CountDown extends CountDownTimer {

    private Activity nextActivity;
    private Class nextClass;
    private FragmentManager fm;
    public CountDown(long millisInFuture, long countDownInterval,Activity act,Class cls) {
        super(millisInFuture, countDownInterval);
        nextActivity=act;
        nextClass=cls;
        this.fm =fm;
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
