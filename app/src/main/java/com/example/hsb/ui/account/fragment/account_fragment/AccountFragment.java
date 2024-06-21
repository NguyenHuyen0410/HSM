package com.example.hsb.ui.account.fragment.account_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.AccountRole;
import com.example.hsb.entities.Role;
import com.example.hsb.ui.account.adapter.AccountAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private List<Account> accountList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private List<AccountRole> accountRoleList = new ArrayList<>();
    private AccountAdapter adapter;
    private AccountFragmentViewModel accountFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        roleList.add(new Role("role1", "MANAGER", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role("role2", "RECEPTIONIST", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role("role3", "CUSTOMER", false, LocalDateTime.now(), LocalDateTime.now()));

        accountFragmentViewModel = new ViewModelProvider(this).get(AccountFragmentViewModel.class);

        // Observe changes in the account list
        accountFragmentViewModel.getListAccountLiveData().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                if (accounts != null) {
                    accountList.clear();
                    accountList.addAll(accounts);
                    adapter.notifyDataSetChanged();
                    System.out.println("Account list updated: " + accountList.size() + " accounts");
                }
            }
        });

        // Observe changes in the account role list
        accountFragmentViewModel.getListAccountRoleLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccountRole>>() {
            @Override
            public void onChanged(List<AccountRole> accountRoles) {
                if (accountRoles != null) {
                    accountRoleList.clear();
                    accountRoleList.addAll(accountRoles);
                    adapter.notifyDataSetChanged();
                    System.out.println("Account role list updated: " + accountRoleList.size() + " account roles");
                }
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.account_list);
        adapter = new AccountAdapter(accountList, requireContext(), accountRoleList, roleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}