package com.example.hsb.ui.history.activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.entities.ServiceBill;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.entities.Employee;
import com.example.hsb.repository.ServiceBillDetailRepository;
import com.example.hsb.repository.RoomRepository;
import com.example.hsb.repository.ServiceBillDetailRepository;
import com.example.hsb.repository.EmployeeRepository;
import com.example.hsb.repository.ServiceBillRepository;

import java.util.List;

import lombok.Getter;

public class EditOrderedServiceDetailViewModel extends ViewModel {
    @Getter
    private MutableLiveData<ServiceBillDetail> mServiceBillDetail= new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    @Getter
    private ServiceBillRepository serviceBillRepository;
    @Getter
    private ServiceBillDetailRepository serviceBillDetailRepository;


    public EditOrderedServiceDetailViewModel() {
        serviceBillDetailRepository = ServiceBillDetailRepository.getInstance();
        serviceBillRepository = ServiceBillRepository.getInstance();
    }

    public MutableLiveData<ServiceBillDetail> getServiceBillDetailLiveData() {
        return mServiceBillDetail;
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

}