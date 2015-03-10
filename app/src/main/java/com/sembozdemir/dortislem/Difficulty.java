package com.sembozdemir.dortislem;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Created by Semih Bozdemir on 4.3.2015.
 */
public class Difficulty extends Observer {
    // zorluk seviyeleri
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int EXPERT = 4;
    public static final int GENIUS = 5;

    public static final double SN_EASY = 1.1;
    public static final double SN_MEDIUM = 1.4;
    public static final double SN_HARD = 1.8;
    public static final double SN_EXPERT = 2.2;
    public static final double SN_GENIUS = 2.8;

    private int level;
    private int color;
    private Context context;
    private long time;
    private GoogleApiClient googleApiClient;

    public Difficulty(int level) {
        this.level = level;
    }

    // level ilk oluşturulduğunda EASY olarak oluşacak daha sonra kendi scorea göre update methodunda değişecek.
    public Difficulty(Score score, Context context) {
        level = EASY;
        this.context = context;
        color = context.getResources().getColor(R.color.easy_color);
        time = (long) (SN_EASY*1000);
        this.score = score;

        // add as Observer automatically
        this.score.addObserver(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void update() {
        // TODO: zorluk skalasını gözden geçir
        if (0 < score.getState() && score.getState() <= 80) {
            level = EASY;
            color = context.getResources().getColor(R.color.easy_color);
            time = (long) (SN_EASY*1000);
        } else if(80 < score.getState() && score.getState() <= 100){
            level = EASY;
            color = context.getResources().getColor(R.color.easy_color);
            time = (long) (SN_EASY*1000);
            Games.Achievements.unlock(googleApiClient, context.getString(R.string.achievement_beginner_id));
        } else if (100 < score.getState() && score.getState() <= 1000) {
            level = MEDIUM;
            color = context.getResources().getColor(R.color.medium_color);
            time = (long) (SN_MEDIUM*1000);
            Games.Achievements.unlock(googleApiClient, context.getString(R.string.achievement_amatuer_id));
        } else if (1000 < score.getState() && score.getState() <= 5000) {
            level = HARD;
            color = context.getResources().getColor(R.color.hard_color);
            time = (long) (SN_HARD*1000);
            Games.Achievements.unlock(googleApiClient, context.getString(R.string.achievement_hardplayer_id));
        } else if (5000 < score.getState() && score.getState() <= 15000) {
            level = EXPERT;
            color = context.getResources().getColor(R.color.expert_color);
            time = (long) (SN_EXPERT*1000);
            Games.Achievements.unlock(googleApiClient, context.getString(R.string.achievement_expert_id));
        } else if (15000 < score.getState()) {
            level = GENIUS;
            color = context.getResources().getColor(R.color.genius_color);
            time = (long) (SN_GENIUS*1000);
            Games.Achievements.unlock(googleApiClient, context.getString(R.string.achievement_genius_id));
            // TODO: Einstein badge i için incrementler ve diğer işlemler burada yapılacak
        }
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        switch (level) {
            case EASY: return "EASY";
            case MEDIUM: return "MEDIUM";
            case HARD: return "HARD";
            case EXPERT: return "EXPERT";
            case GENIUS: return "GENIUS";
            default: return "EASY";
        }
    }
}
