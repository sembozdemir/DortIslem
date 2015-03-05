package com.sembozdemir.dortislem;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    private static final int TOPLAMA = 0;
    private static final int CIKARMA = 1;
    private static final int TEST_MODE_EXTRA_SCORE = 0;

    protected LinearLayout backgroundLayout;
    protected Button buttonTrue;
    protected Button buttonFalse;
    protected TextView textViewIslem;
    protected TextView textViewScore;
    protected TextView textViewDifficulty;
    protected TextView textViewTime;
    protected AbstractDortIslem mIslem;
    protected Score mScore;
    protected Random mIslemSecici;
    protected Difficulty mDifficulty;
    protected GameTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiliaze View component
        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);
        buttonTrue = (Button) findViewById(R.id.buttonTrue);
        buttonFalse = (Button) findViewById(R.id.buttonFalse);
        textViewIslem = (TextView) findViewById(R.id.textViewIslem);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewDifficulty = (TextView) findViewById(R.id.textViewDifficulty);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));

        // Score is 0 in the beginning
        mScore = new Score(0);
        textViewScore.setText(mScore.toString());

        // Difficulty is EASY in the beginning
        mDifficulty = new Difficulty(mScore);
        textViewDifficulty.setText(mDifficulty.toString());

        // initiliaze IslemSecici
        mIslemSecici = new Random();

        newDortIslem();

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIslem.isTrue()) {
                    plusScore();
                    newDortIslem();
                } else gameOver();
            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mIslem.isTrue())) {
                    plusScore();
                    newDortIslem();
                } else gameOver();
            }
        });

    }

    /*private void minusScore() {
        mTimer.cancel();
        mScore.minus(mDifficulty);
        textViewScore.setText(String.valueOf(mScore));
        handleLevelChanges();
    }*/

    private void plusScore() {
        mTimer.cancel();
        // Zorluk seviyesine göre scoru ayarla
        mScore.plus(mDifficulty);
        textViewScore.setText(String.valueOf(mScore));
        handleLevelChanges();
    }

    private void handleLevelChanges() {
        // TODO: Animasyonlar eklenmeli, daha güzel bir görüntü elde edilmeli. Tasarım da değişiklikler yapılmalı
        switch (mDifficulty.getLevel()) {
            case Difficulty.EASY:
                textViewDifficulty.setText("EASY");
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
                break;
            case Difficulty.MEDIUM:
                textViewDifficulty.setText("MEDIUM");
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.medium_color));
                break;
            case Difficulty.HARD:
                textViewDifficulty.setText("HARD");
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.hard_color));
                break;
            case Difficulty.EXPERT:
                textViewDifficulty.setText("EXPERT");
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.expert_color));
                break;
            case Difficulty.GENIUS:
                textViewDifficulty.setText("GENIUS");
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.genius_color));
                break;
            default:
                textViewDifficulty.setText(String.valueOf(mDifficulty));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
        }

    }

    private void newDortIslem() {
        mTimer = new GameTimer(getTime(), 1000);
        mTimer.start();

        AbstractDortIslemBuilder builder;
        switch (mIslemSecici.nextInt(2)) {
            case TOPLAMA:
                builder = new ToplamaBuilder(); break;
            case CIKARMA:
                builder = new CikarmaBuilder(); break;
            // TODO : diger islemler eklenecek
            default:
                builder = new ToplamaBuilder(); break;
        }

        builder.setDifficulty(mDifficulty);
        builder.setX();
        builder.setY();
        mIslem = builder.getDortIslem();

        textViewIslem.setText(mIslem.toString());
    }

    private long getTime() {
        int sn = 0;

        switch (mDifficulty.getLevel()) {
            case Difficulty.EASY:
                sn = 2;
                break;
            case Difficulty.MEDIUM:
                sn = 3;
                break;
            case Difficulty.HARD:
                sn = 4;
                break;
            case Difficulty.EXPERT:
                sn = 5;
                break;
            case Difficulty.GENIUS:
                sn = 6;
                break;
            default:
                sn = 3;
        }

        return 1000*sn;
    }

    public void gameOver() {
        mTimer.cancel();
        // TODO: Oyun bitişini farklı yansıt. Bu geçici çözüm.
        Toast.makeText(this, "Oyun bitti", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Oyun Bitti");
        dialogBuilder.setMessage("Puanınız: " + mScore.getState());
        dialogBuilder.setPositiveButton("OK", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public class GameTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public GameTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO: UI da timeri bir geri sayım olarak değil de, progress bar olarak gösterebilrsin.
            // Burada, UI da timer bir şekilde ifade edilecek.
            textViewTime.setText(String.valueOf(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            //timer bittiğinde oyun biter
            gameOver();
        }
    }




    /*@Override
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
    }*/
}
