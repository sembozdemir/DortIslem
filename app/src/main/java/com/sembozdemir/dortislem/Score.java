package com.sembozdemir.dortislem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semih Bozdemir on 4.3.2015.
 */
public class Score {
    private int state; // score değeri
    private List<Observer> observers;

    public Score(int state) {
        this.state = state;
        observers = new ArrayList<Observer>();
    }

    public Score() {
        state = 0;
        observers = new ArrayList<Observer>();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(state);
    }

    public void plus(Difficulty difficulty) {
        setState(getState() + difficulty.getLevel()*10);
    }

    /*public void minus(Difficulty difficulty) {
        // kafadan attım daha sonra kontrol et
        setState(getState() - difficulty.getLevel()*50);
    }*/
}
