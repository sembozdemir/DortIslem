package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 2.3.2015.
 */
public class CikarmaBuilder extends AbstractDortIslemBuilder {
    public CikarmaBuilder() {
        super();
        islem = new CikarmaIslemi();
    }

    @Override
    protected void setZ() {
        int z;

        if (islem.isTrue()) {
            z = islem.getX() - islem.getY();
        } else { // TODO : Yanlışşsa sadece fazlasını gosteriyor eksigini de gosterebilmeli
            z = islem.getX() - islem.getY();
            switch (islem.getDifficulty().getLevel()) {
                case Difficulty.EASY:
                    z += random.nextInt(3) + 1; break;
                case Difficulty.MEDIUM:
                    z += random.nextInt(13) + 3; break;
                case Difficulty.HARD:
                    z += random.nextInt(23) + 3; break;
                case Difficulty.EXPERT:
                    z += random.nextInt(99) + 33; break;
                case Difficulty.GENIUS:
                    z += random.nextInt(99) + 33; break;
                default: z += 3;
            }
        }

        islem.setZ(z);
    }

    @Override
    public CikarmaIslemi getDortIslem() {
        return (CikarmaIslemi) super.getDortIslem();
    }
}
