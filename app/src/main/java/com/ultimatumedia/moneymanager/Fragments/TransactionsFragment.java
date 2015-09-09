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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.Accept;

@EFragment(R.layout.fragment_transactions)
public class TransactionsFragment extends Fragment {

    @ViewById(R.id.transactions_listview_transactsions)
    ListView transactions;

    @AfterViews
    public void init() {
        transactions.setAdapter(new TransactionsBaseAdapter(getView().getContext()));
    }

    @Click(R.id.transactions_button_new_transaction)
    public void addTransactionClicked() {
        getFragmentManager().beginTransaction().replace(
                R.id.fragment_container,
                new TransactionEditFragment_().builder().build(),
                getActivity().getFragmentManager().findFragmentById(R.id.fragment_container).getTag() + "-Transaction_Edit_Fragment")
                .commit();
    }

}
