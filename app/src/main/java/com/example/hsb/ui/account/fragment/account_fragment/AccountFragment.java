package com.example.hsb.ui.account.fragment.account_fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hsb.R;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.response.AccountResponse;
import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Account;
import com.example.hsb.entities.AccountRole;
import com.example.hsb.entities.Role;
import com.example.hsb.ui.account.adapter.AccountAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    List<Account> accountList = new ArrayList<>();
    private List<Role> roleList = new ArrayList<>();
    private List<AccountRole> accountRoleList = new ArrayList<>();
    private AccountAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        roleList.add(new Role("role1", "MANAGER", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role("role2", "RECEPTIONIST", false, LocalDateTime.now(), LocalDateTime.now()));
        roleList.add(new Role("role3", "CUSTOMER", false, LocalDateTime.now(), LocalDateTime.now()));
        callApi();
        RecyclerView recyclerView = view.findViewById(R.id.account_list);
        adapter = new AccountAdapter(accountList, requireContext(), accountRoleList, roleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void callApi() {
        Call<AccountResponse> call = RetrofitClient.getInstance().getAccountServiceApi().getRecords();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AccountRecord> records = response.body().getItems();
                    int index = 0;
                    for (AccountRecord record : records) {
                        index++;
                        // Process each record
                        Account account = new Account(record.getId(), record.getUsername(), "john.doe@example.com",
                                "password9", "active",
                                R.drawable.android_image_1, record.is_deleted(), stringToLocalDateTime(record.getCreated()), stringToLocalDateTime(record.getUpdated()));
                        accountList.add(account);
                        accountRoleList.add(new AccountRole("accountRole" + index, account.getId(), "role1", false, LocalDateTime.now(), LocalDateTime.now()));
                        System.out.println("Added account: " + account.toString());
                    }

                    // Moved the System.out.println statements inside the callback
                    if (!accountList.isEmpty()) {
                        System.out.println("First account: " + accountList.get(0).toString());
                        if (accountList.size() > 1) {
                            System.out.println("Second account: " + accountList.get(1).toString());
                            if (accountList.size() > 2) {
                                System.out.println("Third account: " + accountList.get(2).toString());
                            }
                        }
                    }

                    // Notify adapter about data changes
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the case where the response is not successful
                    Toast.makeText(getActivity(), "Response not successful: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Handle the failure case
                Toast.makeText(getActivity(), "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LocalDateTime stringToLocalDateTime(String l){
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss")
                .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true)
                .appendPattern("'Z'")
                .toFormatter();

        return LocalDateTime.parse(l, formatter);
    }

    private void setStaticData() {

        accountRoleList.add(new AccountRole("accountRole1", "account1", "role1", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole2", "account2", "role2", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole3", "account3", "role3", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole4", "account4", "role3", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole5", "account5", "role3", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole6", "account6", "role3", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole7", "account7", "role3", false, LocalDateTime.now(), LocalDateTime.now()));
        accountRoleList.add(new AccountRole("accountRole8", "account8", "role3", false, LocalDateTime.now(), LocalDateTime.now()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            accountList.add(new Account("account1", "John Doe", "john.doe@example.com", "password1", "active",
                    R.drawable.android_image_1, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account2", "Jane Smith", "jane.smith@example.com", "password2", "active",
                    R.drawable.android_image_2, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account3", "Michael Johnson", "michael.johnson@example.com", "password3", "active",
                    R.drawable.android_image_3, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account4", "Emily Brown", "emily.brown@example.com", "password4", "active",
                    R.drawable.android_image_4, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account5", "William Davis", "william.davis@example.com", "password5", "active",
                    R.drawable.android_image_5, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account6", "Olivia Martinez", "olivia.martinez@example.com", "password6", "active",
                    R.drawable.android_image_6, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account7", "James Wilson", "james.wilson@example.com", "password7", "terminated",
                    R.drawable.android_image_7, false, LocalDateTime.now(), LocalDateTime.now()));
            accountList.add(new Account("account8", "Sophia Anderson", "sophia.anderson@example.com", "password8", "terminated",
                    R.drawable.android_image_8, false, LocalDateTime.now(), LocalDateTime.now()));
        }
    }


}