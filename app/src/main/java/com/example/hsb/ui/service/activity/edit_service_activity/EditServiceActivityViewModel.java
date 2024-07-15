package com.example.hsb.ui.service.activity.edit_service_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Room;
import com.example.hsb.entities.Service;
import com.example.hsb.repository.AccountRepository;
import com.example.hsb.repository.RoomRepository;
import com.example.hsb.repository.ServiceRepository;

import java.util.List;

import lombok.Getter;

public class EditServiceActivityViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<Service> mService = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    @Getter
    private final MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Service>> mListServiceLiveData;
    private final ServiceRepository serviceRepository;
    private final AccountRepository accountRepository;

    public EditServiceActivityViewModel() {
        serviceRepository = ServiceRepository.getInstance();
        accountRepository = AccountRepository.getInstance();
        mListServiceLiveData = serviceRepository.getServiceList();
    }
    public MutableLiveData<Service> getServiceLiveData() {
        return mService;
    }

    public void editService(Service service){
        serviceRepository.editService(service, new ServiceRepository.EditServiceCallback(){
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
    public void createService(Service service){
        serviceRepository.createService(service,  new ServiceRepository.CreateServiceCallback(){
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
    public void deleteService (String serviceId){
        serviceRepository.deleteService(serviceId, new ServiceRepository.DeleteServiceCallback(){
            @Override
            public void onDeleteSuccess() {
                deleteStatusLiveData.postValue(true);
            }

            @Override
            public void onDeleteFailure(String errorMessage) {
                toastMessageLiveData.postValue("Delete service failed: " + errorMessage);
            }
        });
    }
}
