package com.example.hsb.ui.home_customer.activity.edit_order_service_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.repository.ServiceBillDetailRepository;

import java.util.List;

import lombok.Getter;

public class OrderServiceActivityViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<ServiceBillDetail> mServiceBillDetail = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private final ServiceBillDetailRepository serviceBillDetailRepository;

    public OrderServiceActivityViewModel() {
        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
    }


    public void createServiceBillDetail(ServiceBillDetail serviceBillDetail) {
        serviceBillDetailRepository.createServiceBillDetail(serviceBillDetail, new ServiceBillDetailRepository.CreateServiceBillDetailCallback() {
            @Override
            public void onCreateSuccess(ServiceBillDetail newServiceBillDetail) {
                mServiceBillDetail.postValue(newServiceBillDetail);
                toastMessageLiveData.postValue("ServiceBillDetail created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create serviceBillDetail failed: " + errorMessage);
            }
        });
    }

}