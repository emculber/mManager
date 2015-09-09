package com.ultimatumedia.moneymanager.DatabaseLayer;

import android.content.Context;

import com.ultimatumedia.moneymanager.Database.TransactionDatabase;
import com.ultimatumedia.moneymanager.Database.WalletDatabase;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.UnallocatedWallet;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;

import java.util.ArrayList;

/**
 * Created by Erik on 6/21/15.
 */
public class DatabaseLayer {

    private static boolean databaseOn = true;

    public static ArrayList<WalletAbstract> getWallets(Context context, ArrayList<String> typeExceptions) {
        ArrayList<WalletAbstract> normalWallets = new ArrayList<WalletAbstract>();
        if(databaseOn) {
            WalletDatabase walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            normalWallets = walletDatabase.getWallets(context, WalletManagerSubject.getInstance(context), false);
            walletDatabase.close();
            normalWallets = removeTypedWallets(context, normalWallets, typeExceptions);
        }
        return normalWallets;
    }

    public static ArrayList<WalletAbstract> getWallets(Context context) {
        ArrayList<WalletAbstract> walletAbstracts = new ArrayList<WalletAbstract>();
        if(databaseOn) {
            WalletDatabase walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletAbstracts = walletDatabase.getWallets(context, WalletManagerSubject.getInstance(context), false);
            walletDatabase.close();
        }
        return walletAbstracts;
    }

    public static ArrayList<WalletAbstract> removeTypedWallets(Context context, ArrayList<WalletAbstract> walletAbstracts, ArrayList<String> typeExceptions) {
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

    public static ArrayList<WalletAbstract> getTypedWallets(Context context, String getType) {
        ArrayList<WalletAbstract> walletAbstracts = getWallets(context);
        for(int i = 0; i < walletAbstracts.size(); i++) {
                WalletAbstract walletAbstract = walletAbstracts.get(i);
                if(!walletAbstract.getType().equalsIgnoreCase(getType)) {
                    walletAbstracts.remove(walletAbstract);
                    i--;
                }
            }
        return walletAbstracts;
    }

    public static void TurnOnObservers(Context context) {
        if(databaseOn) {
            if (!WalletManagerSubject.getInstance(context).observersSet) {
                WalletDatabase walletDatabase = new WalletDatabase(context);
                walletDatabase.open();
                walletDatabase.getWallets(context, WalletManagerSubject.getInstance(context), true);
                walletDatabase.close();
            }
        }
    }

    public static WalletAbstract getUnallocatedWallet(Context context) {
        WalletAbstract unallocatedWallet = UnallocatedWallet.getInstance();
        if(databaseOn) {
            ArrayList<WalletAbstract> walletAbstracts = getTypedWallets(context, "Unallocated");
            for(WalletAbstract testUnallocatedWallet : walletAbstracts) {
                if(testUnallocatedWallet.getName().equalsIgnoreCase("Unallocated")) {
                    unallocatedWallet.setAll(testUnallocatedWallet.getId(), testUnallocatedWallet.getName(),
                            testUnallocatedWallet.getPercent(), false,
                            testUnallocatedWallet.getExpense(), testUnallocatedWallet.getIncome(),
                            testUnallocatedWallet.isSubscribe(), testUnallocatedWallet.getType());
                    return unallocatedWallet;
                }
            }
            addWallet(context, unallocatedWallet);
        }
        return unallocatedWallet;
    }

    public static void addWallet(Context context, WalletAbstract walletAbstract) {
        if(databaseOn) {
            WalletDatabase walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletDatabase.create(walletAbstract);
            walletDatabase.close();
        }
    }

    public static void updateWallet(Context context, WalletAbstract walletAbstract) {
        if(databaseOn) {
            WalletDatabase walletDatabase = new WalletDatabase(context);
            walletDatabase.open();
            walletDatabase.updateWallet(walletAbstract);
            walletDatabase.close();
        }
    }

    public static ArrayList<Transaction> getTransactionList(Context context) {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        if(databaseOn) {
            TransactionDatabase transactionDatabase = new TransactionDatabase(context);
            transactionDatabase.open();
            transactions = transactionDatabase.getTransactions();
            transactionDatabase.close();
        }
        return transactions;
    }

    public static double getTotalTransactionIncome(Context context) {
        double income = 0;
        if(databaseOn) {
            ArrayList<Transaction> transactions = getTransactionList(context);
            for (Transaction transaction : transactions) {
                if (transaction.amount > 0) {
                    income = MoneyMath.addMoney(income, transaction.amount);
                }
            }
        }
        return income;
    }

    public static double getTotalTransactionExpense(Context context) {
        double expense = 0;
        if(databaseOn) {
            ArrayList<Transaction> transactions = getTransactionList(context);
            for (Transaction transaction : transactions) {
                if (transaction.amount < 0) {
                    expense = MoneyMath.addMoney(expense, transaction.amount);
                }
            }
        }
        return expense;
    }

    public static void addNewTransaction(Context context, Transaction transaction) {
        if(databaseOn) {
            TransactionDatabase transactionDatabase = new TransactionDatabase(context);
            transactionDatabase.open();
            transactionDatabase.create(transaction);
            transactionDatabase.close();
        }
    }
}
