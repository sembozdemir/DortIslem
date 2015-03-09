package com.sembozdemir.dortislem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.squareup.picasso.Picasso;

import info.hoang8f.widget.FButton;


public class IntroActivity extends Activity {

    protected FButton buttonPlay;
    protected RoundCornerProgressBar progressBar;
    protected ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // TODO: intro tasarımı yenilenecek
        // TODO: google play services eklenecek

        // initialize view components
        initViewComponents();

    }

    private void initViewComponents() {
        buttonPlay = (FButton) findViewById(R.id.buttonPlay);
        progressBar = (RoundCornerProgressBar) findViewById(R.id.progressCenter);
        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(R.drawable.freakingdiv_logo)
                .resize(425*2, 125*2)
                .into(imageView);

        // initiliaze 0 ProgressBar in the beginning
        progressBar.setMax(100);
        progressBar.setProgress(0);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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
