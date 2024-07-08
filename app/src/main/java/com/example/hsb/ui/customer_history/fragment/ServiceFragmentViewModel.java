package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Service;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class ServiceFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Service>> mListServiceLiveData;
    // LiveData for toast messages
    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public ServiceFragmentViewModel() {
        mListServiceLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        ServiceRepository serviceRepository = ServiceRepository.getInstance();
        mListServiceLiveData = serviceRepository.getServiceList();
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }

}
