package com.ultimatumedia.moneymanager.Fragments;

import android.app.Fragment;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.DebtWallet;
import com.ultimatumedia.moneymanager.Objects.Wallets.NormalWallet;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;
import com.ultimatumedia.moneymanager.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_wallet_edit)
public class WalletEditFragment extends Fragment {

    @ViewById(R.id.walletedit_textview_name)
    public TextView name;
    @ViewById(R.id.walletedit_textview_percent)
    public TextView percent;

    @Click(R.id.walletedit_button_add)
    public void walletEditClicked(){
        generateNormalWallet();
        getActivity().onBackPressed();
    }

    private void generateNormalWallet() {
        //TEST CODE:
        WalletAbstract debtWallet = new DebtWallet(getView().getContext(), WalletManagerSubject.getInstance(getView().getContext()));
        debtWallet.setPercent(25, true);

        WalletAbstract normalWallet = new NormalWallet(getView().getContext(), WalletManagerSubject.getInstance(getView().getContext()));
        normalWallet.setName(name.getText().toString());
        normalWallet.setPercent(Double.parseDouble(percent.getText().toString()), true);
        normalWallet.setSubscribe(true);
        DatabaseLayer.addWallet(getView().getContext(), normalWallet);
    }
}
