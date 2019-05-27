package com.artexceptionals.youreuro.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artexceptionals.youreuro.CustomClickListener;
import com.artexceptionals.youreuro.R;
import com.artexceptionals.youreuro.model.Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{

    private CustomClickListener clickListener;
    private List<Account> accountList = new ArrayList<>();


    @NonNull
    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_account,viewGroup,false);

        return new AccountAdapter.AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountViewHolder holder, int index) {

        if (holder != null){
            Account account =  accountList.get(index);

            holder.accountName.setText(account.getAccountName());
            holder.accountAmount.setText(account.getBalance());
        }
    }

    @Override
    public int getItemCount() {

        if (accountList != null) {
            return accountList.size();
        }
        return 0;
    }

    public void addAccount(Account account) {
        accountList.add(account);
        notifyDataSetChanged();
    }

    public class AccountViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.account_name_display)
        TextView accountName;

        @BindView(R.id.account_amount_display)
        TextView accountAmount;


        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(getLayoutPosition());
        }
    }
}
