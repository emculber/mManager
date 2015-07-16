package com.ultimatumedia.moneymanager.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ultimatumedia.moneymanager.Adapters.TransactionsBaseAdapter;
import com.ultimatumedia.moneymanager.R;

public class TransactionsFragment extends Fragment {

    public TransactionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        ListView transactions = (ListView) view.findViewById(R.id.transactions_listview_transactsions);
        transactions.setAdapter(new TransactionsBaseAdapter(view.getContext()));

        Button newTransaction = (Button) view.findViewById(R.id.transactions_button_new_transaction);
        newTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionEditFragment transactionEditFragment = new TransactionEditFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, transactionEditFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
