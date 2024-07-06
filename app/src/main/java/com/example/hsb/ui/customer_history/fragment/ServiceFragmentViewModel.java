package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Service;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

public class ServiceFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Service>> mListServiceLiveData;
    private ServiceRepository serviceRepository;
    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public ServiceFragmentViewModel() {
        mListServiceLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        serviceRepository = ServiceRepository.getInstance();
        mListServiceLiveData = serviceRepository.getServiceList();
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }
}
