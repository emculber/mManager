package com.ultimatumedia.moneymanager.ObserverPattern.Abstracts;

import java.util.ArrayList;

/**
 * Created by Erik on 7/6/15.
 */
public abstract class Subject {
    protected ArrayList<Observer> observers = new ArrayList<Observer>();
    private State state;

    public enum State {
        PERCENT_UPDATE,
        NEW_TRANSACTION
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        notifyAllObservers();
    }

    public void Subscribe(Observer observer){
        observers.add(observer);
    }

    public void Unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update(state.toString());
        }
    }
}
