package com.sembozdemir.dortislem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;


public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String PREFS_KEY_BEST = "Best";
    private static final String PREFS_KEY_VIBRATION = "isVibrationOpen";
    private static final String PREFS_KEY_VOLUME = "isVolumeOn";
    private static final String PREFS_KEY_FIRST_TIME = "isFirstTime";
    private static final String TAG = MainActivity.class.getSimpleName();

    private static int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    protected LinearLayout backgroundLayout;
    protected TextView textViewScore;
    protected TextView textViewBolunen;
    protected TextView textViewBolen;
    protected ImageView imgVolume;
    protected FButton buttons[];
    protected BolmeIslemi mIslem;
    protected BolmeFactory mIslemFactory;
    protected Score mScore;
    protected Difficulty mDifficulty;
    protected GameTimer mTimer;
    protected RoundCornerProgressBar mProgressTimer;
    protected RoundCornerProgressBar mProgressDifficulties[];
    protected boolean isGameOverDialogShown;
    protected int mBest;
    protected Vibrator vibrator;
    private MediaPlayer mp;

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

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();

        // initiliaze View component
        initViewComponents();
        // initiliaze other things
        initOthers();
        // initiliaze IslemFactory
        mIslemFactory = new BolmeFactory();
        // initiliaze beginning
        initBeginning();

        newIslem();
        mTimer.cancel();

        // Set click listeners to the answer buttons
        for (int i = 0 ; i < buttons.length ; i++) {
            final int cevap = i + 2;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIslem.isTrue(cevap)) {
                        if (Prefs.getBoolean(PREFS_KEY_VOLUME, true)) {
                            mp = MediaPlayer.create(MainActivity.this, R.raw.right_answer);
                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mp.release();
                                }

                            });
                            mp.start();
                        }
                        plusScore();
                        newIslem();
                    } else {
                        mTimer.cancel();
                        gameOver();
                    }
                }
            });
        }

        // Set click listener to the volume image
        imgVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isVolumeOn = Prefs.getBoolean(PREFS_KEY_VOLUME, true);
                Prefs.putBoolean(PREFS_KEY_VOLUME, !isVolumeOn);

                final int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

                if(Prefs.getBoolean(PREFS_KEY_VOLUME, true)) {
                    Picasso.with(MainActivity.this).load(R.drawable.ic_volume_on).resize(px, px).centerCrop().into(imgVolume);
                } else {
                    Picasso.with(MainActivity.this).load(R.drawable.ic_volume_mute).resize(px, px).centerCrop().into(imgVolume);
                }
            }
        });

    }

    private void initOthers() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        if(Prefs.getBoolean(PREFS_KEY_VOLUME, true)) {
            Picasso.with(MainActivity.this).load(R.drawable.ic_volume_on).resize(px, px).centerCrop().into(imgVolume);
        } else {
            Picasso.with(MainActivity.this).load(R.drawable.ic_volume_mute).resize(px, px).centerCrop().into(imgVolume);
        }

    }

    private void initBeginning() {

        // Score is 0 in the beginning
        mScore = new Score(0);
        textViewScore.setText(mScore.toString());

        // Difficulty is EASY in the beginning
        mDifficulty = new Difficulty(mScore, this);
        mDifficulty.setGoogleApiClient(mGoogleApiClient);
        handleLevelChanges();

        // initiliaze Best Score
        mBest = Prefs.getInt(PREFS_KEY_BEST, 0);

        isGameOverDialogShown = false;

    }

    private void initViewComponents() {
        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);
        textViewBolunen = (TextView) findViewById(R.id.textViewBolunen);
        textViewBolen = (TextView) findViewById(R.id.textViewBolen);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        imgVolume = (ImageView) findViewById(R.id.imageViewVolume);
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
        mTimer = new GameTimer(mDifficulty.getTime(), 1);
        mProgressTimer.setMax(mDifficulty.getTime());
        mProgressTimer.setProgress(mDifficulty.getTime());
        mTimer.start();

        mIslem = mIslemFactory.makeIslem(mDifficulty);

        textViewBolunen.setText(String.valueOf(mIslem.getX()));
        textViewBolen.setText(String.valueOf(mIslem.getY()));
        YoYo.with(Techniques.SlideInRight).duration(100).playOn(textViewBolunen);
        YoYo.with(Techniques.SlideInRight).duration(100).playOn(textViewBolen);
    }

    public void gameOver() {

        if(Prefs.getBoolean(PREFS_KEY_VIBRATION, true)) {
            vibrator.vibrate(100);
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialoglayout = inflater.inflate(R.layout.game_over_dialog, null);
        TypefaceHelper.typeface(dialoglayout);
        TextView dialogScore = (TextView) dialoglayout.findViewById(R.id.dialogTextScore);
        dialogScore.setText(String.valueOf(mScore.getState()));
        TextView dialogBest = (TextView) dialoglayout.findViewById(R.id.dialogTextBest);
        if (isHighScore()) {
            mBest = mScore.getState();
            dialogBest.setTextColor(getResources().getColor(R.color.hard_color));
            Toast.makeText(this, "You have new High Score: " + mBest, Toast.LENGTH_LONG).show();
            Prefs.putInt(PREFS_KEY_BEST, mBest);
            // Submit score to the leaderboard in Google Play Game Services
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_id), 1337);
        }
        dialogBest.setText(String.valueOf(mBest));
        FButton buttonPlay = (FButton) dialoglayout.findViewById(R.id.buttonPlayDialog);
        FButton buttonIntro = (FButton) dialoglayout.findViewById(R.id.buttonIntroDialog);

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

    private boolean isHighScore() {
        return mScore.getState() > mBest;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        Log.d(TAG, "onStop() is called");
    }

    @Override
    public void onConnected(Bundle bundle) {
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.
        // is it First Time playing?
        if (Prefs.getBoolean(PREFS_KEY_FIRST_TIME, true)) {
            Prefs.putBoolean(PREFS_KEY_FIRST_TIME, false);
            // it is first time playing, so unlock 'Abacus' badge
            Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_abacus_id));
        }
        Log.d(TAG, "Connection : ok");
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
        Log.d(TAG, "Connection : suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
        Log.d(TAG, "onConnectionFailed() is called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
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

    // Dialogdaki hatayı gidermek için alternatif timer sınıfı yazmaya çalıştım ama şuan çalışmıyor.
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
}
