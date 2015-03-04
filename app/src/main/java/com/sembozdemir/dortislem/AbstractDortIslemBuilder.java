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

    public void setDifficulty(int difficulty) {
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

        switch (islem.getDifficulty()) {
            case AbstractDortIslem.EASY:
                n = random.nextInt(4) + 1; break;
            case AbstractDortIslem.MEDIUM:
                n = random.nextInt(8) + 1; break;
            case AbstractDortIslem.HARD:
                n = random.nextInt(39) + 10; break;
            case AbstractDortIslem.EXPERT:
                n = random.nextInt(89) + 10; break;
            case AbstractDortIslem.GENIUS:
                n = random.nextInt(899) + 100; break;
            default: n = 0;
        }

        return n;
    }
}
