package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 2.3.2015.
 */
public abstract class AbstractDortIslem {
    // zorluk seviyeleri
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int EXPERT = 4;
    public static final int GENIUS = 5;

    protected AbstractDortIslem() {
        x = 0;
        y = 0;
        z = 0;
        difficulty = 1;
        truth = true;
    }

    // Ex: x + y = z or x - y = z or x * y = z or x / y = z etc.
    protected int x;
    protected int y;
    protected int z;

    protected int difficulty;
    protected boolean truth;

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

    public void setTruth(boolean truth) {
        this.truth = truth;
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

    public boolean isTrue() {
        return truth;
    }

    @Override
    public String toString() {
        return super.toString();
        // it will be implemented in subclasses
    }
}
