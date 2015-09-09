package com.ultimatumedia.moneymanager.Objects.Wallets;

import android.content.Context;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;

/**
 * Created by Erik on 6/19/15.
 */
public class NormalWallet extends WalletAbstract{

    public NormalWallet(Context context, Subject subject) {
        super(context, subject);
    }

    @Override
    public void update(String state) {
        if (state.equalsIgnoreCase("PERCENT_UPDATE")) {
            double newPercent = ((WalletManagerSubject) subject).UpdatePercent(getPercent());
            setPercent(newPercent, false);
        } else if (state.equalsIgnoreCase("NEW_TRANSACTION")) {
            double incomingMoney = ((WalletManagerSubject) subject).getMoney(getName(), getPercent());
            if (incomingMoney > 0) {
                addIncome(incomingMoney);
            } else {
                addExpense(incomingMoney);
            }
            DatabaseLayer.updateWallet(context, this);
        }
    }
}
