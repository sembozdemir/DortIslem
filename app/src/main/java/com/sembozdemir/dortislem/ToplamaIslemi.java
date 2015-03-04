package com.sembozdemir.dortislem;

/**
 * Created by Semih Bozdemir on 2.3.2015.
 */
public class ToplamaIslemi extends AbstractDortIslem {
    public ToplamaIslemi() {
        super();
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
