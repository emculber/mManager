package com.ultimatumedia.moneymanager.DatabaseLayer;

import android.content.Context;

import com.ultimatumedia.moneymanager.Database.TransactionDatabase;
import com.ultimatumedia.moneymanager.Database.WalletDatabase;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.NormalWallet;
import com.ultimatumedia.moneymanager.Objects.Wallets.UnallocatedWallet;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;

import java.util.ArrayList;

/**
 * Created by Erik on 6/21/15.
 */
public class DatabaseLayer {

    private boolean databaseOn = true;
    private WalletDatabase walletDatabase;
    private TransactionDatabase transactionDatabase;
    private Context context;

    public DatabaseLayer(Context context) {
        this.context = context;
    }

    public ArrayList<WalletAbstract> getWallets(ArrayList<String> typeExceptions) {
        ArrayList<WalletAbstract> normalWallets = new ArrayList<WalletAbstract>();
        if(databaseOn) {
            walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            normalWallets = walletDatabase.getWallets(context, WalletManager.getInstance(context), false);
            walletDatabase.close();
            normalWallets = removeTypedWallets(normalWallets, typeExceptions);
        }
        return normalWallets;
    }

    public ArrayList<WalletAbstract> getWallets(String getType) {
        ArrayList<WalletAbstract> normalWallets = new ArrayList<WalletAbstract>();
        if(databaseOn) {
            walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            normalWallets = walletDatabase.getWallets(context, WalletManager.getInstance(context), false);
            walletDatabase.close();
            normalWallets = getTypedWallets(normalWallets, getType);
        }
        return normalWallets;
    }

    public ArrayList<WalletAbstract> getWallets() {
        ArrayList<WalletAbstract> walletAbstracts = new ArrayList<WalletAbstract>();
        if(databaseOn) {
            walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletAbstracts = walletDatabase.getWallets(context, WalletManager.getInstance(context), false);
            walletDatabase.close();
        }
        return walletAbstracts;
    }

    public ArrayList<WalletAbstract> removeTypedWallets(ArrayList<WalletAbstract> walletAbstracts, ArrayList<String> typeExceptions) {
        for(String type : typeExceptions) {
            for(int i = 0; i < walletAbstracts.size(); i++) {
                WalletAbstract normalWallet = walletAbstracts.get(i);
                if(normalWallet.getType().equalsIgnoreCase(type)) {
                    walletAbstracts.remove(normalWallet);
                    i--;
                }
            }
        }
        return walletAbstracts;
    }

    public ArrayList<WalletAbstract> getTypedWallets(ArrayList<WalletAbstract> walletAbstracts, String getType) {
            for(int i = 0; i < walletAbstracts.size(); i++) {
                WalletAbstract walletAbstract = walletAbstracts.get(i);
                if(!walletAbstract.getType().equalsIgnoreCase(getType)) {
                    walletAbstracts.remove(walletAbstract);
                    i--;
                }
            }
        return walletAbstracts;
    }

    public void TurnOnObservers() {
        if(databaseOn) {
            if (!WalletManager.getInstance(context).observersSet) {
                walletDatabase = new WalletDatabase(context);
                walletDatabase.open();
                walletDatabase.getWallets(context, WalletManager.getInstance(context), true);
                walletDatabase.close();
            }
        }
    }

    public WalletAbstract getUnallocatedWallet() {
        WalletAbstract unallocatedWallet = UnallocatedWallet.getInstance();
        if(databaseOn) {
            ArrayList<WalletAbstract> walletAbstracts = getWallets("Unallocated");
            for(WalletAbstract testUnallocatedWallet : walletAbstracts) {
                if(testUnallocatedWallet.getName().equalsIgnoreCase("Unallocated")) {
                    unallocatedWallet.setAll(testUnallocatedWallet.getId(), testUnallocatedWallet.getName(),
                            testUnallocatedWallet.getPercent(), false,
                            testUnallocatedWallet.getExpense(), testUnallocatedWallet.getIncome(),
                            testUnallocatedWallet.isSubscribe(), testUnallocatedWallet.getType());
                    return unallocatedWallet;
                }
            }
            addWallet(unallocatedWallet);
        }
        return unallocatedWallet;
    }

    public void addWallet(WalletAbstract walletAbstract) {
        if(databaseOn) {
            walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletDatabase.create(walletAbstract);
            walletDatabase.close();
        }
    }

    public void updateWallet(WalletAbstract walletAbstract) {
        if(databaseOn) {
            walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletDatabase.updateWallet(walletAbstract);
            walletDatabase.close();
        }
    }

    public ArrayList<Transaction> getTransactionList() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        if(databaseOn) {
            TransactionDatabase transactionDatabase = new TransactionDatabase(context);
            transactionDatabase.open();
            transactions = transactionDatabase.getTransactions();
            transactionDatabase.close();
        }
        return transactions;
    }

    public double getTotalTransactionIncome() {
        double income = 0;
        if(databaseOn) {
            ArrayList<Transaction> transactions = getTransactionList();
            for (Transaction transaction : transactions) {
                if (transaction.amount > 0) {
                    MoneyMath moneyMath = new MoneyMath();
                    income = moneyMath.addMoney(income, transaction.amount);
                }
            }
        }
        return income;
    }

    public double getTotalTransactionExpense() {
        double expense = 0;
        if(databaseOn) {
            ArrayList<Transaction> transactions = getTransactionList();
            for (Transaction transaction : transactions) {
                if (transaction.amount < 0) {
                    MoneyMath moneyMath = new MoneyMath();
                    expense = moneyMath.addMoney(expense, transaction.amount);
                }
            }
        }
        return expense;
    }

    public void addNewTransaction(Transaction transaction) {
        if(databaseOn) {
            TransactionDatabase transactionDatabase = new TransactionDatabase(context);
            transactionDatabase.open();
            transactionDatabase.create(transaction);
            transactionDatabase.close();
        }
        WalletManager.getInstance(context).newTransaction(transaction);
    }
}
