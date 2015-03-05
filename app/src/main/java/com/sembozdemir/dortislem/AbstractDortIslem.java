package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 2.3.2015.
 */
public abstract class AbstractDortIslem {

    // Ex: x + y = z or x - y = z or x * y = z or x / y = z etc.
    protected int x;
    protected int y;
    protected int z;

    protected Difficulty difficulty;
    protected boolean truth;

    protected AbstractDortIslem() {
        x = 0;
        y = 0;
        z = 0;
        difficulty = new Difficulty(Difficulty.EASY);
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

    public void setDifficulty(Difficulty difficulty) {
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

    public Difficulty getDifficulty() {
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
