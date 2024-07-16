package com.example.hsb.ui.service.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.Service;
import com.example.hsb.repository.CategoryRepository;
import com.example.hsb.repository.PriceRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class ServiceFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Service>> mListServiceLiveData;
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    private MutableLiveData<List<Price>> mListPriceLiveData;
    // LiveData for toast messages
    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public ServiceFragmentViewModel() {
        mListServiceLiveData = new MutableLiveData<>();
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        mListPriceLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        ServiceRepository serviceRepository = ServiceRepository.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        mListServiceLiveData = serviceRepository.getServiceList();
        mListCategoryLiveData = categoryRepository.getCategoryList();
        PriceRepository priceRepository = PriceRepository.getInstance();
        mListPriceLiveData = priceRepository.getPriceList();

//        priceRepository.getPrice(value, new ServiceBillRepository.FetchServiceBillCallback() {
//            @Override
//            public void onSuccess(ServiceBill serviceBill) {
//                // Use the existing MutableLiveData instance and update its value
//                serviceBillDetailRepository.getServiceBillDetailList(field, serviceBill.getId())
//                        .observeForever(serviceBillDetails -> mListServiceBillDetailLiveData.postValue(serviceBillDetails));
//
//                serviceRepository.getServiceList()
//                        .observeForever(services -> mListServiceLiveData.postValue(services));
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                toastMessageLiveData.setValue("Error fetching service bill: " + t.getMessage());
//            }
//        });
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    public MutableLiveData<List<Price>> getListPriceLiveData() {
        return mListPriceLiveData;
    }


}
