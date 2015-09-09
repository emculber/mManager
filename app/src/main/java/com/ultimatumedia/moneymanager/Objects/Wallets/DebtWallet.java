package com.ultimatumedia.moneymanager.Objects.Wallets;

import android.content.Context;
import android.widget.Toast;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;

/**
 * Created by Erik on 8/2/15.
 */
public class DebtWallet extends WalletAbstract {
    public DebtWallet(Context context, Subject subject) {
        super(context, subject);
        setType("Debt");
    }

    public void update(String state) {
        String steate = state;
        if(state.equalsIgnoreCase("PERCENT_UPDATE")) {

        }
        else if(state.equalsIgnoreCase("NEW_TRANSACTION")) {

        }
        DatabaseLayer.updateWallet(context, this);
    }

    public void setPercent(double percent, boolean askForPercent) {
        if(askForPercent) {
            this.percent = ((WalletManagerSubject) subject).RequestPercent(context, percent, true);
            if (this.percent != percent)
                Toast.makeText(context, "There is " + this.percent + "% left, so that's how much was given", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, this.percent + "% was available and was given", Toast.LENGTH_SHORT).show();
        } else {
            this.percent = percent;
        }
    }

    //TODO (Erik): If Debt is payed return percents
    public void addAmount(double amount) {
        this.amount = MoneyMath.addMoney(this.amount, amount);
    }

    public void setSubscribe(boolean subscribe) {
        if (subscribe) {
            subject.Subscribe(this);
        } else {
            if (this.subscribe) {
                ((WalletManagerSubject) subject).ReturnPercent(getPercent());
                setPercent(0, false);
            }
            subject.Unsubscribe(this);
        }
        this.subscribe = subscribe;
    }
}
