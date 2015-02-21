package com.sembozdemir.dortislem;

import java.util.Random;

/**
 * Created by Semih Bozdemir on 21.2.2015.
 *
 * bu sınıf interface olmalı.
 * ToplamaBuilder, CikarmaBuilder, CarpmaBuilder, BolmeBuilder bu interfaceden türemeli
 *
 * Bu sınıf sadece ToplamaBuilder gibi oldu.
 *
 */
public class DortIslemBuilder {

    private DortIslem islem;

    public DortIslemBuilder() {
        islem = new DortIslem();
    }

    public DortIslem getDortIslem() {
        setTruth();
        setZ();
        return islem;
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
        Random random = new Random();

        islem.setTruth(random.nextBoolean());
    }

    private void setZ() {
        // sadece toplama yapıyor şimdilik
        int z;

        if (islem.isTruth()) {
            z = islem.getX() + islem.getY();
        } else { // Yanlışşsa sadece fazlasını gosteriyor eksigini de gosterebilmeli
            Random random = new Random();
            z = islem.getX() + islem.getY();
            switch (islem.getDifficulty()) {
                case DortIslem.EASY:
                    z += random.nextInt(3) + 1; break;
                case DortIslem.MEDIUM:
                    z += random.nextInt(13) + 3; break;
                case DortIslem.HARD:
                    z += random.nextInt(23) + 3; break;
                case DortIslem.EXPERT:
                    z += random.nextInt(99) + 33; break;
                default: z += 3;
            }
        }

        islem.setZ(z);
    }

    private int getANumber() {
        Random random = new Random();
        int n;

        switch (islem.getDifficulty()) {
            case DortIslem.EASY:
                n = random.nextInt(8) + 1; break;
            case DortIslem.MEDIUM:
                n = random.nextInt(39) + 10; break;
            case DortIslem.HARD:
                n = random.nextInt(89) + 10; break;
            case DortIslem.EXPERT:
                n = random.nextInt(899) + 100; break;
            default: n = 0;
        }

        return n;
    }
}
