package com.ultimatumedia.moneymanager.Objects.Wallets;

import android.content.Context;
import android.widget.Toast;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Observer;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;

/**
 * Created by Erik on 6/19/15.
 */
public class NormalWallet extends WalletAbstract{

    public NormalWallet(Context context, Subject subject) {
        super(context, subject);
    }

    @Override
    public void update() {
        double incomingMoney = ((WalletManager) subject).getMoney(getName(), getPercent());
        if (incomingMoney > 0) {
            addIncome(incomingMoney);
        } else {
            addExpense(incomingMoney);
        }

        DatabaseLayer databaseLayer = new DatabaseLayer(context);
        databaseLayer.updateWallet(this);
    }
}
