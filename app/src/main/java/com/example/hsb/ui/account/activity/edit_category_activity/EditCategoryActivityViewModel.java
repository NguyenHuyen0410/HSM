package com.example.hsb.ui.account.activity.edit_category_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Category;
import com.example.hsb.record.CategoryRecord;
import com.example.hsb.response.CategoryResponse;
import com.example.hsb.ui.account.fragment.category_fragment.CategoryFragmentViewModel;
import com.example.hsb.utils.DateUtil;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCategoryActivityViewModel extends ViewModel {
    private MutableLiveData<Category> mCategory = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public MutableLiveData<Category> getCategoryLiveData() {
        return mCategory;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    public void editCategory(Category category) {
        CategoryRecord categoryRecord = new CategoryRecord();
        //account field
        categoryRecord.setId(category.getId());
        categoryRecord.setDescription(category.getDescription());
        categoryRecord.setName(category.getName());

        //system field
        categoryRecord.setCollectionId("xhryqu09dbqpwz6");
        categoryRecord.setCollectionName("category");
        categoryRecord.setDeleted(category.isDeleted());
        categoryRecord.setCreated(DateUtil.localDateTimeToString(category.getCreatedDate()));
        categoryRecord.setUpdated(DateUtil.localDateTimeToString(LocalDateTime.now()));

        Call<CategoryResponse> call = RetrofitClient.getInstance().getCategoryServiceApi().updateRecord(categoryRecord.getId(), categoryRecord);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    CategoryRecord record = categoryResponse.getItems().get(0);
                    Category updatedCategory = new Category(record.getId(),
                            record.getName(),
                            record.getImage(),
                            record.getDescription(),
                            record.isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    mCategory.setValue(updatedCategory);
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

    public void createCategory(Category category) {
        CategoryRecord categoryRecord = new CategoryRecord();
        // Account fields
        categoryRecord.setName(category.getName());
        categoryRecord.setDescription(category.getDescription());
        categoryRecord.setImage(null);//NEED TO CHANGE

        // System fields
        categoryRecord.setCollectionId("xhryqu09dbqpwz6");
        categoryRecord.setCollectionName("category");
        categoryRecord.setDeleted(false);
        categoryRecord.setCreated(LocalDateTime.now().toString());


        Call<CategoryResponse> call = RetrofitClient.getInstance().getCategoryServiceApi().createRecord(categoryRecord);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();
                    CategoryRecord record = categoryResponse.getItems().get(0);
                    Category newCategory = new Category(record.getId(),
                            record.getName(),
                            record.getImage(),
                            record.getDescription(),
                            record.isDeleted(),
                            DateUtil.stringToLocalDateTime(record.getCreated()),
                            DateUtil.stringToLocalDateTime(record.getUpdated()),
                            null
                    );
                    mCategory.setValue(newCategory);
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

    public void deleteCategory(String id) {
        Call<Void> call = RetrofitClient.getInstance().getCategoryServiceApi().deleteRecord(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Notify the fragment of an successful response
                    toastMessageLiveData.setValue("Response successful: " + response.message());
                        // Remove the object



                } else {
                    // Notify the fragment of an unsuccessful response
                    toastMessageLiveData.setValue("Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure case
                toastMessageLiveData.setValue("Request failed: " + t.getMessage());
            }
        });
    }
}