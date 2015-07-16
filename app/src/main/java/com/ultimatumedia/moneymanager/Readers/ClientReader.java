package com.ultimatumedia.moneymanager.Readers;

import android.content.Context;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.Objects.Wallets.NormalWallet;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;
import com.ultimatumedia.moneymanager.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Erik on 7/14/15.
 */
public class ClientReader {
    public ClientReader() {

    }

    public void LoadAllTransactionFromCSV(Context context, DatabaseLayer databaseLayer) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.inittransactionlist);
        CSVReader csvReader = new CSVReader(inputStream);
        List<String[]> transactionStringList = csvReader.readCSVFile(context);
        for(String[] tran : transactionStringList) {
            Transaction transaction = new Transaction();
            transaction.type = tran[0];
            transaction.dateTime = tran[1];
            transaction.amount = Double.parseDouble(tran[2]);
            transaction.walletName = tran[3];
            transaction.note = tran[4];
            databaseLayer.addNewTransaction(transaction);
        }
    }

    public void LoadAllWalletsFromCSV(Context context, DatabaseLayer databaseLayer) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.initwalletlist);
        CSVReader csvReader = new CSVReader(inputStream);
        List<String[]> wallets = csvReader.readCSVFile(context);
        for(String[] sWallet : wallets) {
            NormalWallet normalWallet = new NormalWallet(context, WalletManager.getInstance(context));
            normalWallet.setName(sWallet[0]);
            normalWallet.setPercent(Double.parseDouble(sWallet[1]), true);
            normalWallet.setSubscribe(true);
            databaseLayer.addWallet(normalWallet);
        }
    }
}
