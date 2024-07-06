package com.example.hsb.ui.home.activity.edit_order_service_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.repository.ServiceBillDetailRepository;

import java.util.List;

public class OrderServiceActivityViewModel extends ViewModel {
    private MutableLiveData<ServiceBillDetail> mServiceBillDetail = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ServiceBillDetail>> mListServiceBillDetailLiveData;
    private ServiceBillDetailRepository serviceBillDetailRepository;

    public OrderServiceActivityViewModel() {
        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
        mListServiceBillDetailLiveData = serviceBillDetailRepository.getServiceBillDetailList();
    }

    public MutableLiveData<ServiceBillDetail> c() {
        return mServiceBillDetail;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    public MutableLiveData<Boolean> getDeleteStatusLiveData() {
        return deleteStatusLiveData;
    }

    public MutableLiveData<List<ServiceBillDetail>> getServiceBillDetailListLiveData() {
        return mListServiceBillDetailLiveData;
    }

    public void editServiceBillDetail(ServiceBillDetail serviceBillDetail) {
        serviceBillDetailRepository.editServiceBillDetail(serviceBillDetail, new ServiceBillDetailRepository.EditServiceBillDetailCallback() {
            @Override
            public void onEditSuccess(ServiceBillDetail updatedServiceBillDetail) {
                mServiceBillDetail.postValue(updatedServiceBillDetail);
                toastMessageLiveData.postValue("ServiceBillDetail updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update serviceBillDetail failed: " + errorMessage);
            }
        });
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

    public void deleteServiceBillDetail(String serviceBillDetailId) {
        serviceBillDetailRepository.deleteServiceBillDetail(serviceBillDetailId, new ServiceBillDetailRepository.DeleteServiceBillDetailCallback() {
            @Override
            public void onDeleteSuccess() {
                deleteStatusLiveData.postValue(true);
            }

            @Override
            public void onDeleteFailure(String errorMessage) {
                toastMessageLiveData.postValue("Delete serviceBillDetail failed: " + errorMessage);
            }
        });
    }
}