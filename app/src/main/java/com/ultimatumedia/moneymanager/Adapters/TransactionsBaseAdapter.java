package com.ultimatumedia.moneymanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.R;

import java.util.ArrayList;

/**
 * Created by Erik on 6/20/15.
 */
public class TransactionsBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();


    public TransactionsBaseAdapter(Context context) {
        this.context = context;
        DatabaseLayer databaseLayer = new DatabaseLayer(context);

        transactions = databaseLayer.getTransactionList();
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return transactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return transactions.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TransactionHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.listview_transaction_item, null);
            viewHolder = new TransactionHolder();
            viewHolder.transactiontype = (TextView) view.findViewById(R.id.listview_transaction_item_textview_type);
            viewHolder.transactionDate = (TextView) view.findViewById(R.id.listview_transaction_item_textview_date);
            viewHolder.transactionAmount = (TextView) view.findViewById(R.id.listview_transaction_item_textview_amount);
            viewHolder.transactionWalletName = (TextView) view.findViewById(R.id.listview_transaction_item_textview_wallet);
            viewHolder.transactionNote = (TextView) view.findViewById(R.id.listview_transaction_item_textview_note);

            view.setTag(viewHolder);
        } else {
            viewHolder = (TransactionHolder) view.getTag();
        }

        viewHolder.transactiontype.setText(transactions.get(position).type + " : ");
        viewHolder.transactionDate.setText(transactions.get(position).dateTime);
        viewHolder.transactionAmount.setText("$" + Double.toString(transactions.get(position).amount) + " : ");
        viewHolder.transactionWalletName.setText(transactions.get(position).walletName);
        viewHolder.transactionNote.setText(transactions.get(position).note);

        if(transactions.get(position).amount < 0)
            viewHolder.transactionAmount.setTextColor(view.getResources().getColor(R.color.darkred));
        else if(transactions.get(position).amount > 0)
            viewHolder.transactionAmount.setTextColor(view.getResources().getColor(R.color.darkgreen));
        return view;
    }

    private class TransactionHolder {
        public TextView transactiontype;
        public TextView transactionDate;
        public TextView transactionAmount;
        public TextView transactionWalletName;
        public TextView transactionNote;
    }
}
