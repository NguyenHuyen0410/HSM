package com.example.hsb.ui.employee.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Employee;
import com.example.hsb.repository.EmployeeRepository;

import java.io.File;

import okhttp3.MultipartBody;

public class EditProfileActivityViewModel extends ViewModel {
    MutableLiveData<Employee> employeeMutableLiveData;
    MutableLiveData<String> toastMessageLiveData;
    EmployeeRepository employeeRepository;

    public EditProfileActivityViewModel() {
        // Initialize EmployeeRepository
        employeeRepository = EmployeeRepository.getInstance();
        employeeMutableLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
    }

    public void editEmployee(Employee employee){
        employeeRepository.editEmployee(employee, new EmployeeRepository.EditEmployeeCallBack() {
            @Override
            public void onEditSuccess(Employee employee) {
                employeeMutableLiveData.setValue(employee);
                toastMessageLiveData.setValue("Profile updated successfully.");
            }
            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update profile failed: "+errorMessage);
            }
        });
    }

    public void updateProfileImage(String employeeId, MultipartBody.Part avtImage){
        employeeRepository.uploadImage(employeeId, avtImage, new EmployeeRepository.UpdateProfileImageCallback() {
            @Override
            public void onUpdateSuccess(String imageName) {
                toastMessageLiveData.postValue("Image updated successfully.");
            }
            @Override
            public void onUpdateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update image failed: " + errorMessage);
            }
        });
    }
}
