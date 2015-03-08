package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 6.3.2015.
 */
public class BolmeIslemi {

    public static int[] CEVAPLAR = new int[]{2, 3, 4, 5};

    protected int x;
    protected int y;
    protected int z;

    public BolmeIslemi() {
        x = 0;
        y = 0;
        z = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(x)
                .append(" / ")
                .append(y)
                .append(" = ")
                .append(z);
        return s.toString();
    }

    public boolean isTrue(int cevap) {
        return cevap == getZ();
    }
}
