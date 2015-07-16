package com.ultimatumedia.moneymanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.NormalWallet;
import com.ultimatumedia.moneymanager.ObserverPattern.Abstracts.Subject;

import java.util.ArrayList;

/**
 * Created by Erik on 6/19/15.
 */
public class WalletDatabase {
    public final String[] columns = {
            DatabaseHelper.WALLET_ID,
            DatabaseHelper.WALLET_NAME,
            DatabaseHelper.WALLET_PERCENT,
            DatabaseHelper.WALLET_EXPENSE,
            DatabaseHelper.WALLET_INCOME,
            DatabaseHelper.WALLET_AMOUNT,
            DatabaseHelper.WALLET_SUBSCRIBE,
            DatabaseHelper.WALLET_TYPE };

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public WalletDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues packValues(WalletAbstract wallet) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.WALLET_NAME, wallet.getName());
        values.put(DatabaseHelper.WALLET_PERCENT, wallet.getPercent());
        values.put(DatabaseHelper.WALLET_EXPENSE, wallet.getExpense());
        values.put(DatabaseHelper.WALLET_INCOME, wallet.getIncome());
        values.put(DatabaseHelper.WALLET_AMOUNT, wallet.getAmount());
        if(wallet.isSubscribe())
            values.put(DatabaseHelper.WALLET_SUBSCRIBE, 1);
        else
            values.put(DatabaseHelper.WALLET_SUBSCRIBE, 0);
        values.put(DatabaseHelper.WALLET_TYPE, wallet.getType());
        return values;
    }

    private WalletAbstract unPackValues(Context context, Cursor cursor, Subject WalletManager, boolean observeListVerification) {
        WalletAbstract walletAbstract = new NormalWallet(context, WalletManager);

        if(observeListVerification) {
            if(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.WALLET_SUBSCRIBE)) == 1) {
                walletAbstract.setSubscribe(true);
            }else {
                walletAbstract.setSubscribe(false);
            }
        }else{
            walletAbstract.setSubscribe(false);
        }

        walletAbstract.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.WALLET_ID)));
        walletAbstract.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WALLET_NAME)));
        walletAbstract.setPercent(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.WALLET_PERCENT)), false);
        walletAbstract.addExpense(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.WALLET_EXPENSE)));
        walletAbstract.addIncome(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.WALLET_INCOME)));
        walletAbstract.setType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WALLET_TYPE)));
        return walletAbstract;
    }

    public WalletAbstract create(WalletAbstract wallet) {
        long id = database.insert(DatabaseHelper.WALLET_TABLE_NAME, null, packValues(wallet));
        wallet.setId(id);
        return wallet;
    }

    public ArrayList<WalletAbstract> getWallets(Context context, Subject WalletManager, boolean observeListVerification) {
        ArrayList<WalletAbstract> walletAbstracts = new ArrayList<WalletAbstract>();

        Cursor cursor = database.query(DatabaseHelper.WALLET_TABLE_NAME, columns, null, null, null, null, null);
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                walletAbstracts.add(unPackValues(context, cursor, WalletManager, observeListVerification));
            }
        }
        return walletAbstracts;
    }

    public void updateWallet(WalletAbstract wallet) {
        String where = DatabaseHelper.WALLET_ID + "=" + wallet.getId();
        database.update(DatabaseHelper.WALLET_TABLE_NAME, packValues(wallet), where, null);
    }

    public boolean deleteWallet(WalletAbstract wallet) {
        String where = DatabaseHelper.WALLET_ID + "=?";
        int result = database.delete(DatabaseHelper.WALLET_TABLE_NAME, where, new String[] {Long.toString(wallet.getId())});
        return (result == 1);
    }
}
