package com.sembozdemir.dortislem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    protected Button buttonTrue;
    protected Button buttonFalse;
    protected TextView textViewIslem;
    protected TextView textViewScore;
    protected DortIslem mIslem;
    protected int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiliaze View component
        buttonTrue = (Button) findViewById(R.id.buttonTrue);
        buttonFalse = (Button) findViewById(R.id.buttonFalse);
        textViewIslem = (TextView) findViewById(R.id.textViewIslem);
        textViewScore = (TextView) findViewById(R.id.textViewScore);

        // Score is 0 in the beginning
        mScore = 0;
        textViewScore.setText("0");

        newDortIslem();

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIslem.isTruth())
                    plusScore();
                newDortIslem();
            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(mIslem.isTruth()))
                    plusScore();
                newDortIslem();
            }
        });

    }

    private void plusScore() {
        mScore++;
        textViewScore.setText(String.valueOf(mScore));
    }

    private void newDortIslem() {
        DortIslemBuilder dortIslemBuilder = new DortIslemBuilder();
        dortIslemBuilder.setDifficulty(DortIslem.EASY); // TODO: Baska zorluk seviyeleri ekle
        dortIslemBuilder.setX();
        dortIslemBuilder.setY();
        mIslem = dortIslemBuilder.getDortIslem();

        textViewIslem.setText(mIslem.toString());
    }


    @Override
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
    }
}
