package com.ultimatumedia.moneymanager.ObserverChildren;

import android.content.Context;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.UnallocatedWallet;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;
import com.ultimatumedia.moneymanager.Readers.ClientReader;

import java.util.ArrayList;

/**
 * Created by Erik on 7/6/15.
 */
public class WalletManager extends Subject {

    private static WalletManager instance = null;

    public static WalletManager getInstance(Context context) {
        if (instance == null) {
            instance = new WalletManager();
            instance.init(context);
        }
        return instance;
    }

    private WalletAbstract unallocatedWallet;
    private Transaction lastTransaction;
    private double amountTaken = 0;
    public boolean observersSet = false;

    private WalletManager() {
    }

    private void init(Context context) {
        DatabaseLayer databaseLayer = new DatabaseLayer(context);
        unallocatedWallet = databaseLayer.getUnallocatedWallet();

        databaseLayer.TurnOnObservers();
        observersSet = true;

        ClientReader clientReader = new ClientReader();
        if(databaseLayer.getWallets().size() >= 1) {
            clientReader.LoadAllWalletsFromCSV(context, databaseLayer);
        }
        if(databaseLayer.getTransactionList().size() == 0) {
            clientReader.LoadAllTransactionFromCSV(context, databaseLayer);
        }
    }

    public void newTransaction(Transaction transaction) {
        lastTransaction = transaction;
        amountTaken = 0;
        setState(State.NEW_TRANSACTION);
        getMoney(unallocatedWallet.getName(), unallocatedWallet.getPercent());
    }

    public double RequestPercent(Context context, double requestedPrecent) {
        MoneyMath moneyMath = new MoneyMath();
        double newUnallocatedWalletPercent = 0;
        if (requestedPrecent > unallocatedWallet.getPercent()) {
            requestedPrecent = unallocatedWallet.getPercent();
        }
        newUnallocatedWalletPercent = moneyMath.subtractPercent(unallocatedWallet.getPercent(), requestedPrecent);
        unallocatedWallet.setPercent(newUnallocatedWalletPercent, false);
        DatabaseLayer databaseLayer = new DatabaseLayer(context);
        databaseLayer.updateWallet(unallocatedWallet);
        return requestedPrecent;
    }

    public void ReturnPercent(double returnPrecent) {
        MoneyMath moneyMath = new MoneyMath();
        double newUnusedWalletPercent = moneyMath.addPercent(unallocatedWallet.getPercent(), returnPrecent);
        unallocatedWallet.setPercent(newUnusedWalletPercent, false);
    }

    public double PercentAvalable() {
        return unallocatedWallet.getPercent();
    }

    public double getMoney(String walletName, double percent) {
        MoneyMath moneyMath = new MoneyMath();
        double amount = 0;
        if (walletName.equalsIgnoreCase("Unallocated")) {
            amount = moneyMath.subtractMoney(lastTransaction.amount, amountTaken);
        } else if (lastTransaction.walletName.equalsIgnoreCase("Split")) {
            amount = moneyMath.multiplyMoneyPercent(lastTransaction.amount, percent);
        } else if (lastTransaction.walletName.equalsIgnoreCase(walletName)) {
            amount = lastTransaction.amount;
        }
        amountTaken = moneyMath.addMoney(amountTaken, amount);
        return amount;
    }
}