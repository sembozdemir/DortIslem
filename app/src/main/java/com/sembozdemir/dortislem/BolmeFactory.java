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
                n = random.nextInt(3) + 1; break; // Bolen 1-3 arasında
            case Difficulty.MEDIUM:
                n = random.nextInt(4) + 4; break; // 4-7
            case Difficulty.HARD:
                n = random.nextInt(12) + 8; break; // 8-19
            case Difficulty.EXPERT:
                n = random.nextInt(80) + 20; break; // 20-99
            case Difficulty.GENIUS:
                n = random.nextInt(900) + 99; break; // 99-999
            default: n = 1;
        }

        return n;
    }
}
