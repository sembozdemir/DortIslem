package com.sembozdemir.dortislem;

import android.content.Context;

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

    private int level;
    private int color;
    private Context context;

    public Difficulty(int level) {
        this.level = level;
    }

    // level ilk oluşturulduğunda EASY olarak oluşacak daha sonra kendi scorea göre update methodunda değişecek.
    public Difficulty(Score score, Context context) {
        level = EASY;
        this.context = context;
        color = context.getResources().getColor(R.color.easy_color);
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

    @Override
    public void update() {
        // TODO: zorluk skalasını gözden geçir
        if (0 < score.getState() && score.getState() <= 100) {
            level = EASY;
            color = context.getResources().getColor(R.color.easy_color);
        } else if (100 < score.getState() && score.getState() <= 1000) {
            level = MEDIUM;
            color = context.getResources().getColor(R.color.medium_color);
        } else if (1000 < score.getState() && score.getState() <= 5000) {
            level = HARD;
            color = context.getResources().getColor(R.color.hard_color);
        } else if (5000 < score.getState() && score.getState() <= 15000) {
            level = EXPERT;
            color = context.getResources().getColor(R.color.expert_color);
        } else if (15000 < score.getState()) {
            level = GENIUS;
            color = context.getResources().getColor(R.color.genius_color);
        }
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
