package com.ultimatumedia.moneymanager.Objects.Wallets;

import android.content.Context;
import android.widget.Toast;

import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;

/**
 * Created by Erik on 7/8/15.
 */
public class UnallocatedWallet extends WalletAbstract {
    private static UnallocatedWallet instance = null;

    public static UnallocatedWallet getInstance() {
        if (instance == null) {
            instance = new UnallocatedWallet();
        }
        return instance;
    }

    private UnallocatedWallet() {
        super(null, null);
        id = 0;
        name = "Unallocated";
        percent = 100.0;
        type = "Unallocated";
    }

    @Override
    public void setName(String name) {
        return;
    }

    @Override
    public void setPercent(double percent, boolean askForPercent) {
        this.percent = percent;
    }

    @Override
    public boolean isSubscribe() {
        return false;
    }

    @Override
    public void setSubscribe(boolean subscribe) {
        return;
    }

    @Override
    public String getType() {
        return "Unallocated";
    }

    @Override
    public void setType(String type) {
        return;
    }
}
