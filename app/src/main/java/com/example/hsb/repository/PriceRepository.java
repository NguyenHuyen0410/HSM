package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Price;
import com.example.hsb.record.PriceRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceRepository {
    private static PriceRepository instance;
    private final MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Price>> mListPriceLiveData = new MutableLiveData<>();

    public static PriceRepository getInstance() {
        if (instance == null) {
            instance = new PriceRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Price>> getPriceList() {
        fetchPriceList();
        return mListPriceLiveData;
    }

    private void fetchPriceList() {
        List<Price> priceList = new ArrayList<>();
        Call<ListResponse<PriceRecord>> call = RetrofitClient.getInstance().getPriceServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<PriceRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<PriceRecord>> call, @NonNull  Response<ListResponse<PriceRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PriceRecord> records = response.body().getItems();
                    for (PriceRecord record : records) {
                        // Process each record
                        Price price = new Price(
                                record.getId(),
                                record.getServiceId(),
                                Double.parseDouble(record.getServicePrice()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getStartTime()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getEndTime()),
                                record.getRemark(),
                                record.isDeleted(),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                                DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated())
                        );
                        priceList.add(price);
                    }

                    // Update LiveData after data is added
                    mListPriceLiveData.setValue(priceList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<ListResponse<PriceRecord>> call, @NonNull  Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }


    public interface EditPriceCallback {
        void onEditSuccess(Price updatedPrice);

        void onEditFailure(String errorMessage);
    }

    public interface CreatePriceCallback {
        void onCreateSuccess(Price newPrice);

        void onCreateFailure(String errorMessage);
    }

    public interface DeletePriceCallback {
        void onDeleteSuccess();

        void onDeleteFailure(String errorMessage);
    }

    public void editPrice(Price price, EditPriceCallback callback) {
        PriceRecord priceRecord = setPriceRecord(price);
        Call<ListResponse<PriceRecord>> call = RetrofitClient.getInstance().getPriceServiceApi().updateRecord(price.getId(), priceRecord);
        call.enqueue(new Callback<ListResponse<PriceRecord>>() {
            @Override
            public void onResponse(@NonNull  Call<ListResponse<PriceRecord>> call, @NonNull Response<ListResponse<PriceRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<PriceRecord> priceResponse = response.body();
                    PriceRecord record = priceResponse.getItems().get(0);
                    Price updatedPrice = new Price(
                            record.getId(),
                            record.getServiceId(),
                            Double.parseDouble(record.getServicePrice()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getStartTime()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getEndTime()),
                            record.getRemark(),
                            record.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated())
                    );
                    callback.onEditSuccess(updatedPrice);
                } else {
                    callback.onEditFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<ListResponse<PriceRecord>> call, @NonNull Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createPrice(Price price, CreatePriceCallback callback) {
        PriceRecord priceRecord = setPriceRecord(price);
        Call<ListResponse<PriceRecord>> call = RetrofitClient.getInstance().getPriceServiceApi().createRecord(priceRecord);
        call.enqueue(new Callback<ListResponse<PriceRecord>>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse<PriceRecord>> call, @NonNull Response<ListResponse<PriceRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<PriceRecord> priceResponse = response.body();
                    PriceRecord record = priceResponse.getItems().get(0);
                    Price newPrice = new Price(
                            record.getId(),
                            record.getServiceId(),
                            Double.parseDouble(record.getServicePrice()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getStartTime()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getEndTime()),
                            record.getRemark(),
                            record.isDeleted(),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getCreated()),
                            DateUtil.apiDateTimeStringToLocalDateTime(record.getUpdated())
                    );
                    callback.onCreateSuccess(newPrice);
                } else {
                    callback.onCreateFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse<PriceRecord>> call,@NonNull  Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deletePrice(String priceId, DeletePriceCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getPriceServiceApi().deleteRecord(priceId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,@NonNull  Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchPriceList();  // Fetch updated price list after deletion
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

    private PriceRecord setPriceRecord(Price price) {
        PriceRecord priceRecord = new PriceRecord();
        if (price.getId() != null) {
            priceRecord.setId(price.getId());
            priceRecord.setCreated(DateUtil.localDateTimeToString(price.getStartDate()));
            priceRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        } else {
            priceRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            priceRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        priceRecord.setServicePrice(price.getPrice().toString());
        priceRecord.setServiceId(price.getServiceId());
        priceRecord.setRemark(price.getRemark());
        priceRecord.setStartTime(price.getStartDate().toString());
        priceRecord.setEndTime(price.getEndDate().toString());
        price.setDeleted(price.isDeleted());
        return priceRecord;
    }
}