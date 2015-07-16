package com.ultimatumedia.moneymanager.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Erik on 6/19/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mmanager.db";

    public static final String TRANSACTION_LOG_TABLE_NAME = "transaction_log";
    public static final String TRANSACTION_LOG_ID = "id";
    public static final String TRANSACTION_LOG_TRANSACTION_TYPE = "transactionType";
    public static final String TRANSACTION_LOG_DATE_TIME = "dateTime";
    public static final String TRANSACTION_LOG_AMOUNT = "amount";
    public static final String TRANSACTION_LOG_WALLET_NAME = "walletName";
    public static final String TRANSACTION_LOG_NOTE = "note";

    public static final String TRANSACTION_LOG_SQL_CREATE =
            "CREATE TABLE " + TRANSACTION_LOG_TABLE_NAME         + " (" +
                    TRANSACTION_LOG_ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TRANSACTION_LOG_TRANSACTION_TYPE   + " TEXT, " +
                    TRANSACTION_LOG_DATE_TIME          + " TEXT, " +
                    TRANSACTION_LOG_AMOUNT             + " REAL, " +
                    TRANSACTION_LOG_WALLET_NAME        + " TEXT, " +
                    TRANSACTION_LOG_NOTE               + " TEXT" +
                    ")";
    private static final String SQL_DELETE_TRANSACTION_LOG =
            "DROP TABLE IF EXISTS " + TRANSACTION_LOG_TABLE_NAME;

    //-------------------------------------------------------------------------------------------

    public static final String WALLET_TABLE_NAME = "wallets";
    public static final String WALLET_ID = "id";
    public static final String WALLET_NAME = "name";
    public static final String WALLET_PERCENT = "percent";
    public static final String WALLET_EXPENSE = "expense";
    public static final String WALLET_INCOME = "income";
    public static final String WALLET_AMOUNT = "amount";
    public static final String WALLET_SUBSCRIBE = "subscribe";
    public static final String WALLET_TYPE = "type";

    public static final String WALLET_SQL_CREATE =
            "CREATE TABLE " + WALLET_TABLE_NAME         + " (" +
                    WALLET_ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WALLET_NAME               + " TEXT, " +
                    WALLET_PERCENT            + " REAL, " +
                    WALLET_EXPENSE            + " REAL, " +
                    WALLET_INCOME             + " REAL, " +
                    WALLET_AMOUNT             + " REAL, " +
                    WALLET_SUBSCRIBE          + " INTEGER, " +
                    WALLET_TYPE               + " TEXT" +
                    ")";

    private static final String SQL_DELETE_WALLETS =
            "DROP TABLE IF EXISTS " + WALLET_TABLE_NAME;

    //-------------------------------------------------------------------------------------------

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRANSACTION_LOG_SQL_CREATE);
        db.execSQL(WALLET_SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TRANSACTION_LOG);
        db.execSQL(SQL_DELETE_WALLETS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
