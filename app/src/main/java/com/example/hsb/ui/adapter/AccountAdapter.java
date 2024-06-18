package com.example.hsb.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.AccountRole;
import com.example.hsb.entities.Role;
import com.example.hsb.ui.activity.EditAccountActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<Account> account;

    private List<AccountRole> accountRoles;

    private List<Role> roles;

    private Context context;

    private static String hexColor;

    public AccountAdapter(List<Account> account, Context context, List<AccountRole> accountRoles,
                          List<Role> roles) {
        this.context = context;
        this.account = account;
        this.accountRoles = accountRoles;
        this.roles = roles;
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
        AccountRole accountRole = this.accountRoles.get(position);
        Glide.with(context)
                .load(account.getImages()) // replace with your image source
                .apply(RequestOptions.circleCropTransform())
                .into(holder.images);

        holder.name.setText(account.getName());
        String status = account.getAccountStatus();
        holder.status.setText(status);
        if(status.equals("active")) hexColor = "#32BA7C";
        else if(status.equals("terminated")) hexColor = "#F44336";
        holder.status.setTextColor(Color.parseColor(hexColor));
        String roleName = "";
        for(Role role: roles){
            if(accountRole.getRoleId() == role.getId()){
                roleName = "ROLE: "+role.getName();
            }
        }

        holder.role.setText(roleName);

        String createdDate = "Created Date: "+localDateTimeToString(account.getCreatedDate());
        holder.createdDate.setText(createdDate);
        String lastModifiedDate = "Last Modified Date: "+localDateTimeToString(account.getLastModifiedDate());
        holder.lastModifedDate.setText(lastModifiedDate);

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

        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAccountActivity.class);
            intent.putExtra("account", account);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return account.size();
    }

    public class AccountHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView status;
        TextView role;
        TextView createdDate;
        TextView lastModifedDate;
        ImageView icon;
        ImageView images;
        ConstraintLayout accountItem;
        ConstraintLayout expandableLayout;

        Button button;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_account_name);
            role = itemView.findViewById(R.id.tv_role);
            status = itemView.findViewById(R.id.tv_account_status);
            icon = itemView.findViewById(R.id.status_icon);
            createdDate = itemView.findViewById(R.id.tv_created_date);
            lastModifedDate = itemView.findViewById(R.id.tv_last_modified_date);
            images = itemView.findViewById(R.id.imv_ava);
            accountItem = itemView.findViewById(R.id.account_item);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            button = itemView.findViewById(R.id.button);
        }
    }

    public static String localDateTimeToString(LocalDateTime input) {
        // Define the output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Format the LocalDateTime object to the desired format
        return input.format(formatter);
    }
}