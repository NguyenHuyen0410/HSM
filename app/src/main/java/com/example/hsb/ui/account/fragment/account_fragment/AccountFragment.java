package com.example.hsb.ui.account.fragment.account_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.entities.Account;
import com.example.hsb.ui.account.activity.edit_account_activity.EditAccountActivity;
import com.example.hsb.ui.account.adapter.AccountAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    private List<Account> accountList = new ArrayList<>();
    private AccountAdapter adapter;
    private AccountFragmentViewModel accountFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        accountFragmentViewModel = new AccountFragmentViewModel();

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

        RecyclerView recyclerView = view.findViewById(R.id.account_list);
        adapter = new AccountAdapter(accountList, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button addAccountBtn = view.findViewById(R.id.btn_add_account);
        addAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditAccountActivity.class);
                startActivity(i);
            }
        });
    }
}