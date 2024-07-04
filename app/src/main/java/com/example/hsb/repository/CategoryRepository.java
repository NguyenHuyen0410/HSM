package com.example.hsb.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Category;
import com.example.hsb.entities.Service;
import com.example.hsb.record.CategoryRecord;
import com.example.hsb.record.ServiceCategoryRecord;
import com.example.hsb.record.ServiceRecord;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static CategoryRepository instance;
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mListCategoryLiveData = new MutableLiveData<>();

    public static CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        fetchCategoryList();
        return mListCategoryLiveData;
    }

    private void fetchCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        Call<ListResponse<CategoryRecord>> call = RetrofitClient.getInstance().getCategoryServiceApi().getRecords();
        call.enqueue(new Callback<ListResponse<CategoryRecord>>() {
            @Override
            public void onResponse(Call<ListResponse<CategoryRecord>> call, Response<ListResponse<CategoryRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryRecord> records = response.body().getItems();
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

                        // Initialize serviceList for the category
                        List<Service> serviceList = new ArrayList<>();
                        if (record.getExpand() != null) {
                            for (ServiceCategoryRecord serviceCategoryRecord : record.getExpand().getServiceCategoryRecordList()) {
                                ServiceRecord serviceRecordDetail = serviceCategoryRecord.getExpand().getServiceRecord();
                                Service service = new Service(
                                        serviceRecordDetail.getId(),
                                        serviceRecordDetail.getName(),
                                        serviceRecordDetail.getImage(),
                                        serviceRecordDetail.getDescription(),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getStartTime()),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getEndTime()),
                                        serviceRecordDetail.getRemark(),
                                        false,
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getCreated()),
                                        DateUtil.stringToLocalDateTime(serviceRecordDetail.getUpdated())
                                );
                                serviceList.add(service);
                            }
                        }

                        // Set serviceList to the category
                        category.setServiceList(serviceList);
                    }

                    // Update LiveData after data is added
                    mListCategoryLiveData.setValue(categoryList);
                } else {
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ListResponse<CategoryRecord>> call, Throwable t) {
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }

    public interface EditCategoryCallback {
        void onEditSuccess(Category updatedCategory);

        void onEditFailure(String errorMessage);
    }

    public interface CreateCategoryCallback {
        void onCreateSuccess(Category newCategory);

        void onCreateFailure(String errorMessage);
    }

    public interface DeleteCategoryCallback {
        void onDeleteSuccess();

        void onDeleteFailure(String errorMessage);
    }

    public void editCategory(Category category, EditCategoryCallback callback) {
        CategoryRecord categoryRecord = setCategoryRecord(category);
        Call<ListResponse<CategoryRecord>> call = RetrofitClient.getInstance().getCategoryServiceApi().updateRecord(category.getId(), categoryRecord);
        call.enqueue(new Callback<ListResponse<CategoryRecord>>() {
            @Override
            public void onResponse(Call<ListResponse<CategoryRecord>> call, Response<ListResponse<CategoryRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<CategoryRecord> categoryResponse = response.body();
                    CategoryRecord record = categoryResponse.getItems().get(0);
                    Category updatedCategory = new Category(
                            record.getId(),
                            record.getName(),
                            record.getImage(),
                            record.getDescription(),
                            record.isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    callback.onEditSuccess(updatedCategory);
                } else {
                    callback.onEditFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ListResponse<CategoryRecord>> call, Throwable t) {
                callback.onEditFailure(t.getMessage());
            }
        });
    }

    public void createCategory(Category category, CreateCategoryCallback callback) {
        CategoryRecord categoryRecord = setCategoryRecord(category);
        Call<ListResponse<CategoryRecord>> call = RetrofitClient.getInstance().getCategoryServiceApi().createRecord(categoryRecord);
        call.enqueue(new Callback<ListResponse<CategoryRecord>>() {
            @Override
            public void onResponse(Call<ListResponse<CategoryRecord>> call, Response<ListResponse<CategoryRecord>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ListResponse<CategoryRecord> categoryResponse = response.body();
                    CategoryRecord record = categoryResponse.getItems().get(0);
                    Category newCategory = new Category(
                            record.getId(),
                            record.getName(),
                            record.getImage(),
                            record.getDescription(),
                            record.isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    callback.onCreateSuccess(newCategory);
                } else {
                    callback.onCreateFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ListResponse<CategoryRecord>> call, Throwable t) {
                callback.onCreateFailure(t.getMessage());
            }
        });
    }

    public void deleteCategory(String categoryId, DeleteCategoryCallback callback) {
        Call<Void> call = RetrofitClient.getInstance().getCategoryServiceApi().deleteRecord(categoryId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchCategoryList();  // Fetch updated category list after deletion
                    callback.onDeleteSuccess();
                } else {
                    callback.onDeleteFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onDeleteFailure(t.getMessage());
            }
        });
    }

    private CategoryRecord setCategoryRecord(Category category) {
        CategoryRecord categoryRecord = new CategoryRecord();
        if (category.getId() != null) {
            categoryRecord.setId(category.getId());
            categoryRecord.setCreated(DateUtil.localDateTimeToString(category.getCreatedDate()));
            categoryRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        } else {
            categoryRecord.setCreated(DateUtil.localDateTimeToString(LocalDateTime.now()));
            categoryRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));
        }
        categoryRecord.setName(category.getName());
        categoryRecord.setImage(category.getImage());
        categoryRecord.setDescription(category.getDescription());
        categoryRecord.setDeleted(category.isDeleted());
        return categoryRecord;
    }
}