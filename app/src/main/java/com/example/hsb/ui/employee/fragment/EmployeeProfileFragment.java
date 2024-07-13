package com.example.hsb.ui.employee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hsb.R;
import com.example.hsb.entities.Employee;
import com.example.hsb.record.AccountRecord;
import com.example.hsb.storage.SharedPrefManager;
import com.example.hsb.ui.auth.activity.LoginActivity;
import com.example.hsb.ui.employee.activity.EditProfileActivity;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;

public class EmployeeProfileFragment extends Fragment {
    ImageView accountAvt;
    TextView profileName, roleName, fieldDob, fieldGender, fieldAddress, fieldPhoneNumber, fieldStartWorkingFrom, tvLogout;
    LinearLayout updateProfile;
    EmployeeFragmentViewModel employeeFragmentViewModel;

    private void bindingView(View view) {
        accountAvt = view.findViewById(R.id.account_avt);
        profileName = view.findViewById(R.id.profile_name);
        roleName = view.findViewById(R.id.room_name);
        fieldDob = view.findViewById(R.id.field_dob_content);
        fieldGender = view.findViewById(R.id.field_gd_content);
        fieldAddress = view.findViewById(R.id.field_address_content);
        fieldPhoneNumber = view.findViewById(R.id.field_pn_content);
        fieldStartWorkingFrom = view.findViewById(R.id.field_swf_content);
        updateProfile = view.findViewById(R.id.profile_update);
        view.findViewById(R.id.logout).setOnClickListener(v -> logout());

        updateProfile.setOnClickListener(v -> {
            Employee employee = employeeFragmentViewModel.getEmployeeMutableLiveData().getValue();
            if (employee != null) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("profileInfo", employee);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bindingView(view);
        AccountRecord currentAccount = SharedPrefManager.getInstance().get("account", AccountRecord.class);
        employeeFragmentViewModel = new EmployeeFragmentViewModel(currentAccount.getId());
        employeeFragmentViewModel.getEmployeeMutableLiveData().observe(getViewLifecycleOwner(), this::updateUI);
        return view;
    }

    private void updateUI(Employee employee) {
        if (employee == null) return;
        String imageUrl = "https://hotel-service-manage.pockethost.io/api/files/s1fvh4cvz1v4k80/" + employee.getId() + "/" + employee.getProfileImage() + "?token=";
        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(accountAvt);
        profileName.setText(employee.getFirstName() + " " + employee.getLastName());
        roleName.setText(employee.getAccount().getRole().getName());
        if(!employee.getDob().equals(LocalDateTime.MIN)){
            fieldDob.setText(DateUtil.localDateTimeToString(employee.getDob()));
        } else{
            fieldDob.setText("");
        }
        fieldGender.setText(employee.getGender());
        fieldAddress.setText(employee.getAddress());
        fieldPhoneNumber.setText(employee.getPhoneNumber());
        if(!employee.getStartWorkDate().equals(LocalDateTime.MIN)){
            fieldStartWorkingFrom.setText(DateUtil.localDateTimeToString(employee.getStartWorkDate()));
        } else{
            fieldDob.setText("");
        }

    }

    private void logout() {
        SharedPrefManager.getInstance().clear();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

