package com.example.hsb.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<Account> account;

    public AccountAdapter(List<Account> account) {
        this.account = account;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account, parent, false);
        return new AccountHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account = this.account.get(position);
        holder.images.setImageResource(account.getImages());
        holder.name.setText(account.getName());
        holder.status.setText(String.valueOf(account.getStatus()));
        holder.isActive.setText(String.valueOf(account.isActive()));

        boolean isExpandable = account.isExpanded();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        int pos = position;
        holder.accountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setExpanded(!account.isExpanded());
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return account.size();
    }

    public class AccountHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView status;
        TextView isActive;
        ImageView images;

        ConstraintLayout accountItem;

        ConstraintLayout expandableLayout;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_account_name);
            status = itemView.findViewById(R.id.tv_status);
            isActive = itemView.findViewById(R.id.tv_is_active);
            images = itemView.findViewById(R.id.imv_ava);
            accountItem = itemView.findViewById(R.id.account_item);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
        }
    }

}