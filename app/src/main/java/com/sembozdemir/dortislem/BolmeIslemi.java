package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 6.3.2015.
 */
public class BolmeIslemi extends AbstractDortIslem {
    public BolmeIslemi() {
        super();
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(x)
                .append(" / ")
                .append(y)
                .append(" = ")
                .append(z);
        return s.toString();
    }
}
