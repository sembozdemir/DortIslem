package com.sembozdemir.dortislem;

import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

/**
 * Created by Semih Bozdemir on 11.3.2015.
 *
 * Usage examples:
 *
 * AdPresenter.init()
          .addTestDevice(YOUR_TEST_DEVICE_ID)
          .loadWith(findViewById(R.id.adView))
          .hideEveryOddSec();

 *
 *
 */

public class AdPresenter {
    private static final String TAG = AdPresenter.class.getSimpleName();
    private static AdView adView;
    private static AdRequest adRequest;
    private static AdRequest.Builder adRequestBuilder;

    public AdPresenter() {

    }

    public static AdPresenter init() {
        adRequestBuilder = new AdRequest.Builder();
        return new AdPresenter();
    }

    public AdPresenter addTestDevice(String deviceId) {
        adRequestBuilder.addTestDevice(deviceId);
        return this;
    }

    public AdPresenter loadWith(View adView) {
        adRequest = adRequestBuilder.build();
        this.adView = (AdView) adView;
        this.adView.loadAd(adRequest);
        return this;
    }

    public AdPresenter show() {
        adView.setVisibility(View.VISIBLE);
        return this;
    }

    public AdPresenter hide() {
        adView.setVisibility(View.INVISIBLE);
        return this;
    }

    public void showEveryOddSec() {
        Calendar now = Calendar.getInstance();
        int sec = now.get(Calendar.SECOND);
        Log.d(TAG, "Current sec:" + sec);
        if (sec % 2 == 0) {
            // sec is even
            adView.setVisibility(View.INVISIBLE);
        } else {
            // sec is odd, so show the ad
            adView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEveryOddSec() {
        Calendar now = Calendar.getInstance();
        int sec = now.get(Calendar.SECOND);
        Log.d(TAG, "Current sec:" + sec);
        if (sec % 2 == 0) {
            // sec is even
            adView.setVisibility(View.VISIBLE);
        } else {
            // sec is odd, so hide the ad
            adView.setVisibility(View.INVISIBLE);
        }
    }

    public void showEverySec(int sec) {
        // If you want to show the ad every X second
        try {
            if (1 <= sec && sec <= 59) {
                Calendar now = Calendar.getInstance();
                int currentSec = now.get(Calendar.SECOND);
                Log.d(TAG, "Current sec:" + currentSec);
                if (currentSec % sec == 0) {
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.INVISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void hideEverySec(int sec) {
        // If you want to hide the ad every X second
        try {
            if (1 <= sec && sec <= 59) {
                Calendar now = Calendar.getInstance();
                int currentSec = now.get(Calendar.SECOND);
                Log.d(TAG, "Current sec:" + currentSec);
                if (currentSec % sec == 0) {
                    adView.setVisibility(View.INVISIBLE);
                } else {
                    adView.setVisibility(View.VISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void showIfSecIs(int sec) {
        // If you want to show the ad at a specific second
        try {
            if (1 <= sec && sec <= 59) {
                Calendar now = Calendar.getInstance();
                int currentSec = now.get(Calendar.SECOND);
                Log.d(TAG, "Current sec:" + currentSec);
                if (currentSec == sec) {
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.INVISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void hideIfSecIs(int sec) {
        // If you want to hide the at a specific second
        try {
            if (1 <= sec && sec <= 59) {
                Calendar now = Calendar.getInstance();
                int currentSec = now.get(Calendar.SECOND);
                Log.d(TAG, "Current sec:" + currentSec);
                if (currentSec == sec) {
                    adView.setVisibility(View.INVISIBLE);
                } else {
                    adView.setVisibility(View.VISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void showEveryOddMin() {
        Calendar now = Calendar.getInstance();
        int min = now.get(Calendar.MINUTE);
        Log.d(TAG, "Current min:" + min);
        if (min % 2 == 0) {
            // min is even
            adView.setVisibility(View.INVISIBLE);
        } else {
            // min is odd, so show the ad
            adView.setVisibility(View.VISIBLE);
        }
    }

    public void hideEveryOddMin() {
        Calendar now = Calendar.getInstance();
        int min = now.get(Calendar.MINUTE);
        Log.d(TAG, "Current min:" + min);
        if (min % 2 == 0) {
            // min is even
            adView.setVisibility(View.VISIBLE);
        } else {
            // min is odd, so hide the ad
            adView.setVisibility(View.INVISIBLE);
        }
    }

    public void showEveryMin(int min) {
        // If you want to show the add every X minute
        try {
            if (1 <= min && min <= 59) {
                Calendar now = Calendar.getInstance();
                int currentMin = now.get(Calendar.MINUTE);
                Log.d(TAG, "Current min:" + currentMin);
                if (currentMin % min == 0) {
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.INVISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void hideEveryMin(int min) {
        // If you want to hide the add every X minute
        try {
            if (1 <= min && min <= 59) {
                Calendar now = Calendar.getInstance();
                int currentMin = now.get(Calendar.MINUTE);
                Log.d(TAG, "Current min:" + currentMin);
                if (currentMin % min == 0) {
                    adView.setVisibility(View.INVISIBLE);
                } else {
                    adView.setVisibility(View.VISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void showIfMinIs(int min) {
        // If you want to show the add every X minute
        try {
            if (1 <= min && min <= 59) {
                Calendar now = Calendar.getInstance();
                int currentMin = now.get(Calendar.MINUTE);
                Log.d(TAG, "Current min:" + currentMin);
                if (currentMin == min) {
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.INVISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void hideIfMinIs(int min) {
        // If you want to hide the add every X minute
        try {
            if (1 <= min && min <= 59) {
                Calendar now = Calendar.getInstance();
                int currentMin = now.get(Calendar.MINUTE);
                Log.d(TAG, "Current min:" + currentMin);
                if (currentMin == min) {
                    adView.setVisibility(View.INVISIBLE);
                } else {
                    adView.setVisibility(View.VISIBLE);
                }
            } else {
                throw new AdPresenterTimeOutOfException();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private static class AdPresenterTimeOutOfException extends Exception {
        @Override
        public String getMessage() {
            return "Min/Sec has to be between 1 and 59";
        }
    }
}
