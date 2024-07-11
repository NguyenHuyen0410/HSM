package com.example.hsb.ui.employee.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Employee;
import com.example.hsb.repository.EmployeeRepository;

import lombok.Getter;

public class EmployeeFragmentViewModel extends ViewModel {
    @Getter
    MutableLiveData<Employee> employeeMutableLiveData;
    MutableLiveData<String> toastMessageLiveData;

    public EmployeeFragmentViewModel(@NonNull String accountId){
        employeeMutableLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData(accountId);
    }

    public void initData(String accountId){
        EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
        employeeMutableLiveData = employeeRepository.getEmployee(accountId);
    }

}
