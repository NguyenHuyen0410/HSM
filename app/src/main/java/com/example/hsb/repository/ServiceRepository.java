package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Service;
import com.example.hsb.record.ServiceRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository {
    private static ServiceRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Service>> mListServiceLiveData = new MutableLiveData<>();

    public static ServiceRepository getInstance() {
        if (instance == null) {
            instance = new ServiceRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Service>> getServiceList() {
        fetchServiceList();
        return mListServiceLiveData;
    }

    private void fetchServiceList() {
        List<Service> serviceList = new ArrayList<>();
        Call<ListResponse<ServiceRecord>> call = RetrofitClient.getInstance().getServicesServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<ServiceRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceRecord>> call, @NonNull Response<ListResponse<ServiceRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ServiceRecord> records = response.body().getItems();
                    for (ServiceRecord record : records) {
                        Service service = new Service(
                                record.getId(),
                                record.getName(),
                                record.getImage(),
                                record.getDescription(),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getStartTime()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getEndTime()),
                                record.getRemark(),
                                record.isDeleted(),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated())
                        );
                        serviceList.add(service);
                    }
                    mListServiceLiveData.setValue(serviceList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditServiceCallback {
        void onEditSuccess(Service updatedService);

        void onEditFailure(String errorMessage);
    }

    public interface CreateServiceCallback {
        void onCreateSuccess(Service newService);

        void onCreateFailure(String errorMessage);
    }

    public interface DeleteServiceCallback {
        void onDeleteSuccess();

        void onDeleteFailure(String errorMessage);
    }

    public void editService(Service service, EditServiceCallback callback) {
        ServiceRecord serviceRecord = setServiceRecord(service);
        Call<ServiceRecord> call = RetrofitClient.getInstance().getServicesServiceApi().updateRecord(service.getId(), serviceRecord);
        call.enqueue(new Callback<ServiceRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceRecord> call, @NonNull Response<ServiceRecord> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServiceRecord serviceResponse = response.body();
                    Service updatedService = new Service(
                            serviceResponse.getId(),
                            serviceResponse.getName(),
                            serviceResponse.getImage(),
                            serviceResponse.getDescription(),
                            DateUtil.apiDateTimeStringToLocalDateTime(serviceResponse.getStartTime()),
                            DateUtil.apiDateTimeStringToLocalDateTime(serviceResponse.getEndTime()),
                            serviceResponse.getRemark(),
                            serviceResponse.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(serviceResponse.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(serviceResponse.getUpdated())
                    );
                    callback.onEditSuccess(updatedService);
                } else {
                    callback.onEditFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceRecord> call, @NonNull Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createService(Service service, CreateServiceCallback callback) {
        ServiceRecord serviceRecord = setServiceRecord(service);
        Call<ListResponse<ServiceRecord>> call = RetrofitClient.getInstance().getServicesServiceApi().createRecord(serviceRecord);
        call.enqueue(new Callback<ListResponse<ServiceRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceRecord>> call, @NonNull Response<ListResponse<ServiceRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<ServiceRecord> serviceResponse = response.body();
                    ServiceRecord record = serviceResponse.getItems().get(0);
                    Service newService = new Service(
                            record.getId(),
                            record.getName(),
                            record.getImage(),
                            record.getDescription(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getStartTime()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getEndTime()),
                            record.getRemark(),
                            record.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated())
                    );
                    callback.onCreateSuccess(newService);
                } else {
                    callback.onCreateFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceRecord>> call, @NonNull Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteService(String serviceId, DeleteServiceCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getServicesServiceApi().deleteRecord(serviceId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchServiceList();  // Fetch updated service list after deletion
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

    private ServiceRecord setServiceRecord(Service service) {
        ServiceRecord serviceRecord = new ServiceRecord();
        if (service.getId() != null) {
            serviceRecord.setId(service.getId());
            serviceRecord.setCreated(DateUtil.localDateTimeToString(service.getCreatedDate()));
            serviceRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        } else {
            serviceRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            serviceRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        serviceRecord.setStartTime(DateUtil.localDateTimeToJsonFormat(service.getStartTime()));
        serviceRecord.setEndTime(DateUtil.localDateTimeToJsonFormat(service.getEndTime()));
        serviceRecord.setName(service.getName());
        serviceRecord.setDescription(service.getDescription());
        serviceRecord.setImage(service.getImage());
        serviceRecord.setDeleted(service.isDeleted());
        serviceRecord.setRemark(service.getRemark());
        return serviceRecord;
    }
}