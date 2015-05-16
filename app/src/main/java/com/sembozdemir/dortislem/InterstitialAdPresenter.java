package com.sembozdemir.dortislem;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;

/**
 * Created by Semih Bozdemir on 12.3.2015.
 *
 * ** How to use **
 *
 *  * First, you have to init your ad in your onStart() method
    myAd = InterstitialAdPresenter.init(context)
                .setAdUnitId(YOUR_AD_UNIT_ID)
                .addTestDevice(YOUR_TEST_DEVICE_ID)
                .load();

    * And then, you can use that code wherever you want
    myAd.display();  // or myAd.displayEveryMin(5) etc.
 */
public class InterstitialAdPresenter {

    private static final String TAG = InterstitialAdPresenter.class.getSimpleName();
    private static InterstitialAd interstitialAd;
    private static AdRequest adRequest;
    private static AdRequest.Builder adRequestBuilder;

    public InterstitialAdPresenter() {

    }

    public static InterstitialAdPresenter init(Context context) {
        interstitialAd = new InterstitialAd(context);
        adRequestBuilder = new AdRequest.Builder();
        return new InterstitialAdPresenter();
    }

    public InterstitialAdPresenter setAdUnitId(String adUnitId) {
        interstitialAd.setAdUnitId(adUnitId);
        return this;
    }

    public InterstitialAdPresenter addTestDevice(String deviceId) {
        adRequestBuilder.addTestDevice(deviceId);
        return this;
    }

    public InterstitialAdPresenter load() {
        adRequest = adRequestBuilder.build();
        interstitialAd.loadAd(adRequest);
        return this;
    }

    public void display() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Log.d(TAG ,"Ad has not been loaded yet");
        }
    }

    public void displayEverySec(int sec) {
        try {
            if (1 <= sec && sec <= 59) {
                Calendar now = Calendar.getInstance();
                int currentSec = now.get(Calendar.SECOND);
                Log.d(TAG, "Current sec:" + currentSec);
                if (currentSec % sec == 0) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Log.d(TAG ,"Ad has not been loaded yet");
                    }
                    refresh();
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void displayEverySec(int[] secArray) {
        for (int sec : secArray) {
            try {
                if (1 <= sec && sec <= 59) {
                    Calendar now = Calendar.getInstance();
                    int currentSec = now.get(Calendar.SECOND);
                    Log.d(TAG, "Current sec:" + currentSec);
                    if (currentSec % sec == 0) {
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                            return;
                        } else {
                            Log.d(TAG ,"Ad has not been loaded yet");
                        }
                        refresh();
                    }
                } else {
                    throw new AdPresenterTimeOutOfException();
                }
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }

    }

    public void displayEveryMin(int min) {
        try {
            if (1 <= min && min <= 59) {
                Calendar now = Calendar.getInstance();
                int currentMin = now.get(Calendar.MINUTE);
                Log.d(TAG, "Current min:" + currentMin);
                if (currentMin % min == 0) {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Log.d(TAG ,"Ad has not been loaded yet");
                    }
                    refresh();
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void displayAndRefreshAfterMin(int min) {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Log.d(TAG ,"Ad has not been loaded yet");
        }
        RefreshTimer refreshTimer = new RefreshTimer(min*60000, 1);
        refreshTimer.start();
    }

    public void displayAndRefresh() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Log.d(TAG ,"Ad has not been loaded yet");
        }
        refresh();
    }

    private void refresh() {
        interstitialAd.loadAd(adRequest);
    }

    private static class AdPresenterTimeOutOfException extends Exception {
        @Override
        public String getMessage() {
            return "Min/Sec has to be between 1 and 59";
        }
    }

    private class RefreshTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public RefreshTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            refresh();
        }
    }

}
