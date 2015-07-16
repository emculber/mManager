package com.ultimatumedia.moneymanager.Objects;

import android.content.Context;
import android.widget.Toast;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Observer;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;

/**
 * Created by Erik on 6/19/15.
 */
public abstract class WalletAbstract extends Observer {

    protected long id = 0;
    protected String name = "";
    protected double percent = 0.0;
    protected double expense = 0.0;
    protected double income = 0.0;
    protected double amount = 0.0;
    protected boolean subscribe = false;
    protected String type = "Normal";
    protected boolean requestMorePercentIfNegitive = false;
    protected Context context;

    public WalletAbstract(Context context, Subject subject) {
        this.subject = subject;
        this.context = context;
    }

    public void setAll(long id, String name, double percent, boolean askForPercentAvaliable, double expense, double income, boolean subscribe, String type) {
        setId(id);
        setName(name);
        setPercent(percent, askForPercentAvaliable);
        addExpense(expense);
        addIncome(income);
        setSubscribe(subscribe);
        setType(type);
    }

    public void update() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent, boolean askForPercent) {
        if(askForPercent) {
            this.percent = ((WalletManager) subject).RequestPercent(context, percent);
            if (this.percent != percent)
                Toast.makeText(context, "There is " + this.percent + "% left, so that's how much was given", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, this.percent + "% was available and was given", Toast.LENGTH_SHORT).show();
        } else {
            this.percent = percent;
        }
    }

    public double getExpense() {
        return expense;
    }

    public void addExpense(double expense) {
        MoneyMath moneyMath = new MoneyMath();
        this.expense = moneyMath.addMoney(this.expense, expense);
        addAmount(expense);
    }

    public double getIncome() {
        return income;
    }

    public void addIncome(double income) {
        MoneyMath moneyMath = new MoneyMath();
        this.income = moneyMath.addMoney(this.income, income);
        addAmount(income);
    }

    public double getAmount() {
        return amount;
    }

    public void addAmount(double amount) {
        MoneyMath moneyMath = new MoneyMath();
        this.amount = moneyMath.addMoney(this.amount, amount);
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        if (subscribe) {
            subject.Subscribe(this);
        } else {
            if (this.subscribe) {
                ((WalletManager) subject).ReturnPercent(getPercent());
                setPercent(0, false);
            }
            subject.Unsubscribe(this);
        }
        this.subscribe = subscribe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
