package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 21.2.2015.
 *
 * TASARIM YANLIS OLDU.
 * DortIslem sınıfı interface olmalı ve ondan toplama, cikarma, carpma, bolme islemleri turemeli
 * bu sınıf toplama isleminin gerceklestirimi oldu şimdilik
 *
 * TODO: Bu sınıf ve builder'ı baz alınarak tasarım yeniden yapılmalı
 *
 */
public class DortIslem {

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int EXPERT = 4;

    // Ex: x + y = z or x - y = z etc.
    private int x,y,z;

    private int difficulty;
    private boolean truth;

    public DortIslem() {
        x = 0;
        y = 0;
        z = 0;
        difficulty = 1;
        truth = true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getZ() {
        return z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(x)
                .append(" + ")
                .append(y)
                .append(" = ")
                .append(z);
        return s.toString();
    }
}
