package com.ultimatumedia.moneymanager.Fragments;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.Adapters.WalletsBaseAdapter;
import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.R;
import com.ultimatumedia.moneymanager.UIComponents.CircleEqualityProgress;

public class WalletsFragment extends Fragment {

    public WalletsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallets, container, false);

        CircleEqualityProgress circleEqualityProgress = (CircleEqualityProgress)view.findViewById(R.id.equality_progress);
        //circleEqualityProgress.runingMethod();

        DatabaseLayer databaseLayer = new DatabaseLayer(view.getContext());


        TextView expenseTextView = (TextView) view.findViewById(R.id.wallets_textview_expense);
        TextView incomeTextView = (TextView) view.findViewById(R.id.wallets_textview_income);
        TextView currentAmountTextView = (TextView) view.findViewById(R.id.wallets_textview_current_amount);

        double income = databaseLayer.getTotalTransactionIncome();
        double expense = databaseLayer.getTotalTransactionExpense();
        MoneyMath moneyMath = new MoneyMath();
        double total = moneyMath.addMoney(income, expense);

        float incomeAngle = (float)((income/moneyMath.subtractMoney(income, expense))*360.0);
        circleEqualityProgress.updateView(Double.toString(total), incomeAngle);

        expenseTextView.setText(Double.toString(expense));
        incomeTextView.setText(Double.toString(income));
        currentAmountTextView.setText(Double.toString(total));

        expenseTextView.setTextColor(getResources().getColor(R.color.darkred));
        incomeTextView.setTextColor(getResources().getColor(R.color.darkgreen));

        if((total) < 0)
            currentAmountTextView.setTextColor(getResources().getColor(R.color.darkred));
        else
            currentAmountTextView.setTextColor(getResources().getColor(R.color.darkgreen));

        GridView wallets = (GridView) view.findViewById(R.id.wallets_gridview_wallets);
        wallets.setAdapter(new WalletsBaseAdapter(view.getContext()));
        wallets.setBackgroundColor(Color.LTGRAY);
        wallets.setVerticalSpacing(4);
        wallets.setHorizontalSpacing(4);

        Button transactionList = (Button) view.findViewById(R.id.wallets_button_transaction);
        transactionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionsFragment transactionsFragment = new TransactionsFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, transactionsFragment);
                fragmentTransaction.commit();
            }
        });
        Button newWallet = (Button) view.findViewById(R.id.wallets_button_wallet);
        newWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletEditFragment walletEditFragment = new WalletEditFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, walletEditFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
