package com.sembozdemir.dortislem;

import java.util.Random;

/**
 * Created by Semih Bozdemir on 6.3.2015.
 */
public class BolmeFactory {

    // x / y = z

    protected Random random;
    protected BolmeIslemi islem;
    protected Difficulty difficulty;


    public BolmeFactory() {
        islem = new BolmeIslemi();
        random = new Random();
    }

    public BolmeFactory(Difficulty difficulty) {
        islem = new BolmeIslemi();
        random = new Random();
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setY() {
        // 1 - zorluk seviyesine göre BÖLEN belirlenir.
        islem.setY(getANumber());
    }

    protected void setZ() {
        // 2 - katsayı(aynı zamanda CEVAP) belirlenir.
        // TODO: zorluk seviyesine göre ayarlanabilir
        islem.setZ(BolmeIslemi.CEVAPLAR[random.nextInt(4)]);
    }

    public void setX() {
        // 3 - (y*z=x) işlemi ile BÖLÜNEN belirlenir.
        islem.setX(islem.getY() * islem.getZ());
    }

    public BolmeIslemi makeIslem(Difficulty level) {
        setDifficulty(level);
        setY();
        setZ();
        setX();
        return islem;
    }

    protected int getANumber() {
        int n;

        switch (difficulty.getLevel()) {
            case Difficulty.EASY:
                n = random.nextInt(2) + 2; break; // Bolen 2-3 arasında
            case Difficulty.MEDIUM:
                n = random.nextInt(4) + 3; break; // 3-6
            case Difficulty.HARD:
                n = random.nextInt(14) + 6; break; // 6-19
            case Difficulty.EXPERT:
                n = random.nextInt(90) + 10; break; // 20-99
            case Difficulty.GENIUS:
                n = random.nextInt(900) + 99; break; // 99-999
            default: n = 0;
        }

        return n;
    }
}
