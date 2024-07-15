package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.ServiceCategory;
import com.example.hsb.entities.Role;
import com.example.hsb.record.ServiceCategoryRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCategoryRepository {
    private static ServiceCategoryRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ServiceCategory>> mListServiceCategoryLiveData = new MutableLiveData<>();

    public static ServiceCategoryRepository getInstance() {
        if (instance == null) {
            instance = new ServiceCategoryRepository();
        }
        return instance;
    }

    public MutableLiveData<List<ServiceCategory>> getServiceCategoryList() {
        fetchServiceCategoryList();
        return mListServiceCategoryLiveData;
    }

    private void fetchServiceCategoryList() {
        List<ServiceCategory> ServiceCategoryList = new ArrayList<>();
        Call<ListResponse<ServiceCategoryRecord>> call = RetrofitClient.getInstance().getServiceCategoryServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<ServiceCategoryRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceCategoryRecord>> call, @NonNull Response<ListResponse<ServiceCategoryRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ServiceCategoryRecord> records = response.body().getItems();
                    for (ServiceCategoryRecord record : records) {
                        ServiceCategoryList.add(setServiceCategory(record));
                    }
                    mListServiceCategoryLiveData.setValue(ServiceCategoryList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceCategoryRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditServiceCategoryCallback {
        void onEditSuccess(ServiceCategory updatedServiceCategory);
        void onEditFailure(String errorMessage);
    }

    public interface CreateServiceCategoryCallback {
        void onCreateSuccess(ServiceCategory newServiceCategory);
        void onCreateFailure(String errorMessage);
    }

    public interface DeleteServiceCategoryCallback {
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public void editServiceCategory(ServiceCategory ServiceCategory, EditServiceCategoryCallback callback) {
        ServiceCategoryRecord ServiceCategoryRecord = setServiceCategoryRecord(ServiceCategory);
        System.out.println("ServiceCategoryRecord: " + new Gson().toJson(ServiceCategoryRecord));
        Call<ServiceCategoryRecord> call = RetrofitClient.getInstance().getServiceCategoryServiceApi().updateRecord(ServiceCategory.getId(), ServiceCategoryRecord);
        call.enqueue(new Callback<ServiceCategoryRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceCategoryRecord> call, @NonNull Response<ServiceCategoryRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onEditSuccess(setServiceCategory(response.body()));
                } else {
                    callback.onEditFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServiceCategoryRecord> call, @NonNull Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createServiceCategory(ServiceCategory ServiceCategory, CreateServiceCategoryCallback callback) {
        ServiceCategoryRecord ServiceCategoryRecord = setServiceCategoryRecord(ServiceCategory);
        System.out.println("ServiceCategoryRecord: " + new Gson().toJson(ServiceCategoryRecord));
        Call<ServiceCategoryRecord> call = RetrofitClient.getInstance().getServiceCategoryServiceApi().createRecord(ServiceCategoryRecord);
        call.enqueue(new Callback<ServiceCategoryRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceCategoryRecord> call,@NonNull Response<ServiceCategoryRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onCreateSuccess(setServiceCategory(response.body()));
                } else {
                    callback.onCreateFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceCategoryRecord> call,@NonNull Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteServiceCategory(String ServiceCategoryId, DeleteServiceCategoryCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getServiceCategoryServiceApi().deleteRecord(ServiceCategoryId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchServiceCategoryList();  // Fetch updated ServiceCategory list after deletion
                    callback.onDeleteSuccess();
                } else {
                    callback.onDeleteFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onDeleteFailure(t.getMessage());
            }
        });
    }

    private ServiceCategory setServiceCategory(ServiceCategoryRecord record){
        return new ServiceCategory(
                record.getId(),
                record.getCategoryId(),
                record.getServiceId(),
                record.isDeleted(),
                DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated()));
    }

    private ServiceCategoryRecord setServiceCategoryRecord(ServiceCategory ServiceCategory) {
        ServiceCategoryRecord ServiceCategoryRecord = new ServiceCategoryRecord();
        if (ServiceCategory.getId() != null) {
            ServiceCategoryRecord.setId(ServiceCategory.getId());
            ServiceCategoryRecord.setCreated(DateUtil.localDateTimeToString(ServiceCategory.getCreatedDate()));
            ServiceCategoryRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        } else {
            ServiceCategoryRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            ServiceCategoryRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        ServiceCategoryRecord.setCategoryId(ServiceCategory.getCategoryId());
        ServiceCategoryRecord.setServiceId(ServiceCategory.getServiceId());
        ServiceCategoryRecord.setDeleted(ServiceCategory.isDeleted());
        return ServiceCategoryRecord;
    }
}