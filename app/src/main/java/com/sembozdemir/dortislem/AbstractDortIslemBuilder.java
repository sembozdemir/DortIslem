package com.sembozdemir.dortislem;

import java.util.Random;

/**
 * Created by Semih Bozdemir on 2.3.2015.
 */
public abstract class AbstractDortIslemBuilder {
    protected Random random;
    protected AbstractDortIslem islem;

    protected AbstractDortIslemBuilder() {
        random = new Random();
    }

    public void setDifficulty(Difficulty difficulty) {
        islem.setDifficulty(difficulty);
    }

    public void setX() {
        islem.setX(getANumber());
    }

    public void setY() {
        islem.setY(getANumber());
    }

    private void setTruth() {
        islem.setTruth(random.nextBoolean());
    }

    protected void setZ() {
        // it will be implemented in sublclasses
    }

    public AbstractDortIslem getDortIslem() {
        setTruth();
        setZ();
        return islem;
    }

    private int getANumber() {
        int n;

        switch (islem.getDifficulty().getLevel()) {
            case Difficulty.EASY:
                n = random.nextInt(4) + 1; break;
            case Difficulty.MEDIUM:
                n = random.nextInt(8) + 1; break;
            case Difficulty.HARD:
                n = random.nextInt(39) + 10; break;
            case Difficulty.EXPERT:
                n = random.nextInt(89) + 10; break;
            case Difficulty.GENIUS:
                n = random.nextInt(899) + 100; break;
            default: n = 0;
        }

        return n;
    }
}
