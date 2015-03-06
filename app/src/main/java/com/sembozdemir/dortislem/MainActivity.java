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

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Random;


public class MainActivity extends Activity {

    private static final int TOPLAMA = 0;
    private static final int CIKARMA = 1;

    protected LinearLayout backgroundLayout;
    protected Button buttonTrue;
    protected Button buttonFalse;
    protected TextView textViewIslem;
    protected TextView textViewScore;
    protected AbstractDortIslem mIslem;
    protected Score mScore;
    protected Random mIslemSecici;
    protected Difficulty mDifficulty;
    protected GameTimer mTimer;
    protected RoundCornerProgressBar mProgressTimer;
    protected RoundCornerProgressBar mProgressDifficulties[];

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
        mProgressTimer = (RoundCornerProgressBar) findViewById(R.id.progressTimer);
        mProgressDifficulties = new RoundCornerProgressBar[]{null,
                (RoundCornerProgressBar) findViewById(R.id.progressEasy),
                (RoundCornerProgressBar) findViewById(R.id.progressMedium),
                (RoundCornerProgressBar) findViewById(R.id.progressHard),
                (RoundCornerProgressBar) findViewById(R.id.progressExpert),
                (RoundCornerProgressBar) findViewById(R.id.progressGenius)};


        // Score is 0 in the beginning
        mScore = new Score(0);
        textViewScore.setText(mScore.toString());

        // Difficulty is EASY in the beginning
        backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
        mDifficulty = new Difficulty(mScore);
        mProgressDifficulties[Difficulty.EASY].setProgress(1);
        mProgressTimer.setProgressColor(getResources().getColor(R.color.easy_color));


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
        YoYo.with(Techniques.Pulse).duration(700).playOn(textViewScore);
        handleLevelChanges();
    }

    private void handleLevelChanges() {
        switch (mDifficulty.getLevel()) {
            case Difficulty.EASY:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.easy_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
                handleProgressDifficulties();
                break;
            case Difficulty.MEDIUM:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.medium_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.medium_color));
                handleProgressDifficulties();
                break;
            case Difficulty.HARD:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.hard_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.hard_color));
                handleProgressDifficulties();
                break;
            case Difficulty.EXPERT:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.expert_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.expert_color));
                handleProgressDifficulties();
                break;
            case Difficulty.GENIUS:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.genius_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.genius_color));
                handleProgressDifficulties();
                break;
            default:
                mProgressTimer.setProgressColor(getResources().getColor(R.color.easy_color));
                backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
                handleProgressDifficulties();
        }

    }

    private void handleProgressDifficulties() {
        int level = mDifficulty.getLevel();
        for(int i = 1 ; i <= Difficulty.GENIUS ; i++) {
            if (i <= level) {
                mProgressDifficulties[i].setProgress(1);
            } else {
                mProgressDifficulties[i].setProgress(0);
            }
        }
    }

    private void newDortIslem() {
        mTimer = new GameTimer(getTime(), 1);
        mProgressTimer.setMax(getTime());
        mProgressTimer.setProgress(getTime());
        mTimer.start();

        AbstractDortIslemBuilder builder;
        switch (mIslemSecici.nextInt(2)) {
            case TOPLAMA:
                builder = new ToplamaBuilder(); break;
            case CIKARMA:
                builder = new CikarmaBuilder(); break;
            // TODO : diger islemler eklenecek
            // TODO : veya sadece Freaking Division olabilir. Bölünen progressTimer'ın üstünde bölen altında olabilir. aşağıya da doğru yanlış yerine 4 tane ardışık sayı konur oyuncu doğru olanı seçer.
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
        double sn;

        switch (mDifficulty.getLevel()) {
            case Difficulty.EASY:
                sn = 1;
                break;
            case Difficulty.MEDIUM:
                sn = 1.5;
                break;
            case Difficulty.HARD:
                sn = 2;
                break;
            case Difficulty.EXPERT:
                sn = 2.5;
                break;
            case Difficulty.GENIUS:
                sn = 3;
                break;
            default:
                sn = 1;
        }

        return (long) (1000*sn);
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
            // Burada, UI da timer bir şekilde ifade edilecek.
            mProgressTimer.setProgress(millisUntilFinished);
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
