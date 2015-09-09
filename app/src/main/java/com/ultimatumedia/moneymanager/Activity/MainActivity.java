package com.ultimatumedia.moneymanager.Activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ultimatumedia.moneymanager.Fragments.TransactionEditFragment_;
import com.ultimatumedia.moneymanager.Fragments.TransactionsFragment_;
import com.ultimatumedia.moneymanager.Fragments.WalletEditFragment_;
import com.ultimatumedia.moneymanager.Fragments.WalletsFragment_;
import com.ultimatumedia.moneymanager.R;
import com.ultimatumedia.moneymanager.Subjects.WalletManagerSubject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    public Toolbar toolbar;

    @AfterViews
    public void init() {
        setSupportActionBar(toolbar);
        WalletManagerSubject.getInstance(getApplicationContext()); //Init Singleton
        initFragment();
    }

    public void initFragment() {
        getFragmentManager().beginTransaction().add(R.id.fragment_container, new WalletsFragment_().builder().build(), "Wallets_Fragment").commit();
    }

    @Override
    public void onBackPressed() {
        String tag = getFragmentManager().findFragmentById(R.id.fragment_container).getTag();
        String newTag = "";
        String[] tagSplit = tag.split("-");
        if(tagSplit.length - 2 >= 0) {
            tag = tagSplit[tagSplit.length - 2];
            newTag += tagSplit[0];
            for(int i = 1; i < tagSplit.length - 1; i++) {
                newTag += ("-"+tagSplit[i]);
            }
        }else {
            tag = "";
        }
        switch (tag) {
            case "Wallets_Fragment": {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletsFragment_().builder().build(), newTag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
            break;
            case "Wallet_Edit_Fragment": {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new WalletEditFragment_().builder().build(), newTag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }break;
            case "Transactions_Fragment": {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TransactionsFragment_().builder().build(), newTag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
            break;
            case "Transaction_Edit_Fragment": {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TransactionEditFragment_().builder().build(), newTag).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
            break;
            default: {
                super.onBackPressed();
            }
        }
    }
}
