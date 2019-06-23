package com.artexceptionals.youreuro.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.helpers.CurrencyHelper;
import com.artexceptionals.youreuro.model.Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>{

    private final Context context;
    private List<Account> balanceList = new ArrayList<>();

    public BalanceAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_account,viewGroup,false);

        return new BalanceViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BalanceViewHolder holder, int index) {

        if (holder != null){
            Account account =  balanceList.get(index);

            holder.netAmount.setText(String.valueOf(account.getBalance()));
            holder.currencySymbol.setText(CurrencyHelper.getSymbol(account.getAccountName()));

            if (account.getBalance() > 0){
                holder.netAmount.setBackgroundColor(context.getResources().getColor(R.color.highlight_green));
                holder.currencySymbol.setBackgroundColor(context.getResources().getColor(R.color.highlight_green));
                holder.totalBalance.setBackgroundColor(context.getResources().getColor(R.color.highlight_green));
            }else {
                holder.netAmount.setBackgroundColor(context.getResources().getColor(R.color.highlight_red));
                holder.currencySymbol.setBackgroundColor(context.getResources().getColor(R.color.highlight_red));
                holder.totalBalance.setBackgroundColor(context.getResources().getColor(R.color.highlight_red));

            }
        }
    }

    @Override
    public int getItemCount() {

        if (balanceList != null) {
            return balanceList.size();
        }
        return 0;
    }

    public List<Account> getBalanceList(){
        return balanceList;
    }
    public void addAccount(Account account) {
        balanceList.add(account);
        notifyDataSetChanged();
    }

    public void deleteAccount(Account account) {
        balanceList.get(balanceList.indexOf(account)).setBalance(balanceList.get(balanceList.indexOf(account)).getBalance() - account.getBalance());
        notifyDataSetChanged();
    }

    public void updateAccounts(List<Account> accountList) {
        balanceList.clear();
        balanceList.addAll(accountList);
        notifyDataSetChanged();
    }

    public void updateAccount(Account account) {

        if (balanceList.contains(account)) {
            account.setBalance(account.getBalance()+balanceList.get(balanceList.indexOf(account)).getBalance());
            balanceList.remove(account);
            balanceList.add(account);
        }else {
            balanceList.add(account);
        }
        notifyDataSetChanged();
    }

    public class BalanceViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.net_balance)
        TextView netAmount;

        @BindView(R.id.currency_symbol)
        TextView currencySymbol;

        @BindView(R.id.total_balance)
        TextView totalBalance;

        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
