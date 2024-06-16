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
import com.example.hsb.ui.adapter.AccountAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment{

    List<Account> accountList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Generating 9 accounts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            accountList.add(new Account(1, "John Doe", "john.doe@example.com", "password1", 1,
                    true, true, R.drawable.android_image_1, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(2, "Jane Smith", "jane.smith@example.com", "password2", 1,
                    true, true, R.drawable.android_image_2, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(3, "Michael Johnson", "michael.johnson@example.com", "password3", 1,
                    true, true, R.drawable.android_image_3, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(4, "Emily Brown", "emily.brown@example.com", "password4", 1,
                    true, true, R.drawable.android_image_4, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(5, "William Davis", "william.davis@example.com", "password5", 1,
                    true, true, R.drawable.android_image_5, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(6, "Olivia Martinez", "olivia.martinez@example.com", "password6", 1,
                    true, true, R.drawable.android_image_6, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(7, "James Wilson", "james.wilson@example.com", "password7", 1,
                    true, true, R.drawable.android_image_7, false, LocalDateTime.now(), LocalDateTime.now()));

            accountList.add(new Account(8, "Sophia Anderson", "sophia.anderson@example.com", "password8", 1,
                    true, true, R.drawable.android_image_8, false, LocalDateTime.now(), LocalDateTime.now()));
        }
        RecyclerView r = view.findViewById(R.id.account_list);
        AccountAdapter adapter = new AccountAdapter(accountList);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));
        r.setAdapter(adapter);
        // Inflate the layout for this fragment

        return view;
    }
}