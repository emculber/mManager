package com.ultimatumedia.moneymanager.Fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.Transactions.Transaction;
import com.ultimatumedia.moneymanager.R;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

@EFragment(R.layout.fragment_transaction_edit)
public class TransactionEditFragment extends Fragment {

    @ViewById(R.id.transactionedit_textview_type)
    public TextView type;
    @ViewById(R.id.transactionedit_textview_date)
    public TextView date;
    @ViewById(R.id.transactionedit_textview_amount)
    public TextView amount;
    @ViewById(R.id.transactionedit_textview_wallet)
    public TextView wallet;
    @ViewById(R.id.transactionedit_textview_note)
    public TextView note;

    @Click(R.id.transactionedit_button_add)
    public void AddTranscationClicked() {
        GenerateTransaction();
        getActivity().onBackPressed();
    }

    private void GenerateTransaction() {
        Transaction transaction = new Transaction();
        transaction.type = type.getText().toString();
        transaction.dateTime = date.getText().toString();
        transaction.amount = Double.parseDouble(amount.getText().toString());
        transaction.walletName = wallet.getText().toString();
        transaction.note = note.getText().toString();

        WalletManagerSubject.getInstance(getView().getContext()).newTransaction(getView().getContext(), transaction);
    }
}
