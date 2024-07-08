package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Price;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.record.PriceRecord;
import com.example.hsb.record.ServiceBillDetailRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceBillDetailRepository {
    private static ServiceBillDetailRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ServiceBillDetail>> mListServiceBillDetailLiveData = new MutableLiveData<>();

    public static ServiceBillDetailRepository getInstance() {
        if (instance == null) {
            instance = new ServiceBillDetailRepository();
        }
        return instance;
    }

    public MutableLiveData<List<ServiceBillDetail>> getServiceBillDetailList() {
        fetchServiceBillDetailList();
        return mListServiceBillDetailLiveData;
    }

    private void fetchServiceBillDetailList() {
        List<ServiceBillDetail> serviceBillDetailList = new ArrayList<>();
        Call<ListResponse<ServiceBillDetailRecord>> call = RetrofitClient.getInstance().getServiceBillDetailServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<ServiceBillDetailRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Response<ListResponse<ServiceBillDetailRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ServiceBillDetailRecord> records = response.body().getItems();
                    for (ServiceBillDetailRecord record : records) {
                        // Process each record


                        PriceRecord priceRecord = record.getExpand().getPriceRecord();
                        Price price = new Price(
                                priceRecord.getId(),
                                priceRecord.getServiceId(),
                                Double.parseDouble(priceRecord.getServicePrice()),
                                DateUtil.apiDateTimeStringToLocalDateTime(priceRecord.getStartTime()),
                                DateUtil.apiDateTimeStringToLocalDateTime(priceRecord.getEndTime()),
                                priceRecord.getRemark(),
                                priceRecord.isDeleted(),
                                DateUtil.apiDateTimeStringToLocalDateTime(priceRecord.getCreated()),
                                DateUtil.apiDateTimeStringToLocalDateTime(priceRecord.getUpdated())
                        );

                        ServiceBillDetail serviceBillDetail = new ServiceBillDetail(
                                record.getId(),
                                record.getServiceId(),
                                record.getQuantity(),
                                record.getStatus(),
                                record.getRemark(),
                                record.getBillId(),
                                record.getPriceId(),
                                record.isDeleted(),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated()),
                                price
                        );
                        serviceBillDetailList.add(serviceBillDetail);

                    }

                    // Update LiveData after data is added
                    mListServiceBillDetailLiveData.setValue(serviceBillDetailList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditServiceBillDetailCallback {
        void onEditSuccess(ServiceBillDetail updatedServiceBillDetail);

        void onEditFailure(String errorMessage);
    }

    public interface CreateServiceBillDetailCallback {
        void onCreateSuccess(ServiceBillDetail newServiceBillDetail);

        void onCreateFailure(String errorMessage);
    }

    public interface DeleteServiceBillDetailCallback {
        void onDeleteSuccess();

        void onDeleteFailure(String errorMessage);
    }

    public void editServiceBillDetail(ServiceBillDetail serviceBillDetail, EditServiceBillDetailCallback callback) {
        ServiceBillDetailRecord serviceBillDetailRecord = setServiceBillDetailRecord(serviceBillDetail);
        Call<ListResponse<ServiceBillDetailRecord>> call = RetrofitClient.getInstance().getServiceBillDetailServiceApi().updateRecord(serviceBillDetail.getId(), serviceBillDetailRecord);
        call.enqueue(new Callback<ListResponse<ServiceBillDetailRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Response<ListResponse<ServiceBillDetailRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<ServiceBillDetailRecord> serviceBillDetailResponse = response.body();
                    ServiceBillDetailRecord record = serviceBillDetailResponse.getItems().get(0);
                    ServiceBillDetail updatedServiceBillDetail = new ServiceBillDetail(
                            record.getId(),
                            record.getServiceId(),
                            record.getQuantity(),
                            record.getStatus(),
                            record.getRemark(),
                            record.getBillId(),
                            record.getPriceId(),
                            record.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    callback.onEditSuccess(updatedServiceBillDetail);
                } else {
                    callback.onEditFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createServiceBillDetail(ServiceBillDetail serviceBillDetail, CreateServiceBillDetailCallback callback) {
        ServiceBillDetailRecord serviceBillDetailRecord = setServiceBillDetailRecord(serviceBillDetail);
        Call<ListResponse<ServiceBillDetailRecord>> call = RetrofitClient.getInstance().getServiceBillDetailServiceApi().createRecord(serviceBillDetailRecord);
        call.enqueue(new Callback<ListResponse<ServiceBillDetailRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Response<ListResponse<ServiceBillDetailRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<ServiceBillDetailRecord> serviceBillDetailResponse = response.body();
                    ServiceBillDetailRecord record = serviceBillDetailResponse.getItems().get(0);
                    ServiceBillDetail newServiceBillDetail = new ServiceBillDetail(
                            record.getId(),
                            record.getServiceId(),
                            record.getQuantity(),
                            record.getStatus(),
                            record.getRemark(),
                            record.getBillId(),
                            record.getPriceId(),
                            record.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    callback.onCreateSuccess(newServiceBillDetail);
                } else {
                    callback.onCreateFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceBillDetailRecord>> call, @NonNull Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteServiceBillDetail(String serviceBillDetailId, DeleteServiceBillDetailCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getServiceBillDetailServiceApi().deleteRecord(serviceBillDetailId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchServiceBillDetailList();  // Fetch updated serviceBillDetail list after deletion
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

    private ServiceBillDetailRecord setServiceBillDetailRecord(ServiceBillDetail serviceBillDetail) {
        ServiceBillDetailRecord serviceBillDetailRecord = new ServiceBillDetailRecord();
        if (serviceBillDetail.getId() != null) {
            serviceBillDetailRecord.setId(serviceBillDetail.getId());
            serviceBillDetailRecord.setCreated(DateUtil.localDateTimeToString(serviceBillDetail.getCreatedDate()));
            serviceBillDetailRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        } else {
            serviceBillDetailRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            serviceBillDetailRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        serviceBillDetailRecord.setDeleted(serviceBillDetail.isDeleted());
        serviceBillDetailRecord.setQuantity(serviceBillDetail.getQuantity());
        serviceBillDetailRecord.setRemark(serviceBillDetail.getRemark());
        serviceBillDetailRecord.setStatus(serviceBillDetail.getStatus());
        serviceBillDetailRecord.setServiceId(serviceBillDetail.getServiceId());
        serviceBillDetailRecord.setBillId(serviceBillDetail.getBillId());
        serviceBillDetailRecord.setPriceId(serviceBillDetail.getPriceId());
        return serviceBillDetailRecord;
    }
}