package com.sembozdemir.dortislem;

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

    public Difficulty(int level) {
        this.level = level;
    }

    // level ilk oluşturulduğunda EASY olarak oluşacak daha sonra kendi scorea göre update methodunda değişecek.
    public Difficulty(Score score) {
        level = EASY;
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

    @Override
    public void update() {
        // TODO: score değiştiğinde level e olacaklar
        // TODO: zorluk skalasını gözden geçir
        if (0 < score.getState() && score.getState() <= 100)
            level = EASY;
        else if (100 < score.getState() && score.getState() <= 500)
            level = MEDIUM;
        else if (500 < score.getState() && score.getState() <= 2500)
            level = HARD;
        else if (2500 < score.getState() && score.getState() <= 10000)
            level = EXPERT;
        else if (10000 < score.getState())
            level = GENIUS;
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
