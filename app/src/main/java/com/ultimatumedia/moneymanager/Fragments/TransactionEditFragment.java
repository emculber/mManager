package com.ultimatumedia.moneymanager.Fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.R;

public class TransactionEditFragment extends Fragment {

    public TransactionEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_edit, container, false);
        Button add = (Button) view.findViewById(R.id.transactionedit_button_add);
        final TextView type = (TextView) view.findViewById(R.id.transactionedit_textview_type);
        final TextView date = (TextView) view.findViewById(R.id.transactionedit_textview_date);
        final TextView amount = (TextView) view.findViewById(R.id.transactionedit_textview_amount);
        final TextView wallet = (TextView) view.findViewById(R.id.transactionedit_textview_wallet);
        final TextView note = (TextView) view.findViewById(R.id.transactionedit_textview_note);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Transaction transaction = new Transaction();
                transaction.type = type.getText().toString();
                transaction.dateTime = date.getText().toString();
                transaction.amount = Double.parseDouble(amount.getText().toString());
                transaction.walletName = wallet.getText().toString();
                transaction.note = note.getText().toString();

                DatabaseLayer databaseLayer = new DatabaseLayer(view.getContext());
                databaseLayer.addNewTransaction(transaction);

                TransactionsFragment transactionsFragment = new TransactionsFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, transactionsFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
