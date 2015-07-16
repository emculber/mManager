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
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.NormalWallet;
import com.ultimatumedia.moneymanager.ObserverChildren.WalletManager;
import com.ultimatumedia.moneymanager.R;

public class WalletEditFragment extends Fragment {

    public WalletEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_edit, container, false);
        Button add = (Button) view.findViewById(R.id.walletedit_button_add);
        final TextView name = (TextView) view.findViewById(R.id.walletedit_textview_name);
        final TextView percent = (TextView) view.findViewById(R.id.walletedit_textview_percent);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WalletAbstract walletAbstract = new NormalWallet(view.getContext(), WalletManager.getInstance(view.getContext()));
                walletAbstract.setName(name.getText().toString());
                walletAbstract.setPercent(Double.parseDouble(percent.getText().toString()), true);
                walletAbstract.setSubscribe(true);
                DatabaseLayer databaseLayer = new DatabaseLayer(view.getContext());
                databaseLayer.addWallet(walletAbstract);

                WalletsFragment walletsFragment = new WalletsFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, walletsFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
