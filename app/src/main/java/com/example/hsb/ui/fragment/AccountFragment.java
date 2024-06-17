package com.example.hsb.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.AccountRole;
import com.example.hsb.entities.Role;
import com.example.hsb.ui.adapter.AccountAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment{

    List<Account> accountList = new ArrayList<>();
    List<Role> roleList = new ArrayList<>();
    List<AccountRole> accountRoleList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        roleList.add(new Role(1, "MANAGER", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role(2, "RECEPTIONIST", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role(3, "CUSTOMER", false, LocalDateTime.now(), LocalDateTime.now()));

        accountRoleList.add(new AccountRole(1, 1, 1, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(2, 2, 2, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(3, 3, 3, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(4, 4, 3, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(5, 5, 3, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(6, 6, 3, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(7, 7, 3, false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole(8, 8, 3, false, LocalDateTime.now(), LocalDateTime.now()));

        // Generating 9 accounts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            accountList.add(new Account(1, "John Doe", "john.doe@example.com", "password1", "active",
                     R.drawable.android_image_1, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(2, "Jane Smith", "jane.smith@example.com", "password2", "active",
                     R.drawable.android_image_2, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(3, "Michael Johnson", "michael.johnson@example.com", "password3", "active",
                     R.drawable.android_image_3, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(4, "Emily Brown", "emily.brown@example.com", "password4", "active",
                     R.drawable.android_image_4, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(5, "William Davis", "william.davis@example.com", "password5", "active",
                     R.drawable.android_image_5, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(6, "Olivia Martinez", "olivia.martinez@example.com", "password6", "active",
                     R.drawable.android_image_6, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(7, "James Wilson", "james.wilson@example.com", "password7", "terminated",
                     R.drawable.android_image_7, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(8, "Sophia Anderson", "sophia.anderson@example.com", "password8", "terminated",
                     R.drawable.android_image_8, false, LocalDateTime.now(), LocalDateTime.now()));
        }
        RecyclerView r = view.findViewById(R.id.account_list);
        AccountAdapter adapter = new AccountAdapter(accountList, requireContext(), accountRoleList, roleList);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));
        r.setAdapter(adapter);
        // Inflate the layout for this fragment

        return view;
    }
}