package com.example.hsb.ui.account.fragment.category_fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Service;
import com.example.hsb.record.CategoryRecord;
import com.example.hsb.record.ServiceCategoryRecord;
import com.example.hsb.record.ServiceRecord;
import com.example.hsb.response.CategoryResponse;
import com.example.hsb.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragmentViewModel extends ViewModel {

    private List<Category> categoryList;
    private MutableLiveData<List<Category>> mListCategoryLiveData;

    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public CategoryFragmentViewModel() {
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        categoryList = new ArrayList<>();
        callApi();
        mListCategoryLiveData.setValue(categoryList);
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    private void callApi() {
        Call<CategoryResponse> call = RetrofitClient.getInstance().getCategoryServiceApi().getRecords();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryRecord> records = response.body().getItems();
                    List<Service> serviceList;
                    for (CategoryRecord record : records) {
                        // Process each record
                        Category category = new Category(record.getId(),
                                record.getName(),
                                record.getImage(),
                                record.getDescription(),
                                record.isDeleted(),
                                DateUtil.stringToLocalDateTime(record.getCreated()),
                                DateUtil.stringToLocalDateTime(record.getUpdated()),
                                new ArrayList<>()
                        );
                        categoryList.add(category);
                        serviceList = category.getServiceList();
                        if (record.getExpand() != null) {
                            for (ServiceCategoryRecord serviceCategoryRecord : record.getExpand().getServiceCategoryRecordList()) {
                                ServiceRecord.ServiceRecordDetail serviceRecordDetail = serviceCategoryRecord.getServiceRecord().getServiceRecordDetail();
                                Service service = new Service(
                                        serviceRecordDetail.getId(),
                                        serviceRecordDetail.getName(),
                                        serviceRecordDetail.getImage(),
                                        serviceRecordDetail.getDescription(),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getStartTime()),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getEndTime()),
                                        serviceRecordDetail.getRemark(),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getCreated()),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getUpdated()),
                                        false
                                );
                                serviceList.add(service);
                            }
                        }

                    }

                    // Update LiveData after data is added
                    mListCategoryLiveData.setValue(categoryList);
                } else {
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }
}
