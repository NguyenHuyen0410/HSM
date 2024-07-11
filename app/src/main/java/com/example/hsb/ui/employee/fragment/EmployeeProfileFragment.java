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
import com.example.hsb.ui.employee.activity.EditProfileActivity;
import com.example.hsb.utils.DateUtil;

public class EmployeeProfileFragment extends Fragment {
    ImageView accountAvt;
    TextView profileName;
    TextView roleName;
    TextView fieldDob;
    TextView fieldGender;
    TextView fieldAddress;
    TextView fieldPhoneNumber;
    TextView fieldStartWorkingFrom;
    LinearLayout updateProfile;
    EmployeeFragmentViewModel employeeFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize ViewModel
        employeeFragmentViewModel = new EmployeeFragmentViewModel("2pg3s16qnj2myjc");
        employeeFragmentViewModel.getEmployeeMutableLiveData().observe(getViewLifecycleOwner(), employee -> {
            if (employee != null) {
                String imageUrl = "https://hotel-service-manage.pockethost.io/api/files/s1fvh4cvz1v4k80/"+employee.getId()+"/" +employee.getProfileImage()+"?token=";
                Glide.with(EmployeeProfileFragment.this)
                        .load(imageUrl)
                        .apply(RequestOptions.circleCropTransform())
                        .into(accountAvt);
                profileName.setText(employee.getFirstName() + " " + employee.getLastName());
                roleName.setText(employee.getAccount().getRole().getName());
                fieldDob.setText(DateUtil.localDateTimeToString(employee.getDob()));
                fieldGender.setText(employee.getGender());
                fieldAddress.setText(employee.getAddress());
                fieldPhoneNumber.setText(employee.getPhoneNumber());
                fieldStartWorkingFrom.setText(DateUtil.localDateTimeToString(employee.getStartWorkDate()));

                updateProfile.setOnClickListener(v -> {
                    Intent i = new Intent(getContext() ,EditProfileActivity.class);
                    i.putExtra("profileInfo", employee);
                    startActivity(i);
                });
            }
        });
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accountAvt = view.findViewById(R.id.account_avt);
        profileName = view.findViewById(R.id.profile_name);
        roleName = view.findViewById(R.id.room_name);
        fieldDob = view.findViewById(R.id.field_dob_content);
        fieldGender = view.findViewById(R.id.field_gd_content);
        fieldAddress = view.findViewById(R.id.field_address_content);
        fieldPhoneNumber = view.findViewById(R.id.field_pn_content);
        fieldStartWorkingFrom = view.findViewById(R.id.field_swf_content);
        updateProfile = view.findViewById(R.id.profile_update);
    }
}
