package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.repository.ServiceBillDetailRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

public class ServiceHistoryFragmentViewModel extends ViewModel {
    private MutableLiveData<List<ServiceBillDetail>> mListServiceBillDetailLiveData;
    private MutableLiveData<List<Service>> mListServiceLiveData;
    private ServiceBillDetailRepository serviceBillDetailRepository;
    private ServiceRepository serviceRepository;
    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public ServiceHistoryFragmentViewModel(String field, String value) {
        mListServiceBillDetailLiveData = new MutableLiveData<>();
        mListServiceLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData(field,value);
    }

    public void initData(String field, String value) {
        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
        serviceRepository = ServiceRepository.getInstance();

        mListServiceBillDetailLiveData = serviceBillDetailRepository.getServiceBillDetailList(field,value);
        mListServiceLiveData = serviceRepository.getServiceList();

    }

    public MutableLiveData<List<ServiceBillDetail>> getListServiceBillDetailLiveData() {
        return mListServiceBillDetailLiveData;
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }
}
