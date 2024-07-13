package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Room;
import com.example.hsb.entities.Service;
import com.example.hsb.entities.ServiceBill;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.repository.RoomRepository;
import com.example.hsb.repository.ServiceBillDetailRepository;
import com.example.hsb.repository.ServiceBillRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class ServiceHistoryFragmentViewModel extends ViewModel {
    private MutableLiveData<List<ServiceBillDetail>> mListServiceBillDetailLiveData;
    private MutableLiveData<List<Service>> mListServiceLiveData;
    private ServiceBillDetailRepository serviceBillDetailRepository;
    private ServiceRepository serviceRepository;
    private ServiceBillRepository serviceBillRepository;
    // LiveData for toast messages
    @Getter
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
        serviceBillRepository = ServiceBillRepository.getInstance();
        serviceBillRepository.fetchServiceBillByAccountId(value, new ServiceBillRepository.FetchServiceBillCallback() {
            @Override
            public void onSuccess(ServiceBill serviceBill) {
                mListServiceBillDetailLiveData = serviceBillDetailRepository.getServiceBillDetailList(field,serviceBill.getId());
                mListServiceLiveData = serviceRepository.getServiceList();
            }
            @Override
            public void onError(Throwable t) {
                System.out.println(t);
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
