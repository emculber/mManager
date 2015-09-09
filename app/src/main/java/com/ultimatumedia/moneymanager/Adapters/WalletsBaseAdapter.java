package com.ultimatumedia.moneymanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ultimatumedia.moneymanager.DatabaseLayer.DatabaseLayer;
import com.ultimatumedia.moneymanager.Objects.WalletAbstract;
import com.ultimatumedia.moneymanager.Objects.Wallets.UnallocatedWallet;
import com.ultimatumedia.moneymanager.R;

import java.util.ArrayList;

/**
 * Created by Erik on 6/20/15.
 */
public class WalletsBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<WalletAbstract> walletAbstracts = new ArrayList<WalletAbstract>();

    public WalletsBaseAdapter(Context context) {
        this.context = context;
        walletAbstracts = DatabaseLayer.getWallets(context);
        walletAbstracts.remove(0);
        walletAbstracts.add(UnallocatedWallet.getInstance());
    }

    @Override
    public int getCount() {
        return walletAbstracts.size();
    }

    @Override
    public Object getItem(int i) {
        return walletAbstracts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return walletAbstracts.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        WalletHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.gridview_wallet_item, null);
            viewHolder = new WalletHolder();
            viewHolder.walletName = (TextView) view.findViewById(R.id.gridview_wallet_item_name);
            viewHolder.walletPercent = (TextView) view.findViewById(R.id.gridview_wallet_item_percent);
            viewHolder.walletExpense = (TextView) view.findViewById(R.id.gridview_wallet_item_expense);
            viewHolder.walletIncome = (TextView) view.findViewById(R.id.gridview_wallet_item_income);
            viewHolder.walletAmount = (TextView) view.findViewById(R.id.gridview_wallet_item_current_amount);

            view.setTag(viewHolder);
        } else {
            viewHolder = (WalletHolder) view.getTag();
        }

        viewHolder.walletName.setText(walletAbstracts.get(position).getName() + ": ");
        viewHolder.walletPercent.setText(Double.toString(walletAbstracts.get(position).getPercent()) + "%");
        viewHolder.walletExpense.setText("$" + Double.toString(walletAbstracts.get(position).getExpense()));
        viewHolder.walletIncome.setText("$" + Double.toString(walletAbstracts.get(position).getIncome()));
        viewHolder.walletAmount.setText("$" + Double.toString(walletAbstracts.get(position).getAmount()));

        if(walletAbstracts.get(position).getAmount() < 0)
            viewHolder.walletAmount.setTextColor(view.getResources().getColor(R.color.darkred));
        else if(walletAbstracts.get(position).getAmount() > 0)
            viewHolder.walletAmount.setTextColor(view.getResources().getColor(R.color.darkgreen));
        return view;
    }

    private class WalletHolder {
        public TextView walletName;
        public TextView walletPercent;
        public TextView walletExpense;
        public TextView walletIncome;
        public TextView walletAmount;
    }
}
