package com.ultimatumedia.moneymanager.ObserverPattern.Abstracts;

/**
 * Created by Erik on 7/6/15.
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
