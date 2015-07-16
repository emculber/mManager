package com.ultimatumedia.moneymanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;

import java.util.ArrayList;

/**
 * Created by Erik on 6/19/15.
 */
public class TransactionDatabase {
    public final String[] columns = {
            DatabaseHelper.TRANSACTION_LOG_ID,
            DatabaseHelper.TRANSACTION_LOG_TRANSACTION_TYPE,
            DatabaseHelper.TRANSACTION_LOG_DATE_TIME,
            DatabaseHelper.TRANSACTION_LOG_AMOUNT,
            DatabaseHelper.TRANSACTION_LOG_WALLET_NAME,
            DatabaseHelper.TRANSACTION_LOG_NOTE };

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    public TransactionDatabase(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Transaction create(Transaction transaction) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TRANSACTION_LOG_TRANSACTION_TYPE, transaction.type);
        values.put(DatabaseHelper.TRANSACTION_LOG_DATE_TIME, transaction.dateTime);
        values.put(DatabaseHelper.TRANSACTION_LOG_AMOUNT, transaction.amount);
        values.put(DatabaseHelper.TRANSACTION_LOG_WALLET_NAME, transaction.walletName);
        values.put(DatabaseHelper.TRANSACTION_LOG_NOTE, transaction.note);
        long id = database.insert(DatabaseHelper.TRANSACTION_LOG_TABLE_NAME, null, values);
        transaction.id = id;
        return transaction;
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        Cursor cursor = database.query(DatabaseHelper.TRANSACTION_LOG_TABLE_NAME, columns, null, null, null, null, null);
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Transaction transaction = new Transaction();
                transaction.id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_ID));
                transaction.type = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_TRANSACTION_TYPE));
                transaction.dateTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_DATE_TIME));
                transaction.amount = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_AMOUNT));
                transaction.walletName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_WALLET_NAME));
                transaction.note = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TRANSACTION_LOG_NOTE));
                transactions.add(0, transaction);
            }
        }
        return transactions;
    }

    public boolean deleteMessages(Transaction transaction) {
        String where = DatabaseHelper.TRANSACTION_LOG_ID + "=?";
        int result = database.delete(DatabaseHelper.TRANSACTION_LOG_TABLE_NAME, where, new String[] {Long.toString(transaction.id)});
        return (result == 1);
    }
}
