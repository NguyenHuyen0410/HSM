package com.example.hsb.ui.service.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.entities.Room;
import com.example.hsb.entities.Service;
import com.example.hsb.repository.CategoryRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class ServiceFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Service>> mListServiceLiveData;
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    // LiveData for toast messages
    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public ServiceFragmentViewModel() {
        mListServiceLiveData = new MutableLiveData<>();
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        ServiceRepository serviceRepository = ServiceRepository.getInstance();
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        mListServiceLiveData = serviceRepository.getServiceList();
        mListCategoryLiveData = categoryRepository.getCategoryList();
    }

    public MutableLiveData<List<Service>> getListServiceLiveData() {
        return mListServiceLiveData;
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

}
