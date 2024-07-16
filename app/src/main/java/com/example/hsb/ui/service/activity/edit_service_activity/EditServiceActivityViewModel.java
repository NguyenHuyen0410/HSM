package com.example.hsb.ui.service.activity.edit_service_activity;

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

public class EditServiceActivityViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<Service> mService = new MutableLiveData<>();
    private final MutableLiveData<Price> mPrice = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Service>> mListServiceLiveData;
    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRepository priceRepository;
    private MutableLiveData<List<Category>> mListCategoryLiveData;

    public EditServiceActivityViewModel() {
        serviceRepository = ServiceRepository.getInstance();
        categoryRepository = CategoryRepository.getInstance();
        priceRepository = PriceRepository.getInstance();
        mListCategoryLiveData = new MutableLiveData<>();
        mListServiceLiveData = serviceRepository.getServiceList();
        mListCategoryLiveData = categoryRepository.getCategoryList();
    }

    public MutableLiveData<Service> getServiceLiveData() {
        return mService;
    }

    public void editService(Service service) {
        serviceRepository.editService(service, new ServiceRepository.EditServiceCallback() {
            @Override
            public void onEditSuccess(Service updatedService) {
                mService.postValue(updatedService);
                toastMessageLiveData.postValue("Service updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update service failed: " + errorMessage);
            }
        });
    }

    public void createService(Service service) {
        serviceRepository.createService(service, new ServiceRepository.CreateServiceCallback() {
            @Override
            public void onCreateSuccess(Service newService) {
                mService.postValue(newService);
                toastMessageLiveData.postValue("Service created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create service failed: " + errorMessage);
            }
        });
    }

    public void createPrice(Price price) {
        priceRepository.createPrice(price, new PriceRepository.CreatePriceCallback() {
            @Override
            public void onCreateSuccess(Price newPrice) {
                mPrice.postValue(newPrice);
                toastMessageLiveData.postValue("Price created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create price failed: " + errorMessage);
            }
        });
    }

    public void editPrice(Price price) {
        priceRepository.editPrice(price, new PriceRepository.EditPriceCallback() {
            @Override
            public void onEditSuccess(Price updatedPrice) {
                mPrice.postValue(updatedPrice);
                toastMessageLiveData.postValue("Price updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update price failed: " + errorMessage);
            }
        });
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }
}