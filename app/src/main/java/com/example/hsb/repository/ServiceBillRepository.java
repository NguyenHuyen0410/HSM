package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Room;
import com.example.hsb.entities.ServiceBill;
import com.example.hsb.entities.ServiceBillDetail;
import com.example.hsb.record.ServiceBillDetailRecord;
import com.example.hsb.record.ServiceBillRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceBillRepository {

    public static ServiceBillRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ServiceBill>> mServiceBillListLiveData = new MutableLiveData<>();

    public static ServiceBillRepository getInstance(){
        if(instance==null){
            instance = new ServiceBillRepository();
        }
        return instance;
    }

    public MutableLiveData<List<ServiceBill>>  getServiceBill(String field, String value){
        fetchServiceBill(field, value);
        return mServiceBillListLiveData;
    }

    public void fetchServiceBill(String field, String value){
        String expand = "room_id";
        String filter;
        if (field == null || field.isEmpty()) {
            filter = null;
        } else if (Objects.equals(value, "false") || Objects.equals(value, "true")) {
            filter = field+"="+value;
        } else {
            filter =  field+"='"+value+"'";
        }
        List<ServiceBill> serviceBillList = new ArrayList<>();
        Call<ListResponse<ServiceBillRecord>> call = RetrofitClient.getInstance().getServiceBillServiceApi().getRecords(expand, filter);
        call.enqueue(new Callback<ListResponse<ServiceBillRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<ServiceBillRecord>> call, @NonNull Response<ListResponse<ServiceBillRecord>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ServiceBillRecord> records = response.body().getItems();

                    for (ServiceBillRecord record : records) {
                        serviceBillList.add(setServiceBill(record));
                    }
                    mServiceBillListLiveData.setValue(serviceBillList);
                } else{
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ListResponse<ServiceBillRecord>> call, @NonNull Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditServiceBillCallBack{
        void onEditSuccess(ServiceBill updatedServiceBill);
        void onEditFailure(String errorMessage);
    }

    public interface CreateServiceBillCallBack{
        void onCreateSuccess(ServiceBill newServiceBill);
        void onCreateFailure(String errorMessage);
    }

    public interface DeleteServiceBillCallBack{
        void onDeleteSuccess();
        void onDeleteFailure(String errorMessage);
    }

    public void editServiceBill(ServiceBill serviceBill, EditServiceBillCallBack editServiceBillCallBack){
        Call<ServiceBillRecord> call = RetrofitClient.getInstance().getServiceBillServiceApi().updateRecord(serviceBill.getId(), setServiceBillRecord(serviceBill));
        call.enqueue(new Callback<ServiceBillRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceBillRecord> call, @NonNull Response<ServiceBillRecord> response) {
                if(response.isSuccessful() && response.body() != null){
                    editServiceBillCallBack.onEditSuccess(setServiceBill(response.body()));
                } else{
                    editServiceBillCallBack.onEditFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServiceBillRecord> call, @NonNull Throwable t) {
                editServiceBillCallBack.onEditFailure(t.getMessage());
            }
        });
    }

    public void createServiceBill(ServiceBill serviceBill, CreateServiceBillCallBack createServiceBillCallBack){
        Call<ServiceBillRecord> call = RetrofitClient.getInstance().getServiceBillServiceApi().createRecord(setServiceBillRecord(serviceBill));
        call.enqueue(new Callback<ServiceBillRecord>() {
            @Override
            public void onResponse(@NonNull Call<ServiceBillRecord> call, @NonNull Response<ServiceBillRecord> response) {
                if(response.isSuccessful() && response.body() != null){
                    createServiceBillCallBack.onCreateSuccess(setServiceBill(response.body()));
                } else{
                    createServiceBillCallBack.onCreateFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServiceBillRecord> call, @NonNull Throwable t) {
                createServiceBillCallBack.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteServiceBill(String serviceBillId, DeleteServiceBillCallBack deleteServiceBillCallBack){
        Call<Void> call = RetrofitClient.getInstance().getServiceBillServiceApi().deleteRecord(serviceBillId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    deleteServiceBillCallBack.onDeleteSuccess();
                } else{
                    deleteServiceBillCallBack.onDeleteFailure(response.message());
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                deleteServiceBillCallBack.onDeleteFailure(t.getMessage());
            }
        });
    }

    public ServiceBillRecord setServiceBillRecord(ServiceBill serviceBill){
        ServiceBillRecord serviceBillRecord = new ServiceBillRecord();
        if(!serviceBill.getId().isEmpty()){
            serviceBillRecord.setId(serviceBill.getId());
            serviceBillRecord.setCreated(DateUtil.localDateTimeToJsonFormat(serviceBill.getCreated()));
            serviceBillRecord.setUpdated(DateUtil.localDateTimeToJsonFormat(serviceBill.getUpdated()));
        } else{
            serviceBillRecord.setCreated(DateUtil.localDateTimeToJsonFormat(LocalDateTime.now()));
            serviceBillRecord.setUpdated(DateUtil.localDateTimeToJsonFormat(LocalDateTime.now()));
        }
        serviceBillRecord.set_deleted(serviceBill.is_deleted());
        serviceBillRecord.setPaymentStatus(serviceBill.isPaymentStatus());
        serviceBillRecord.setBillDate(serviceBillRecord.getBillDate());
        serviceBillRecord.setRoomId(serviceBillRecord.getRoomId());
        return serviceBillRecord;
    }

    public ServiceBill setServiceBill(ServiceBillRecord serviceBillRecord){
        Room room = new Room(
                serviceBillRecord.getExpand().getRoom().getId(),
                DateUtil.apiDateTimeStringToLocalDateTime(serviceBillRecord.getExpand().getRoom().getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(serviceBillRecord.getExpand().getRoom().getUpdated()),
                serviceBillRecord.getExpand().getRoom().is_deleted(),
                serviceBillRecord.getExpand().getRoom().getRoomNumber(),
                serviceBillRecord.getExpand().getRoom().getRoomType(),
                serviceBillRecord.getExpand().getRoom().getRoomCapacity(),
                serviceBillRecord.getExpand().getRoom().getRoomArea(),
                serviceBillRecord.getExpand().getRoom().getRoomImage(),
                serviceBillRecord.getExpand().getRoom().getDescription(),
                serviceBillRecord.getExpand().getRoom().getDeviceAccountId(),
                serviceBillRecord.getExpand().getRoom().getStatus(),
                serviceBillRecord.getExpand().getRoom().getRemark()
        );
        return new ServiceBill(
                serviceBillRecord.getId(),
                DateUtil.apiDateTimeStringToLocalDateTime(serviceBillRecord.getCreated()),
                DateUtil.apiDateTimeStringToLocalDateTime(serviceBillRecord.getUpdated()),
                serviceBillRecord.is_deleted(),
                serviceBillRecord.isPaymentStatus(),
                DateUtil.apiDateTimeStringToLocalDateTime(serviceBillRecord.getBillDate()),
                serviceBillRecord.getRoomId(),
                room
        );
    }
}
