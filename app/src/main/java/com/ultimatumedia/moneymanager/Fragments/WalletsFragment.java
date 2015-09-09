package com.ultimatumedia.moneymanager.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.Adapters.WalletsBaseAdapter;
import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.MoneyMath.LargeSumConverter;
import com.ultimatumedia.moneymanager.MoneyMath.MoneyMath;
import com.ultimatumedia.moneymanager.R;
import com.ultimatumedia.moneymanager.UIComponents.CircleEqualityProgress;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_wallets)
public class WalletsFragment extends Fragment {

    @ViewById(R.id.wallet_equality_progress)
    public CircleEqualityProgress circleEqualityProgress;
    @ViewById(R.id.wallets_textview_expense)
    public TextView expenseTextView;
    @ViewById(R.id.wallets_textview_income)
    public TextView incomeTextView;
    @ViewById(R.id.wallets_gridview_wallets)
    public GridView wallets;

    private double income;
    private double expense;
    private double total;

    @AfterViews
    public void init() {
        Context context = getView().getContext();

        income = DatabaseLayer.getTotalTransactionIncome(context);
        expense = DatabaseLayer.getTotalTransactionExpense(context);
        total = MoneyMath.addMoney(income, expense);

        initEqualityProgress();
        initTextViews();
        initWalletsGridview(context);
    }

    private void initEqualityProgress() {
        float incomeAngle = (float) ((income / MoneyMath.subtractMoney(income, expense)) * 360.0);
        circleEqualityProgress.updateView(LargeSumConverter.AutoConvert(total), incomeAngle, income, MoneyMath.subtractMoney(income, expense));
    }

    private void initTextViews() {
        expenseTextView.setText(LargeSumConverter.AutoConvert(expense));
        expenseTextView.setTextColor(getResources().getColor(R.color.darkred));

        incomeTextView.setText(LargeSumConverter.AutoConvert(income));
        incomeTextView.setTextColor(getResources().getColor(R.color.darkgreen));
    }

    private void initWalletsGridview(Context context) {
        wallets.setAdapter(new WalletsBaseAdapter(context));
        wallets.setVerticalSpacing(4);
        wallets.setHorizontalSpacing(4);
    }

    @Click(R.id.wallets_button_transaction)
    public void transactionListClicked() {
        getFragmentManager().beginTransaction().replace(
                R.id.fragment_container,
                new TransactionsFragment_().builder().build(),
                getActivity().getFragmentManager().findFragmentById(R.id.fragment_container).getTag() + "-Transactions_Fragment")
                .commit();
    }

    @Click(R.id.wallets_button_wallet)
    public void NewWalletClicked() {
        getFragmentManager().beginTransaction().replace(
                R.id.fragment_container,
                new WalletEditFragment_().builder().build(),
                getActivity().getFragmentManager().findFragmentById(R.id.fragment_container).getTag() + "-Wallet_Edit_Fragment")
                .commit();
    }
}
