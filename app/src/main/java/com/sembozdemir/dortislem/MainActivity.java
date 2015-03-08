package com.sembozdemir.dortislem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import info.hoang8f.widget.FButton;


public class MainActivity extends Activity {

    protected LinearLayout backgroundLayout;
    protected TextView textViewScore;
    protected TextView textViewBolunen;
    protected TextView textViewBolen;
    protected FButton buttons[];
    protected BolmeIslemi mIslem;
    protected BolmeFactory mIslemFactory;
    protected Score mScore;
    protected Difficulty mDifficulty;
    protected GameTimer mTimer;
    protected RoundCornerProgressBar mProgressTimer;
    protected RoundCornerProgressBar mProgressDifficulties[];
    protected boolean isGameOverDialogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize typeface helper
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), getString(R.string.font_path)))
                .create();
        TypefaceHelper.init(typeface);
        setContentView(R.layout.activity_main);
        // Apply custom typefaces!
        TypefaceHelper.typeface(this);

        // initiliaze View component
        initViewComponents();
        // initiliaze IslemFactory
        mIslemFactory = new BolmeFactory();
        // initiliaze beginning
        initBeginning();

        newIslem();
        mTimer.cancel();

        for (int i = 0 ; i < buttons.length ; i++) {
            final int cevap = i + 2;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIslem.isTrue(cevap)) {
                        plusScore();
                        newIslem();
                    } else {
                        mTimer.cancel();
                        gameOver();
                    }
                }
            });
        }

    }

    private void initBeginning() {
        // Score is 0 in the beginning
        mScore = new Score(0);
        textViewScore.setText(mScore.toString());

        // Difficulty is EASY in the beginning
        mDifficulty = new Difficulty(mScore, this);
        backgroundLayout.setBackgroundColor(getResources().getColor(R.color.easy_color));
        mProgressDifficulties[Difficulty.EASY].setProgress(1);
        mProgressTimer.setProgressColor(getResources().getColor(R.color.easy_color));

        isGameOverDialogShown = false;
    }

    private void initViewComponents() {
        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);
        textViewBolunen = (TextView) findViewById(R.id.textViewBolunen);
        textViewBolen = (TextView) findViewById(R.id.textViewBolen);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        mProgressTimer = (RoundCornerProgressBar) findViewById(R.id.progressTimer);
        mProgressDifficulties = new RoundCornerProgressBar[]{null,
                (RoundCornerProgressBar) findViewById(R.id.progressEasy),
                (RoundCornerProgressBar) findViewById(R.id.progressMedium),
                (RoundCornerProgressBar) findViewById(R.id.progressHard),
                (RoundCornerProgressBar) findViewById(R.id.progressExpert),
                (RoundCornerProgressBar) findViewById(R.id.progressGenius)};
        buttons = new FButton[]{(FButton) findViewById(R.id.button2),
                (FButton) findViewById(R.id.button3),
                (FButton) findViewById(R.id.button4),
                (FButton) findViewById(R.id.button5)};
    }

    private void plusScore() {
        mTimer.cancel();
        // Zorluk seviyesine göre scoru ayarla
        mScore.plus(mDifficulty);
        textViewScore.setText(String.valueOf(mScore));
        YoYo.with(Techniques.Pulse).duration(500).playOn(textViewScore);
        handleLevelChanges();
    }

    private void handleLevelChanges() {
        mProgressTimer.setProgressColor(mDifficulty.getColor());
        backgroundLayout.setBackgroundColor(mDifficulty.getColor());
        handleProgressDifficulties();
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

    private void newIslem() {
        mTimer = new GameTimer(getTime(), 1);
        mProgressTimer.setMax(getTime());
        mProgressTimer.setProgress(getTime());
        mTimer.start();

        mIslem = mIslemFactory.makeIslem(mDifficulty);

        textViewBolunen.setText(String.valueOf(mIslem.getX()));
        YoYo.with(Techniques.SlideInRight).duration(100).playOn(textViewBolunen);
        textViewBolen.setText(String.valueOf(mIslem.getY()));
        YoYo.with(Techniques.SlideInRight).duration(100).playOn(textViewBolen);
    }

    private long getTime() {
        double sn;

        switch (mDifficulty.getLevel()) {
            case Difficulty.EASY:
                sn = 1;
                break;
            case Difficulty.MEDIUM:
                sn = 1.3;
                break;
            case Difficulty.HARD:
                sn = 1.8;
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

        LayoutInflater inflater = LayoutInflater.from(this);
        // TODO: dialog tasarımı güzelleştirilecek
        View dialoglayout = inflater.inflate(R.layout.game_over_dialog, null);
        TypefaceHelper.typeface(dialoglayout);
        TextView dialogScore = (TextView) dialoglayout.findViewById(R.id.dialogTextScore);
        dialogScore.setText(String.valueOf(mScore));
        dialogScore.setTextColor(mDifficulty.getColor());
        FButton buttonPlay = (FButton) dialoglayout.findViewById(R.id.buttonPlayDialog);
        FButton buttonIntro = (FButton) dialoglayout.findViewById(R.id.buttonIntroDialog);

        // TODO: best değeri gösterebilmek için google play services ve SharedPreferences kullanılacak

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialoglayout);
        dialogBuilder.setCancelable(false);
        final AlertDialog dialogGameOver = dialogBuilder.create();

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogGameOver.cancel();
                initBeginning();
                newIslem();
                mTimer.cancel();
            }
        });

        buttonIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // If set, this activity will become the start of a new task on this history stack.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // If set in an Intent passed to Context.startActivity(), this flag will cause any existing task that would be associated with the activity to be cleared before the activity is started.
                dialogGameOver.cancel();
                startActivity(intent);
            }
        });

        // TODO: dialog gösteriminde oluşan hata düzeltilmeli
        if (isGameOverDialogShown == false) {
            dialogGameOver.show();
            isGameOverDialogShown = true;
        }

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

    private class GameTimerr extends AsyncTask<Void, Long, Void> {

        private long millisInFuture;
        private long countDownInterval;

        private GameTimerr(long millisInFuture, long countDownInterval) {
            this.millisInFuture = millisInFuture;
            this.countDownInterval = countDownInterval;
        }

        public void start() {
            if (isCancelled()) {
                this.execute();
            }
        }

        public void cancel() {
            this.cancel(true);
        }

        // A callback method executed on UI thread on starting the task
        @Override
        protected void onPreExecute() {

        }

        // A callback method executed on non UI thread, invoked after
        // onPreExecute method if exists
        @Override
        protected Void doInBackground(Void... params) {
            for(long i=millisInFuture;i>=0;i--){
                try {
                    Thread.sleep(countDownInterval);
                    publishProgress(i); // Invokes onProgressUpdate()
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        // A callback method executed on UI thread, invoked by the publishProgress()
        // from doInBackground() method
        @Override
        protected void onProgressUpdate(Long... values) {
            // Burada, UI da timer bir şekilde ifade edilecek.
            mProgressTimer.setProgress(Float.parseFloat(Integer.toString(values[0].intValue())));
        }

        // A callback method executed on UI thread, invoked after the completion of the task
        @Override
        protected void onPostExecute(Void result) {
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
