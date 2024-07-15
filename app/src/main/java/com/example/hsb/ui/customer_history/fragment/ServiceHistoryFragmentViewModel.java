package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBill;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.repository.ServiceBillDetailRepository;
import com.example.hsb.repository.ServiceBillRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class ServiceHistoryFragmentViewModel extends ViewModel {

    private MutableLiveData<List<ServiceBillDetail>> mListServiceBillDetailLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Service>> mListServiceLiveData = new MutableLiveData<>();
    private ServiceBillDetailRepository serviceBillDetailRepository;
    private ServiceRepository serviceRepository;
    private ServiceBillRepository serviceBillRepository;

    @Getter
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public ServiceHistoryFragmentViewModel(String field, String value) {
        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
        serviceRepository = ServiceRepository.getInstance();
        serviceBillRepository = ServiceBillRepository.getInstance();
        initData(field, value);
    }

    public void initData(String field, String value) {
        serviceBillRepository.fetchServiceBillByAccountId(value, new ServiceBillRepository.FetchServiceBillCallback() {
            @Override
            public void onSuccess(ServiceBill serviceBill) {
                // Use the existing MutableLiveData instance and update its value
                serviceBillDetailRepository.getServiceBillDetailList(field, serviceBill.getId())
                        .observeForever(serviceBillDetails -> mListServiceBillDetailLiveData.postValue(serviceBillDetails));

                serviceRepository.getServiceList()
                        .observeForever(services -> mListServiceLiveData.postValue(services));
            }

            @Override
            public void onError(Throwable t) {
                toastMessageLiveData.setValue("Error fetching service bill: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<ServiceBillDetail>> getListServiceBillDetailLiveData() {
        return mListServiceBillDetailLiveData;
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }
}