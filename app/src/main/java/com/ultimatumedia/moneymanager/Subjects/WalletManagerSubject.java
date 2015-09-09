package com.ultimatumedia.moneymanager.Subjects;

import android.content.Context;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;
import com.ultimatumedia.moneymanager.Readers.ClientReader;

import java.util.ArrayList;

/**
 * Created by Erik on 7/6/15.
 */
public class WalletManagerSubject extends Subject {

    private static WalletManagerSubject instance = null;

    public static WalletManagerSubject getInstance(Context context) {
        if (instance == null) {
            instance = new WalletManagerSubject();
            instance.init(context);
        }
        return instance;
    }

    private WalletAbstract unallocatedWallet;
    private Transaction lastTransaction;
    private double amountTaken = 0;
    public boolean observersSet = false;
    private double percentFromForcePercent = 0;
    private double requestedFromForcePercent = 0;
    private double percentPoolFromForcePercent = 0;
    private double percentRatioFromForcePercent = 0;

    private WalletManagerSubject() {
    }

    private void init(Context context) {
        unallocatedWallet = DatabaseLayer.getUnallocatedWallet(context);

        DatabaseLayer.TurnOnObservers(context);
        observersSet = true;

        ClientReader clientReader = new ClientReader();
        if(DatabaseLayer.getWallets(context).size() <= 1) {
            clientReader.LoadAllWalletsFromCSV(context);
        }
        if(DatabaseLayer.getTransactionList(context).size() == 0) {
            clientReader.LoadAllTransactionFromCSV(context);
        }
    }

    public void newTransaction(Context context, Transaction transaction) {
        lastTransaction = transaction;
        DatabaseLayer.addNewTransaction(context, transaction);
        amountTaken = 0;
        setState(State.NEW_TRANSACTION);
        getMoney(unallocatedWallet.getName(), unallocatedWallet.getPercent());
    }

    public double RequestPercent(Context context, double requestedPrecent, boolean forcePercent) {
        if(forcePercent) {
            percentPoolFromForcePercent = WalletPercents(context, "Normal");
            requestedFromForcePercent = requestedPrecent;
            percentFromForcePercent = 0;
            percentRatioFromForcePercent = MoneyMath.divideTwoPercents(requestedFromForcePercent, percentPoolFromForcePercent);
            setState(State.PERCENT_UPDATE);
        }
        double newUnallocatedWalletPercent = 0;
        if (requestedPrecent > unallocatedWallet.getPercent()) {
            requestedPrecent = unallocatedWallet.getPercent();
        }
        newUnallocatedWalletPercent = MoneyMath.subtractPercent(unallocatedWallet.getPercent(), requestedPrecent);
        unallocatedWallet.setPercent(newUnallocatedWalletPercent, false);
        DatabaseLayer.updateWallet(context, unallocatedWallet);
        return requestedPrecent;
    }

    public double UpdatePercent(double percent) {
        double percentFromWallet = MoneyMath.multiplyTwoPercents(percent, percentRatioFromForcePercent);
        percentFromForcePercent += percentFromWallet;
        double newWalletPercent = MoneyMath.subtractPercent(percent, percentFromWallet);
        return newWalletPercent;
    }

    public void ReturnPercent(double returnPrecent) {
        double newUnusedWalletPercent = MoneyMath.addPercent(unallocatedWallet.getPercent(), returnPrecent);
        unallocatedWallet.setPercent(newUnusedWalletPercent, false);
    }

    public int RequestPercentWaitingNumber() {
        int currentLineNumber = 0;
        for (int i = 0; i < observers.size(); i++) {
            int currentWalletsLineNumber = ((WalletAbstract)observers.get(i)).getLineNumber();
            if(currentWalletsLineNumber > currentLineNumber) {
                currentLineNumber = currentWalletsLineNumber;
            }
        }
        return currentLineNumber;
    }

    public void ReturnPercentWaitingNumber() {
        for (int i = 0; i < observers.size(); i++) {
            int currentWalletsLineNumber = ((WalletAbstract)observers.get(0)).getLineNumber();
            if(currentWalletsLineNumber != 0) {
                ((WalletAbstract)observers.get(i)).setLineNumber(--currentWalletsLineNumber);
            }
        }
    }

    public double WalletPercents(Context context, String walletType) {
        ArrayList<WalletAbstract> typedWallets = DatabaseLayer.getTypedWallets(context, walletType);
        double returnPercent = 0;
        for(WalletAbstract walletAbstract : typedWallets) {
            returnPercent += walletAbstract.getPercent();
        }
        return returnPercent;
    }

    public double getMoney(String walletName, double percent) {
        double amount = 0;
        if (walletName.equalsIgnoreCase("Unallocated")) {
            amount = MoneyMath.subtractMoney(lastTransaction.amount, amountTaken);
        } else if (lastTransaction.walletName.equalsIgnoreCase("Split")) {
            amount = MoneyMath.multiplyMoneyPercent(lastTransaction.amount, percent);
        } else if (lastTransaction.walletName.equalsIgnoreCase(walletName)) {
            amount = lastTransaction.amount;
        }
        amountTaken = MoneyMath.addMoney(amountTaken, amount);
        return amount;
    }
}